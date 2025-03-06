export const formatDateTime = (date, options = {}) => 
    new Date(date).toLocaleString(undefined, { 
      dateStyle: 'medium', 
      timeStyle: 'short', 
      ...options 
    });