package br.org.catolicasc.fcgf.projprog4.web.abstracts;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Fabio Tavares Dippold, FCGF
 */
public abstract class AbstractWebCmd {

    protected static final String PERSISTENCE_UNIT_NAME = "PersistenciaPU";

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
