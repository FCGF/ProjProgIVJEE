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

        String searchFor = request.getParameter(SEARCH);
        List<Category> categories;

        if (searchFor == null || searchFor.isEmpty()) {
            categories = findAllCategories();
        } else {
            categories = findCategories(searchFor.toLowerCase());
        }

        List<List<FieldData<Object>>> objects = new ArrayList<>();
        categories.stream().map((c) -> {
            List<FieldData<Object>> fields = new ArrayList<>(3);
            fields.add(new FieldData<>(ID, c.getId(), false, null, Type.ID));
            fields.add(new FieldData<>(NOME, c.getNome(), false, null, Type.TEXT));
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
        CategoryDAO categoryDao = new CategoryDAO(factory);

        String name = request.getParameter(NOME);

        Category category = new Category(name);

        categoryDao.create(category);

        request.setAttribute(MESSAGE, "Category created successfully.");

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
            CategoryDAO dao = new CategoryDAO(factory);

            try {
                dao.destroy(id);
                request.setAttribute(MESSAGE, "Category deleted successfully.");
            } catch (NonexistentEntityException ex) {
                request.setAttribute(ERROR, "Category not found.");
            } catch (IllegalOrphanException ex) {
                request.setAttribute(ERROR, "Product(s) with this Category found. Please delete or edit them first.");
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

    private void setName(HttpServletRequest request) {
        request.setAttribute(NAME, "Categories");
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
            CategoryDAO dao = new CategoryDAO(factory);
            Category category = dao.findCategory(id);

            if (category == null) {
                request.setAttribute(ERROR, "Category not found.");
                link = list(request, response);
            } else {
                List<FieldData<Object>> fields = new ArrayList<>(3);
                fields.add(new FieldData<>(ID, category.getId(), false, null, Type.ID));
                fields.add(new FieldData<>(NOME, category.getNome(), false, null, Type.TEXT));

                request.setAttribute(FIELDS, fields);
                request.setAttribute(NAME, "Category");

                link = update ? UPDATE_PATH : DETAIL_PATH;
            }
        }

        return link;
    }

}
