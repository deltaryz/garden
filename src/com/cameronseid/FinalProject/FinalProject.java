package com.cameronseid.FinalProject;

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

    public static void main(String[] args) {

        System.out.println("\n\n*** GARDEN ***\n\nFinal project for Intro to Programming Fundamentals\nDeveloped by Cameron Seid (me@cameronseid.com)\n\nAvailable on GitHub! https://github.com/techniponi/finalproject\nPublished under the MIT License, see LICENSE file for details.\n");

        // TODO: high score display on title screen
        System.out.print("Please select a game mode:\n0: Endless Mode (Tend your garden for all of eternity!)\n1: Time Attack Mode (How many crops can you yield in 100 turns?)\n");

        // Make sure the input is valid before continuing
        boolean gameModeSelectSuccess = false;
        int gameModeSelected = -1;

        while (!gameModeSelectSuccess) {
            System.out.print("\n> ");
            try {
                gameModeSelected = Integer.parseInt(keyboard.nextLine());
                if (gameModeSelected < 0 || gameModeSelected > 1) {
                    System.out.print("Invalid gamemode!");
                } else {
                    gameModeSelectSuccess = true;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid gamemode!");
            }
        }

        // Prepare game for a new session
        initiateGarden(gameModeSelected);
        gameRunning = true;

        // Run game
        while (gameRunning) {
            gameLoop();
        }

    }

    /**
     * Called every "turn" of the game - handles all input and game logic
     */
    private static void gameLoop() {

        // TODO: print garden and display seeds

        System.out.println(printGarden() + "\nPlease type\n<COMMAND> <X>x<Y>\nto execute a command\nExample: check 4x2\n\nCommands: CHECK, PLANT, WATER, EXIT\n\nSeeds: " + seeds);

        // input handler
        boolean inputSuccess = false;
        String input;

        while (!inputSuccess) {
            inputSuccess = true;
            System.out.print("\n> ");
            input = keyboard.nextLine();
            String[] inputArgs = input.toLowerCase().split(" ");
            switch (inputArgs[0]) {
                case "test":
                    break;
                case "check":
                    try {
                        if (inputArgs.length > 1) {
                            String[] coordinatesString = inputArgs[1].split("x");
                            int[] coordinates = {abs(Integer.parseInt(coordinatesString[0])), abs(Integer.parseInt(coordinatesString[1]))};
                            if (coordinates[0] > 5 || coordinates[1] > 5) {
                                System.out.print("That tile doesn't exist!");
                                inputSuccess = false;
                                break;
                            } else {
                                System.out.println(garden[coordinates[0]][coordinates[1]].getInfo());
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

    }

    /**
     * Initiates a fresh garden for gameplay
     *
     * @param gameMode will be 0 for endless mode, 1 for time attack mode
     */
    private static void initiateGarden(int gameMode) {

        garden = new Tile[5][5];

        seeds = 2; // only give the player 2 seeds to start so they can learn the mechanics in a controlled scenario

        // loop through every index in 2D garden array to create tile objects
        for (int x = 0; x < garden.length; x++) {
            for (int y = 0; y < garden[x].length; y++) {
                garden[x][y] = new Tile(); // create new tile object
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
            for (Tile tile : tileArray) {
                gardenView += tile.getSymbol();
            }
            gardenView += "\n";
        }
        return gardenView;
    }

}
