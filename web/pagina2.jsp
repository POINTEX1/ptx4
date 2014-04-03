<%-- 
    Document   : pagina2
    Created on : 30-03-2014, 01:33:39 AM
    Author     : patricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtener el parametro con el nombre de la persona
    String persona = request.getParameter("persona");

    // Devolver el lugar de residencia de la persona
    if (persona.equalsIgnoreCase("Jesus Hernandez")) {
        out.print("GUADALAJARA");
    } else if (persona.equalsIgnoreCase("Fulano de tal")) {
        out.print("PINTO");
    } else if (persona.equalsIgnoreCase("Fulano de cual")) {
        out.print("VALDEMORO");
    } else {
        out.print("PERSONA DESCONOCIDA");
    }
%>
