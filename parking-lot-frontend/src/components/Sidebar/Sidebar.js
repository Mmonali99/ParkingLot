import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = () => (
  <aside className="sidebar">
    <nav>
      <ul>
        <li><Link to="/dashboard">Dashboard</Link></li>
        <li><Link to="/gates">Gates</Link></li>
        <li><Link to="/operators">Operators</Link></li>
        <li><Link to="/parking-floors">Parking Floors</Link></li>
        <li><Link to="/parking-lots">Parking Lots</Link></li>
        <li><Link to="/parking-spots">Parking Spots</Link></li>
        <li><Link to="/tickets">Tickets</Link></li>
        <li><Link to="/vehicles">Vehicles</Link></li>
        <li><Link to="/invoices">Invoices</Link></li>
        <li><Link to="/profile">Profile</Link></li>
      </ul>
    </nav>
  </aside>
);

export default Sidebar;