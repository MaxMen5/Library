package ru.mendeleev.editClasses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FullAuthor {
    private Integer id;
    private String name;
    private Integer countryId;
    private String countryName;
    private Integer birthYear;
    private String bookList;
}
