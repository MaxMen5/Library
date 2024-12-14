package ru.mendeleev.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.entity.Country;
import ru.mendeleev.entity.Genre;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthorEdit {
    private String name;
    private Country country;
    private Integer year;
}