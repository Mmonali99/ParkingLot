import { useState, useEffect } from 'react';

const useAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token);
  }, []);

  const login = (token) => {
    localStorage.setItem('token', token);
    setIsAuthenticated(true);
    // Navigation handled by calling component
  };

  const logout = () => {
    localStorage.removeItem('token');
    setIsAuthenticated(false);
    // Navigation handled by calling component
  };

  return { isAuthenticated, login, logout };
};

export { useAuth };