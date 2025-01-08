package tetris.utils;

import tetris.db.Player;

import java.util.LinkedList;

public class SearchUtil {

    // Buscar un jugador por su nombre de usuario 
    public static Player searchPlayerByUsername(LinkedList<Player> players, String username) {
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return player; // Retorna el jugador si se encuentra
            }
        }
        return null; // Retorna null si no se encuentra el jugador
    }
}
