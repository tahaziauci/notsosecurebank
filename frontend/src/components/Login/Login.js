import React, { Component } from "react";
import axios from 'axios';
import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AppContext from '../AppContext';

export function Login(props) {
  const [values, setValues] = useState({
    username: '',
    password: ''
  });
  const [errorMessage, setErrorMessage] = useState('');

  const appValues = useContext(AppContext);
  const navigate = useNavigate();

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
    axios.post('http://localhost:8080/signin', {username, password})
      .then(response => {
        appValues.updateUsername(username);
        console.log(response.data)
        navigate("/welcome")
      })
      .catch(error => {
        setErrorMessage("Error: " + error.response.data.message);
      });

  };

    return (
      <form onSubmit={handleSubmit}>
        <h3>Sign In</h3>
        <div className="mb-3">
          <label>Username</label>
          <input
            type="username"
            className="form-control"
            placeholder="Enter username"
            value={values.username}
            onChange={handleUsernameChange}
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
            value={values.password}
            onChange={handlePasswordChange}
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </div>
        {errorMessage && <div className="mb-3 error-message">{errorMessage}</div>}
        <p className="forgot-password text-right">
          New user? Go to <a href="/registration">registration</a>
        </p>
      </form>
    )
}
