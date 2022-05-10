import React, { useState, useEffect, useContext } from 'react'
import axios from 'axios';
import AppContext from '../AppContext';

export function MainPage(props) {
    const [withdrawAmount, setWithdrawAmount] = useState(0);
    const [depositAmount, setDepositAmount] = useState(0);

    const appValues = useContext(AppContext);

    const handleWithdrawAmountChange = (event) => {
        event.persist();
        const re = /^[0-9]+\.?[0-9]*$/;

        if (event.target.value === '' || re.test(event.target.value)) {
            setWithdrawAmount(event.target.value);
        }
    }

    const handleDepositAmountChange = (event) => {
        event.persist();

        const re = /^[0-9]+\.?[0-9]*$/;

        if (event.target.value === '' || re.test(event.target.value)) {
            setDepositAmount(event.target.value);
        }
    }

    useEffect(() => {
        axios.defaults.withCredentials = true;
        axios.get('http://localhost:8080/view')
        .then(response => appValues.updateBalance(response.data.balance))
        .catch(error => {
          console.error('There was an error!', error);
        });
      }, []);

    const sendWithdrawRequest = () => {
        if (withdrawAmount > appValues.balance) {
            console.error('Not enough cash')
        } else {
            const username = appValues.username;
            const amount = parseFloat(withdrawAmount);
            axios.defaults.withCredentials = true;
            axios.post('http://localhost:8080/withdraw', {username, amount})
            .then(response => {
                setWithdrawAmount(0)
                appValues.updateBalance(response.data.balance)
            })
            .catch(error => {
                console.error('There was an error!', error);
            })
        }
    }

    const sendDepositRequest = () => {
        const username = appValues.username;
        const amount = parseFloat(depositAmount);
        axios.defaults.withCredentials = true;
        axios.post('http://localhost:8080/deposit', {username, amount})
        .then(response => {
            setDepositAmount(0)
            appValues.updateBalance(response.data.balance)
        })
        .catch(error => {
            console.error('There was an error!', error);
        })
    }

    return(
        <div>
        <h3>Welcome, {appValues.username}!</h3>
        <div className="mb-3">
          <label>Balance: {appValues.balance}</label>
        </div>
        <div className="mb-3">
          <label>Withdraw amount</label>
          <input
            type="amount"
            className="form-control"
            pattern="[0-9]*"
            placeholder="Enter amount"
            value={withdrawAmount}
            onChange={handleWithdrawAmountChange}
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary" onClick={sendWithdrawRequest}>
            Withdraw
          </button>
        </div>
        <div className="mb-3"></div>
        <div className="mb-3">
          <label>Deposit amount</label>
          <input
            type="amount"
            className="form-control"
            pattern="[0-9]*"
            placeholder="Enter amount"
            value={depositAmount}
            onChange={handleDepositAmountChange}
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary" onClick={sendDepositRequest}>
            Deposit
          </button>
        </div>
        </div>
    );
}