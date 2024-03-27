package edu.mcc.codeschool.graphql.controllers;

import edu.mcc.codeschool.graphql.model.Author;
import edu.mcc.codeschool.graphql.model.Book;
import edu.mcc.codeschool.graphql.model.Genre;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GraphqlController {
    private List<Author> authors = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    @QueryMapping
    public List<Book> getBooks(){
        return books;
    }

    @QueryMapping
    public List<Author> getAuthors(){
        return authors;
    }

    @QueryMapping
    public Book getBookById(@Argument UUID bookId){
        return books.stream().filter(book -> book.getId().toString().equals(bookId.toString())).findFirst().get();
    }

    @QueryMapping
    public Author getAuthorById(@Argument UUID authorId){
        return authors.stream().filter(author -> author.getId().toString().equals(authorId.toString())).findFirst().get();
    }

    @MutationMapping
    public Author createAuthor(@Argument String firstName, @Argument String lastName, @Argument Integer booksPublished){
        Author author = new Author();
        author.setId(UUID.randomUUID()).setFirstName(firstName).setLastName(lastName).setBooksPublished(booksPublished);
        authors.add(author);
        return author;
    }

    @MutationMapping
    public Book createBook(@Argument String title, @Argument Integer releaseYear, @Argument Integer pages, @Argument String publisher, @Argument Boolean isHardCover, @Argument UUID authorId, @Argument Genre genre){
        Book book = new Book();

        book.setId(UUID.randomUUID()).setTitle(title)
                .setReleaseYear(releaseYear).setPages(pages)
                .setPublisher(publisher).setHardCover(isHardCover)
                .setAuthorId(authorId).setGenre(genre);
        books.add(book);

        return book;
    }

    @MutationMapping
    public void deleteAuthorById(@Argument UUID authorId){
        Author toDelete = authors.stream().filter(author -> author.getId().toString().equals(authorId.toString())).findFirst().get();
        authors.remove(toDelete);
    }

    @MutationMapping
    public void deleteBookById(@Argument UUID bookId){
        Book toDelete = books.stream().filter(book -> book.getId().toString().equals(bookId.toString())).findFirst().get();
        books.remove(toDelete);
    }

    @SchemaMapping(typeName = "Author", field = "books")
    public List<Book> getBooks(Author author){
        return books.stream().filter(book -> book.getAuthorId().toString().equals(author.getId().toString())).collect(Collectors.toList());
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author getAuthor(Book book){
        return authors.stream().filter(author -> author.getId().toString().equals(book.getAuthorId().toString())).findFirst().get();
    }
}
