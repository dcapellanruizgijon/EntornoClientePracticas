import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

export default function CountrySelector(){
  const [paises, setPaises] = useState([])
  const [filtrados, setFiltrados] = useState([])
  const [cargando, setCargando] = useState(false)
  const [error, setError] = useState('')
  const navigate = useNavigate()
  const API_KEY = process.env.REACT_APP_RESTCOUNTRIES_API_KEY;

  useEffect(()=>{ cargar() }, [])

    

  const cargar = () => {
  setCargando(true)
  
  // Preferir el proxy del backend para evitar bloqueos CORS.
  // Construimos la URL del backend a partir de Vite env si está disponible.
  const backendApi = (import.meta.env && import.meta.env.VITE_API_URL)
    ? import.meta.env.VITE_API_URL.replace(/\/api\/paises\/?$/, '')
    : ''
  const url = backendApi
    ? `${backendApi}/api/external/paises`
    : 'https://restcountries.com/v5.1/all?fields=name,unMember,currencies,capital,region,flags,population'

  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Error HTTP: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      // Normalizar respuesta: algunos proxys o errores devuelven objetos en vez de arrays
      let list = data
      if (!Array.isArray(list)) {
        // si viene como string JSON
        if (typeof list === 'string') {
          try { list = JSON.parse(list) } catch(e) { /* ignore */ }
        }
        // si viene envuelto en { body: [...] } o { data: [...] }
        if (!Array.isArray(list)) {
          if (list && Array.isArray(list.body)) list = list.body
          else if (list && Array.isArray(list.data)) list = list.data
          else if (list && typeof list === 'object') {
            // buscar la primera propiedad que sea array
            for (const k of Object.keys(list)) {
              if (Array.isArray(list[k])) { list = list[k]; break }
            }
          }
        }
      }

      if (!Array.isArray(list)) {
        console.error('Respuesta inesperada del endpoint de países:', data)
        throw new Error('Respuesta inesperada del servicio de países')
      }

      const mapped = list.map(p => ({
        nombre: p.name?.common || (p.name && (p.name.common || p.name)) || 'Desconocido',
        miembroOnu: p.unMember || false,
        moneda: p.currencies ? Object.values(p.currencies)[0]?.name + ' (' + Object.values(p.currencies)[0]?.symbol + ')' : 'No disponible',
        capital: Array.isArray(p.capital) ? p.capital[0] : (p.capital || 'No tiene capital'),
        region: p.region || 'Desconocida',
        banderas: (p.flags && (p.flags.png || p.flags.svg)) || '',
        poblacion: p.population || 0
      }))
      setPaises(mapped)
      setFiltrados(mapped)
    })
    .catch(err => { 
      console.error(err); 
      setError('Error al cargar países') 
    })
    .finally(() => setCargando(false))
}

  const buscar = (e) => {
    const t = e.target.value.toLowerCase().trim()
    if(t === '') return setFiltrados(paises)
    setFiltrados(paises.filter(p => p.nombre.toLowerCase().includes(t)))
  }

  const seleccionar = (pais) => {
    // Guardar en sessionStorage y navegar al formulario de añadir
    sessionStorage.setItem('paisSeleccionado', JSON.stringify(pais))
    navigate('/agregar-pais')
  }

  return (
    <div>
      <h2 className="mb-4 text-primary">Buscar países (API pública)</h2>

      {error && <div className="alert alert-danger">{error}</div>}

      <div className="mb-3">
        <div className="input-group">
          <span className="input-group-text bg-primary text-white"><i className="bi bi-search"></i></span>
          <input className="form-control" placeholder="Buscar país por nombre..." onInput={buscar} />
        </div>
      </div>

      {cargando ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" style={{width:'3rem',height:'3rem'}} role="status"></div>
          <p className="mt-3 text-muted">Cargando países...</p>
        </div>
      ) : (
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
                <th>Acción</th>
              </tr>
            </thead>
            <tbody>
              {filtrados.map((pais, idx) => (
                <tr key={idx}>
                  <td className="text-center"><img src={pais.banderas} alt={pais.nombre} style={{width:60,height:40,objectFit:'cover'}} /></td>
                  <td className="fw-bold">{pais.nombre}</td>
                  <td>{pais.capital}</td>
                  <td><span className="badge bg-info text-dark">{pais.region}</span></td>
                  <td><span className="badge bg-secondary">{pais.poblacion.toLocaleString()}</span></td>
                  <td>{pais.moneda}</td>
                  <td>
                    <button className="btn btn-sm btn-outline-primary" onClick={() => seleccionar(pais)}>Seleccionar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  )
}
