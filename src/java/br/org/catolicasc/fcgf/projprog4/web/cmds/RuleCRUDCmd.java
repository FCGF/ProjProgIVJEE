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
import org.catolica.prog4.persistencia.daos.exceptions.IllegalOrphanException;
import org.catolica.prog4.persistencia.daos.exceptions.NonexistentEntityException;
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

        String searchFor = request.getParameter(SEARCH);
        List<Rule> rules;

        if (searchFor == null || searchFor.isEmpty()) {
            rules = findAllRules();
        } else {
            rules = findRules(searchFor.toLowerCase());
        }

        List<Map<String, Object>> objects = new ArrayList<>();
        rules.stream().map((u) -> {
            Map<String, Object> fields = new LinkedHashMap<>(3);
            fields.put(ID, u.getId());
            fields.put(NOME, u.getNome());
            return fields;
        }).forEach((fields) -> {
            objects.add(fields);
        });

        request.setAttribute(OBJECTS, objects);
        setName(request);

        return LIST_PATH;
    }

    //Para criar a view de Create
    public String create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        Map<String, List<Object>> fields = new LinkedHashMap<>();
        fields.put(NOME, null);

        request.setAttribute(FIELDS, fields);
        setName(request);

        return CREATE_PATH;
    }

    //Para criar e voltar a view de List
    public String makeAndList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
        RuleDAO ruleDao = new RuleDAO(factory);

        String name = request.getParameter(NOME);

        Rule rule = new Rule(name);

        ruleDao.create(rule);

        request.setAttribute(MESSAGE, "Rule created successfully.");

        return list(request, response);
    }

    public String detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        final String receivedId = request.getParameter(ID);
        final String link;

        if (receivedId == null || receivedId.isEmpty() || !ParseHelper.tryParseLong(receivedId)) {
            request.setAttribute(ERROR, "Id not received.");
            link = list(request, response);
        } else {
            Long id = Long.parseLong(receivedId);
            EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
            RuleDAO dao = new RuleDAO(factory);
            Rule rule = dao.findRule(id);

            if (rule == null) {
                request.setAttribute(ERROR, "Rule not found.");
                link = list(request, response);
            } else {
                Map<String, Object> fields = new LinkedHashMap<>(6);
                fields.put(ID, rule.getId());
                fields.put(NOME, rule.getNome());

                request.setAttribute(FIELDS, fields);
                request.setAttribute(NAME, "Rule");

                link = DETAIL_PATH;
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

        final String receivedId = request.getParameter(ID);

        if (receivedId == null || receivedId.isEmpty() || !ParseHelper.tryParseLong(receivedId)) {
            request.setAttribute(ERROR, "Id not received.");
        } else {
            Long id = Long.parseLong(receivedId);

            EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
            RuleDAO dao = new RuleDAO(factory);

            try {
                dao.destroy(id);
                request.setAttribute(MESSAGE, "Rule deleted successfully.");
            } catch (NonexistentEntityException ex) {
                request.setAttribute(ERROR, "Rule not found.");
            } catch (IllegalOrphanException ex) {
                request.setAttribute(ERROR, "User(s) with this Rule found. Please delete or edit them first.");
            }
        }
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

    private void setName(HttpServletRequest request) {
        request.setAttribute(NAME, "Rules");
    }
}
