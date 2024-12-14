package ru.mendeleev.gui;

import org.springframework.stereotype.Component;
import ru.mendeleev.dao.interfaces.IAuthorDao;
import ru.mendeleev.dao.interfaces.IBookDao;
import ru.mendeleev.dao.interfaces.IGenreDao;
import ru.mendeleev.editClasses.BookEdit;
import ru.mendeleev.editClasses.BookFilter;
import ru.mendeleev.editClasses.BookLists;
import ru.mendeleev.editClasses.FullBook;
import ru.mendeleev.editClasses.SmallAuthor;
import ru.mendeleev.entity.Genre;
import ru.mendeleev.service.AuthManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class BookPanel extends JPanel {

    private final BookTableModel tableModel = new BookTableModel();
    private final JTable table = new JTable(tableModel);

    BookLists bookLists = new BookLists();

    private final IAuthorDao authorDao;
    private final IGenreDao genreDao;
    private final IBookDao bookDao;

    private final JTextField filterNameField = new JTextField();
    private final JTextField filterAuthorField = new JTextField();
    private final JTextField filterYearField = new JTextField();
    private final JTextField filterGenreField = new JTextField();
    private final JTextField filterPagesField = new JTextField();

    private AuthManager authManager;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    public BookPanel(IBookDao bookDao, AuthManager authManager, IAuthorDao authorDao, IGenreDao genreDao) {
        this.bookDao = bookDao;
        this.authManager = authManager;
        this.authorDao = authorDao;
        this.genreDao = genreDao;

        bookLists.setAuthors(authorDao.findSmallAuthors());
        bookLists.setGenres(genreDao.findAll());

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
        table.removeColumn(table.getColumnModel().getColumn(3));

        refreshTableData();
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

        toolBar.add(new JLabel("   Название: "));
        toolBar.add(filterNameField);
        filterNameField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Автор: "));
        toolBar.add(filterAuthorField);
        filterAuthorField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Год выхода: "));
        toolBar.add(filterYearField);
        filterYearField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Жанр: "));
        toolBar.add(filterGenreField);
        filterGenreField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Кол-во страниц: "));
        toolBar.add(filterPagesField);
        filterPagesField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JButton(new FilterBookAction()));

        return toolBar;
    }

    public void refreshTableData() {
        boolean isLoggedIn = authManager.isLoggedIn();
        addButton.setEnabled(isLoggedIn);
        editButton.setEnabled(isLoggedIn);
        removeButton.setEnabled(isLoggedIn);

        BookFilter bookFilter = new BookFilter();
        bookFilter.setName(filterNameField.getText());
        bookFilter.setAuthor(filterAuthorField.getText());
        bookFilter.setYear(filterYearField.getText());
        bookFilter.setGenre(filterGenreField.getText());
        bookFilter.setPage(filterPagesField.getText());

        List<FullBook> allBooks = bookDao.findAll(bookFilter);
        tableModel.initWith(allBooks);
        table.revalidate();
        table.repaint();
    }

    private class AddBookAction extends AbstractAction {
        AddBookAction() {
            putValue(SHORT_DESCRIPTION, "Добавить книгу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EditBookDialog editBookDialog = new EditBookDialog(bookLists, bookEdit -> {
                bookDao.saveBook(bookEdit);
                refreshTableData();
            });
            editBookDialog.setLocationRelativeTo(BookPanel.this);
            editBookDialog.setVisible(true);
        }
    }

    private class EditBookAction extends AbstractAction {
        EditBookAction() {
            putValue(SHORT_DESCRIPTION, "Изменить книгу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        BookPanel.this,
                        "Для редпктирования выберите книгу!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedBookId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);

            BookEdit bookEdit = new BookEdit();
            bookEdit.setName((String) tableModel.getValueAt(selectedRowIndex, 1));

            SmallAuthor author = new SmallAuthor();
            author.setId((Integer) tableModel.getValueAt(selectedRowIndex, 2));
            author.setName((String) tableModel.getValueAt(selectedRowIndex, 3));

            bookEdit.setAuthor(author);
            bookEdit.setYear((Integer) tableModel.getValueAt(selectedRowIndex, 4));

            Genre genre = new Genre();
            genre.setId((Integer) tableModel.getValueAt(selectedRowIndex, 5));
            genre.setName((String) tableModel.getValueAt(selectedRowIndex, 6));

            bookEdit.setGenre(genre);
            bookEdit.setPages((Integer) tableModel.getValueAt(selectedRowIndex, 7));

            EditBookDialog editBookDialog = new EditBookDialog(bookLists, bookEdit, changedBook -> {
                bookDao.update(selectedBookId, changedBook);
                refreshTableData();
            });
            editBookDialog.setLocationRelativeTo(BookPanel.this);
            editBookDialog.setVisible(true);
        }
    }

    private class RemoveBookAction extends AbstractAction {
        RemoveBookAction() {
            putValue(SHORT_DESCRIPTION, "Удалить книгу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        BookPanel.this,
                        "Для удаления выберите книгу!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedBookId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedBookName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            if (JOptionPane.showConfirmDialog(
                    BookPanel.this,
                    "Удалить книгу '" + selectedBookName + "'?",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                bookDao.deleteBookById(selectedBookId);
                refreshTableData();
            }
        }
    }

    private class FilterBookAction extends AbstractAction {
        FilterBookAction() {
            putValue(SHORT_DESCRIPTION, "Фильтровать книги");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_refresh.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTableData();
        }
    }
}
