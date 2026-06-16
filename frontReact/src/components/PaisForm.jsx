import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { getById, createPais, updatePais } from '../services/paisesService'

export default function PaisForm(){
  const { id } = useParams()
  const navigate = useNavigate()
  const [pais, setPais] = useState({ nombre:'', bandera:'', motivoViaje:'', zona:'', presupuesto:0, prioridad:1, notasPersonales:'' })
  const [cargando, setCargando] = useState(false)
  const [allowEditName, setAllowEditName] = useState(true)
  const [mensaje, setMensaje] = useState('')

  useEffect(() => {
    if(id){
      setCargando(true)
      getById(id).then(res => setPais(res.data)).catch(err => setMensaje('Error al cargar país')).finally(()=>setCargando(false))
    }
    else {
      // Si venimos de la selección externa, recuperar país guardado en sessionStorage
      try {
        const sel = sessionStorage.getItem('paisSeleccionado')
        if(sel){
          const p = JSON.parse(sel)
          setPais(prev => ({
            ...prev,
            nombre: p.nombre || prev.nombre,
            bandera: p.banderas || prev.bandera,
            zona: p.region || prev.zona,
            motivoViaje: prev.motivoViaje,
            notasPersonales: prev.notasPersonales
          }))
          // limpiar para evitar reutilizarlo accidentalmente
          sessionStorage.removeItem('paisSeleccionado')
        }
      } catch(e){}
    }
  }, [id])

  // Cuando hay un id (editar existente), por defecto no permitir cambiar el nombre
  useEffect(() => {
    setAllowEditName(!id)
  }, [id])

  const handleChange = (e) => {
    const { name, value } = e.target
    setPais(prev => ({ ...prev, [name]: name === 'presupuesto' || name === 'prioridad' ? Number(value) : value }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    setCargando(true)
    const op = id ? updatePais(id, pais) : createPais(pais)
    op.then(()=>{
      navigate('/')
    }).catch(err => {
      console.error(err)
      setMensaje('Error al guardar')
    }).finally(()=>setCargando(false))
  }

  return (
    <div>
      <h2 className="mb-4 text-success">{id ? 'Editar país' : 'Añadir país'}</h2>

      {mensaje && <div className="alert alert-danger">{mensaje}</div>}

      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Nombre</label>
          {id && (
            <div className="form-text mb-2">Al editar un país guardado, el nombre está bloqueado por defecto.</div>
          )}
          <div className="d-flex gap-2 align-items-center mb-2">
            {id && (
              <div className="form-check">
                <input className="form-check-input" type="checkbox" id="allowEditName" checked={allowEditName} onChange={e=>setAllowEditName(e.target.checked)} />
                <label className="form-check-label" htmlFor="allowEditName">Permitir editar nombre</label>
              </div>
            )}
          </div>
          <input name="nombre" value={pais.nombre} onChange={handleChange} className="form-control" required disabled={id && !allowEditName} />
        </div>

        <div className="mb-3">
          <label className="form-label">URL de la bandera</label>
          <input name="bandera" value={pais.bandera} onChange={handleChange} className="form-control" />
        </div>

        <div className="mb-3">
          <label className="form-label">Motivo</label>
          <input name="motivoViaje" value={pais.motivoViaje} onChange={handleChange} className="form-control" />
        </div>

        <div className="mb-3">
          <label className="form-label">Zona</label>
          <input name="zona" value={pais.zona} onChange={handleChange} className="form-control" />
        </div>

        <div className="row">
          <div className="col-md-6 mb-3">
            <label className="form-label">Presupuesto</label>
            <input name="presupuesto" type="number" value={pais.presupuesto} onChange={handleChange} className="form-control" />
          </div>
          <div className="col-md-6 mb-3">
            <label className="form-label">Prioridad (1-5)</label>
            <input name="prioridad" type="number" min="1" max="5" value={pais.prioridad} onChange={handleChange} className="form-control" />
          </div>
        </div>

        <div className="mb-3">
          <label className="form-label">Notas personales</label>
          <textarea name="notasPersonales" value={pais.notasPersonales} onChange={handleChange} className="form-control" rows="3"></textarea>
        </div>

        <div className="d-flex gap-2">
          <button className="btn btn-success" disabled={cargando}>{cargando ? 'Guardando...' : 'Guardar'}</button>
          <button type="button" className="btn btn-outline-secondary" onClick={()=>navigate(-1)}>Cancelar</button>
        </div>
      </form>
    </div>
  )
}
