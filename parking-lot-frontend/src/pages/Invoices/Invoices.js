import React, { useState, useEffect } from 'react';
import useApi from '../../hooks/useApi';
import Table from '../../components/Table/Table';
import Modal from '../../components/Modal/Modal';
import Button from '../../components/Button/Button';
import { formatDateTime } from '../../utils/formatDate';
import './Invoices.css';

const Invoices = () => {
  const { request, data, error } = useApi();
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedInvoice, setSelectedInvoice] = useState(null);

  useEffect(() => {
    request('/api/invoices', 'GET');
  }, [request]);

  const columns = [
    { key: 'number', label: 'Invoice Number' },
    { key: 'exitTime', label: 'Exit Time', render: (value) => formatDateTime(value) },
    { key: 'totalCost', label: 'Total Cost' },
    { key: 'paymentType', label: 'Payment Type' },
    { key: 'actions', label: 'Actions' },
  ];

  const tableData = data ? data.map(invoice => ({
    ...invoice,
    actions: <Button onClick={() => { setSelectedInvoice(invoice); setModalOpen(true); }}>View</Button>
  })) : [];

  return (
    <div className="invoices">
      <h2>Invoices</h2>
      {error && <p className="error">{error}</p>}
      <Table columns={columns} data={tableData} />
      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        {selectedInvoice && (
          <div>
            <h3>Invoice Details</h3>
            <p><strong>Number:</strong> {selectedInvoice.number}</p>
            <p><strong>Exit Time:</strong> {formatDateTime(selectedInvoice.exitTime)}</p>
            <p><strong>Total Cost:</strong> ${selectedInvoice.totalCost}</p>
            <p><strong>Payment Type:</strong> {selectedInvoice.paymentType}</p>
            <p><strong>Ticket:</strong> {selectedInvoice.ticket.ticketNumber}</p>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default Invoices;