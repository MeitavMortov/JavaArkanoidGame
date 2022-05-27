package src.gameobjects;

import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * One of the main game objects. Repels the ball against the bricks.
 */
public class Paddle extends danogl.GameObject{

    private static final float MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private float minDistanceFromEdge;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner - Position of the object,
     * in window coordinates (pixels).
     * Note that (0,0) is the top-left corner of
     * the window.
     * @param dimensions - Width and height in window coordinates.
     * @param renderable - The renderable representing the object.
     *  Can be null, in which case
     *  @param inputListener-
     *  @param windowDimensions-
     * @param minDistanceFromEdge - border for paddle movement

     */
    public Paddle(danogl.util.Vector2 topLeftCorner,
                   danogl.util.Vector2 dimensions,
                   danogl.gui.rendering.Renderable renderable,
                   danogl.gui.UserInputListener inputListener,
                   danogl.util.Vector2 windowDimensions,
                   int minDistanceFromEdge){
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
        this.setTag("Paddle");
    }


    /**
     * update the game
     * @param  deltaTime - the amount of time
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        float max_distance = windowDimensions.x() - minDistanceFromEdge - getDimensions().x();
        if (minDistanceFromEdge > this.getTopLeftCorner().x()){

            this.transform().setTopLeftCornerX(minDistanceFromEdge);
        }
        else if (max_distance < this.getTopLeftCorner().x()) {

            this.transform().setTopLeftCornerX(max_distance);
        }
    }
}
