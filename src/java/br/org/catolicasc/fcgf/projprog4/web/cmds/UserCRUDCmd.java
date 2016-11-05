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
import org.catolica.prog4.persistencia.daos.UserDAO;
import org.catolica.prog4.persistencia.daos.exceptions.NonexistentEntityException;
import org.catolica.prog4.persistencia.entities.User;
import org.catolica.prog4.persistencia.helpers.EntityManagerFactoryManager;

/**
 *
 * @author FCGF
 */
public class UserCRUDCmd extends AbstractWebCmd implements IWebCmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        String searchFor = request.getParameter("srch");
        List<User> users;

        if (searchFor == null || searchFor.isEmpty()) {
            users = findAllUsers();
        } else {
            users = findUsers(searchFor.toLowerCase());
        }

        List<Map<String, Object>> objects = new ArrayList<>();
        users.stream().map((u) -> {
            Map<String, Object> fields = new LinkedHashMap<>(6);
            fields.put("Id", u.getId());
            fields.put("Nome", u.getNome());
            fields.put("E-mail", u.getEmail());
            fields.put("Rule", u.getRule().getNome());
            return fields;
        }).forEach((fields) -> {
            objects.add(fields);
        });

        request.setAttribute("objects", objects);
        request.setAttribute("name", "Users");

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

        if (receivedId == null || receivedId.isEmpty() || !ParseHelper.tryParseLong(receivedId)) {
            request.setAttribute("msg", "Id not received.");
            link = list(request, response);
        } else {
            Long id = Long.parseLong(receivedId);
            EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
            UserDAO dao = new UserDAO(factory);
            User user = dao.findUser(id);

            if (user == null) {
                request.setAttribute("msg", "User not found.");
                link = list(request, response);
            } else {
                Map<String, Object> fields = new LinkedHashMap<>(6);
                fields.put("Id", user.getId());
                fields.put("Nome", user.getNome());
                fields.put("E-mail", user.getEmail());
                fields.put("Rule", user.getRule().getNome());

                request.setAttribute("fields", fields);
                request.setAttribute("name", "Users");

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

        final String receivedId = request.getParameter("id");

        if (receivedId == null || receivedId.isEmpty() || !ParseHelper.tryParseLong(receivedId)) {
            request.setAttribute("msg", "Id not received.");
        } else {
            Long id = Long.parseLong(receivedId);

            EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
            UserDAO dao = new UserDAO(factory);

            try {
                dao.destroy(id);
                request.setAttribute("msg", "User deleted successfully.");
            } catch (NonexistentEntityException ex) {
                request.setAttribute("msg", "User not found.");
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
}
