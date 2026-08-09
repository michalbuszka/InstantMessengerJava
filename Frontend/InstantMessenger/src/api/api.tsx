import axios from 'axios';

// Czysty klient HTTP wskazujący na Twój Backend
const api = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true, // Zostawiamy, jeśli serwer ustawia np. ciasteczka sesyjne
});

// Tymczasowe atrapy (mocki) funkcji, aby nie rozbić importów w innych plikach
export const getUserId = () => '';
export const setAccessToken = (token: string) => {};
export const getAccessToken = async () => null;
export const checkAuthWithBackend = async () => true;

export default api;