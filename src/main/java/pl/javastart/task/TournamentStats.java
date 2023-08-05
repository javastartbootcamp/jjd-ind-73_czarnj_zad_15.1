package pl.javastart.task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TournamentStats {

    private static final String STOP = "STOP";
    private static final int SORT_BY_FIRST_NAME = 1;
    private static final int SORT_BY_LAST_NAME = 2;
    private static final int SORT_BY_SCORE = 3;
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;
    private static final String FILE_NAME = "stats.csv";

    void run(Scanner scanner) {
        List<Player> players = addPlayers(scanner);
        int parameterOption = getParameterOption(scanner);
        int sortOption = getSortOption(scanner);
        switch (parameterOption) {
            case SORT_BY_FIRST_NAME -> sort(players, new FirstNamePlayerComparator(), sortOption);
            case SORT_BY_LAST_NAME -> sort(players, new LastNamePlayerComparator(), sortOption);
            case SORT_BY_SCORE -> sort(players, new ScorePlayerComparator(), sortOption);
            default -> throw new RuntimeException("Wystąpił błąd przy wyborze parametru");
        }
        if (saveToFile(players)) {
            System.out.printf("Dane posortowano i zapisano do pliku %s.%n", FILE_NAME);
        } else {
            System.out.println("Wystąpił problem z zapisywaniem");
        }
    }

    private void sort(List<Player> players, Comparator<Player> comparator, int sortOption) {
        if (sortOption == ASCENDING) {
            players.sort(comparator);
        } else {
            Comparator<Player> reversedComparator = comparator.reversed();
            players.sort(reversedComparator);
        }
    }

    private List<Player> addPlayers(Scanner scanner) {
        List<Player> players = new ArrayList<>();
        String input;
        boolean shouldContinue = true;
        do {
            System.out.println("Podaj wynik kolejnego gracza (lub stop):");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase(STOP)) {
                shouldContinue = false;
            } else {
                Player player = parsePlayer(input);
                if (player != null) {
                    players.add(player);
                } else {
                    System.out.println("Dane wprowadzone błędnie");
                }
            }
        } while (shouldContinue);
        return players;
    }

    private int getParameterOption(Scanner scanner) {
        boolean isInputCorrect = false;
        int option = SORT_BY_SCORE;
        while (!isInputCorrect) {
            System.out.printf("Po jakim parametrze posortować? (%d - imię, %d - nazwisko, %d - wynik)%n",
                    SORT_BY_FIRST_NAME, SORT_BY_LAST_NAME, SORT_BY_SCORE);
            option = scanner.nextInt();
            scanner.nextLine();
            if (option == SORT_BY_FIRST_NAME || option == SORT_BY_LAST_NAME || option == SORT_BY_SCORE) {
                isInputCorrect = true;
            }
        }
        return option;
    }

    private int getSortOption(Scanner scanner) {
        boolean isInputCorrect = false;
        int option = ASCENDING;
        while (!isInputCorrect) {
            System.out.printf("Sortować rosnąco czy malejąco? (%d - rosnąco, %d - malejąco)%n",
                    ASCENDING, DESCENDING);
            option = scanner.nextInt();
            scanner.nextLine();
            if (option == ASCENDING || option == DESCENDING) {
                isInputCorrect = true;
            }
        }
        return option;
    }

    private Player parsePlayer(String input) {
        String[] inputAsArray = input.split(" ");
        if (inputAsArray.length < 3) {
            return null;
        }
        String firstName = inputAsArray[0];
        String lastName = inputAsArray[1];
        int score;
        try {
            score = Integer.parseInt(inputAsArray[2]);
        } catch (NumberFormatException ex) {
            return null;
        }
        return new Player(firstName, lastName, score);
    }

    private boolean saveToFile(List<Player> players) {
        try (var bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Player player : players) {
                bw.write(getPlayerAsString(player));
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private String getPlayerAsString(Player player) {
        return player.getFirstName() + " " + player.getLastName() + ";" + player.getScore() + "\n";
    }
}
