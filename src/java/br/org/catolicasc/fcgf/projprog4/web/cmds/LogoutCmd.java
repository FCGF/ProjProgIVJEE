package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
import br.org.catolicasc.fcgf.projprog4.web.interfaces.IWebCmd;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FCGF
 */
public class LogoutCmd extends AbstractWebCmd implements IWebCmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.setDefaultAppModel(request);
        
        HttpSession session = request.getSession(true);
        session.setAttribute("userid", null);
        session.setAttribute("username", null);
        session.setAttribute("userroleid", null);
        
        session.invalidate();
        
        return "signin.jsp";
    }

}
