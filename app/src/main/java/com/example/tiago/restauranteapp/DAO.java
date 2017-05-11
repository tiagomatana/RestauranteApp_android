package com.example.tiago.restauranteapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago on 08/05/2017.
 */

class DAO {

    private static List<Restaurante> restaurantes = new ArrayList<Restaurante>();

    public static void incluirRestaurante(Restaurante restaurante) {
        restaurante.setId((long) (restaurantes.size() + 1));
        restaurantes.add(restaurante);
    }

    public static List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public static void alterarRestaurante(Restaurante restauranteParam) {
        Restaurante restauranteDaLista;
        for (int i = 0; i < restaurantes.size(); i++) {

            restauranteDaLista = restaurantes.get(i);

            if (restauranteDaLista.getId().equals(restauranteParam.getId())) {
                restaurantes.set(i, restauranteParam);
            }
        }
    }
    public static void excluirRestaurante(Restaurante restaurante) {
            Restaurante restauranteDaLista;
            for (int i = 0; i < restaurantes.size(); i++) {

                restauranteDaLista = restaurantes.get(i);

                if (restauranteDaLista.getId().equals(restaurante.getId())) {
                    restaurantes.remove(restauranteDaLista);
                }
            }
        }
}
