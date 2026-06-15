import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Header from './components/Header'
import Footer from './components/Footer'
import PaisesList from './components/PaisesList'
import CountrySelector from './components/CountrySelector'
import PaisForm from './components/PaisForm'

export default function App() {
  return (
    <div className="d-flex flex-column min-vh-100">
      <Header />
      <main className="flex-fill container py-4">
        <Routes>
          <Route path="/" element={<PaisesList />} />
          <Route path="/lista-paises" element={<CountrySelector />} />
          <Route path="/agregar-pais" element={<PaisForm />} />
          <Route path="/editar-pais/:id" element={<PaisForm />} />
        </Routes>
      </main>
      <Footer />
    </div>
  )
}
