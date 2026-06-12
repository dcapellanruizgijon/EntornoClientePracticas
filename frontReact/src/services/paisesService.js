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

// ✅ CORREGIDO: Quitar el slash '/' - ahora usamos string vacío o solo el ID
export const getAll = () => api.get('')      // ← Sin slash, solo string vacío
export const getById = (id) => api.get(`/${id}`)  // ← Esto está bien, añade el ID
export const createPais = (pais) => api.post('', pais)  // ← Sin slash
export const updatePais = (id, pais) => api.put(`/${id}`, pais)  // ← Esto está bien
export const deletePais = (id) => api.delete(`/${id}`)  // ← Esto está bien

export default api