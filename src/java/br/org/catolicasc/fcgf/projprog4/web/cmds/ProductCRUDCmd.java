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
import org.catolica.prog4.persistencia.daos.ProductDAO;
import org.catolica.prog4.persistencia.daos.exceptions.NonexistentEntityException;
import org.catolica.prog4.persistencia.entities.Category;
import org.catolica.prog4.persistencia.entities.Product;
import org.catolica.prog4.persistencia.helpers.EntityManagerFactoryManager;

/**
 *
 * @author FCGF
 */
public class ProductCRUDCmd extends AbstractWebCmd implements IWebCmd {

    private static final String DESCRIPTION = "Description";
    private static final String PRICE = "Price";
    private static final String CATEGORY = "Category";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        String searchFor = request.getParameter(SEARCH);
        List<Product> products;

        if (searchFor == null || searchFor.isEmpty()) {
            products = findAllProducts();
        } else {
            products = findProducts(searchFor.toLowerCase());
        }

        List<List<FieldData<Category>>> objects = new ArrayList<>();
        products.stream().map((p) -> {
            List<FieldData<Category>> fields = new ArrayList<>(8);
            fields.add(new FieldData<>(ID, p.getId(), false, null, Type.ID));
            fields.add(new FieldData<>(NOME, p.getNome(), false, null, Type.TEXT));
            fields.add(new FieldData<>(DESCRIPTION, p.getDescription(), false, null, Type.TEXT));
            fields.add(new FieldData<>(PRICE, p.getPrice(), false, null, Type.NUMBER));
            fields.add(new FieldData<>(CATEGORY, p.getCategory(), true, null, Type.TEXT));
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
        CategoryDAO dao = new CategoryDAO(factory);

        List<FieldData<Category>> fields = new ArrayList<>(6);
        fields.add(new FieldData<>(NOME, null, false, null, Type.TEXT));
        fields.add(new FieldData<>(DESCRIPTION, null, false, null, Type.TEXT));
        fields.add(new FieldData<>(PRICE, null, false, null, Type.NUMBER));
        fields.add(new FieldData<>(CATEGORY, null, true, dao.findAll(), Type.TEXT));

        request.setAttribute(FIELDS, fields);
        setName(request);

        return CREATE_PATH;
    }

    //Para criar e voltar a view de List
    public String makeAndList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();
        CategoryDAO categoryDao = new CategoryDAO(factory);
        ProductDAO productDao = new ProductDAO(factory);

        String name = request.getParameter(NOME);
        String description = request.getParameter(DESCRIPTION);
        String price = request.getParameter(PRICE);
        String categoryId = request.getParameter(CATEGORY);
        Category category = categoryDao.findCategory(Long.parseLong(categoryId));

        Product product = new Product(name, description, Double.parseDouble(price), category);

        productDao.create(product);

        request.setAttribute(MESSAGE, "Product created successfully.");

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
            ProductDAO dao = new ProductDAO(factory);

            try {
                dao.destroy(id);
                request.setAttribute(MESSAGE, "Product deleted successfully.");
            } catch (NonexistentEntityException ex) {
                request.setAttribute(ERROR, "Product not found.");
            }
        }
        return list(request, response);
    }

    private List<Product> findAllProducts() {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        ProductDAO dao = new ProductDAO(factory);

        return dao.findAll();
    }

    private List<Product> findProducts(String keyword) {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        ProductDAO dao = new ProductDAO(factory);

        List<Product> products = dao.findAll();

        List<Product> filteredProducts = new ArrayList<>();

        products.stream().filter((p) -> ((ParseHelper.tryParseLong(keyword) && Long.parseLong(keyword) == p.getId())
                || (p.getNome().toLowerCase().contains(keyword))
                || (p.getDescription().toLowerCase().contains(keyword)
                || (ParseHelper.tryParseDouble(keyword) && Double.parseDouble(keyword) == p.getPrice())
                || (p.getCategory().getNome().toLowerCase().contains(keyword))))).forEachOrdered((p) -> {
            filteredProducts.add(p);
        });

        return filteredProducts;
    }

    private void setName(HttpServletRequest request) {
        request.setAttribute(NAME, "Products");
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
            ProductDAO productDao = new ProductDAO(factory);
            Product product = productDao.findProduct(id);

            if (product == null) {
                request.setAttribute(ERROR, "Product not found.");
                link = list(request, response);
            } else {
                List<Category> categories = update ? new CategoryDAO(factory).findAll() : null;

                List<FieldData<Category>> fields = new ArrayList<>(8);
                fields.add(new FieldData<>(ID, product.getId(), false, null, Type.ID));
                fields.add(new FieldData<>(NOME, product.getNome(), false, null, Type.TEXT));
                fields.add(new FieldData<>(DESCRIPTION, product.getDescription(), false, null, Type.TEXT));
                fields.add(new FieldData<>(PRICE, product.getPrice(), false, null, Type.NUMBER));
                fields.add(new FieldData<>(CATEGORY, product.getCategory(), true, categories, Type.TEXT));

                request.setAttribute(FIELDS, fields);
                request.setAttribute(NAME, "Product");

                link = update ? UPDATE_PATH : DETAIL_PATH;
            }
        }

        return link;
    }
}
