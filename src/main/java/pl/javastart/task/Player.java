package pl.javastart.task;

import java.util.Comparator;

public class Player implements Comparable<Player> {
    private String firstName;
    private String lastName;
    private int score;

    public Player(String firstName, String lastName, int score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Player p) {
        return Integer.compare(score, p.score);
    }
}
