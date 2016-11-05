package br.org.catolicasc.fcgf.projprog4.web.abstracts;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Fabio Tavares Dippold, FCGF
 */
public abstract class AbstractWebCmd {

    protected static final String PERSISTENCE_UNIT_NAME = "PersistenciaPU";

    protected static final String ID = "Id";
    protected static final String NOME = "Nome";
    protected static final String NAME = "name";
    protected static final String SEARCH = "srch";
    protected static final String OBJECTS = "objects";
    protected static final String FIELDS = "fields";
    protected static final String ERROR = "error";
    protected static final String MESSAGE = "msg";

    protected static final String LIST_PATH = "/WEB-INF/views/list.jsp";
    protected static final String CREATE_PATH = "/WEB-INF/views/create.jsp";
    protected static final String UPDATE_PATH = "/WEB-INF/views/update.jsp";
    protected static final String DETAIL_PATH = "/WEB-INF/views/detail.jsp";

    protected void setDefaultAppModel(HttpServletRequest request) {
        request.setAttribute("appName", "ProjProgIV");
    }

    protected final String readParameter(HttpServletRequest request, String parameterName,
            String defaultValue) {
        String value = request.getParameter(parameterName);
        if ((value == null) || (value.equals(""))) {
            value = defaultValue;
        }

        return value;
    }

    protected void setCmdName(HttpServletRequest request) {
        final String clazz = this.getClass().getSimpleName();
        request.setAttribute("cmd", clazz);
    }

}
