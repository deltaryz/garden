package com.cameronseid.FinalProject;

/**
 * Plant tile, extends the generic tile to add plant-specific functionality
 */
public class Plant extends Tile {

    private int fullyGrownValue = 10; // the value at which plants are considered "fully grown"

    /**
     * Constructor method to generate a new Plant tile
     *
     * @param coordinates X and Y coordinates of the tile in the world
     */
    public Plant(int[] coordinates) {

        super(coordinates);

        this.hydration = 8;
        this.growth = 0;
        this.symbol = "x"; // this will change after the plant is grown
        this.materialName = "Sprout"; // this will change after the plant is grown
        this.coordinates = coordinates;

    }

    /**
     * Returns a string to be printed to the user which contains all relevant info about the tile.
     *
     * @return The string to be printed to the user containing info about the tile
     */
    public String getInfo() {
        if (this.materialName == "Sprout") {
            return materialName + "\nHydration: " + this.hydration + "\nGrowth: " + this.growth;
        } else {
            return materialName + "\nThis plant can be harvested!";
        }
    }

    /**
     * Method called every turn - increases growth state, decreases hydration
     *
     * @return returns "false" if the plant should be deleted due to dehydration
     */
    public boolean progressTurn() {
        hydration--;

        if (hydration == 0) {
            System.out.println("Plant at " + coordinates[0] + "x" + coordinates[1] + " has died.");
            return false;
        } else {
            if (hydration == 1) {
                System.out.println("Plant at " + coordinates[0] + "x" + coordinates[1] + " needs watering now!");
            } else { // plants don't grow at 1 hydration
                if (growth < fullyGrownValue && hydration > 1) {
                    growth++;
                    if (growth >= (fullyGrownValue / 2)) {
                        this.symbol = "X";
                    }
                }
                if (growth >= fullyGrownValue) {
                    this.setInfo("@", "Grown Plant");
                    System.out.println("Plant at " + coordinates[0] + "x" + coordinates[1] + " is now ready for harvest!");
                }
            }
            return true;
        }
    }
}
