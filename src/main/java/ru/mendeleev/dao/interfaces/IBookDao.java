package ru.mendeleev.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.editClasses.BookEdit;
import ru.mendeleev.editClasses.BookFilter;
import ru.mendeleev.editClasses.FullBook;
import ru.mendeleev.entity.Book;

import java.util.List;

public interface IBookDao extends IDao<Book> {

    @Override
    default RowMapper<Book> rowMapper() {
        return (resultSet, i) -> {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthorId(resultSet.getInt("author_country_id"));
            book.setYear(resultSet.getInt("birthday_year"));
            book.setGenreId(resultSet.getInt("book_genre_id"));
            book.setPageCount(resultSet.getInt("page_count"));
            return book;
        };
    }

    //================================================================================================================//

    @Transactional
    public List<FullBook> findAll(BookFilter filter);

    @Transactional
    public List<FullBook> findAll();

    @Transactional
    public List<FullBook> findNotAllBooks(Integer id);

    @Transactional
    public void deleteBookById(Integer id);

    @Transactional
    public void saveBook(BookEdit book);

    @Transactional
    public void update(Integer Id, BookEdit book);


    default RowMapper<FullBook> fullRowMapper() {
        return (resultSet, i) -> {
            FullBook book = new FullBook();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthorId(resultSet.getInt("author_country_id"));
            book.setAuthorName(resultSet.getString("author_name"));
            book.setYear(resultSet.getInt("birthday_year"));
            book.setGenreId(resultSet.getInt("book_genre_id"));
            book.setGenreName(resultSet.getString("book_genre_name"));
            book.setPageCount(resultSet.getInt("page_count"));
            return book;
        };
    }
}
