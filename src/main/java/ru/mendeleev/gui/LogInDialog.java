package ru.mendeleev.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mendeleev.service.AuthManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class LogInDialog extends JDialog {

    @Autowired
    private AuthManager authManager;
    @Autowired
    private BookPanel bookPanel;

    private static final String TITLE = "Авторизация";

    private final JTextField loginField = new JTextField();
    private final JTextField passwordField = new JTextField();

    public LogInDialog() {
        setTitle(TITLE);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));

        JPanel loginPanel = new JPanel(new BorderLayout());
        JPanel passwordPanel = new JPanel(new BorderLayout());

        loginPanel.add(new JLabel("Логин: "), BorderLayout.WEST);
        loginPanel.add(loginField, BorderLayout.CENTER);

        passwordPanel.add(new JLabel("Пароль: "), BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        mainPanel.add(loginPanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(new JButton(new ButtonAction()));

        getContentPane().add(mainPanel);

        setSize(400, 120);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class ButtonAction extends AbstractAction {
        ButtonAction() {
            putValue(NAME, "Авторизоваться");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (loginField.getText().equals("1") && passwordField.getText().equals("1")) {
                authManager.setLoggedIn(true);
                bookPanel.refreshTableData();
                dispose();
            }
            loginField.setText("");
            passwordField.setText("");
        }
    }

}
