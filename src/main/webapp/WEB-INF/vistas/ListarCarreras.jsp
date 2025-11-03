<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carreras de ${facultad.nombre}</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f4f4f4;
            }
            h1 {
                color: #333;
            }
            table {
                width: 80%;
                margin: 20px auto;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #4CAF50;
                color: white;
            }
            tr:hover {
                background-color: #f5f5f5;
            }
            .back-link {
                display: inline-block;
                margin: 20px;
                padding: 10px 20px;
                background-color: #008CBA;
                color: white;
                text-decoration: none;
                border-radius: 4px;
            }
            .back-link:hover {
                background-color: #007B9A;
            }
            .btn-agregar {
                display: inline-block;
                padding: 6px 12px;
                background-color: #4CAF50;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                font-size: 12px;
            }
            .btn-agregar:hover {
                background-color: #45a049;
            }
            .no-data {
                text-align: center;
                color: #666;
                padding: 20px;
            }
        </style>
    </head>
    <body>
        <a href="${pageContext.request.contextPath}/faces/index.xhtml" class="back-link">← Volver al Menú</a>
        
        <h1>Carreras de la Facultad: ${facultad.nombre}</h1>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre de la Carrera</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="carrera" items="${facultad.carreraCollection}">
                    <tr>
                        <td>${carrera.idcarrera}</td>
                        <td>${carrera.nombre}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/AgregarAlumno?carreraId=${carrera.idcarrera}" 
                               class="btn-agregar">
                                ➕ Agregar Alumno
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <c:if test="${empty facultad.carreraCollection}">
            <p class="no-data">No hay carreras registradas para esta facultad.</p>
        </c:if>
    </body>
</html>