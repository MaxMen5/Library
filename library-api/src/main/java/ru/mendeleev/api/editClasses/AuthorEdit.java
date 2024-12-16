package ru.mendeleev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.api.entity.Country;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthorEdit {
    private String name;
    private Country country;
    private Integer year;
    private List<FullBook> book;
}