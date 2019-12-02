package com.starwars.service;

import com.starwars.model.Film;
import com.starwars.model.ListPlanets;
import com.starwars.model.Planet;
import com.starwars.model.PlanetModel;
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

    @Cacheable("listPlanets")
    public ListPlanets getAllPlanets() {
        return template.getForObject(uri, ListPlanets.class);
    }

    @Cacheable("planet")
    public Planet getByIdPlanet(int baseUrl) {
        return template.getForObject(uri + "/" + baseUrl, Planet.class);
    }

    @Cacheable("film")
    public Film getFilmByUrl(String urlFilm) {
        return template.getForObject(urlFilm, Film.class);
    }

    public PlanetModel randomPlanet() {
        PlanetModel model = new PlanetModel();
        List<Film> films = new ArrayList<>();

        ListPlanets listPlanets = getAllPlanets();
        Random random = new Random();

        int idPlanet = random.nextInt(listPlanets.count);
        Planet planet = getByIdPlanet(idPlanet);
        planet.films.forEach(f -> films.add(getFilmByUrl(f)));

        model.name = planet.name;
        model.climate = planet.climate;
        model.population = planet.population;
        model.terrain = planet.terrain;
        model.films = films;

        return model;
    }
}
