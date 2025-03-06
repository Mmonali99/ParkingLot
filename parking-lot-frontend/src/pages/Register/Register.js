import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useApi from '../../hooks/useApi';
import Button from '../../components/Button/Button';
import './Register.css';

const Register = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState(''); // Added password field
  const [role, setRole] = useState('ROLE_OPERATOR');
  const { request, error } = useApi();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await request('/api/auth/register', 'POST', { username, email, password, role });
      alert('Registration successful! Please log in.');
      navigate('/login'); // Redirect to login
    } catch (err) {
      console.error('Registration failed:', err);
    }
  };

  return (
    <div className="register">
      <form className="register-form" onSubmit={handleSubmit}>
        <h2>Register</h2>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <select value={role} onChange={(e) => setRole(e.target.value)}>
          <option value="ROLE_OPERATOR">Operator</option>
          <option value="ROLE_ADMIN">Admin</option>
        </select>
        {error && <p className="error">{error}</p>}
        <Button type="submit">Register</Button>
      </form>
    </div>
  );
};

export default Register;