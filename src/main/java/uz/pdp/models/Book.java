package uz.pdp.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
public class Book {
    private UUID id;
    private String name;
    private Integer pageCount;
    private String author;
    private String path;
    private String img_path;

    public Book() {
        this.id = UUID.randomUUID();
    }

    public Book(String id, String name, String author, int pageCount) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.pageCount = pageCount;
        this.author = author;
        this.path = path;
    }

    public String getIdAsString() {
        return this.id.toString();
    }
}
