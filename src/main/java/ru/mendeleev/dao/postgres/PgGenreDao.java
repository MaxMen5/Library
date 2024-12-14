package ru.mendeleev.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.dao.interfaces.AbstractDao;
import ru.mendeleev.dao.interfaces.IGenreDao;
import ru.mendeleev.entity.Genre;

import java.util.List;

@Component
@Lazy
public class PgGenreDao extends AbstractDao<Genre> implements IGenreDao {

    @Override
    public List<Genre> findAll() {
        return query("select * from genre order by id");
    }
}
