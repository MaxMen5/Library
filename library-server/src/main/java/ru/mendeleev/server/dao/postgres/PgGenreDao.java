package ru.mendeleev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.server.dao.interfaces.AbstractDao;
import ru.mendeleev.server.dao.interfaces.IGenreDao;
import ru.mendeleev.api.entity.Genre;

import java.util.List;

@Component
@Lazy
public class PgGenreDao extends AbstractDao<Genre> implements IGenreDao {

    @Override
    public List<Genre> findAll() {
        return query("select * from genre order by id");
    }
}
