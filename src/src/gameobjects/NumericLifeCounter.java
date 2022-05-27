package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;

import java.awt.*;

/**
 *Display a graphic object
 *  on the game window showing a numeric count of lives left.
 */
public class NumericLifeCounter extends GameObject {

    private final TextRenderable textRenderable;
    private Counter livesCounter;
    private GameObjectCollection gameObjectCollection;

    /**
     * Constructor.
     * @param  livesCounter - global lives counter of game.
     * @param  topLeftCorner - top left corner of renderable
     * @param dimensions - dimensions of renderable.
     * @param gameObjectCollection - global game object collection
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter,
                               danogl.util.Vector2 topLeftCorner,
                               danogl.util.Vector2 dimensions,
                               danogl.collisions.GameObjectCollection gameObjectCollection){
        super(topLeftCorner,dimensions,
                new TextRenderable(Integer.toString(livesCounter.value())));
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.textRenderable = new TextRenderable(Integer.toString(livesCounter.value()));
    }

    /**
     * update
     * @param deltaTime - the delta of time passed
     */
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        //System.out.println(livesCounter.value());
    }
}

