import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import './Gates.css';

const Gates = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedGate, setSelectedGate] = useState(null);
  const [hasFetched, setHasFetched] = useState(false);

  useEffect(() => {
    if (hasFetched) return;
    console.log('Fetching gates');
    request('/api/gates', 'GET').then(() => setHasFetched(true));
  }, [request, hasFetched]);

  const columns = [
    { key: 'gateNumber', label: 'Gate Number' },
    { key: 'gateType', label: 'Type' },
    { key: 'operatorName', label: 'Operator' },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(gate => ({
    ...gate,
    actions: <Button onClick={() => { setSelectedGate(gate); setModalOpen(true); }}>Edit</Button>
  })) : [];

  const handleUpdate = async () => {
    await request(`/api/gates/${selectedGate.id}`, 'PUT', selectedGate);
    setModalOpen(false);
    setHasFetched(false); // Allow refresh after update
    request('/api/gates', 'GET').then(() => setHasFetched(true));
  };

  return (
    <div className="gates">
      <h2>Gates</h2>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        {selectedGate && (
          <div className="gate-form">
            <h3>Edit Gate</h3>
            <input
              type="number"
              value={selectedGate.gateNumber}
              onChange={(e) => setSelectedGate({ ...selectedGate, gateNumber: parseInt(e.target.value) })}
            />
            <select
              value={selectedGate.gateType}
              onChange={(e) => setSelectedGate({ ...selectedGate, gateType: e.target.value })}
            >
              <option value="ENTRY">Entry</option>
              <option value="EXIT">Exit</option>
            </select>
            <input
              type="text"
              value={selectedGate.operatorName || ''}
              onChange={(e) => setSelectedGate({ ...selectedGate, operatorName: e.target.value })}
            />
            <Button onClick={handleUpdate}>Save</Button>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default Gates;