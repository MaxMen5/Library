package ru.mendeleev.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.dao.interfaces.AbstractDao;
import ru.mendeleev.dao.interfaces.IAuthorDao;
import ru.mendeleev.entity.Author;

import java.util.List;

@Component
@Lazy
public class PgAuthorDao extends AbstractDao<Author> implements IAuthorDao {

    @Override
    public List<Author> findAll() {
        return query("select * from author order by id");
    }
}
