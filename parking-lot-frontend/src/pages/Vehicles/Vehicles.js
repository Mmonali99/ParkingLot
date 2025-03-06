import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import './Vehicles.css';

const Vehicles = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [newVehicle, setNewVehicle] = useState({ vehicleNumber: '', vehicleType: 'CAR' });

  useEffect(() => {
    request('/api/vehicles', 'GET');
  }, [request]);

  const columns = [
    { key: 'vehicleNumber', label: 'Vehicle Number' },
    { key: 'vehicleType', label: 'Type' },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(vehicle => ({
    ...vehicle,
    actions: <Button onClick={() => alert('View not implemented')}>View</Button>
  })) : [];

  const handleAdd = async () => {
    await request('/api/vehicles', 'POST', newVehicle);
    setModalOpen(false);
    setNewVehicle({ vehicleNumber: '', vehicleType: 'CAR' });
    request('/api/vehicles', 'GET');
  };

  return (
    <div className="vehicles">
      <h2>Vehicles</h2>
      <Button onClick={() => setModalOpen(true)}>Register Vehicle</Button>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        <div className="vehicle-form">
          <h3>Register Vehicle</h3>
          <input
            type="text"
            placeholder="Vehicle Number"
            value={newVehicle.vehicleNumber}
            onChange={(e) => setNewVehicle({ ...newVehicle, vehicleNumber: e.target.value })}
          />
          <select
            value={newVehicle.vehicleType}
            onChange={(e) => setNewVehicle({ ...newVehicle, vehicleType: e.target.value })}
          >
            <option value="CAR">Car</option>
            <option value="BIKE">Bike</option>
            <option value="BUS">Bus</option>
          </select>
          <Button onClick={handleAdd}>Save</Button>
        </div>
      </Modal>
    </div>
  );
};

export default Vehicles;