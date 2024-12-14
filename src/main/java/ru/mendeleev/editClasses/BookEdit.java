package ru.mendeleev.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.entity.Author;
import ru.mendeleev.entity.Genre;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookEdit {
    private String name;
    private SmallAuthor author;
    private Integer year;
    private Genre genre;
    private Integer pages;
}
