package com.herokuapp.ebookhub.user.entities;

// import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
// @Getter
// @Setter
// @ToString
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
public class User {

    @Id
    @GeneratedValue
    private Long user_id;

    @Column
    private String role;
    private String email;
    private String password;
    private String username;

    public User() {}

    public User(
        String role, 
        String email, 
        String password,
        String username) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRole(){
        return this.role;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public Long getId() {
        return this.user_id;
    }

    public void setId(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(user_id, user.user_id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}
