import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import processing.core.*;

public class GUI extends PApplet {
    Simulator sim;
    DisplayWindow gridDisplay;

    public void settings() {
        size(640, 550); // set the size of the screen.
    }

    public void setup() {
        frameRate(2);

        // Create a simulator
        sim = new Simulator(100, 100);
        sim.initialize(0.37);
        int count = sim.count(Simulator.LIVE_TREE);
        System.out.println(count + "/" + sim.size() + " = " + ((double) count / sim.size()));

        for (int i = 0; i < 5; i++) {
            sim.setPointFire();
        }

        // Create the display
        // parameters: (10,10) is upper left of display
        // (620, 530) is the width and height
        gridDisplay = new DisplayWindow(this, 10, 10, 620, 530);

        gridDisplay.setNumCols(sim.getWidth());        // NOTE:  these must match your simulator!!
        gridDisplay.setNumRows(sim.getHeight());

        // Set different grid values to different colors

        gridDisplay.setColor(Simulator.FIRE, color(255, 0, 0));
        gridDisplay.setColor(Simulator.ASH, color(180, 180, 180));
        gridDisplay.setColor(Simulator.LIVE_TREE, color(0, 255, 0));
        gridDisplay.setColor(Simulator.EMPTY, color(255, 255, 255));
    }

    @Override
    public void draw() {
        background(200);

        // TODO: have your simulation run one step.
        sim.runOneStep();

        gridDisplay.drawGrid(sim.getDisplayGrid()); // display the game
    }

    public static void main(String[] args) {
        PApplet.main("GUI");
    }
}