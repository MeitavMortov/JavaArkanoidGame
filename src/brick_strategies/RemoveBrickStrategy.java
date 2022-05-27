package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {

    private GameObjectCollection gameObjectCollection;

    /**
     * Constructor.
     */
    public RemoveBrickStrategy(danogl.collisions.GameObjectCollection gameObjectCollection){
        this.gameObjectCollection = gameObjectCollection;
    }


    /**
     *  To be called on brick collision.
     * @param thisObj -
     * @param otherObj -
     * @param counter - global brick counter.
     **/
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter){
        if (gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            counter.decrement();
        }
    }


    /**
     *    All collision strategy objects should hold a reference to the global
     *    game object collection and be able to return it.\
     * @return global game object collection whose reference is held in object.
     */
    @Override
    public danogl.collisions.GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }

   }
