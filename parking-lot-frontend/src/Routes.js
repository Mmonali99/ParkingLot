import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import Register from './pages/Register/Register';
import Dashboard from './pages/Dashboard/Dashboard';
import Gates from './pages/Gates/Gates';
import Operators from './pages/Operators/Operators';
import ParkingFloors from './pages/ParkingFloors/ParkingFloors';
import ParkingLots from './pages/ParkingLots/ParkingLots';
import ParkingSpots from './pages/ParkingSpots/ParkingSpots';
import Tickets from './pages/Tickets/Tickets';
import Vehicles from './pages/Vehicles/Vehicles';
import Invoices from './pages/Invoices/Invoices';
import Profile from './pages/Profile/Profile';

const AppRoutes = () => (
  <Routes>
    <Route path="/" element={<Home />} />
    <Route path="/login" element={<Login />} />
    <Route path="/register" element={<Register />} />
    <Route path="/dashboard" element={<Dashboard />} />
    <Route path="/gates" element={<Gates />} />
    <Route path="/operators" element={<Operators />} />
    <Route path="/parking-floors" element={<ParkingFloors />} />
    <Route path="/parking-lots" element={<ParkingLots />} />
    <Route path="/parking-spots" element={<ParkingSpots />} />
    <Route path="/tickets" element={<Tickets />} />
    <Route path="/vehicles" element={<Vehicles />} />
    <Route path="/invoices" element={<Invoices />} />
    <Route path="/profile" element={<Profile />} />
  </Routes>
);

export default AppRoutes;