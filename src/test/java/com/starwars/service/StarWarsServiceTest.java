package com.starwars.service;

import com.starwars.StarWarsApplicationTests;
import com.starwars.model.Film;
import com.starwars.model.Planet;
import com.starwars.model.Planets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StarWarsServiceTest extends StarWarsApplicationTests {

    private static String URI = "https://swapi.co/api/planets";

    @Spy
    private RestTemplate template = new RestTemplate();

    @InjectMocks
    private StarWarsService starWarsServiceMock;

    @BeforeAll
    public void setup() {
        starWarsServiceMock = new StarWarsService(this.template, URI);
    }

    @Test
    void testGetAllPlanetsSuccess() {
        ResponseEntity<String> result = template.getForEntity(URI, String.class);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().contains("count"));
    }

    @Test
    void testRandomBetweenIdPlanet(){
        Planets planets = starWarsServiceMock.getCountAllPlanets();
        Random random = new Random();
        int idPlanetRandom = random.nextInt(planets.count) + 1;
        assertTrue(idPlanetRandom >= 1 && idPlanetRandom <= planets.count);
    }

    @Test
    void testGetByIdPlanet() {
        Planet planetMock = new Planet();
        List<String> filmsMock = new ArrayList<>();
        filmsMock.add("https://swapi.co/api/films/1/");
        planetMock.name =  "Yavin IV";
        planetMock.population = "1000";
        planetMock.climate = "temperate, tropical";
        planetMock.terrain = "jungle, rainforests";
        planetMock.films = filmsMock;

        Mockito.when(template.getForEntity("https://swapi.co/api/planets/3/", Film.class))
                .thenReturn(new ResponseEntity(planetMock, HttpStatus.OK));

        Planet planet = starWarsServiceMock.getByIdPlanet(3);
        assertEquals(planetMock,planet);
    }

    @Test
    void testGetFilmByUrl() {
        Film filmMock = new Film();
        filmMock.title = "The Empire Strikes Back";
        Mockito.when(template.getForEntity("https://swapi.co/api/films/2/", Film.class))
                .thenReturn(new ResponseEntity(filmMock, HttpStatus.OK));

        Film film = starWarsServiceMock.getFilmByUrl("https://swapi.co/api/films/2/");
        assertEquals(filmMock, film);
    }
}