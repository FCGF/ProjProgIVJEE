package br.org.catolicasc.fcgf.projprog4.web.cmds;

import br.org.catolicasc.fcgf.projprog4.web.abstracts.AbstractWebCmd;
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
        List<Product> products = findAllProducts();

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

    private List<Product> findAllProducts() {
        EntityManagerFactory factory = EntityManagerFactoryManager.getEntityManagerFactory();

        ProductDAO dao = new ProductDAO(factory);

        return dao.findAll();
    }
}
