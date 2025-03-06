import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import Sidebar from './components/Sidebar/Sidebar';
import Routes from './Routes';
import { useAuth } from './hooks/useAuth';
import './styles/global.css';

const App = () => {
  const { isAuthenticated } = useAuth();
  return (
    <Router>
      <div className="app-container">
        <Header />
        <div className="main-content">
          {isAuthenticated && <Sidebar />}
          <div className={isAuthenticated ? 'content-with-sidebar' : 'content-full'}>
            <Routes />
          </div>
        </div>
        <Footer />
      </div>
    </Router>
  );
};

export default App;