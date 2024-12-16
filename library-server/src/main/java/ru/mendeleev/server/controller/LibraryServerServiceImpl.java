package ru.mendeleev.server.controller;

import org.springframework.stereotype.Component;
import ru.mendeleev.api.editClasses.AuthorEdit;
import ru.mendeleev.api.editClasses.AuthorFilter;
import ru.mendeleev.api.editClasses.BookEdit;
import ru.mendeleev.api.editClasses.BookFilter;
import ru.mendeleev.api.editClasses.FullAuthor;
import ru.mendeleev.api.editClasses.FullBook;
import ru.mendeleev.api.editClasses.SmallAuthor;
import ru.mendeleev.api.entity.Country;
import ru.mendeleev.api.entity.Genre;
import ru.mendeleev.api.servcie.LibraryServerService;

import java.util.Collections;
import java.util.List;

@Component
public class LibraryServerServiceImpl implements LibraryServerService { // TODO !!!

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public List<FullAuthor> loadAllAuthors(AuthorFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public List<Country> loadAllCountries() {
        return Collections.emptyList();
    }

    @Override
    public List<FullBook> loadAllBooks() {
        return Collections.emptyList();
    }

    @Override
    public void saveAuthor(AuthorEdit authorEdit) {

    }

    @Override
    public List<FullBook> loadAuthorBooks(Integer authorId) {
        return Collections.emptyList();
    }

    @Override
    public List<FullBook> loadNotAllBooks(Integer authorId) {
        return Collections.emptyList();
    }

    @Override
    public void updateAuthor(Integer authorId, AuthorEdit changedAuthor) {

    }

    @Override
    public void deleteAuthorById(Integer authorId) {

    }

    @Override
    public void deleteAuthorBooks(Integer authorId) {

    }

    @Override
    public List<SmallAuthor> loadSmallAuthors() {
        return Collections.emptyList();
    }

    @Override
    public List<Genre> loadAllGenres() {
        return Collections.emptyList();
    }

    @Override
    public List<FullBook> loadAllBooks(BookFilter bookFilter) {
        return Collections.emptyList();
    }

    @Override
    public void saveBook(BookEdit bookEdit) {

    }

    @Override
    public void updateBook(Integer bookId, BookEdit changedBook) {

    }

    @Override
    public void deleteBookById(Integer bookId) {

    }

    @Override
    public boolean login(String login, String password) {
        return false;
    }

    @Override
    public void logout() {

    }
}
