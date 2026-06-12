# frontReact

App React para consumir la API de `apipracticas` (CRUD de `Pais`).

Requisitos:
- Node 18+ recomendado

Variables de entorno:
- `VITE_API_URL` -> URL base de la API.
- Por defecto usa `http://localhost:8080/api/paises` para desarrollo local.

Comandos:

```bash
# instalar dependencias
cd frontReact
npm install

# ejecutar en modo desarrollo
npm run dev

# build de producción
npm run build

# vista previa del build
npm run preview
```

Archivo de configuración local:
- Crear `frontReact/.env` con:
  ```env
  VITE_API_URL=http://localhost:8080/api/paises
  ```

Notas:
- La app usa Vite, React, Bootstrap y Sass.
- Asegúrate de que la API (backend) esté accesible desde `VITE_API_URL`.
