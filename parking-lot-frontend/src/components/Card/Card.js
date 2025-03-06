import React from 'react';
import './Card.css';

const Card = ({ title, children }) => (
  <div className="card">
    {title && <h3>{title}</h3>}
    <div className="card-content">{children}</div>
  </div>
);

export default Card;