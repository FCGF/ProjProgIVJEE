package br.org.catolicasc.fcgf.projprog4.web.helpers;

/**
 *
 * @author Fernando
 */
public final class ParseHelper {

    private ParseHelper() {
    }

    public static final boolean tryParseDouble(String value) {
        boolean isDouble = false;
        try {
            Double.parseDouble(value);
            isDouble = true;
        } finally {
            return isDouble;
        }
    }

    public static final boolean tryParseLong(String value) {
        boolean isLong = false;
        try {
            Long.parseLong(value);
            isLong = true;
        } finally {
            return isLong;
        }
    }

}
