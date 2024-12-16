package ru.mendeleev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.entity.IEntity;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FullBook implements IEntity {
    private Integer id;
    private String name;
    private Integer authorId;
    private String authorName;
    private Integer year;
    private Integer genreId;
    private String genreName;
    private Integer pageCount;
}
