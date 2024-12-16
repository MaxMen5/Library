package ru.mendeleev.api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Book implements IEntity, Serializable {
    private Integer id;
    private String name;
    private Integer authorId;
    private Integer year;
    private Integer genreId;
    private Integer pageCount;
}
