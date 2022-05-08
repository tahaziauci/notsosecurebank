import React, { useState } from 'react'
import axios from 'axios';

export function Registration(props) {
    const [values, setValues] = useState({
      username: '',
      password: ''
    });

    const handleUsernameChange = (event) => {
      event.persist();

      setValues((values) => ({
        ...values,
        username: event.target.value,
      }));
    };

    const handlePasswordChange = (event) => {
      event.persist();

      setValues((values) => ({
        ...values,
        password: event.target.value,
      }));
    };

    const handleSubmit = (event) => {
      event.preventDefault();
      const {username, password} = values;


      axios.post('http://localhost:8080/register', {username, password, balance: 5000})
        .then(response => console.log(response))
        .catch(error => {
            console.error('There was an error!', error);
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
        <p className="forgot-password text-right">
          Already registered <a href="/login">sign in?</a>
        </p>
      </form>
    )
}