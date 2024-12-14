package ru.mendeleev.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.entity.Book;
import ru.mendeleev.entity.Country;

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