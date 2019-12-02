package com.starwars.controller;

import com.starwars.expection.Message;
import com.starwars.model.PlanetModel;
import com.starwars.service.StarWarsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarWarsController {

    private StarWarsService starWarsService;

    public StarWarsController(StarWarsService starWarsService) {
        this.starWarsService = starWarsService;
    }

    @GetMapping("randomPlanet")
    public ResponseEntity<PlanetModel> randPlanet() {
        try {
            PlanetModel model = starWarsService.randomPlanet();
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
