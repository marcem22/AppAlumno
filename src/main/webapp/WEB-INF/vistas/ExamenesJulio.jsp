<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Exámenes Turno Julio 2025</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        h1 {
            color: #333;
            border-bottom: 3px solid #4CAF50;
            padding-bottom: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        th {
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            text-align: left;
        }
        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .nota {
            font-weight: bold;
            font-size: 1.1em;
        }
        .aprobado {
            color: green;
        }
        .desaprobado {
            color: red;
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
        }
    </style>
</head>
<body>
    <h1>📋 Mesas de Examen - Turno Julio 2025</h1>
    
    <c:choose>
        <c:when test="${not empty examenes}">
            <table>
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Alumno</th>
                        <th>Materia</th>
                        <th>Nota</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="examen" items="${examenes}">
                        <tr>
                            <td>
                                <fmt:formatDate value="${examen.examenPK.fecha}" 
                                              pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td>
                                ${examen.alumno.nombre} ${examen.alumno.apellido}
                            </td>
                            <td>
                                ${examen.materia.nombre}
                            </td>
                            <td class="nota ${examen.nota >= 6 ? 'aprobado' : 'desaprobado'}">
                                ${examen.nota}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${examen.nota >= 6}">
                                        ✅ Aprobado
                                    </c:when>
                                    <c:otherwise>
                                        ❌ Desaprobado
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <p style="margin-top: 20px; color: #666;">
                Total de exámenes: <strong>${examenes.size()}</strong>
            </p>
        </c:when>
        <c:otherwise>
            <div class="empty">
                <p>📭 No hay exámenes registrados para el turno de julio 2025.</p>
            </div>
        </c:otherwise>
    </c:choose>
    
    <a href="${pageContext.request.contextPath}/faces/index.xhtml" class="volver">
        ← Volver al inicio
    </a>
</body>
</html>