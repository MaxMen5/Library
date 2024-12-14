package ru.mendeleev.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
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
    public List<Book> findAll();
}
