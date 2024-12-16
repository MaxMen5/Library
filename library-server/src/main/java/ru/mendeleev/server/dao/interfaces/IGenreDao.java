package ru.mendeleev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.mendeleev.entity.Genre;

import java.util.List;

public interface IGenreDao extends IDao<Genre> {

    @Override
    default RowMapper<Genre> rowMapper() {
        return (resultSet, i) -> {
            Genre genre = new Genre();
            genre.setId(resultSet.getInt("id"));
            genre.setName(resultSet.getString("name"));
            return genre;
        };
    }

    //================================================================================================================//

    @Transactional
    public List<Genre> findAll();
}
