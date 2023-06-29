package localization;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;

public class LanguageModel {
    public static String PROPERTY_NAME = "LanguageModel.m_lang";

    private PropertyChangeSupport propChangeDispatcher = new PropertyChangeSupport(this);

    private static final LanguageModel INSTANCE = new LanguageModel();

    private LanguageModel() {

    }

    public static LanguageModel getInstance() {
        return INSTANCE;
    }

    public void setLocalization(Locale newLocale) {
        if (!Localization.getLocale().equals(newLocale)) {
            Locale oldLocale = Localization.getLocale();
            Localization.setLocalization(newLocale);
            propChangeDispatcher.firePropertyChange(PROPERTY_NAME, oldLocale, newLocale);
        }
    }

    public void addTextChangeListener(PropertyChangeListener listener) {
        propChangeDispatcher.addPropertyChangeListener(PROPERTY_NAME, listener);
    }
}
