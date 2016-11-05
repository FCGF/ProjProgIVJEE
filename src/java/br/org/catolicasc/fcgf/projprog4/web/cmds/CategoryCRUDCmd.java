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
import org.catolica.prog4.persistencia.daos.CategoryDAO;
import org.catolica.prog4.persistencia.daos.exceptions.IllegalOrphanException;
import org.catolica.prog4.persistencia.daos.exceptions.NonexistentEntityException;
import org.catolica.prog4.persistencia.entities.Category;
import org.catolica.prog4.persistencia.helpers.EntityManagerFactoryManager;

/**
 *
 * @author FCGF
 */
public class CategoryCRUDCmd extends AbstractWebCmd implements IWebCmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        String searchFor = request.getParameter("srch");
        List<Category> categories;

        if (searchFor == null || searchFor.isEmpty()) {
            categories = findAllCategories();
        } else {
            categories = findCategories(searchFor.toLowerCase());
        }

        List<Map<String, Object>> objects = new ArrayList<>();
        categories.stream().map((u) -> {
            Map<String, Object> fields = new LinkedHashMap<>(6);
            fields.put("Id", u.getId());
            fields.put("Nome", u.getNome());
            return fields;
        }).forEach((fields) -> {
            objects.add(fields);
        });

        request.setAttribute("objects", objects);
        request.setAttribute("name", "Categories");

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
            CategoryDAO dao = new CategoryDAO(factory);
            Category category = dao.findCategory(id);

            if (category == null) {
                request.setAttribute("msg", "Category not found.");
                link = list(request, response);
            } else {
                Map<String, Object> fields = new LinkedHashMap<>(6);
                fields.put("Id", category.getId());
                fields.put("Nome", category.getNome());

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

        final String receivedId = request.getParameter("id");

        if (receivedId == null || receivedId.isEmpty() || !ParseHelper.tryParseLong(receivedId)) {
            request.setAttribute("msg", "Id not received.");
        } else {
            Long id = Long.parseLong(receivedId);

            EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
            CategoryDAO dao = new CategoryDAO(factory);

            try {
                dao.destroy(id);
                request.setAttribute("msg", "Category deleted successfully.");
            } catch (NonexistentEntityException ex) {
                request.setAttribute("msg", "Category not found.");
            } catch (IllegalOrphanException ex) {
                request.setAttribute("msg", "Product(s) with this Category found. Please delete or edit them first.");
            }
        }
        return list(request, response);
    }

    private List<Category> findAllCategories() {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        CategoryDAO dao = new CategoryDAO(factory);

        return dao.findAll();
    }

    private List<Category> findCategories(String keyword) {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        CategoryDAO dao = new CategoryDAO(factory);

        List<Category> categories = dao.findAll();

        List<Category> filteredCategories = new ArrayList<>();

        categories.stream().filter((c) -> ((ParseHelper.tryParseLong(keyword) && Long.parseLong(keyword) == c.getId())
                || (c.getNome().toLowerCase().contains(keyword)))).forEachOrdered((c) -> {
                    filteredCategories.add(c);
        });

        return filteredCategories;
    }
}
