package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle extends Paddle{
    public static boolean	isInstantiated;
    private GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       - Position of the object,
     *                            in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of
     *                            the window.
     * @param dimensions          - Width and height in window coordinates.
     * @param renderable          - The renderable representing the object.
     *                            Can be null, in which case
     * @param inputListener       -listener object for user input.
     * @param windowDimensions    - dimensions of game window.
     * @param gameObjectCollection - Collection of gameObject
     * @param minDistanceFromEdge - border for paddle movement
     * @param numCollisionsToDisappear - num Collisions To Disappear
     */
    public MockPaddle(Vector2 topLeftCorner,
                      Vector2 dimensions,
                      Renderable renderable,
                      UserInputListener inputListener,
                      Vector2 windowDimensions,
                      danogl.collisions.GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge,
                      int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        isInstantiated  = true;
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
    }

    /**
     * on Collision Enter
     * @param other other game object
     * @param collision collision of game objects
     */
    @Override
    public void onCollisionEnter(danogl.GameObject other, danogl.collisions.Collision collision){
        super.onCollisionEnter(other, collision);
        numCollisionsToDisappear -= 1;
        if (numCollisionsToDisappear == 0){
            isInstantiated = false;
            gameObjectCollection.removeGameObject(this);
        }
    }
}
