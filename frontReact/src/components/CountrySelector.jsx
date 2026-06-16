import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

const API_KEY = 'Bearer rc_live_3456fff8e4804596b83abcebd6b9cfa1'
const BASE_URL = 'https://api.restcountries.com/countries/v5'
const FIELDS = 'names.common,classification.un_member,currencies,capitals,region,flag.url_png,flag.emoji,population'

async function fetchPagina(offset) {
  const res = await fetch(
    `${BASE_URL}?response_fields=${FIELDS}&limit=100&offset=${offset}`,
    { headers: { Authorization: API_KEY } }
  )
  if (!res.ok) throw new Error(`Error HTTP ${res.status}`)
  const json = await res.json()
  return json?.data?.objects || []
}

function mapearPais(p) {
  const nombre = p['names.common'] || p.names?.common || 'Desconocido'

  const miembroOnu = p['classification.un_member'] ?? p.classification?.un_member ?? false

  let moneda = 'No disponible'
  const currencies = p.currencies
  if (currencies && typeof currencies === 'object' && !Array.isArray(currencies)) {
    const first = Object.values(currencies)[0]
    if (first) {
      moneda = first.name
        ? `${first.name}${first.symbol ? ' (' + first.symbol + ')' : ''}`
        : first.symbol || 'No disponible'
    }
  }

  let capital = 'Sin capital'
  const caps = p.capitals
  if (Array.isArray(caps) && caps.length > 0) {
    capital = caps[0]?.name || caps[0] || 'Sin capital'
  }

  const region = p.region || 'Desconocida'

  const bandera = p['flag.url_png'] || p['flag.emoji'] || p.flag?.url_png || p.flag?.emoji || ''

  const poblacion = p.population ?? 0

  return { nombre, miembroOnu, moneda, capital, region, bandera, poblacion }
}

export default function CountrySelector() {
  const [paises, setPaises] = useState([])
  const [filtrados, setFiltrados] = useState([])
  const [cargando, setCargando] = useState(false)
  const [error, setError] = useState('')
  const navigate = useNavigate()

  useEffect(() => { cargar() }, [])

  const cargar = async () => {
    setCargando(true)
    setError('')
    try {
      // La API tiene 249 países, límite máx 100, 3 páginas
      const [p1, p2, p3] = await Promise.all([
        fetchPagina(0),
        fetchPagina(100),
        fetchPagina(200),
      ])
      const todos = [...p1, ...p2, ...p3].map(mapearPais)
      setPaises(todos)
      setFiltrados(todos)
    } catch (err) {
      console.error(err)
      setError('Error al cargar países. Revisa la consola para más detalles.')
    } finally {
      setCargando(false)
    }
  }

  const buscar = (e) => {
    const t = e.target.value.toLowerCase().trim()
    setFiltrados(t === '' ? paises : paises.filter(p => p.nombre.toLowerCase().includes(t)))
  }

  const seleccionar = (pais) => {
    sessionStorage.setItem('paisSeleccionado', JSON.stringify(pais))
    navigate('/agregar-pais')
  }

  return (
    <div>
      <h2 className="mb-4 text-primary">Buscar países (API pública)</h2>

      {error && <div className="alert alert-danger">{error}</div>}

      <div className="mb-3">
        <div className="input-group">
          <span className="input-group-text bg-primary text-white">
            <i className="bi bi-search"></i>
          </span>
          <input
            className="form-control"
            placeholder="Buscar país por nombre..."
            onInput={buscar}
          />
        </div>
      </div>

      {cargando ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" style={{ width: '3rem', height: '3rem' }} role="status"></div>
          <p className="mt-3 text-muted">Cargando países...</p>
        </div>
      ) : (
        <>
          <p className="text-muted small mb-2">
            {filtrados.length} país{filtrados.length !== 1 ? 'es' : ''} encontrado{filtrados.length !== 1 ? 's' : ''}
          </p>
          <div className="table-responsive">
            <table className="table table-hover table-striped align-middle">
              <thead className="table-dark">
                <tr>
                  <th>Bandera</th>
                  <th>Nombre</th>
                  <th>Capital</th>
                  <th>Región</th>
                  <th>Población</th>
                  <th>Moneda</th>
                  <th>ONU</th>
                  <th>Acción</th>
                </tr>
              </thead>
              <tbody>
                {filtrados.length === 0 ? (
                  <tr>
                    <td colSpan={8} className="text-center text-muted py-4">
                      No se encontraron países
                    </td>
                  </tr>
                ) : (
                  filtrados.map((pais, idx) => (
                    <tr key={idx}>
                      <td className="text-center">
                        {pais.bandera.startsWith('http') ? (
                          <img
                            src={pais.bandera}
                            alt={pais.nombre}
                            style={{ width: 60, height: 40, objectFit: 'cover' }}
                          />
                        ) : (
                          <span style={{ fontSize: '2rem' }}>{pais.bandera}</span>
                        )}
                      </td>
                      <td className="fw-bold">{pais.nombre}</td>
                      <td>{pais.capital}</td>
                      <td><span className="badge bg-info text-dark">{pais.region}</span></td>
                      <td><span className="badge bg-secondary">{pais.poblacion.toLocaleString()}</span></td>
                      <td>{pais.moneda}</td>
                      <td>
                        {pais.miembroOnu
                          ? <span className="badge bg-success">Sí</span>
                          : <span className="badge bg-light text-dark">No</span>
                        }
                      </td>
                      <td>
                        <button
                          className="btn btn-sm btn-outline-primary"
                          onClick={() => seleccionar(pais)}
                        >
                          Seleccionar
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </>
      )}
    </div>
  )
}