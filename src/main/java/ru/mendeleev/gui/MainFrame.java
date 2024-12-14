package ru.mendeleev.gui;

import org.springframework.stereotype.Component;
import ru.mendeleev.service.AuthManager;
import ru.mendeleev.utils.CommonUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

@Component
public final class MainFrame extends JFrame {

    private static final String TITLE = "Library";

    private final AuthManager authManager;
    private final BookPanel bookPanel;

    public MainFrame(AuthManager authManager, BookPanel bookPanel) {
        this.authManager = authManager;
        this.bookPanel = bookPanel;
    }

    @PostConstruct
    public void init() {
        setTitle(TITLE);
        createGUI();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Вы действительно хотите выйти?",
                        "Выйти?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(CommonUtils.prepareWindowSizeWithShifts(0, 40));
        setVisible(true);
    }

    private void createGUI() {
// TODO
        getContentPane().add(createTabbedPane(), BorderLayout.CENTER);
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Игроки", bookPanel);

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

}
