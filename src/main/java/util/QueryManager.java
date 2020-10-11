package util;

import java.util.ResourceBundle;

public class QueryManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("queries");

    public QueryManager() {
    }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
