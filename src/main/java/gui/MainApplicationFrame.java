package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;

import localization.LanguageAdapter;
import localization.LanguageModel;
import localization.LanguageUpdate;
import localization.Localization;
import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame implements LanguageUpdate {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private DataModel m_model;
    private LanguageModel languageModel = LanguageModel.getInstance();

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);


        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);
        m_model = new DataModel();
        CoordinatesWindow coordinatesWindow = new CoordinatesWindow(m_model);
        coordinatesWindow.setSize(200, 200);
        addWindow(coordinatesWindow);

        GameWindow gameWindow = new GameWindow(m_model);
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        addPropertyChangeListener(new LanguageAdapter(this, languageModel));
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
//
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(generateLookAndFeelMenu());
        menuBar.add(generateTestMenu());
        menuBar.add(generateExitMenu());
        menuBar.add(generateLangMenu());
        return menuBar;
    }

    private JMenu generateLangMenu() {
        JMenu locale = new JMenu(Localization.get("lang"));
        locale.setMnemonic(KeyEvent.VK_T);
        locale.getAccessibleContext().setAccessibleDescription(Localization.get("exit_from")
        );

        {
            JMenuItem setLocaleEng = new JMenuItem(Localization.get("lang_en"), KeyEvent.VK_S);
            setLocaleEng.addActionListener((event) -> {
                languageModel.setLocalization(new Locale("en_UK"));
            });
            JMenuItem setLocaleRu = new JMenuItem(Localization.get("lang_ru"), KeyEvent.VK_S);
            setLocaleEng.addActionListener((event) -> {
                languageModel.setLocalization(new Locale("ru_RU"));
            });
            locale.add(setLocaleEng);
            locale.add(setLocaleRu);
        }
        return locale;
    }


    private JMenu generateExitMenu() {
        JMenu exitMenu = new JMenu(Localization.get("exit"));
        exitMenu.setMnemonic(KeyEvent.VK_T);
        exitMenu.getAccessibleContext().setAccessibleDescription(
                Localization.get("exit_from"));

        {
            JMenuItem setExit = new JMenuItem("Выход", KeyEvent.VK_S);
            setExit.addActionListener((event) -> {
                int dialogResult =
                        JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите закрыть окно?", "Подтверждение", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                } else {
                    // оставить окно открытым
                }

            });
            exitMenu.add(setExit);
        }
        return exitMenu;
    }

    private JMenu generateTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }
        return lookAndFeelMenu;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }

    @Override
    public void changeLanguage() {
        this.setJMenuBar(generateMenuBar());
    }
}
