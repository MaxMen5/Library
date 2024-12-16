package ru.mendeleev.client.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mendeleev.dao.interfaces.IAuthorDao;
import ru.mendeleev.dao.interfaces.IBookDao;
import ru.mendeleev.dao.interfaces.ICountryDao;
import ru.mendeleev.editClasses.AuthorEdit;
import ru.mendeleev.editClasses.AuthorFilter;
import ru.mendeleev.editClasses.AuthorLists;
import ru.mendeleev.editClasses.BookFilter;
import ru.mendeleev.editClasses.FullAuthor;
import ru.mendeleev.entity.Country;
import ru.mendeleev.service.AuthManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class AuthorPanel extends JPanel {

    private final AuthorTableModel tableModel = new AuthorTableModel();
    private final JTable table = createTable();

    @Autowired
    private BookPanel bookPanel;

    private AuthorLists authorList = new AuthorLists();

    private final IAuthorDao authorDao;
    private final ICountryDao countryDao;
    private  final IBookDao bookDao;
    private AuthManager authManager;

    private final JTextField filterNameField = new JTextField();
    private final JTextField filterCountryField = new JTextField();
    private final JTextField filterYearField = new JTextField();

    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    public AuthorPanel(AuthManager authManager, IAuthorDao authorDao, ICountryDao countryDao, IBookDao bookDao) {
        this.authManager = authManager;
        this.authorDao = authorDao;
        this.countryDao = countryDao;
        this.bookDao = bookDao;
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createBookToolBar(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(1));

        refreshTableData();
    }

    private JTable createTable() {
        JTable table = new JTable(tableModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int column = columnAtPoint(p);

                Object value = getValueAt(row, column);
                if (value == null) {
                    return null;
                }

                String strValue = value.toString();
                if (!strValue.isEmpty()) {
                    return "<html>" + strValue.replaceAll(",", "<br>") + "</html>";
                }

                return (String) value;
            }
        };
        return table;
    }

    private JToolBar createBookToolBar() {
        JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);

        toolBar.setFloatable(false);
        addButton = new JButton(new AddBookAction());
        addButton.setEnabled(false);
        toolBar.add(addButton);
        editButton = new JButton(new EditBookAction());
        editButton.setEnabled(false);
        toolBar.add(editButton);
        removeButton = new JButton(new RemoveBookAction());
        removeButton.setEnabled(false);
        toolBar.add(removeButton);
        toolBar.add(new JLabel("   Имя: "));
        toolBar.add(filterNameField);
        filterNameField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Страна: "));
        toolBar.add(filterCountryField);
        filterCountryField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Год рождения: "));
        toolBar.add(filterYearField);
        filterYearField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JButton(new AuthorPanel.FilterAuthorAction()));

        return toolBar;
    }

    public void refreshTableData() {
        boolean isLoggedIn = authManager.isLoggedIn();
        addButton.setEnabled(isLoggedIn);
        editButton.setEnabled(isLoggedIn);
        removeButton.setEnabled(isLoggedIn);

        AuthorFilter filter = new AuthorFilter();
        filter.setName(filterNameField.getText());
        filter.setCountry(filterCountryField.getText());
        filter.setYear(filterYearField.getText());

        List<FullAuthor> allAuthors = authorDao.findAll(filter);
        tableModel.initWith(allAuthors);
        table.revalidate();
        table.repaint();
    }

    private class AddBookAction extends AbstractAction {
        AddBookAction() {
            putValue(SHORT_DESCRIPTION, "Добавить автора");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            authorList.setCountry(countryDao.findAll());
            authorList.setBook(bookDao.findAll());

            EditAuthorDialog editauthorDialog = new EditAuthorDialog(authorList, authorEdit -> {
                authorDao.saveAuthor(authorEdit);
                refreshTableData();
                bookPanel.refreshTableData();
            });
            editauthorDialog.setLocationRelativeTo(AuthorPanel.this);
            editauthorDialog.setVisible(true);
        }
    }

    private class EditBookAction extends AbstractAction {
        EditBookAction() {
            putValue(SHORT_DESCRIPTION, "Изменить автора");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        AuthorPanel.this,
                        "Для редпктирования выберите автора!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedAuthorId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);

            AuthorEdit authorEdit = new AuthorEdit();
            authorEdit.setName((String) tableModel.getValueAt(selectedRowIndex, 1));

            Country country = new Country();
            country.setId((Integer) tableModel.getValueAt(selectedRowIndex, 2));
            country.setName((String) tableModel.getValueAt(selectedRowIndex, 3));

            authorEdit.setCountry(country);
            authorEdit.setYear((Integer) tableModel.getValueAt(selectedRowIndex, 4));
            authorEdit.setBook(bookDao.findAuthorBooks(selectedAuthorId));

            authorList.setCountry(countryDao.findAll());
            authorList.setBook(bookDao.findNotAllBooks(selectedAuthorId));

            EditAuthorDialog editAuthorDialog = new EditAuthorDialog(authorList, authorEdit, changedAuthor -> {
                authorDao.update(selectedAuthorId, changedAuthor);
                refreshTableData();
                bookPanel.refreshTableData();
            });
            editAuthorDialog.setLocationRelativeTo(AuthorPanel.this);
            editAuthorDialog.setVisible(true);
        }
    }

    private class RemoveBookAction extends AbstractAction {
        RemoveBookAction() {
            putValue(SHORT_DESCRIPTION, "Удалить автора");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        AuthorPanel.this,
                        "Для удаления выберите автора!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedAuthorId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedAuthorName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            if (JOptionPane.showConfirmDialog(
                    AuthorPanel.this,
                    "Удалить автора '" + selectedAuthorName + "'? Все его книги будут также удалены!",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                authorDao.deleteAuthorById(selectedAuthorId);
                bookDao.deleteAuthorBooks(selectedAuthorId);
                refreshTableData();
                bookPanel.refreshTableData();
            }
        }
    }

    private class FilterAuthorAction extends AbstractAction {
        FilterAuthorAction() {
            putValue(SHORT_DESCRIPTION, "Фильтровать авторов");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_refresh.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTableData();
        }
    }
}
