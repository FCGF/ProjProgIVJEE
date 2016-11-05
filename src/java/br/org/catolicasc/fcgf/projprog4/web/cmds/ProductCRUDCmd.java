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
import org.catolica.prog4.persistencia.daos.ProductDAO;
import org.catolica.prog4.persistencia.daos.exceptions.NonexistentEntityException;
import org.catolica.prog4.persistencia.entities.Product;
import org.catolica.prog4.persistencia.helpers.EntityManagerFactoryManager;

/**
 *
 * @author FCGF
 */
public class ProductCRUDCmd extends AbstractWebCmd implements IWebCmd {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return list(request, response);
    }

    public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCmdName(request);

        String searchFor = request.getParameter("srch");
        List<Product> products;

        if (searchFor == null || searchFor.isEmpty()) {
            products = findAllProducts();
        } else {
            products = findProducts(searchFor.toLowerCase());
        }

        List<Map<String, Object>> objects = new ArrayList<>();
        products.stream().map((u) -> {
            Map<String, Object> fields = new LinkedHashMap<>(8);
            fields.put("Id", u.getId());
            fields.put("Nome", u.getNome());
            fields.put("Description", u.getDescription());
            fields.put("Price", u.getPrice());
            fields.put("Category", u.getCategory().getNome());
            return fields;
        }).forEach((fields) -> {
            objects.add(fields);
        });

        request.setAttribute("objects", objects);
        request.setAttribute("name", "Products");

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
            ProductDAO dao = new ProductDAO(factory);
            Product product = dao.findProduct(id);

            if (product == null) {
                request.setAttribute("msg", "Product not found.");
                link = list(request, response);
            } else {
                Map<String, Object> fields = new LinkedHashMap<>(6);
                fields.put("Id", product.getId());
                fields.put("Nome", product.getNome());
                fields.put("Description", product.getDescription());
                fields.put("Price", product.getPrice());
                fields.put("Category", product.getCategory().getNome());

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
            ProductDAO dao = new ProductDAO(factory);

            try {
                dao.destroy(id);
                request.setAttribute("msg", "Product deleted successfully.");
            } catch (NonexistentEntityException ex) {
                request.setAttribute("msg", "Product not found.");
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
}
