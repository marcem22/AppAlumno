<%@ page import="conexion.ConexionBD" %>
<%@ page import="java.sql.Connection" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Test Conexión</title>
</head>
<body>
<%
    Connection conn = ConexionBD.getConexion();
    if (conn != null) {
%>
        <h2 style="color:green;">? Conexión establecida correctamente con la base de datos.</h2>
<%
    } else {
%>
        <h2 style="color:red;">? Error al conectar con la base de datos.</h2>
<%
    }
%>
</body>
</html>
