package ru.mendeleev.server.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import ru.mendeleev.entity.IEntity;

public interface IDao<E extends IEntity> {
    RowMapper<E> rowMapper();
}
