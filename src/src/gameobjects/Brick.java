package src.gameobjects;


import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;

/**
 * Brick is a game object.
 */
public class Brick extends GameObject {


    private CollisionStrategy collisionStrategy;
    private Counter counter;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner - Position of the object, in window coordinates
     *  (pixels). Note that (0,0) is the top-left
     * @param dimensions - Width and height in window coordinates.
     * @param renderable - The renderable representing the object.
     *  Can be null, in which case
     * @param counter -
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 CollisionStrategy collisionStrategy,
                 Counter counter) {
        super(topLeftCorner, dimensions, renderable);

        this.collisionStrategy = collisionStrategy;

        this.counter = counter;
        this.counter.increment();
    }

    /**
     * On collision, object velocity is
     * reflected about the normal vector of the surface it collides with.
     * @param other - other GameObject instance participating in collision.
     * @param collision - Collision object.
     */
    @Override
    public void onCollisionEnter(danogl.GameObject other, danogl.collisions.Collision collision)
    {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, counter);
    }
}
