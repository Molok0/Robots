package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import localization.LanguageAdapter;
import localization.LanguageModel;
import localization.LanguageUpdate;
import localization.Localization;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener, LanguageUpdate {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    private LanguageModel languageModel = LanguageModel.getInstance();
    private LanguageAdapter languageAdapter;

    public LogWindow(LogWindowSource logSource) {
        super(Localization.get("window_protocol"), true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
//        addPropertyChangeListener(new LanguageAdapter(this, languageModel));
        languageAdapter = new LanguageAdapter(this, languageModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();

    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void changeLanguage() {
        setTitle(Localization.get("window_protocol"));
    }
}
