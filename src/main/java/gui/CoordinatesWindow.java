package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CoordinatesWindow extends JInternalFrame implements Observer {
    private DataModel m_model;
    private JTextArea coordinate;
    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    public CoordinatesWindow(DataModel model)
    {
        super("Координаты робота", true, true, true, true);
        m_model = model;
        m_model.addObserver(this);

        JPanel panel = new JPanel(new BorderLayout());
        coordinate = new JTextArea();
        coordinate.setPreferredSize(new Dimension(250, 80));
        panel.add(coordinate, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
//        coordinate.setText(String.valueOf(m_robotPositionY) +"\n" + String.valueOf(m_robotPositionX));
        setDoubleBuffered(true);
    }


    @Override
    public void update(Observable o, Object arg) {
        onCoordinatesChange();
    }

    private void onCoordinatesChange(){
        double m_robotPositionX = m_model.getRobotPositionX();
        double m_robotPositionY = m_model.getRobotPositionY();
        coordinate.setText(String.valueOf(m_robotPositionY) +"\n" + String.valueOf(m_robotPositionX));
        repaint();
    }
}
