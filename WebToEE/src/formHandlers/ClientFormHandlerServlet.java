package formHandlers;

import controller.ClientController;
import problemDomain.Client;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andrey
 * Date: 24.02.13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class ClientFormHandlerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            boolean result = false;
            //out.println("<html>");
            String submitAction = request.getParameter("submitAction");// from jsp to server it is a parameter
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");

            //String option = request.getParameter("option");

            ClientController cc = new ClientController();   //

            RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); // page we want to dispatch to

            if (submitAction.equals("Show Client")) {
                ArrayList<Client> clientArr = cc.showClients(submitAction, fname, lname, gender, phone);

                if (clientArr!=null) {
                    result = true;
                    request.setAttribute("showClients", "Success"); // from server to a jsp it is an attribute
                    request.setAttribute("clientArr", clientArr);
                }
            }
            else {
                Client clinet = new Client(fname, lname, gender,phone,"");

                result = cc.clientAction(submitAction, clinet); // должен возвращать объект Client -
                // тк только в базе он может создаваться(client_id генерит БД)
            }

            if (result) {
                request.setAttribute("message", "Success");  //setting attribute and sending back to index.jsp page
            }
            else {
                request.setAttribute("message", "Fail");
            }
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }
}
