package ru.mendeleev.gui;

import ru.mendeleev.editClasses.AuthorEdit;
import ru.mendeleev.editClasses.AuthorLists;
import ru.mendeleev.editClasses.BookEdit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import ru.mendeleev.editClasses.SmallAuthor;
import ru.mendeleev.entity.Country;
import ru.mendeleev.entity.Genre;
import ru.mendeleev.utils.CommonUtils;
import java.util.function.Consumer;

import static ru.mendeleev.utils.CommonUtils.isBlank;


public class EditAuthorDialog extends JDialog {

    private static final String TITLEADD = "Добавление автора";
    private static final String TITLEEDIT = "Редактирование автора";

    private final JComboBox countries = new JComboBox();
    private final JTextField nameField = new JTextField();
    private final JTextField yearField = new JTextField();

    private final AuthorLists authorList;
    private final AuthorEdit prevData;
    private final Consumer<AuthorEdit> newAuthorConsumer;

    public EditAuthorDialog(AuthorLists authorList, Consumer<AuthorEdit> newAuthorConsumer) {
        this(authorList, null, newAuthorConsumer);
    }

    public EditAuthorDialog(AuthorLists authorList, AuthorEdit prevData, Consumer<AuthorEdit> newAuthorConsumer) {
        this.newAuthorConsumer = newAuthorConsumer;
        this.authorList = authorList;
        this.prevData = prevData;

        if (prevData != null) {setTitle(TITLEEDIT);}
        else setTitle(TITLEADD);

        for (int i = 0; i < authorList.getCountry().size(); i++) {
            countries.addItem(authorList.getCountry().get(i).getName());
        }

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel countryPanel = new JPanel(new BorderLayout());
        JPanel yearPanel = new JPanel(new BorderLayout());

        namePanel.add(new JLabel("Имя:"), BorderLayout.WEST);
        countryPanel.add(new JLabel("Автор:"), BorderLayout.WEST);
        yearPanel.add(new JLabel("Год выхода:"), BorderLayout.WEST);

        if (prevData != null) {
            nameField.setText(prevData.getName());
            countries.setSelectedItem(prevData.getCountry().getName());
            yearField.setText(CommonUtils.toStringSafe(prevData.getYear()));
        }

        namePanel.add(nameField, BorderLayout.CENTER);
        countryPanel.add(countries, BorderLayout.CENTER);
        yearPanel.add(yearField, BorderLayout.CENTER);

        mainPanel.add(namePanel);
        mainPanel.add(countryPanel);
        mainPanel.add(yearPanel);
        mainPanel.add(new JButton(new EditAuthorDialog.SaveAction()));

        getContentPane().add(mainPanel);
        setSize(400, 270);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevData != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isBlank(nameField.getText())
                    || countries.getSelectedItem() == null
                    || isBlank(yearField.getText())) {
                JOptionPane.showMessageDialog(
                        EditAuthorDialog.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Country country = new Country();
            for (int i = 0; i < authorList.getCountry().size(); i++) {
                if (authorList.getCountry().get(i).getName().equals(countries.getSelectedItem())) {
                    country = authorList.getCountry().get(i);
                    break;
                }
            }

            AuthorEdit authorEdit = new AuthorEdit();
            authorEdit.setName(nameField.getText());
            authorEdit.setCountry(country);
            authorEdit.setYear(Integer.parseInt(yearField.getText()));
            newAuthorConsumer.accept(authorEdit);
            dispose();
        }
    }

}
