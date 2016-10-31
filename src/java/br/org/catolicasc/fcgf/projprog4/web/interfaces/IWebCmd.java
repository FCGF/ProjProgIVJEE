package br.org.catolicasc.fcgf.projprog4.web.interfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FCGF
 */
public interface IWebCmd {

    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
