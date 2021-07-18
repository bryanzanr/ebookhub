package com.herokuapp.ebookhub.category.entities;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

import java.util.List;
// import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategory(String category);

    // @Query("select c from category where c.sub_category = ?1")
    // Optional<Category> searchJpaQueryTest(String sub_category);

}