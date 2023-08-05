package pl.javastart.task;

import java.util.Comparator;

public class LastNamePlayerComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        if (p1 == null && p2 == null) {
            return 0;
        } else if (p1 == null && p2 != null) {
            return -1;
        } else if (p1 != null && p2 == null) {
            return 1;
        }
        return p1.getLastName().toUpperCase().compareTo(p2.getLastName().toUpperCase());
    }
}
