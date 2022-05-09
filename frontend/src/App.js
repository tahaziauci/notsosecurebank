import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import { useState } from 'react';
import AppContext from './components/AppContext';
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { Login } from './components/Login/Login';
import { Registration } from './components/Registration/Registration'
import { MainPage } from './components/MainPage/MainPage';

function App() {

  const [username, setUsername] = useState(localStorage.getItem("username"));
  const [balance, setBalance] = useState('');

  const updateUsername = (username) => {
    localStorage.setItem("username", username)
    setUsername(username);
  }

  const updateBalance = (balance) => {
    setBalance(balance);
  }

  const userValues = {
    username,
    balance,
    updateUsername,
    updateBalance
  }

  return (
    <AppContext.Provider value={userValues}>
      <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <Link className="navbar-brand" to={username ? '/welcome' : '/login'}>
              Bank
            </Link>
            {!username && 
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link className="nav-link" to={'/login'}>
                  Login
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to={'/registration'}>
                  Sign up
                </Link>
              </li>
            </ul>
          </div>
            }
            {username && 
              <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link className="nav-link" onClick={() => updateUsername("")} to={'/login'}>
                    Sign out
                  </Link>
                </li>
              </ul>
            </div>
            }
          </div>
        </nav>
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Routes>
              <Route exact path="/" element={username ? <MainPage /> : <Login />} />
              <Route path="/welcome" element={username ? <MainPage /> : <Login />}/>
              <Route path="/login" element={<Login />} />
              <Route path="/registration" element={<Registration />} />
            </Routes>
          </div>
        </div>
      </div>
    </Router>
    </AppContext.Provider>

  )
}

export default App