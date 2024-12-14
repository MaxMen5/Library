package ru.mendeleev.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.dao.interfaces.AbstractDao;
import ru.mendeleev.dao.interfaces.IBookDao;
import ru.mendeleev.editClasses.FullBook;
import ru.mendeleev.entity.Book;

import java.util.List;

@Component
@Lazy
public class PgBookDao extends AbstractDao<Book> implements IBookDao {

    @Override
    public List<FullBook> findAll() {
        return query("select " +
                "b.id as id, " +
                "b.name as name, " +
                "b.book_author_id as author_country_id, " +
                "a.name as author_name, " +
                "b.year as birthday_year, " +
                "b.book_genre_id as book_genre_id, " +
                "g.name as book_genre_name, " +
                "b.page_count as page_count " +
                "from book b " +
                "inner join author a on b.book_author_id = a.id " +
                "inner join genre g on b.book_genre_id = g.id " +
                "order by b.id", fullRowMapper());
    }

    @Override
    public void deleteBookById(Integer id) {
        query("delete from book where id = " + id);
    }

    @Override
    public void saveBook(Book book) {
        update("insert into book(");
    }

}
