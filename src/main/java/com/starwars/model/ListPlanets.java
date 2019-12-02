package com.starwars.model;

import lombok.Data;
import java.util.List;

@Data
public class ListPlanets {
    public int count;
    public List<Planet> result;
}
