package ru.mendeleev.server.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.server.dao.interfaces.AbstractDao;
import ru.mendeleev.server.dao.interfaces.ICountryDao;
import ru.mendeleev.entity.Country;

import java.util.List;

@Component
@Lazy
public class PgCountryDao extends AbstractDao<Country> implements ICountryDao {

    @Override
    public List<Country> findAll() {
        return query("select * from country order by id");
    }
}
