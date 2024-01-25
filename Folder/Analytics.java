package Folder;

import java.util.ArrayList;

public class Analytics {

    public static void printCell(int gridX, int gridY) {
        System.out.println(
                "\nCell\n---------" +
                        "\n\nType: " + Settings.grid[gridX][gridY].getType() +
                        "\n\tAge: " + Settings.grid[gridX][gridY].getAge() +
                        "\n\tX: " + gridX +
                        "\n\tY: " + gridY);
        // "\n\tAdjacent: " + findMassDirections(gridX, gridY, true, Settings.grid) +
        // "\n\tDiagonal: " + findMassDirections(gridX, gridY, false, Settings.grid));
    }

    private static ArrayList<Integer> averageTotal = new ArrayList<Integer>();
    private static ArrayList<Integer> averageDif = new ArrayList<Integer>();

    private static int massCount = 0;
    private static int energyCount = 0;
    private static int emptyCount = 0;
    private static int total;
    private static int averageCount;

    private static double averageDifference = 0;
    private static double averageRecentDifference = 0;
    private static int difference = 0;

    public static void calcOverview() {

        massCount = 0;
        energyCount = 0;
        emptyCount = 0;

        averageDifference = 0;
        difference = 0;

        // Process each cell in the grid
        for (int i = 0; i < Settings.GRID_SIZE; i++) {
            for (int j = 0; j < Settings.GRID_SIZE; j++) {
                Cell currentCell = Settings.grid[i][j];

                // Mass
                if (currentCell.getType() == Cell.Type.MASS) {
                    massCount++;
                }

                // Energy
                else if (currentCell.getType() == Cell.Type.ENERGY) {
                    energyCount++;
                }

                // Empty
                else if (currentCell.getType() == Cell.Type.EMPTY) {
                    emptyCount++;
                }

            }

        }

        total = massCount + energyCount;

        if (averageTotal.size() != 0)
            difference = averageTotal.getLast();
        else
            difference = total;

        averageTotal.add(total);
        averageCount = (int) averageTotal.stream().mapToInt(val -> val).average().orElse(0.0);

        if (averageTotal.size() != 0) {
            difference = averageTotal.getLast() - difference;

            averageDif.add(difference);
            averageDifference = averageDif.stream().mapToInt(val -> val).average().orElse(0.0);

            if (averageDif.size() > 30) {
                var sublist = averageDif.subList(averageDif.size() - 30, averageDif.size());
                averageRecentDifference = sublist.stream()
                        .mapToInt(val -> val).average().orElse(0.0);
            }
        }

    }

    public static String overviewString() {
        int cellCount = Settings.GRID_SIZE * Settings.GRID_SIZE;

        double percentMass = ((double) massCount / cellCount) * 100;
        double percentEnergy = ((double) energyCount / cellCount) * 100;
        double percentEmpty = ((double) emptyCount / cellCount) * 100;

        return ("Overview\n---------" +
                "\n\nCounts: " +
                "\n\tTotal: " + total +
                "\n\tAverage Count: " + averageCount +
                "\n\n\tMass: " + massCount + " - " + String.format("%.1f", percentMass) + "%" +
                "\n\tEnergy: " + energyCount + " - " + String.format("%.1f", percentEnergy) + "%" +
                "\n\tEmpty: " + emptyCount + " - " + String.format("%.1f", percentEmpty) + "%" +

                "\n\nDifferences: " +
                "\n\tDifference: " + difference +
                "\n\tAverage Difference: " + String.format("%.1f", averageDifference) +
                "\n\tAverage Recent: " + String.format("%.1f", averageRecentDifference));

    }

}
