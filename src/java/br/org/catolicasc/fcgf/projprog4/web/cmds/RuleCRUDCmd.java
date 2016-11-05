package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
import br.org.catolicasc.fcgf.projprog4.web.helpers.ParseHelper;
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
        setCmdName(request);

        String searchFor = request.getParameter("srch");
        List<Rule> rules;

        if (searchFor == null || searchFor.isEmpty()) {
            rules = findAllRules();
        } else {
            rules = findRules(searchFor.toLowerCase());
        }

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
        setCmdName(request);
        return list(request, response);
    }

    public String detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        final String receivedId = request.getParameter("id");
        final String link;

        if (receivedId == null || receivedId.isEmpty()) {
            request.setAttribute("msg", "Id not received.");
            link = list(request, response);
        } else {
            Long id = Long.parseLong(receivedId);
            EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
            RuleDAO dao = new RuleDAO(factory);
            Rule rule = dao.findRule(id);

            if (rule == null) {
                request.setAttribute("msg", "Rule not found.");
                link = list(request, response);
            } else {
                Map<String, Object> fields = new LinkedHashMap<>(6);
                fields.put("Id", rule.getId());
                fields.put("Nome", rule.getNome());

                request.setAttribute("fields", fields);
                request.setAttribute("name", "Rules");

                link = "/WEB-INF/views/detail.jsp";
            }
        }

        return link;
    }

    public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);
        return list(request, response);
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);
        return list(request, response);
    }

    private List<Rule> findAllRules() {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        RuleDAO dao = new RuleDAO(factory);

        return dao.findAll();
    }

    private List<Rule> findRules(String keyword) {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        RuleDAO dao = new RuleDAO(factory);

        List<Rule> rules = dao.findAll();

        List<Rule> filteredRules = new ArrayList<>();

        rules.stream().filter((r) -> ((ParseHelper.tryParseLong(keyword) && Long.parseLong(keyword) == r.getId())
                || (r.getNome().toLowerCase().contains(keyword)))).forEachOrdered((r) -> {
                    filteredRules.add(r);
        });

        return filteredRules;
    }
}
