import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import './ParkingSpots.css';

const ParkingSpots = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedSpot, setSelectedSpot] = useState(null);

  useEffect(() => {
    request('/api/parking-spots', 'GET');
  }, [request]);

  const columns = [
    { key: 'slotNumber', label: 'Slot Number' },
    { key: 'status', label: 'Status' },
    { key: 'vehicleType', label: 'Vehicle Type' },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(spot => ({
    ...spot,
    actions: <Button onClick={() => { setSelectedSpot(spot); setModalOpen(true); }}>Edit</Button>
  })) : [];

  const handleUpdate = async () => {
    await request(`/api/parking-spots/${selectedSpot.id}`, 'PUT', selectedSpot);
    setModalOpen(false);
    request('/api/parking-spots', 'GET');
  };

  return (
    <div className="parking-spots">
      <h2>Parking Spots</h2>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        {selectedSpot && (
          <div className="spot-form">
            <h3>Edit Parking Spot</h3>
            <input
              type="number"
              value={selectedSpot.slotNumber}
              onChange={(e) => setSelectedSpot({ ...selectedSpot, slotNumber: parseInt(e.target.value) })}
            />
            <select
              value={selectedSpot.status}
              onChange={(e) => setSelectedSpot({ ...selectedSpot, status: e.target.value })}
            >
              <option value="AVAILABLE">Available</option>
              <option value="OCCUPIED">Occupied</option>
              <option value="RESERVED">Reserved</option>
            </select>
            <select
              value={selectedSpot.vehicleType}
              onChange={(e) => setSelectedSpot({ ...selectedSpot, vehicleType: e.target.value })}
            >
              <option value="CAR">Car</option>
              <option value="BIKE">Bike</option>
              <option value="BUS">Bus</option>
            </select>
            <Button onClick={handleUpdate}>Save</Button>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default ParkingSpots;