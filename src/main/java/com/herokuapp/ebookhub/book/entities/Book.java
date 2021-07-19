package com.herokuapp.ebookhub.book.entities;

// import com.herokuapp.ebookhub.user.entities.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
// import java.util.Objects;
import java.util.*;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue
    @Column(name="book_id")
    private Long bookId;

    @Column(name="img_path")
    private String imgPath;
    
    @Column
    private String title;
    private String author;
    private String publisher;
    private String description;
    private Integer quantity;
    private String category;

    @Column(name="publish_date")
    private Date publishDate;

    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    public Book() {}

    public Book(
    String imgPath,
    String title,
    String author,
    String publisher,
    String description,
    Integer quantity,
    String category,
    Date publishDate) {
        this.imgPath= imgPath;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.publishDate = publishDate;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public String getCategory() {
        return this.category;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;

        return Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return 56842787;
    }

    public static Book builder() {
        return new Book();
    }

    public static Book build() {
        return new Book();
    }
}
