package org.nomade.load.model;

import lombok.Data;

import java.util.List;

@Data
public class Restaurant {

    private long id;

    private String area;

    private long cost;

    private List<String> cuisine;

    private double latitude;

    private double longitude;

    private String name;

    private int preference;

    private double ratings;

    private String type;

    private int vote;
}
