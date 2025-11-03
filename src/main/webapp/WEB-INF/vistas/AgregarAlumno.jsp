<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Alumno</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f4f4f4;
            }
            h1 {
                color: #333;
            }
            .form-container {
                max-width: 500px;
                margin: 20px auto;
                background-color: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .form-group {
                margin-bottom: 15px;
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: #555;
            }
            input[type="text"],
            input[type="number"],
            select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 14px;
            }
            select {
                cursor: pointer;
            }
            .btn-container {
                margin-top: 20px;
                display: flex;
                gap: 10px;
            }
            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                text-decoration: none;
                display: inline-block;
                text-align: center;
            }
            .btn-primary {
                background-color: #4CAF50;
                color: white;
            }
            .btn-primary:hover {
                background-color: #45a049;
            }
            .btn-secondary {
                background-color: #008CBA;
                color: white;
            }
            .btn-secondary:hover {
                background-color: #007B9A;
            }
            .required {
                color: red;
            }
        </style>
    </head>
    <body>
        <h1>Agregar Nuevo Alumno</h1>
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/GuardarAlumno" method="POST">
                
                <div class="form-group">
                    <label for="nombre">Nombre <span class="required">*</span></label>
                    <input type="text" id="nombre" name="nombre" required />
                </div>
                
                <div class="form-group">
                    <label for="apellido">Apellido <span class="required">*</span></label>
                    <input type="text" id="apellido" name="apellido" required />
                </div>
                
                <div class="form-group">
                    <label for="dni">DNI <span class="required">*</span></label>
                    <input type="number" id="dni" name="dni" required />
                </div>
                
                <div class="form-group">
                    <label for="carrera">Carrera <span class="required">*</span></label>
                    <select id="carrera" name="carreraId" required>
                        <option value="">-- Seleccione una carrera --</option>
                        <c:forEach var="carrera" items="${carreras}">
                            <option value="${carrera.idcarrera}" 
                                    ${carrera.idcarrera == carreraPreseleccionada ? 'selected' : ''}>
                                ${carrera.nombre}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="btn-container">
                    <button type="submit" class="btn btn-primary">Guardar Alumno</button>
                    <a href="${pageContext.request.contextPath}/faces/index.xhtml" class="btn btn-secondary">Cancelar</a>
                </div>
                
            </form>
        </div>
        
    </body>
</html>