package org.nomade.load.service;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestRestaurantService {

    private RestaurantService restaurantService;

    @Before
    public void setUp() {
        restaurantService = new RestaurantService();
    }

    @Test
    public void load_data() throws IOException {
        restaurantService.load();
    }
}
