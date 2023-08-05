package pl.javastart.task;

import java.util.Comparator;

public class ScorePlayerComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        if (p1 == null && p2 == null) {
            return 0;
        } else if (p1 == null && p2 != null) {
            return -1;
        } else if (p1 != null && p2 == null) {
            return 1;
        }
        return Integer.compare(p1.getScore(), p2.getScore());
    }
}
