# 🎓 Sistema de Gestión Académica - AppAlumno

Sistema web para gestión académica desarrollado con Jakarta EE.

## 🛠️ Tecnologías

- Jakarta EE 10
- JPA (Jakarta Persistence)
- MySQL 8.0
- JSF + PrimeFaces
- GlassFish 7.0.15

## 📋 Funcionalidades Implementadas

### Ejercicio 1: Menú Dinámico de Facultades
- Carga automática desde base de datos
- Servlet listener para inicialización

### Ejercicio 2: Listado de Carreras por Facultad
- Visualización agrupada por facultad
- Navegación mediante servlets

### Ejercicio 3: Alta de Alumnos
- Formulario JSP con validación
- Selección de carrera mediante combo

### Ejercicio 4: Gestión de Docentes
- Entidad Docente con relación Many-to-Many
- Asignación de docentes a materias

### Ejercicio 6: Consultas JPQL
1. **Exámenes de Julio 2025:** Mesas de examen con datos de alumnos y notas
2. **Alumnos sin Exámenes:** Listado de alumnos que no rindieron en 2025
3. **Docentes con Alta Carga:** Docentes que dictan más de 2 materias

## 🗄️ Modelo de Datos

- Facultad
- Carrera (FK: facultad)
- Materia
- Alumno (FK: carrera)
- Docente
- Materia_Docente (relación N:M)
- Examen (clave compuesta)

## 🚀 Instalación

1. Importar el proyecto en NetBeans
2. Configurar conexión MySQL en `persistence.xml`
3. Ejecutar scripts SQL
4. Deploy en GlassFish 7.0.15

## 👤 Autor

**Marcela Mancini**  
GitHub: [@ManciniMarcela22](https://github.com/ManciniMarcela22)  
Noviembre 2025
