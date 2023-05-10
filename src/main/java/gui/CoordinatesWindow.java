package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CoordinatesWindow extends JInternalFrame implements Observer {
    private DataModel m_model;
    private JTextArea coordinate;

    public CoordinatesWindow(DataModel model) {
        super("Координаты робота", true, true, true, true);
        m_model = model;
        m_model.addObserver(this);

        JPanel panel = new JPanel(new BorderLayout());
        coordinate = new JTextArea();
        coordinate.setPreferredSize(new Dimension(250, 80));
        panel.add(coordinate, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        coordinate.setText("X: "+ String.valueOf(m_model.getRobotPositionX()) + "\n" + "Y: " + String.valueOf(m_model.getRobotPositionY()));
        setDoubleBuffered(true);
    }


    @Override
    public void update(Observable o, Object arg) {
        onCoordinatesChange();
    }

    private void onCoordinatesChange() {
        coordinate.setText("X: "+ String.valueOf(m_model.getRobotPositionX()) + "\n" + "Y: " + String.valueOf(m_model.getRobotPositionY()));
    }

}
