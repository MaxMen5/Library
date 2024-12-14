package ru.mendeleev.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.dao.interfaces.AbstractDao;
import ru.mendeleev.dao.interfaces.IBookDao;
import ru.mendeleev.entity.Book;

import java.util.List;

@Component
@Lazy
public class PgBookDao extends AbstractDao<Book> implements IBookDao {

    @Override
    public List<Book> findAll() {
        return query("select * from book order by id");
    }
}
