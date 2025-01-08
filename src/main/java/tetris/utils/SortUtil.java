package tetris.utils;

import tetris.db.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class SortUtil {

    // Ordenar jugadores por puntaje más alto (de mayor a menor) en orden descendente 
    public static void sortPlayersByScore(LinkedList<Player> players) {
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getHighestScore(), p1.getHighestScore()); 
            }
        });
    }
}
