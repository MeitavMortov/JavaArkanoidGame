package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 *Display a graphic object on
 *  the game window showing as many widgets as lives left.
 */
public class GraphicLifeCounter extends GameObject {


    private Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;
    private int numOfLives;

    /**
     * Constructor
     * @param widgetTopLeftCorner  - top left corner of left most life widgets.
     *     Other widgets will be displayed to its right, aligned in hight.
     * @param widgetDimensions - dimensions of widgets to be displayed.
     * @param livesCounter - global lives counter of game.
     * @param widgetRenderable - image to use for widgets.
     * @param gameObjectsCollection - global game object collection managed by game manager.
     * @param numOfLives - global setting of number of lives a player will have in a game.
     */

    public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner,
                               danogl.util.Vector2 widgetDimensions,
                               danogl.util.Counter livesCounter,
                               danogl.gui.rendering.Renderable widgetRenderable,
                               danogl.collisions.GameObjectCollection gameObjectsCollection,
                               int numOfLives){
        super(widgetTopLeftCorner,widgetDimensions,widgetRenderable);

        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;
    }

    /**
     * updates the game.
     * @param deltaTime - the delta of time passed
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
