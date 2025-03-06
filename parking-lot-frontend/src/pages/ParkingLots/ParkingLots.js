import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import './ParkingLots.css';

const ParkingLots = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [newLot, setNewLot] = useState({ name: '', address: '' });

  useEffect(() => {
    request('/api/parking-lots', 'GET');
  }, [request]);

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'address', label: 'Address' },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(lot => ({
    ...lot,
    actions: <Button onClick={() => alert('View Details')}>View</Button>
  })) : [];

  const handleAdd = async () => {
    await request('/api/parking-lots', 'POST', newLot);
    setModalOpen(false);
    setNewLot({ name: '', address: '' });
    request('/api/parking-lots', 'GET');
  };

  return (
    <div className="parking-lots">
      <h2>Parking Lots</h2>
      <Button onClick={() => setModalOpen(true)}>Add Parking Lot</Button>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        <div className="lot-form">
          <h3>Add Parking Lot</h3>
          <input
            type="text"
            placeholder="Name"
            value={newLot.name}
            onChange={(e) => setNewLot({ ...newLot, name: e.target.value })}
          />
          <input
            type="text"
            placeholder="Address"
            value={newLot.address}
            onChange={(e) => setNewLot({ ...newLot, address: e.target.value })}
          />
          <Button onClick={handleAdd}>Save</Button>
        </div>
      </Modal>
    </div>
  );
};

export default ParkingLots;