package ru.mendeleev.api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Author implements IEntity {
    private Integer id;
    private String name;
    private Integer countryId;
    private Integer birthYear;
    private String bookList;
}
