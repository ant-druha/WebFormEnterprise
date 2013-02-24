<%@page import="java.util.ListIterator"%>
<%@page import="problemDomain.Client"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : index
    Created on : 07.08.2012, 22:46:58
    Author     : Andrey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Clinet Form</h1>
        
        <form action="FormHandler" method="POST"> <!-- ClientFormHandlerServlet - servlet -->
            
            <input type="hidden" name="option" value="addClient" />
            
            First Name <input type="text" name="fname" value="" /><br>
            Last Name <input type="text" name="lname" value="" /><br>
            Gender <input type="text" name="gender" value="" /><br>
            Phone <input type="text" name="phone" value="" /><br>
            <input type="submit" value="Add Client" name="submitAction" />
            <input type="submit" value="Show Client" name="submitAction" />
        </form>

        
        <% 
        if (request.getAttribute("message")!=null) {
            out.println("<b>" + request.getAttribute("message") + "</b>");
        }
        
        if (request.getAttribute("showClients")!=null && 
                request.getAttribute("showClients").equals("Success")) {
            ArrayList<Client> clientArr;// = new ArrayList<Client>();
            clientArr = (ArrayList<Client>) request.getAttribute("clientArr");
            ListIterator<Client> it = clientArr.listIterator();
            Client c;
            %>
            
            <h3> Clients </h3>
            <table border="2" cellspacing="1" cellpadding="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Gender</th>
                </tr>
            </thead>

            <tbody>
        <%
        while (it.hasNext()) { 
            c = it.next();
        %>
                <tr>
                    <td><%=c.getClientId() %></td>
                    <td><%=c.getFname() %></td>
                    <td><%=c.getLname() %></td>
                    <td><%=c.getEmail() %></td>
                    <td><%=c.getPhone() %></td>
                    <td><%=c.getGender() %></td>
                </tr>
        <%
        }
        %>
            </tbody>
        </table>
<% 
   }
%>
         
        
    </body>
</html>
