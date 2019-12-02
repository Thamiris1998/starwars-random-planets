package com.starwars.service;

import com.starwars.model.Film;
import com.starwars.model.Planet;
import com.starwars.model.PlanetModel;
import com.starwars.model.Planets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StarWarsService {

    private RestTemplate template;
    private String uri;

    StarWarsService(RestTemplate template, @Value("${application.planets.url}") String uri) {
        this.template = template;
        this.uri = uri;
    }

    @Cacheable("countPlanets")
    public Planets getCountAllPlanets() {
        return template.getForObject(uri, Planets.class);
    }

    @Cacheable("planet")
    public Planet getByIdPlanet(int idPlanet) {
        return template.getForObject(uri + "/" + idPlanet, Planet.class);
    }

    @Cacheable("film")
    public Film getFilmByUrl(String urlFilm) {
        return template.getForObject(urlFilm, Film.class);
    }

    public PlanetModel randomPlanet() {
        PlanetModel model = new PlanetModel();
        List<Film> films = new ArrayList<>();

        Planets planets = getCountAllPlanets();
        Random random = new Random();

        int idPlanetRandom = random.nextInt(planets.count) + 1;
        Planet planet = getByIdPlanet(idPlanetRandom);
        planet.films.forEach(f -> films.add(getFilmByUrl(f)));

        model.name = planet.name;
        model.climate = planet.climate;
        model.population = planet.population;
        model.terrain = planet.terrain;
        model.films = films;

        return model;
    }
}
