package br.org.catolicasc.fcgf.projprog4.web.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio Tavares Dippold, FCGF
 * 
 */
@WebServlet(name = "AjaxSamplesFindAllProject", urlPatterns = {"/allprojects"})
public class AjaxSamplesFindAllProject extends HttpServlet {

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
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        try (PrintWriter out = response.getWriter()) {
            
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(AjaxSamplesFindAllProject.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<Project> projetos = buildProjectList();
                Gson gson = new Gson();
                JsonElement element = gson.toJsonTree(projetos , new TypeToken<List<Project>>() {}.getType());
                JsonArray jsonArray = element.getAsJsonArray();
                out.println(jsonArray.toString());
                out.flush();
        }
    }

    private List<Project> buildProjectList() {
        List<Project> lst = new ArrayList<>();
        
        lst.add(new Project(1L,"Projeto ABX", "Em aprovação", "Fabio Dippold","2016-01-01","2016-03-30"));
        lst.add(new Project(2L,"Projeto XYZ", "Em iniciação", "Carlos Alves","2016-02-22","2016-04-30"));
        lst.add(new Project(3L,"Projeto ANDROMEDA", "Em Blueprint", "Marcio Jose","2016-03-01","2016-08-30"));
        
        return lst;
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
