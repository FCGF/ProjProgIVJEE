package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
import br.org.catolicasc.fcgf.projprog4.web.interfaces.IWebCmd;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.catolica.prog4.persistencia.entities.Rule;
import org.catolica.prog4.persistencia.daos.IRuleDAO;
import org.catolica.prog4.persistencia.daos.RuleDAO;

/**
 *
 * @author Fabio Tavares Dippold, FCGF
 *
 */
public class BuildModelSampleCmd extends AbstractWebCmd implements IWebCmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.setDefaultAppModel(request);

        request.setAttribute("rules", this.findAllRules());

        return "/WEB-INF/views/viewSample.jsp";
    }

    private List<Rule> findAllRules() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        IRuleDAO dao = new RuleDAO(factory);

        return dao.findAll();
    }

}
