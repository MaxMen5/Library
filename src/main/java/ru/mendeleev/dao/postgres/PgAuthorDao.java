package ru.mendeleev.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.dao.interfaces.AbstractDao;
import ru.mendeleev.dao.interfaces.IAuthorDao;
import ru.mendeleev.editClasses.AuthorEdit;
import ru.mendeleev.editClasses.FullAuthor;
import ru.mendeleev.editClasses.SmallAuthor;
import ru.mendeleev.entity.Author;

import java.util.List;

@Component
@Lazy
public class PgAuthorDao extends AbstractDao<Author> implements IAuthorDao {

    @Override
    public List<FullAuthor> findAll() {
        return query("select " +
                "a.id as id, " +
                "a.name as name, " +
                "a.author_country_id as author_country_id, " +
                "c.name as author_country_name, " +
                "a.birthday_year as birthday_year " +
                "from author a " +
                "inner join country c on a.author_country_id = c.id " +
                "order by a.id", fullRowMapper());
    }

    @Override
    public List<SmallAuthor> findSmallAuthors() {
        return query("select id, name from author order by id", smallRowMapper());
    }

    @Override
    public void deleteAuthorById(Integer id) {
        update("delete from author where id = " + id);
    }

    @Override
    public void saveAuthor(AuthorEdit author) {
        update("insert into author (name, author_country_id, birthday_year) values ('" +
                author.getName() + "', " +
                author.getCountry().getId() + ", " +
                author.getYear() + ");");
    }

    @Override
    public void update(Integer id, AuthorEdit author) {
        update("update author set name = '" + author.getName() + "', " +
                "author_country_id = " + author.getCountry().getId() +
                ", birthday_year = " + author.getYear() +
                " where id = " + id);
    }
}
