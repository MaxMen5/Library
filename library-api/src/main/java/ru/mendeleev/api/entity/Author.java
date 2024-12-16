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
public class Author implements IEntity, Serializable {
    private Integer id;
    private String name;
    private Integer countryId;
    private Integer birthYear;
    private String bookList;
}
