package ru.mendeleev.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Genre implements IEntity {
    private Integer id;
    private String name;

}

