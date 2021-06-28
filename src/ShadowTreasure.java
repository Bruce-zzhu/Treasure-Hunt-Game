/**
 * The code of this project contains parts of the solution provided for project 1
 */

import bagel.*;
import bagel.util.Point;

import java.io.*;
import java.text.DecimalFormat;

import java.util.HashMap;

/**
 * An example Bagel game.
 * In this treasure hunt game, the player will interact
 * with zombies and sandwiches before finding the treasure.
 */
public class ShadowTreasure extends AbstractGame {
    // image of the background
    public final Image BACKGROUND = new Image("res/images/background.png");
    // distance where player can interact with other entities
    public static final int MEETING_DISTANCE = 50;
    // distance where bullet can interact with other entities
    public static final int HIT_RANGE = 25;
    // distance where player can shoot
    public static final int SHOT_RANGE = 150;

    // list of characters
    private HashMap<Point, Sandwich> sandwiches = new HashMap<>();
    private HashMap<Point, Zombie> zombies = new HashMap<>();
    private Player player;
    private Treasure treasure;

    // tick update cycle
    private int tick;
    private static final int TICK_CYCLE = 10;

    // end of game indicator
    private boolean endOfGame;

    // constructor, load environment
    public ShadowTreasure() throws IOException {
        this.loadEnvironment("res/IO/environment.csv");
        this.createOutputFile();
        this.endOfGame = false;
        this.tick = 1;
    }

    /**
     * getter of player
     * @return player, which is of type Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * getter of sandwiches
     * @return a list of sandwiches, of type HashMap<Point, Sandwich>
     */
    public HashMap<Point, Sandwich> getSandwiches() {
        return sandwiches;
    }

    /**
     * getter of zombies
     * @return a list of zombies, of type HashMap<Point, Zombie>
     */
    public HashMap<Point, Zombie> getZombies() {
        return zombies;
    }

    /**
     * getter of treasure
     * @return treasure, of type Treasure
     */
    public Treasure getTreasure() {
        return treasure;
    }

    /**
     * setter of endOfGame
     * @param endOfGame, of type boolean
     */
    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    // for rounding double number
    private static DecimalFormat df = new DecimalFormat("0.00");

    // create/clear output.csv file
    public void createOutputFile() {
        try(PrintWriter pw = new PrintWriter("res/IO/output.csv")) {
            pw.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * print bullet's position into output.csv file
     * @param x, x position of bullet, of type double
     * @param y, y position of bullet, of type double
     */
    public static void printBulletPos(double x, double y) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("res/IO/output.csv", true))) {
            pw.append(df.format(x) + "," + df.format(y) + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Load from input file
     */
    private void loadEnvironment(String filename){
        // Code here to read from the file and set up the environment
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String type = values[0].replaceAll("[^a-zA-Z0-9]", ""); // remove special characters
                double x = Double.parseDouble(values[1]);
                double y = Double.parseDouble(values[2]);
                switch (type) {
                    case "Zombie":
                        Zombie zombie = new Zombie(x, y);
                        this.zombies.put(zombie.getPosition(), zombie);
                        break;
                    case "Sandwich":
                        Sandwich sandwich = new Sandwich(x, y);
                        this.sandwiches.put(sandwich.getPosition(), sandwich);
                        break;
                    case "Player":
                        this.player = new Player(x, y, Integer.parseInt(values[3]));
                        break;
                    case "Treasure":
                        this.treasure = new Treasure(x, y);
                        break;
                    default:
                        throw new BagelError("Unknown type: " + type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        // Logic to update the game, as per specification must go here

        if (this.endOfGame || input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        } else {
            // draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // update the frame
            if (tick > TICK_CYCLE) {
                tick = 1;
                // update player's state
                this.player.update(this);
            }

            tick++;

            // draw all the characters
            player.draw();
            treasure.draw();
            for (Point pos: zombies.keySet()) {
                Zombie zombie = zombies.get(pos);
                zombie.draw();
            }
            for (Point pos: sandwiches.keySet()) {
                Sandwich sandwich = sandwiches.get(pos);
                sandwich.draw();
            }
            if (player.getShot()) {
                player.getBullet().draw();
            }
        }


    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
