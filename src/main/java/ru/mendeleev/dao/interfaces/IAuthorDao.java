package ru.mendeleev.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.editClasses.AuthorEdit;
import ru.mendeleev.editClasses.BookEdit;
import ru.mendeleev.editClasses.FullAuthor;
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
    public List<FullAuthor> findAll();

    @Transactional
    public List<SmallAuthor> findSmallAuthors();

    @Transactional
    public void deleteAuthorById(Integer id);

    @Transactional
    public void saveAuthor(AuthorEdit author);

    @Transactional
    public void update(Integer Id, AuthorEdit author);

    default RowMapper<SmallAuthor> smallRowMapper() {
        return (resultSet, i) -> {
            SmallAuthor author = new SmallAuthor();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            return author;
        };
    }

    default RowMapper<FullAuthor> fullRowMapper() {
        return (resultSet, i) -> {
            FullAuthor author = new FullAuthor();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            author.setCountryId(resultSet.getInt("author_country_id"));
            author.setCountryName(resultSet.getString("author_country_name"));
            author.setBirthYear(resultSet.getInt("birthday_year"));
            return author;
        };
    }
}
