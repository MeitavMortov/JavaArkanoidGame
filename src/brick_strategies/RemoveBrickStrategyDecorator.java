package src.brick_strategies;

/**
 * Abstract decorator to add functionality to the remove brick strategy,
 * following the decorator pattern.
 * All strategy decorators should inherit from this class.
 */
public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {
    private CollisionStrategy toBeDecorated;

    /**
     * constructor
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){

        this.toBeDecorated = toBeDecorated;
    }

    /**
     * Should delegate to held Collision strategy object.
     * @param thisObj -
     * @param otherObj -
     * @param counter - global brick counter.
     */
    @Override
    public void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj, danogl.util.Counter counter){
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }

    /**
     * return held reference to global game object. Delegate to held object to be decorated
     * @return reference to global game object.
     */
    @Override
    public danogl.collisions.GameObjectCollection getGameObjectCollection(){
        return toBeDecorated.getGameObjectCollection();
    }
}
