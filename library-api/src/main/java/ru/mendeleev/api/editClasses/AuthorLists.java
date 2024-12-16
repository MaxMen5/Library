package ru.mendeleev.api.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mendeleev.entity.Country;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthorLists {
    private List<Country> country;
    private List<FullBook> book;
}
