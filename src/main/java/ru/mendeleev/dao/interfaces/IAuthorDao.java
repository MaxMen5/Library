package ru.mendeleev.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.editClasses.SmallAuthor;
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

    @Transactional
    public List<SmallAuthor> findAllAuthors();

    default RowMapper<SmallAuthor> smallRowMapper() {
        return (resultSet, i) -> {
            SmallAuthor author = new SmallAuthor();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            return author;
        };
    }
}
