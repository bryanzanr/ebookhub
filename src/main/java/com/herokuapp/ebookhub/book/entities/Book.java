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
    private Long book_id;

    @Column
    private String img_path;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private Integer quantity;
    private String category;
    private Date publish_date;

    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    public Long getBookId() {
        return this.book_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return this.img_path;
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
        return this.publish_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;

        return Objects.equals(book_id, book.book_id);
    }

    @Override
    public int hashCode() {
        return 56842787;
    }
}
