package localization;


import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    private static Locale locale;
    private static ResourceBundle resourceBundle;

    static {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("resources", Locale.getDefault());
    }

    public static String get(String key) {
        return resourceBundle.getString(key);
    }

    public static void setLocalization(Locale newLocale) {
        locale = newLocale;
        resourceBundle = ResourceBundle.getBundle("resources", locale);
    }

    public static Locale getLocale() {
        return locale;
    }
}
