package gui;

import localization.LanguageAdapter;
import localization.LanguageModel;
import localization.LanguageUpdate;
import localization.Localization;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CoordinatesWindow extends JInternalFrame implements Observer, LanguageUpdate {
    private DataModel m_model;
    private JTextArea coordinate;

    private LanguageModel languageModel = LanguageModel.getInstance();

    public CoordinatesWindow(DataModel model) {
        super(Localization.get("window_coordinates"), true, true, true, true);
        m_model = model;
        m_model.addObserver(this);

        JPanel panel = new JPanel(new BorderLayout());
        coordinate = new JTextArea();
        coordinate.setPreferredSize(new Dimension(250, 80));

        addPropertyChangeListener(new LanguageAdapter(this, languageModel));

        panel.add(coordinate, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        coordinate.setText("X: " + String.valueOf(m_model.getRobotPositionX()) + "\n" + "Y: " + String.valueOf(m_model.getRobotPositionY()));
        setDoubleBuffered(true);
    }


    @Override
    public void update(Observable o, Object key) {
        if (DataModel.RobotCoordinatesChangedEvent == key)
            onCoordinatesChange();
    }

    private void onCoordinatesChange() {
        coordinate.setText("X: " + String.valueOf(m_model.getRobotPositionX()) + "\n" + "Y: " + String.valueOf(m_model.getRobotPositionY()));
    }

    @Override
    public void changeLanguage() {
        setTitle(Localization.get("window_coordinates"));
    }
}
