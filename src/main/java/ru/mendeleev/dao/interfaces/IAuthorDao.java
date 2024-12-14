package ru.mendeleev.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.entity.Author;

import java.util.List;

public interface IAuthorDao extends IDao<Author> {

    @Override
    default RowMapper<Author> rowMapper() {
        return (resultSet, i) -> {
            Author author = new Author();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            author.setCountryId(resultSet.getInt("author_country_id"));
            author.setBirthYear(resultSet.getInt("birthday_year"));
            return author;
        };
    }

    //================================================================================================================//

    @Transactional
    public List<Author> findAll();
}
