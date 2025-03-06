import React from 'react';
import { Link, useNavigate } from 'react-router-dom'; // Add useNavigate
import { useAuth } from '../../hooks/useAuth';
import Button from '../Button/Button';
import './Header.css';

const Header = () => {
  const { isAuthenticated, logout } = useAuth();
  const navigate = useNavigate(); // Add this

  const handleLogout = () => {
    logout();
    navigate('/login'); // Redirect to login after logout
  };

  return (
    <header className="header">
      <Link to="/" className="logo">
        <img src="/assets/images/logo.png" alt="Parking Lot" />
      </Link>
      <nav>
        {isAuthenticated ? (
          <>
            <Link to="/dashboard">Dashboard</Link>
            <Button variant="secondary" onClick={handleLogout}>Logout</Button>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </nav>
    </header>
  );
};

export default Header;