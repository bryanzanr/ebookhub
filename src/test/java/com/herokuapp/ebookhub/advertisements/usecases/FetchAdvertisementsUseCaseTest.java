package com.herokuapp.ebookhub.advertisements.usecases;

import com.google.gson.JsonObject;
import com.herokuapp.ebookhub.advertisements.entities.AdvertisementsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FetchAdvertisementsUseCaseTest {

    @InjectMocks
    FetchAdvertisementsUseCase fetchAdvertisementsUseCase;

    @Mock
    AdvertisementsRepository advertisementsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAdvertisements_shouldCallRepository() {

        when(advertisementsRepository.findAll(anyString()))
            .thenReturn(new JsonObject().toString());

        fetchAdvertisementsUseCase.GetAdvertisements("asd");

        verify(advertisementsRepository).findAll(anyString());

    }
    
}