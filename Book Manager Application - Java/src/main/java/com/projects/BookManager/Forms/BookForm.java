package com.projects.BookManager.Forms;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class BookForm {

    private String title;
    private String author;
    private int pages;
    private String language;
    private String category;
    private String publisher;
    private com.projects.BookManager.Entities.Status status;
    private String description;

}
