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
    private final AuthorPanel authorPanel;
    private final LogInDialog logInDialog;


    public MainFrame(AuthManager authManager, BookPanel bookPanel, LogInDialog logInDialog, AuthorPanel authorPanel) {
        this.authManager = authManager;
        this.bookPanel = bookPanel;
        this.logInDialog = logInDialog;
        this.authorPanel = authorPanel;
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
        setJMenuBar(createMenuBar());
        getContentPane().add(createTabbedPane(), BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JButton authorization = new JButton("Войти");
        menuBar.add(authorization);

        authorization.addActionListener(e -> {
            if (!authManager.isLoggedIn()) {
                logInDialog.setLocationRelativeTo(MainFrame.this);
                logInDialog.setVisible(true);
                if (authManager.isLoggedIn()) authorization.setText("Выйти");
            } else {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Вы действительно хотите выйти?",
                        "Вопрос",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    authManager.setLoggedIn(false);
                    authorPanel.refreshTableData();
                    bookPanel.refreshTableData();
                    authorization.setText("Войти");
                }
            }
        });

        return menuBar;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Игроки", bookPanel);
        tabbedPane.addTab("Игроки", authorPanel);

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

}
