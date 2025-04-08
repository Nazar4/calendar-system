export const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  if (isNaN(date)) {
    throw new Error('Invalid Date string');
  }

  const day = String(date.getDate()).padStart(2, '0');
  const month = date.toLocaleString('en-US', { month: 'short' });
  const year = date.getFullYear();

  return `${day} ${month} ${year}`;
};
