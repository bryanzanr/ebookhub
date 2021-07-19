package com.herokuapp.ebookhub.orders.entities;
import com.herokuapp.ebookhub.user.entities.User;
import com.herokuapp.ebookhub.book.entities.Book;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Orders orders = (Orders) o;

        return Objects.equals(orderId, orders.orderId);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}
