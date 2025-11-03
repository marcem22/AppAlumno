<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alumnos Sin Exámenes - 2025</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f5f5f5;
            }
            h1 {
                color: #333;
                border-bottom: 3px solid #ff9800;
                padding-bottom: 10px;
            }
            .info {
                background-color: #fff3cd;
                border-left: 4px solid #ff9800;
                padding: 15px;
                margin: 20px 0;
                border-radius: 4px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                margin-top: 20px;
            }
            th {
                background-color: #ff9800;
                color: white;
                padding: 12px;
                text-align: left;
            }
            td {
                padding: 10px;
                border-bottom: 1px solid #ddd;
            }
            tr:hover {
                background-color: #fff3e0;
            }
            .volver {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #2196F3;
                color: white;
                text-decoration: none;
                border-radius: 4px;
            }
            .volver:hover {
                background-color: #0b7dda;
            }
            .empty {
                text-align: center;
                padding: 40px;
                color: #666;
                font-style: italic;
                background-color: white;
                border-radius: 4px;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <h1>⚠️ Alumnos que NO rindieron ningún examen en 2025</h1>

        <div class="info">
            <strong>ℹ️ Información:</strong> Esta lista muestra los alumnos que no tienen registrado 
            ningún examen durante el año en curso (2025).
        </div>

        <c:choose>
            <c:when test="${not empty alumnos}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Apellido</th>
                            <th>Nombre</th>
                            <th>DNI</th>
                            <th>Carrera</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="alumno" items="${alumnos}">
                            <tr>
                                <td>${alumno.idalumno}</td>
                                <td>${alumno.apellido}</td>
                                <td>${alumno.nombre}</td>
                                <td>${alumno.dni}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty alumno.carreraIdcarrera}">
                                            ${alumno.carreraIdcarrera.nombre}
                                        </c:when>
                                        <c:otherwise>
                                            Sin carrera
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <p style="margin-top: 20px; color: #666;">
                    Total de alumnos sin exámenes: <strong>${alumnos.size()}</strong>
                </p>
            </c:when>
            <c:otherwise>
                <div class="empty">
                    <p>✅ ¡Excelente! Todos los alumnos rindieron al menos un examen en 2025.</p>
                </div>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/faces/index.xhtml" class="volver">
            ← Volver al inicio
        </a>
    </body>
</html>