package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
import br.org.catolicasc.fcgf.projprog4.web.interfaces.IWebCmd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.catolica.prog4.persistencia.daos.RuleDAO;
import org.catolica.prog4.persistencia.entities.Rule;
import org.catolica.prog4.persistencia.helpers.EntityManagerFactoryManager;

/**
 *
 * @author FCGF
 */
public class RuleCRUDCmd extends AbstractWebCmd implements IWebCmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Rule> rules = findAllRules();

        List<Map<String, Object>> objects = new ArrayList<>();
        rules.stream().map((u) -> {
            Map<String, Object> fields = new LinkedHashMap<>(3);
            fields.put("Id", u.getId());
            fields.put("Nome", u.getNome());
            return fields;
        }).forEach((fields) -> {
            objects.add(fields);
        });

        request.setAttribute("objects", objects);
        request.setAttribute("name", "Rules");

        return "/WEB-INF/views/list.jsp";
    }

    public String create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    private List<Rule> findAllRules() {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        RuleDAO dao = new RuleDAO(factory);

        return dao.findAll();
    }
}
