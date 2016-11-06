package br.org.catolicasc.fcgf.projprog4.web.helpers;

import java.util.List;

/**
 *
 * @author Fernando
 * @param <T>
 */
public class FieldData<T> {

    private String name;
    private Object value;
    private boolean combo;
    private List<T> allValues;
    private Type type;

    public FieldData(String name, Object value, boolean combo, List<T> allValues, Type type) {
        setName(name);
        setValue(value);
        setCombo(combo);
        setAllValues(allValues);
        setType(type);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    private void setValue(Object value) {
        this.value = value;
    }

    public boolean isCombo() {
        return combo;
    }

    private void setCombo(boolean combo) {
        this.combo = combo;
    }

    public List<T> getAllValues() {
        return allValues;
    }

    private void setAllValues(List<T> allValues) {
        this.allValues = allValues;
    }

    public Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
    }
      
}
