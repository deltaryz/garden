# GARDEN
### Remember to check the "Releases" tab to download!

Use `java -jar garden.jar` to run the game.

Garden is a simple strategy game in which the player tends to a 5x5 grid which serves as the “garden”. Each turn they are able to perform one single action:
*	Plant a new seed
*	Water an existing plant
*	Harvest a fully-grown plant
*	Wait and do nothing

They may also check a tile for details of its status, but unlike the other four commands, this does not advance the turn.

Plants will grow each turn as long as they are properly watered. If they reach critical dehydration, they will not grow on this turn and will alert the user of the plant’s status. Should it go un-watered for another turn, the plant dies and the tile returns to soil. If the plant is kept watered for 10 consecutive turns, it becomes “fully grown” and can be harvested at any point to yield 2 additional seeds.

“Endless Mode” simply does not end, allowing the player to practice strategies for more efficient farming, or otherwise freely experiment as they please. There is no objective, nor is there any concept of “winning” or “losing” in this state.

The objective in “Time Attack Mode” is to harvest as many plants as possible before 100 turns pass. The player must strategically time his planting and watering so as not to waste time by letting plants die. The scores are then saved to a scores.txt file, which will display on the title screen on each subsequent boot of the game. Players are encouraged to compete with each other for a higher score.

Garden is written using Java 1.8.0 update 121, and uses no third-party libraries.
