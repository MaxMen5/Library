package ru.mendeleev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.entity.Genre;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookLists {
    private List<SmallAuthor> authors;
    private List<Genre> genres;
}
