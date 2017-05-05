package com.cameronseid.FinalProject;

import java.io.*;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Main class for FinalProject. Orchestrates gameplay and user interaction
 */
public class FinalProject {

    private static int seeds; // Amount of seeds the player currently owns
    private static Tile[][] garden; // The garden map
    private static boolean gameRunning; // states whether a game is currently in progress
    private static Scanner keyboard = new Scanner(System.in); // Scanner for input
    private static int gameMode; // will be 0 for endless mode, 1 for time attack mode
    private static int currentTurn; // The current turn of gameplay. Game ends at 100 in time attack mode
    private static int plantsHarvested; // The amount of plants the user has harvested

    public static void main(String[] args) {

        System.out.println("\n\n*** GARDEN ***\n\nFinal project for Intro to Programming Fundamentals\nDeveloped by Cameron Seid (me@cameronseid.com)\n\nAvailable on GitHub! https://github.com/techniponi/finalproject\nPublished under the MIT License, see LICENSE file for details.\n");

        String line = null;
        try {
            FileReader fileReader =
                    new FileReader("scores.txt");

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            System.out.println("Scores:");

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();

            System.out.print("\n");
        } catch (FileNotFoundException ignore) {
        } catch (IOException ignore) {
        }

        // TODO: high score display on title screen
        System.out.print("Please select a game mode:\n0: Endless Mode (Tend your garden for all of eternity!)\n1: Time Attack Mode (How many crops can you yield in 100 turns?)\n");

        // Make sure the input is valid before continuing
        boolean gameModeSelectSuccess = false;
        gameMode = 40;

        while (!gameModeSelectSuccess) {
            System.out.print("\n> ");
            try {
                gameMode = Integer.parseInt(keyboard.nextLine());
                if (gameMode < 0 || gameMode > 1) {
                    System.out.print("Invalid gamemode!");
                } else {
                    gameModeSelectSuccess = true;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid gamemode!");
            }
        }

        // Prepare game for a new session
        initiateGarden();
        gameRunning = true;
        plantsHarvested = 0;

        // Run game
        while (gameRunning) {
            gameLoop();
        }

    }

    /**
     * Called every "turn" of the game - handles all input and game logic
     */
    private static void gameLoop() {

        if (gameMode == 1 && currentTurn >= 100) {
            String name = "";
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTIME'S UP!\n\nWhat is your name?\n> ");

            while (name == "") {
                name = keyboard.nextLine();
            }

            try (FileWriter fw = new FileWriter("scores.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                String score = name + ": " + plantsHarvested + " plants harvested!";
                out.println(score);
                System.out.println("\nThank you for playing, " + name + "!\nYour score: " + plantsHarvested + "\n\nYour score has been saved to scores.txt!");

            } catch (IOException e) {
                System.out.println("Error writing file!");
            }

            gameRunning = false;

        } else

        {

            advanceTurn();

            // input handler
            boolean inputSuccess = false;
            String input;

            while (!inputSuccess) {
                inputSuccess = true;
                System.out.print("\n> ");
                input = keyboard.nextLine();
                String[] inputArgs = input.toLowerCase().split(" ");
                switch (inputArgs[0]) {
                    case "wait":
                        break;
                    case "harvest":
                        try {
                            if (inputArgs.length > 1) {
                                String[] coordinatesString = inputArgs[1].split("x");
                                if (coordinatesString.length > 1) {
                                    int[] coordinates = {abs(Integer.parseInt(coordinatesString[0])), abs(Integer.parseInt(coordinatesString[1]))};
                                    if (coordinates[0] > 4 || coordinates[1] > 4) {
                                        System.out.print("That tile doesn't exist!");
                                        inputSuccess = false;
                                        break;
                                    } else {
                                        if (garden[coordinates[1]][coordinates[0]].getSymbol() == "@") { // this is a grown plant
                                            int[] coordArray = {coordinates[0], coordinates[1]};
                                            garden[coordinates[1]][coordinates[0]] = new Tile(coordArray);
                                            seeds += 2;
                                            plantsHarvested++;
                                            System.out.println("You harvest the plant.");
                                            break;
                                        } else {
                                            System.out.print("You can't harvest this.");
                                            inputSuccess = false;
                                            break;
                                        }
                                    }
                                } else {
                                    System.out.print("Invalid input!");
                                    inputSuccess = false;
                                    break;
                                }
                            } else {
                                System.out.print("Invalid input!");
                                inputSuccess = false;
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input!");
                            inputSuccess = false;
                            break;
                        }
                    case "plant":
                        if (seeds > 0) {
                            try {
                                if (inputArgs.length > 1) {
                                    String[] coordinatesString = inputArgs[1].split("x");
                                    if (coordinatesString.length > 1) {
                                        int[] coordinates = {abs(Integer.parseInt(coordinatesString[0])), abs(Integer.parseInt(coordinatesString[1]))};
                                        if (coordinates[0] > 4 || coordinates[1] > 4) {
                                            System.out.print("That tile doesn't exist!");
                                            inputSuccess = false;
                                            break;
                                        } else {
                                            if (garden[coordinates[1]][coordinates[0]].getSymbol() == "O") { // this is dirt
                                                int[] coordArray = {coordinates[0], coordinates[1]};
                                                garden[coordinates[1]][coordinates[0]] = new Plant(coordArray);
                                                seeds--;
                                                break;
                                            } else {
                                                System.out.println("There's already a plant here!");
                                                inputSuccess = false;
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.print("Invalid input!");
                                        inputSuccess = false;
                                        break;
                                    }
                                } else {
                                    System.out.print("Invalid input!");
                                    inputSuccess = false;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input!");
                                inputSuccess = false;
                                break;
                            }
                        } else {
                            System.out.print("You don't have any seeds!");
                            inputSuccess = false;
                            break;
                        }
                    case "water":
                        try {
                            if (inputArgs.length > 1) {
                                String[] coordinatesString = inputArgs[1].split("x");
                                if (coordinatesString.length > 1) {
                                    int[] coordinates = {abs(Integer.parseInt(coordinatesString[0])), abs(Integer.parseInt(coordinatesString[1]))};
                                    if (coordinates[0] > 4 || coordinates[1] > 4) {
                                        System.out.print("That tile doesn't exist!");
                                        inputSuccess = false;
                                        break;
                                    } else {
                                        if (garden[coordinates[1]][coordinates[0]].getSymbol() != "O") {
                                            garden[coordinates[1]][coordinates[0]].waterPlant();
                                            break;
                                        } else {
                                            System.out.print("That's just dirt!");
                                            inputSuccess = false;
                                            break;
                                        }
                                    }
                                } else {
                                    System.out.print("Invalid input!");
                                    inputSuccess = false;
                                    break;
                                }
                            } else {
                                System.out.print("Invalid input!");
                                inputSuccess = false;
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input!");
                            inputSuccess = false;
                            break;
                        }
                    case "check":
                        try {
                            if (inputArgs.length > 1) {
                                String[] coordinatesString = inputArgs[1].split("x");
                                if (coordinatesString.length > 1) {
                                    int[] coordinates = {abs(Integer.parseInt(coordinatesString[0])), abs(Integer.parseInt(coordinatesString[1]))};
                                    if (coordinates[0] > 4 || coordinates[1] > 4) {
                                        System.out.print("That tile doesn't exist!");
                                        inputSuccess = false;
                                        break;
                                    } else {
                                        System.out.println(garden[coordinates[1]][coordinates[0]].getInfo());
                                        inputSuccess = false;
                                        break;
                                    }
                                } else {
                                    System.out.print("Invalid input!");
                                    inputSuccess = false;
                                    break;
                                }
                            } else {
                                System.out.print("Invalid input!");
                                inputSuccess = false;
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input!");
                            inputSuccess = false;
                            break;
                        }
                    case "exit":
                        gameRunning = false;
                        break;
                    default:
                        System.out.print("Invalid input!");
                        inputSuccess = false;
                        break;
                }
            }

            currentTurn++;
        }

    }

    /**
     * Advances the garden by one turn.
     */
    private static void advanceTurn() {

        System.out.println("\nPlease type\n<COMMAND> <X>x<Y>\nto execute a command\nExample: check 4x2\nThe grid starts at 0x0 and ends at 4x4.\n\nCommands: CHECK, PLANT, WATER, HARVEST, WAIT, EXIT\n\nCurrent Turn: " + currentTurn + "\nSeeds: " + seeds + "\nPlants Harvested: " + plantsHarvested + "\n");

        // loop through every index in 2D garden array
        for (int x = 0; x < garden.length; x++) {
            for (int y = 0; y < garden[x].length; y++) {
                if (!garden[y][x].progressTurn()) {
                    int[] coordinates = {x, y};
                    garden[y][x] = new Tile(coordinates);
                }
            }
        }

        System.out.println(printGarden());
    }

    /**
     * Initiates a fresh garden for gameplay
     */
    private static void initiateGarden() {

        garden = new Tile[5][5];

        seeds = 2; // only give the player 2 seeds to start so they can learn the mechanics in a controlled scenario

        // loop through every index in 2D garden array to create tile objects
        for (int x = 0; x < garden.length; x++) {
            for (int y = 0; y < garden[x].length; y++) {
                int[] coordinates = {x, y};
                garden[y][x] = new Tile(coordinates); // create new tile object
            }
        }
    }

    /**
     * Returns a string containing the visual representation of the garden to the player
     *
     * @return A string containing the visual representation of the garden to the player
     */
    private static String printGarden() {
        String gardenView = "";
        for (Tile[] tileArray : garden) {
            gardenView += "\n";
            for (Tile tile : tileArray) {
                gardenView += tile.getSymbol();
            }
        }
        return gardenView;
    }

}
