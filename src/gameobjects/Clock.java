package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * game object that help the change vilocity satrategy
 */
public class Clock extends danogl.GameObject {
    /**
     * clockColorState is a static field represents what is the state of the game in the current state.
     * help know if needs t change the vilocity or which clocks - which colors to create.
     */
    public static ClockColor clockColorState;
    private static final float TIME_PROGRESS_RATE_FOR_RED_CLOCK = (float) 1.1;
    private static final float TIME_PROGRESS_RATE_FOR_GREEN_CLOCK = (float) 0.9;
    private ClockColor clockColor;
    private WindowController windowController;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Clock(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 ClockColor clockColor,
                 danogl.gui.WindowController windowController) {
        super(topLeftCorner, dimensions, renderable);
        this.clockColor = clockColor;
        this.windowController = windowController;
    }

    /**
     * return if the object should Collide With clock
     * @param object object
     * @return true if paddle
     */
    @Override
    public boolean shouldCollideWith(GameObject object){
        if (object.getTag().equals("Paddle")){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * onCollisionEnter
     * if collide with another color than the current color it change the clockColorState
     * @param other other game object
     * @param collision collision of game objects
     */
    @Override
    public void onCollisionEnter(danogl.GameObject other,
                                 danogl.collisions.Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.shouldCollideWith(other)) {
            if ((this.clockColor == ClockColor.RED) && (clockColorState != ClockColor.RED)) {
                windowController.setTimeScale(TIME_PROGRESS_RATE_FOR_RED_CLOCK);
                clockColorState = this.clockColor;
            }
            if ((this.clockColor == ClockColor.GREEN) && (clockColorState != ClockColor.GREEN)) {
                windowController.setTimeScale(TIME_PROGRESS_RATE_FOR_GREEN_CLOCK);
                clockColorState = this.clockColor;
            }
        }
    }


}
