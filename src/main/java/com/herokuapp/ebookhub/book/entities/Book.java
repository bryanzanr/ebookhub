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
    private Integer stock;
    private String category;
    private Date created_at;

    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private User user;

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
