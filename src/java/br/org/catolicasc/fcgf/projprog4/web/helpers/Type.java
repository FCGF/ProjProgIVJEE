package br.org.catolicasc.fcgf.projprog4.web.helpers;

/**
 *
 * @author Fernando
 */
public enum Type {

    TEXT("TEXT"),
    NUMBER("NUMBER"),
    PASSWORD("PASSWORD");

    private final String type;

    private Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
