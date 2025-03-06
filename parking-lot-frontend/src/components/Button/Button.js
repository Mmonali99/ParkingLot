import React from 'react';
import './Button.css';

const Button = ({ variant = 'primary', children, ...props }) => (
  <button className={`btn btn-${variant}`} {...props}>
    {children}
  </button>
);

export default Button;