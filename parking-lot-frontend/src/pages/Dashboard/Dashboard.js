import React, { useEffect, useState } from 'react';
import useApi from '../../hooks/useApi';
import Card from '../../components/Card/Card';
import './Dashboard.css';

const Dashboard = () => {
  const { request, error, loading } = useApi();
  const [stats, setStats] = useState({
    totalSpots: 50,
    occupiedSpots: 0,
    activeTickets: 0,
    revenueToday: '$0',
  });
  const [hasFetched, setHasFetched] = useState(false);

  useEffect(() => {
    if (hasFetched) return;
    console.log('useEffect triggered');
    const fetchDashboardData = async () => {
      try {
        console.log('Requesting /api/tickets');
        const response = await request('/api/tickets', 'GET');
        console.log('Raw response from /api/tickets:', JSON.stringify(response, null, 2));

        if (!Array.isArray(response)) {
          throw new Error('Expected an array, got: ' + JSON.stringify(response));
        }

        let activeTickets, occupiedSpots;
        if (response.length === 0) {
          activeTickets = 0;
          occupiedSpots = 0;
        } else if (response[0]?.ticketNumber) { // TicketDTO
          activeTickets = response.length;
          occupiedSpots = response.filter(ticket => typeof ticket.slotNumber === 'number' && ticket.slotNumber > 0).length;
        } else if (response[0]?.id && response[0]?.status) { // ParkingSpotDTO
          activeTickets = response.filter(spot => spot.status === 'OCCUPIED').length;
          occupiedSpots = activeTickets;
        } else {
          throw new Error('Unknown response format: ' + JSON.stringify(response[0]));
        }

        console.log('Calculated stats:', { activeTickets, occupiedSpots });
        setStats(prev => ({
          ...prev,
          activeTickets: Number(activeTickets), // Ensure number
          occupiedSpots: Number(occupiedSpots), // Ensure number
        }));
        setHasFetched(true);
      } catch (err) {
        console.error('Failed to fetch dashboard data:', err);
        setStats(prev => ({ ...prev, activeTickets: 0, occupiedSpots: 0 })); // Fallback
      }
    };
    fetchDashboardData();
  }, [request, hasFetched]);

  console.log('Dashboard rendered with stats:', stats);
  return (
    <div className="dashboard">
      <h2>Dashboard</h2>
      {loading && <p>Loading...</p>}
      {error && <p className="error">{error}</p>}
      <div className="dashboard-grid">
        <Card title="Total Spots">{stats.totalSpots}</Card>
        <Card title="Occupied Spots">{stats.occupiedSpots}</Card>
        <Card title="Active Tickets">{stats.activeTickets}</Card>
        <Card title="Revenue Today">{stats.revenueToday}</Card>
      </div>
    </div>
  );
};

export default Dashboard;