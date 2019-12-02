package com.starwars.model;

import lombok.Data;

import java.util.List;

@Data
public class Planet {
    public String name;
    public String population;
    public String climate;
    public String terrain;
    public List<String> films;
}
