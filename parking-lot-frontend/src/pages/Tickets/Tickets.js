import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import { formatDateTime } from '../../utils/formatDate';
import './Tickets.css';

const Tickets = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [ticketData, setTicketData] = useState({ vehicleNumber: '', vehicleType: 'CAR', gateId: '' });

  useEffect(() => {
    request('/api/tickets', 'GET');
  }, [request]);

  const columns = [
    { key: 'ticketNumber', label: 'Ticket Number' },
    { key: 'vehicleNumber', label: 'Vehicle' },
    { key: 'slotNumber', label: 'Spot' },
    { key: 'entryTime', label: 'Entry Time', render: (value) => formatDateTime(value) },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(ticket => ({
    ...ticket,
    actions: <Button onClick={() => handleExit(ticket.ticketNumber)}>Exit</Button>
  })) : [];

  const handleIssue = async () => {
    await request('/api/tickets/issue', 'POST', ticketData);
    setModalOpen(false);
    setTicketData({ vehicleNumber: '', vehicleType: 'CAR', gateId: '' });
    request('/api/tickets', 'GET');
  };

  const handleExit = async (ticketNumber) => {
    await request(`/api/tickets/exit/${ticketNumber}`, 'POST');
    request('/api/tickets', 'GET');
  };

  return (
    <div className="tickets">
      <h2>Tickets</h2>
      <Button onClick={() => setModalOpen(true)}>Issue Ticket</Button>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        <div className="ticket-form">
          <h3>Issue Ticket</h3>
          <input
            type="text"
            placeholder="Vehicle Number"
            value={ticketData.vehicleNumber}
            onChange={(e) => setTicketData({ ...ticketData, vehicleNumber: e.target.value })}
          />
          <select
            value={ticketData.vehicleType}
            onChange={(e) => setTicketData({ ...ticketData, vehicleType: e.target.value })}
          >
            <option value="CAR">Car</option>
            <option value="BIKE">Bike</option>
            <option value="BUS">Bus</option>
          </select>
          <input
            type="number"
            placeholder="Gate ID"
            value={ticketData.gateId}
            onChange={(e) => setTicketData({ ...ticketData, gateId: e.target.value })}
          />
          <Button onClick={handleIssue}>Issue</Button>
        </div>
      </Modal>
    </div>
  );
};

export default Tickets;