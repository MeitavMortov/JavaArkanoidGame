package src.brick_strategies;

import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Changes camera focus from ground to ball until ball
 * collides NUM_BALL_COLLISIONS_TO_TURN_OFF times.
 */
public class ChangeCameraStrategy extends  RemoveBrickStrategyDecorator{

    private static final int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;
    private CollisionStrategy toBeDecorated;
    private WindowController windowController;
    private BrickerGameManager gameManager;

    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                danogl.gui.WindowController windowController,
                                BrickerGameManager gameManager){
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Change camera position on collision and delegate to held CollisionStrategy.
     * @param thisObj -
     * @param otherObj -
     * @param counter - global brick counter.
     */
    @Override
    public void onCollision(danogl.GameObject thisObj,
                             danogl.GameObject otherObj,
                             danogl.util.Counter counter) {
        super.onCollision(thisObj,otherObj,counter);
        if (otherObj instanceof Puck){
            return;
        }
        // check if the camera is null:
        if (gameManager.getCamera() == null) {
            gameManager.setCamera(
                    new Camera(
                            otherObj, 			//object to follow
                            Vector2.ZERO, 	//follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                            windowController.getWindowDimensions()   //share the window dimensions
                    )
            );
            BallCollisionCountdownAgent ballCollisionCountdownAgent =
                    new BallCollisionCountdownAgent((Ball)otherObj,
                    this, NUM_BALL_COLLISIONS_TO_TURN_OFF);
        }

    }

    /**
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange() {
        gameManager.setCamera(null);
    }

}
