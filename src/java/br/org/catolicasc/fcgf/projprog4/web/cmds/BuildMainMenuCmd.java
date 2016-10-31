package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
import br.org.catolicasc.fcgf.projprog4.web.interfaces.IWebCmd;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FCGF
 */
public class BuildMainMenuCmd extends AbstractWebCmd implements IWebCmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.setDefaultAppModel(request);

        return "/WEB-INF/views/mainMenu.jsp";
    }
}
