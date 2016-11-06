package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
import br.org.catolicasc.fcgf.projprog4.web.helpers.FieldData;
import br.org.catolicasc.fcgf.projprog4.web.helpers.ParseHelper;
import br.org.catolicasc.fcgf.projprog4.web.helpers.Type;
import br.org.catolicasc.fcgf.projprog4.web.interfaces.IWebCmd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        List<List<FieldData<Object>>> objects = new ArrayList<>();
        rules.stream().map((r) -> {
            List<FieldData<Object>> fields = new ArrayList<>(3);
            fields.add(new FieldData<>(ID, r.getId(), false, null, Type.ID));
            fields.add(new FieldData<>(NOME, r.getNome(), false, null, Type.TEXT));
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

        List<FieldData<Object>> fields = new ArrayList<>(2);
        fields.add(new FieldData<>(NOME, null, false, null, Type.TEXT));

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
        return detailOrUpdate(request, response, false);
    }

    //Para criar a view de Update
    public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return detailOrUpdate(request, response, true);
    }

    //Para fazer o update e voltar a view de List
    public String editAndList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        final String receivedId = request.getParameter(ID);

        if (receivedId == null || receivedId.isEmpty() || !ParseHelper.tryParseLong(receivedId)) {
            request.setAttribute(ERROR, "Id not received.");
        } else {
            Long id = Long.parseLong(receivedId);

            EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
            RuleDAO ruleDao = new RuleDAO(factory);

            Rule rule = ruleDao.findRule(id);

            if (rule == null) {
                request.setAttribute(ERROR, "Rule not found.");
            } else {

                String name = readParameter(request, NOME, rule.getNome());

                rule.setNome(name);

                try {
                    ruleDao.edit(rule);
                    request.setAttribute(MESSAGE, "Rule edited successfully.");
                } catch (Exception ex) {
                    request.setAttribute(ERROR, "Unable to edit rule.");
                }
            }
        }

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

    private String detailOrUpdate(HttpServletRequest request, HttpServletResponse response, boolean update) throws ServletException, IOException {
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
                List<FieldData<Object>> fields = new ArrayList<>(3);
                fields.add(new FieldData<>(ID, rule.getId(), false, null, Type.ID));
                fields.add(new FieldData<>(NOME, rule.getNome(), false, null, Type.TEXT));

                request.setAttribute(FIELDS, fields);
                request.setAttribute(NAME, "Rule");

                link = update ? UPDATE_PATH : DETAIL_PATH;
            }
        }

        return link;
    }
}
