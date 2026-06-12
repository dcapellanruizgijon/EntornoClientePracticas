import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getAll, deletePais } from '../services/paisesService'

export default function PaisesList(){
  const [misPaises, setMisPaises] = useState([])
  const [cargando, setCargando] = useState(true)
  const [mensaje, setMensaje] = useState('')

  const cargar = () => {
    setCargando(true)
    getAll().then(res => {
      setMisPaises(res.data)
    }).catch(err => {
      console.error(err)
      setMensaje('Error al cargar países')
    }).finally(() => setCargando(false))
  }

  useEffect(() => { cargar() }, [])

  const eliminar = (id) => {
    if(!confirm('¿Eliminar país?')) return
    deletePais(id).then(()=>{
      setMensaje('País eliminado')
      cargar()
    }).catch(err => {
      console.error(err)
      setMensaje('Error al eliminar')
    })
  }

  return (
    <div>
      <h2 className="mb-4 text-success">Mis países para visitar</h2>

      {mensaje && <div className="alert alert-info">{mensaje}</div>}

      {cargando ? (
        <div className="text-center py-5">
          <div className="spinner-border text-success" style={{width:'3rem',height:'3rem'}} role="status"></div>
          <p className="mt-3 text-muted">Cargando tus países...</p>
        </div>
      ) : (
        <div className="row g-4">
          {misPaises.length === 0 && (
            <div className="text-center py-5">
              <h5 className="text-muted">No tienes países en tu lista</h5>
              <Link className="btn btn-success mt-3" to="/agregar-pais">Añadir país</Link>
            </div>
          )}

          {misPaises.map(pais => (
            <div key={pais.id} className="col-12 col-md-6 col-lg-4">
              <div className="card h-100 shadow-sm">
                <div className="card-header bg-success text-white">
                  <div className="d-flex align-items-center">
                    <img src={pais.bandera} alt={`Bandera de ${pais.nombre}`} style={{width:50,height:35,objectFit:'cover',border:'2px solid white'}} className="rounded me-3" />
                    <h5 className="mb-0">{pais.nombre}</h5>
                  </div>
                </div>
                <div className="card-body">
                  <p><strong>Motivo:</strong> {pais.motivoViaje}</p>
                  <p><strong>Zona:</strong> <span className="badge bg-info text-dark">{pais.zona}</span></p>
                  <p><strong>Presupuesto:</strong> <span className="fw-bold text-success">{pais.presupuesto}€</span></p>
                </div>
                <div className="card-footer bg-transparent border-top-0">
                  <div className="d-flex gap-2">
                    <Link className="btn btn-outline-primary flex-grow-1" to={`/editar-pais/${pais.id}`}>Editar</Link>
                    <button className="btn btn-outline-danger flex-grow-1" onClick={()=>eliminar(pais.id)}>Eliminar</button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
