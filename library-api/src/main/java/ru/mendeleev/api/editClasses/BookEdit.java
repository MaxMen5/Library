package ru.mendeleev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.api.entity.Genre;

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
