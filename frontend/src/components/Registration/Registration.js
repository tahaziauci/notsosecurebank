import React, { useState, useContext } from 'react'
import axios from 'axios';
import AppContext from '../AppContext';
import { useNavigate } from 'react-router-dom';

export function Registration(props) {
    const [values, setValues] = useState({
      username: '',
      password: ''
    });

    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();
    const appValues = useContext(AppContext);

    const handleUsernameChange = (event) => {
      event.persist();
      setErrorMessage('');

      setValues((values) => ({
        ...values,
        username: event.target.value,
      }));
    };

    const handlePasswordChange = (event) => {
      event.persist();
      setErrorMessage('');

      setValues((values) => ({
        ...values,
        password: event.target.value,
      }));
    };

    const handleSubmit = (event) => {
      event.preventDefault();
      const {username, password} = values;


      axios.defaults.withCredentials = true;
      axios.post('http://localhost:8080/register', {username, password, balance: 5000})
        .then(() => navigate("/login"))
        .catch(error => {
          setErrorMessage("Error: " + error.response.data.message);
        });
    };

    return (
      <form onSubmit={handleSubmit}>
        <h3>Sign Up</h3>
        <div className="mb-3">
          <label>Username</label>
          <input
            type="username"
            value={values.username}
            className="form-control"
            placeholder="Enter username"
            onChange={handleUsernameChange}
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            value={values.password}
            className="form-control"
            placeholder="Enter password"
            onChange={handlePasswordChange}
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary">
            Sign Up
          </button>
        </div>
        {errorMessage && <div className="mb-3 error-message">{errorMessage}</div>}
        <p className="forgot-password text-right">
          Already registered <a href="/login">sign in?</a>
        </p>
      </form>
    )
}