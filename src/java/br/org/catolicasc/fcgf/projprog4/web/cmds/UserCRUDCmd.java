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
import org.catolica.prog4.persistencia.daos.UserDAO;
import org.catolica.prog4.persistencia.daos.exceptions.NonexistentEntityException;
import org.catolica.prog4.persistencia.entities.Rule;
import org.catolica.prog4.persistencia.entities.User;
import org.catolica.prog4.persistencia.helpers.EntityManagerFactoryManager;

/**
 *
 * @author FCGF
 */
public class UserCRUDCmd extends AbstractWebCmd implements IWebCmd {

    private static final String EMAIL = "E-mail";
    private static final String PASSWORD = "Password";
    private static final String RULE = "Rule";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        String searchFor = request.getParameter(SEARCH);
        List<User> users;

        if (searchFor == null || searchFor.isEmpty()) {
            users = findAllUsers();
        } else {
            users = findUsers(searchFor.toLowerCase());
        }

        List<List<FieldData<Rule>>> objects = new ArrayList<>();
        users.stream().map((u) -> {
            List<FieldData<Rule>> fields = new ArrayList<>(6);
            fields.add(new FieldData<>(ID, u.getId(), false, null, Type.ID));
            fields.add(new FieldData<>(NOME, u.getNome(), false, null, Type.TEXT));
            fields.add(new FieldData<>(EMAIL, u.getEmail(), false, null, Type.EMAIL));
            fields.add(new FieldData<>(RULE, u.getRule(), true, null, Type.TEXT));
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

        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
        RuleDAO ruleDao = new RuleDAO(factory);

        List<FieldData<Rule>> fields = new ArrayList<>(6);
        fields.add(new FieldData<>(NOME, null, false, null, Type.TEXT));
        fields.add(new FieldData<>(EMAIL, null, false, null, Type.EMAIL));
        fields.add(new FieldData<>(PASSWORD, null, false, null, Type.PASSWORD));
        fields.add(new FieldData<>(RULE, null, true, ruleDao.findAll(), Type.TEXT));

        request.setAttribute(FIELDS, fields);
        setName(request);

        return CREATE_PATH;
    }

    //Para criar e voltar a view de List
    public String makeAndList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
        RuleDAO ruleDao = new RuleDAO(factory);
        UserDAO userDao = new UserDAO(factory);

        String name = request.getParameter(NOME);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String ruleId = request.getParameter(RULE);
        Rule rule = ruleDao.findRule(Long.parseLong(ruleId));

        User user = new User(name, email, password, rule);

        userDao.create(user);

        request.setAttribute(MESSAGE, "User created successfully.");

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
            UserDAO userDao = new UserDAO(factory);

            User user = userDao.findUser(id);

            if (user == null) {
                request.setAttribute(ERROR, "User not found.");
            } else {
                RuleDAO ruleDao = new RuleDAO(factory);

                String name = readParameter(request, NOME, user.getNome());
                String email = readParameter(request, EMAIL, user.getEmail());
                String password = readParameter(request, PASSWORD, user.getSenha());
                String ruleId = readParameter(request, RULE, String.valueOf(user.getRule().getId()));

                Rule rule = ruleDao.findRule(Long.parseLong(ruleId));

                user.setNome(name);
                user.setEmail(email);
                user.setSenha(password);
                user.setRule(rule);

                try {
                    userDao.edit(user);
                    request.setAttribute(MESSAGE, "User edited successfully.");
                } catch (Exception ex) {
                    request.setAttribute(ERROR, "Unable to edit user.");
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
            UserDAO dao = new UserDAO(factory);

            try {
                dao.destroy(id);
                request.setAttribute(MESSAGE, "User deleted successfully.");
            } catch (NonexistentEntityException ex) {
                request.setAttribute(ERROR, "User not found.");
            }
        }
        return list(request, response);
    }

    private List<User> findAllUsers() {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        UserDAO dao = new UserDAO(factory);

        return dao.findAll();
    }

    private List<User> findUsers(String keyword) {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        UserDAO dao = new UserDAO(factory);

        List<User> users = dao.findAll();

        List<User> filteredUsers = new ArrayList<>();

        users.stream().filter((u) -> ((ParseHelper.tryParseLong(keyword) && Long.parseLong(keyword) == u.getId())
                || (u.getNome().toLowerCase().contains(keyword))
                || (u.getEmail().toLowerCase().contains(keyword)
                || (u.getRule().getNome().toLowerCase().contains(keyword))))).forEachOrdered((u) -> {
            filteredUsers.add(u);
        });

        return filteredUsers;
    }

    private void setName(HttpServletRequest request) {
        request.setAttribute(NAME, "Users");
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
            UserDAO userDao = new UserDAO(factory);
            User user = userDao.findUser(id);

            if (user == null) {
                request.setAttribute(ERROR, "User not found.");
                link = list(request, response);
            } else {
                List<Rule> rules = update ? new RuleDAO(factory).findAll() : null;

                List<FieldData<Rule>> fields = new ArrayList<>(6);
                fields.add(new FieldData<>(ID, user.getId(), false, null, Type.ID));
                fields.add(new FieldData<>(NOME, user.getNome(), false, null, Type.TEXT));
                fields.add(new FieldData<>(EMAIL, user.getEmail(), false, null, Type.EMAIL));
                fields.add(new FieldData<>(PASSWORD, user.getSenha(), false, null, Type.PASSWORD));
                fields.add(new FieldData<>(RULE, user.getRule(), true, rules, Type.TEXT));

                request.setAttribute(FIELDS, fields);
                request.setAttribute(NAME, "User");

                link = update ? UPDATE_PATH : DETAIL_PATH;
            }
        }
        return link;
    }

}
