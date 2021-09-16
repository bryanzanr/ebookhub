package com.herokuapp.ebookhub.category.usecases;

import com.herokuapp.ebookhub.category.entities.Category;
import com.herokuapp.ebookhub.category.entities.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class GetCategoryUseCaseTest {

    @InjectMocks
    private GetCategoryUseCase getCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenEmptyRequest_whenGetCategories_shouldCallRepository() {

        when(categoryRepository.findAll())
            .thenReturn(Collections.singletonList(new Category()));

        getCategoryUseCase.GetCategories(null, null, null);

        verify(categoryRepository).findAll();

    }

    @Test
    void givenCategoryRequest_whenGetCategories_shouldCallRepository() {

        when(categoryRepository.findByCategory(anyString()))
            .thenReturn(Collections.singletonList(new Category()));

        getCategoryUseCase.GetCategories("asd", null, null);

        verify(categoryRepository).findByCategory(anyString());

    }

    @Test
    void givenSubCategory_whenGetCategories_shouldCallRepository() {

        when(categoryRepository.findBySubCategory(anyString()))
            .thenReturn(Collections.singletonList(new Category()));

        getCategoryUseCase.GetCategories(null, "asd", null);

        verify(categoryRepository).findBySubCategory(anyString());

    }

    @Test
    void givenNumber_whenGetCategories_shouldCallRepository() {

        when(categoryRepository.findById(anyLong()))
            .thenReturn(Optional.of(new Category()));

        getCategoryUseCase.GetCategories(null, null, 1L);

        verify(categoryRepository).findById(anyLong());

    }

    @Test
    void givenEmptyObject_whenGetCategories_shouldRaiseException() {

        when(categoryRepository.findAll())
            .thenReturn(null);

        ResponseEntity<List<Object>> response = getCategoryUseCase.GetCategories(null, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    }

}