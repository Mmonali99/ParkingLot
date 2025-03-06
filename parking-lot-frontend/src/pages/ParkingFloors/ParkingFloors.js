import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import './ParkingFloors.css';

const ParkingFloors = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [newFloor, setNewFloor] = useState({ floorNumber: '' });

  useEffect(() => {
    request('/api/parking-floors', 'GET');
  }, [request]);

  const columns = [
    { key: 'floorNumber', label: 'Floor Number' },
    { key: 'spots', label: 'Spots Count', render: (spots) => spots?.length || 0 },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(floor => ({
    ...floor,
    actions: <Button onClick={() => alert('View Spots')}>View Spots</Button>
  })) : [];

  const handleAdd = async () => {
    await request('/api/parking-floors', 'POST', newFloor);
    setModalOpen(false);
    setNewFloor({ floorNumber: '' });
    request('/api/parking-floors', 'GET');
  };

  return (
    <div className="parking-floors">
      <h2>Parking Floors</h2>
      <Button onClick={() => setModalOpen(true)}>Add Floor</Button>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        <div className="floor-form">
          <h3>Add Parking Floor</h3>
          <input
            type="number"
            placeholder="Floor Number"
            value={newFloor.floorNumber}
            onChange={(e) => setNewFloor({ ...newFloor, floorNumber: parseInt(e.target.value) })}
          />
          <Button onClick={handleAdd}>Save</Button>
        </div>
      </Modal>
    </div>
  );
};

export default ParkingFloors;