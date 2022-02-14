package uz.elmurodov.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Builder
@Getter
@Setter
public class Book {
    private UUID id;
    private String name;
    private Integer pageCount;
    private String author;
    private String path;

    public Book() {
        this.id = UUID.randomUUID();
    }

    public Book(UUID id, String name, Integer pageCount, String author, String path) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.author = author;
        this.path = path;
    }

    public String getIdAsString() {
        return this.id.toString();
    }
}
