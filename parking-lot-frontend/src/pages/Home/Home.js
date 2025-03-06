import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../../components/Button/Button';
import parkingBg from '../../assets/images/parking_bg.jpg';
import './Home.css';

const Home = () => (
  <div className="home">
    <div className="hero" style={{ backgroundImage: `url(${parkingBg})` }}>
      <h1>Welcome to Parking Lot Management</h1>
      <p>Efficiently manage your parking operations with ease.</p>
      <div className="cta-buttons">
        <Link to="/login"><Button>Login</Button></Link>
        <Link to="/register"><Button variant="secondary">Register</Button></Link>
      </div>
    </div>
  </div>
);

export default Home;