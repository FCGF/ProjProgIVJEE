package br.org.catolicasc.fcgf.projprog4.web.controllers;

import org.catolica.prog4.persistencia.daos.UserDAO;
import org.catolica.prog4.persistencia.entities.User;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.catolica.prog4.persistencia.helpers.EntityManagerFactoryManager;

/**
 *
 * @author FCGF
 */
@WebServlet(name = "AuthenticationControllerServlet", urlPatterns = {"/signin"})
public class AuthenticationControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        User user;
        String email = request.getParameter("email"); //name
        String password = request.getParameter("password");

        if (email != null && password != null) {
            user = this.findUser(email, password);
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userid", Long.toString(user.getId()));
                session.setAttribute("username", user.getNome());
                session.setAttribute("userroleid", Long.toString(user.getRule().getId()));
                //session.setAttribute("usercompanyid", Long.toString(user.));

                //logservice
                request.getRequestDispatcher("mvc?cmd=BuildMainMenuCmd").forward(request, response);
            } else {
                //logservice
                request.setAttribute("msg", "Credenciais inválidos ou usuario bloqueado!");
                request.getRequestDispatcher("signin.jsp").forward(request, response);
            }
        } else {
            //logservice
            request.setAttribute("msg", "Inserir credenciais!");
            request.getRequestDispatcher("signin.jsp").forward(request, response);
        }
    }

    private User findUser(String email, String password) {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
        UserDAO dao = new UserDAO(factory);
        User user;
        try {
            user = dao.findUser(email, password);
        } catch (NoResultException e) {
            user = null;
        }

        return user;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
