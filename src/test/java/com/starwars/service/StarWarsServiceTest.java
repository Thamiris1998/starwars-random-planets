package com.starwars.service;

import com.starwars.model.Film;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StarWarsServiceTest {

    @Mock
    private RestTemplate template;
    @InjectMocks
    private StarWarsService starWarsServiceMock= new StarWarsService(template, "https://swapi.co/api/planets");

    @Test
    void getAllPlanets() {
    }

    @Test
    void getByIdPlanet() {
    }

    @Test
    void getFilmByUrl() {
        Film filmMock = new Film();
        filmMock.title = "The Empire Strikes Back";

        Mockito.when(template.getForEntity("https://swapi.co/api/films/2/", Film.class))
                .thenReturn(new ResponseEntity(filmMock, HttpStatus.OK));

        Film film = starWarsServiceMock.getFilmByUrl("https://swapi.co/api/films/2/");
        assertEquals(filmMock, film);
    }
}