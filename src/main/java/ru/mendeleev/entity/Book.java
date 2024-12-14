package ru.mendeleev.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Book implements IEntity {
    private Integer id;
    private String name;
    private Integer authorId;
    private Integer year;
    private Integer genreId;
    private Integer pageCount;
}
