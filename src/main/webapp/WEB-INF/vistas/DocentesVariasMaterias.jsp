<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Docentes con Varias Materias</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        h1 {
            color: #333;
            border-bottom: 3px solid #9c27b0;
            padding-bottom: 10px;
        }
        .info {
            background-color: #f3e5f5;
            border-left: 4px solid #9c27b0;
            padding: 15px;
            margin: 20px 0;
            border-radius: 4px;
        }
        .docente-card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 20px;
            margin: 15px 0;
        }
        .docente-card h3 {
            margin-top: 0;
            color: #9c27b0;
            border-bottom: 2px solid #e1bee7;
            padding-bottom: 10px;
        }
        .cargo {
            display: inline-block;
            padding: 5px 15px;
            background-color: #e1bee7;
            color: #4a148c;
            border-radius: 20px;
            font-size: 0.9em;
            font-weight: bold;
            margin-left: 10px;
        }
        .materias-list {
            list-style: none;
            padding: 0;
            margin: 15px 0;
        }
        .materias-list li {
            padding: 8px;
            margin: 5px 0;
            background-color: #f5f5f5;
            border-left: 3px solid #9c27b0;
            padding-left: 15px;
        }
        .count {
            background-color: #9c27b0;
            color: white;
            padding: 3px 10px;
            border-radius: 12px;
            font-size: 0.85em;
            margin-left: 10px;
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
    <h1>👨‍🏫 Docentes que dictan más de 2 materias</h1>
    
    <div class="info">
        <strong>ℹ️ Información:</strong> Lista de docentes con alta carga horaria 
        (más de 2 materias asignadas).
    </div>
    
    <c:choose>
        <c:when test="${not empty docentes}">
            <c:forEach var="docente" items="${docentes}">
                <div class="docente-card">
                    <h3>
                        ${docente.nombre}
                        <span class="cargo">${docente.cargo}</span>
                        <span class="count">${docente.materiaCollection.size()} materias</span>
                    </h3>
                    
                    <strong>Materias que dicta:</strong>
                    <ul class="materias-list">
                        <c:forEach var="materia" items="${docente.materiaCollection}">
                            <li>📚 ${materia.nombre}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
            
            <p style="margin-top: 20px; color: #666;">
                Total de docentes: <strong>${docentes.size()}</strong>
            </p>
        </c:when>
        <c:otherwise>
            <div class="empty">
                <p>📭 No hay docentes que dicten más de 2 materias.</p>
            </div>
        </c:otherwise>
    </c:choose>
    
    <a href="${pageContext.request.contextPath}/faces/index.xhtml" class="volver">
        ← Volver al inicio
    </a>
</body>
</html>