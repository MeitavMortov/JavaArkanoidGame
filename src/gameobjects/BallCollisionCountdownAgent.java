package src.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * an object of this class is instantiated on collision of ball with a
 * brick with a change camera strategy. It checks
 * ball's collision counter every frame, and once the it finds
 * the ball has collided countDownValue times since instantiation,
 * it calls the strategy to reset the camera to normal.
 */
public class BallCollisionCountdownAgent extends GameObject {
    private  int nunOfCollisionsBefore;
    private Ball ball;
    private ChangeCameraStrategy owner;
    private int countDownValue;

    /**
     * Constructor
     *
     * @param ball                  -  Ball object whose collisions are to be counted.
     * @param owner                 -  Object asking for countdown notification.
     * @param countDownValue-Number of ball collisions.
     *                              Notify caller object that the ball collided countDownValue times since instantiation.
     */
    public BallCollisionCountdownAgent(Ball ball,
                                       ChangeCameraStrategy owner,
                                       int countDownValue) {
        super(ball.getTopLeftCorner(),ball.getDimensions(),null);
        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
        this.nunOfCollisionsBefore = ball.getCollisonCount();
    }

    /**
     * update
     * @param deltaTime delta time
     */
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        if (ball.getCollisonCount() - nunOfCollisionsBefore  >= countDownValue){
            owner.turnOffCameraChange();
            owner.getGameObjectCollection().removeGameObject(this);
        }
    }
}