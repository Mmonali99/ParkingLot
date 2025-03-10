import React from 'react';
import './Table.css';

const Table = ({ columns, data }) => (
  <table className="table">
    <thead>
      <tr>
        {columns.map(col => <th key={col.key}>{col.label}</th>)}
      </tr>
    </thead>
    <tbody>
      {data.map((row, index) => (
        <tr key={index}>
          {columns.map(col => <td key={col.key}>{row[col.key]}</td>)}
        </tr>
      ))}
    </tbody>
  </table>
);

export default Table;