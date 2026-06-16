import axios from 'axios'

// Por defecto usar la API local para desarrollo; puede ser sobrescrito con VITE_API_URL
// Si la app se sirve desde otra máquina/WSL, usamos window.location.hostname para apuntar al mismo host
const DEFAULT_HOST = (typeof window !== 'undefined' && window.location && window.location.hostname) ? window.location.hostname : 'localhost'
const BASE = import.meta.env.VITE_API_URL || `http://${DEFAULT_HOST}:8080/api/paises`

// Log útil en desarrollo para comprobar la URL base que usará el navegador
if (typeof window !== 'undefined') {
  // eslint-disable-next-line no-console
  console.log('[frontReact] API BASE:', BASE, 'window.location.hostname=', window.location.hostname)
}

const api = axios.create({
  baseURL: BASE,
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' }
})


export const getAll = () => api.get('')      
export const getById = (id) => api.get(`/${id}`)
export const createPais = (pais) => api.post('', pais) 
export const updatePais = (id, pais) => api.put(`/${id}`, pais) 
export const deletePais = (id) => api.delete(`/${id}`)  

export default api