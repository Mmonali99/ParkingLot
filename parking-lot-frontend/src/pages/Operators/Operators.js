import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import './Operators.css';

const Operators = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [newOperator, setNewOperator] = useState({ empId: '', name: '' });

  useEffect(() => {
    request('/api/operators', 'GET');
  }, [request]);

  const columns = [
    { key: 'empId', label: 'Employee ID' },
    { key: 'name', label: 'Name' },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(operator => ({
    ...operator,
    actions: <Button onClick={() => alert('Edit not implemented')}>Edit</Button>
  })) : [];

  const handleAdd = async () => {
    await request('/api/operators', 'POST', newOperator);
    setModalOpen(false);
    setNewOperator({ empId: '', name: '' });
    request('/api/operators', 'GET');
  };

  return (
    <div className="operators">
      <h2>Operators</h2>
      <Button onClick={() => setModalOpen(true)}>Add Operator</Button>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        <div className="operator-form">
          <h3>Add Operator</h3>
          <input
            type="text"
            placeholder="Employee ID"
            value={newOperator.empId}
            onChange={(e) => setNewOperator({ ...newOperator, empId: e.target.value })}
          />
          <input
            type="text"
            placeholder="Name"
            value={newOperator.name}
            onChange={(e) => setNewOperator({ ...newOperator, name: e.target.value })}
          />
          <Button onClick={handleAdd}>Save</Button>
        </div>
      </Modal>
    </div>
  );
};

export default Operators;