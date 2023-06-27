package localization;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LanguageAdapter implements
        PropertyChangeListener {

    private final LanguageUpdate instance;
    private final LanguageModel languageModel;

    public LanguageAdapter(LanguageUpdate instance, LanguageModel dispatcher) {
        this.instance = instance;
        this.languageModel = dispatcher;
        dispatcher.addTextChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent ev) {
        if (languageModel.equals(ev.getSource())) {
            if (LanguageModel.PROPERTY_NAME.equals(ev.getPropertyName())) {
                instance.changeLanguage();
            }
        }
    }
}
