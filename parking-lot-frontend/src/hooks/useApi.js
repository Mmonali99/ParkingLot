import { useState, useCallback } from 'react';
import axios from 'axios';

const useApi = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const request = useCallback(async (url, method = 'GET', body = null) => {
    setLoading(true);
    setError(null);
    try {
      const config = {
        method,
        url: `http://localhost:8080${url}`,
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token') || ''}`,
          'Content-Type': 'application/json'
        },
        data: body
      };
      console.log('Making request to:', config.url); // Debug URL
      const response = await axios(config);
      setData(response.data);
      return response.data;
    } catch (err) {
      const errorMessage = err.response
        ? err.response.status === 403
          ? 'Access denied. Please log in again.'
          : `Error: ${err.response.status} - ${err.response.data?.message || 'Something went wrong'}`
        : 'Network error. Please check your connection.';
      setError(errorMessage);
      throw new Error(errorMessage);
    } finally {
      setLoading(false);
    }
  }, []);

  return { data, loading, error, request };
};

export default useApi;