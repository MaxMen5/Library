-- DROP ALL ------------------------------------------------------------------------------------

drop table genre;
drop table country;
drop table author;
drop table book;

-- TABLE CREATIONS -----------------------------------------------------------------------------

create table genre(
    id serial not null primary key,
    name varchar not null
);

--

create table country(
   id serial not null primary key,
   name varchar not null
);

--

create table author(
	 id serial not null primary key,
	 name varchar not null,
	 author_country_id integer not null references country (id) ON UPDATE NO ACTION ON DELETE CASCADE,
	 birthday_year integer not null
);

--

create table book(
	id serial not null primary key,
	name varchar not null,
	book_author_id integer not null references author (id) ON UPDATE NO ACTION ON DELETE CASCADE,
	year integer not null,
	book_genre_id integer not null references genre (id) ON UPDATE NO ACTION ON DELETE CASCADE,
	page_count integer not null
);

-- DATA INSERTIONS ------------------------------------------------------------------------

insert into genre (name)
values
	('Жанр 1'),
	('Жанр 2')
    returning *;

--

insert into country (name)
values
	('Россия'),
	('Казахстан - база')
    returning *;

--

insert into author (name, author_country_id, birthday_year)
values
	('Стивен Кинг', 1, 1976),
	('Еще чел', 2, 1888)
    returning *;

--

insert into book (name, book_author_id, year, book_genre_id, page_count)
values
	('Сияние', 1, 1989, 1, 690),
	('Книга 2', 2, 1899, 2, 487)
    returning *;

-- DATA SELECTIONS ------------------------------------------------------------------------

select * from genre;
select * from country;
select * from author;
select * from book;