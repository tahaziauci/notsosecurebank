import React, { useEffect, useContext } from 'react'
import axios from 'axios';
import AppContext from '../AppContext';

export function MainPage(props) {
    const appValues = useContext(AppContext);

    useEffect(() => {
        axios.defaults.withCredentials = true;
        axios.get('http://localhost:8080/view', {withCredentials: true})
        .then(response => console.log(response))
        .catch(error => {
          console.error('There was an error!', error);
        });
      }, []);

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
            placeholder="Enter amount"
            value={0}
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary">
            Withdraw
          </button>
        </div>
        </div>
    );
}