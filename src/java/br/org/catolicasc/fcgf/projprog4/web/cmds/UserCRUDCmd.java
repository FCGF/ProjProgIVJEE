package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
import br.org.catolicasc.fcgf.projprog4.web.interfaces.IWebCmd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.catolica.prog4.persistencia.daos.UserDAO;
import org.catolica.prog4.persistencia.entities.User;

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
        List<User> users = findAllUsers();

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

    private List<User> findAllUsers() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        UserDAO dao = new UserDAO(factory);

        return dao.findAll();
    }
}
