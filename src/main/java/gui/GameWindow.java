package gui;

import localization.LanguageAdapter;
import localization.LanguageModel;
import localization.LanguageUpdate;
import localization.Localization;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements LanguageUpdate {
    private final GameVisualizer m_visualizer;
    private DataModel m_model;
    private LanguageAdapter languageAdapter;
    private LanguageModel languageModel = LanguageModel.getInstance();

    public GameWindow(DataModel model) {
        super(Localization.get("window_game"), true, true, true, true);
        m_visualizer = new GameVisualizer(model);
        JPanel panel = new JPanel(new BorderLayout());
//        addPropertyChangeListener(new LanguageAdapter(this, languageModel));
        languageAdapter = new LanguageAdapter(this, languageModel);
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void changeLanguage() {
        setTitle(Localization.get("window_game"));
    }
}
