package src.brick_strategies;

import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

public class PuckStrategy extends RemoveBrickStrategyDecorator{
    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * constructor
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader - an ImageReader instance for reading images from files for rendering
     * @param soundReader - a SoundReader instance for reading soundclips from files for rendering event sounds.
     */
    public PuckStrategy(CollisionStrategy toBeDecorated,
                         danogl.gui.ImageReader imageReader,
                         danogl.gui.SoundReader soundReader) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Add pucks to game on collision and delegate to held CollisionStrategy.
     * @param thisObj -
     * @param otherObj -
     * @param counter - global brick counter.
     */
    @Override
    public void onCollision(danogl.GameObject thisObj,
                            danogl.GameObject otherObj,
                            danogl.util.Counter counter){
        super.onCollision(thisObj,otherObj,counter);
        for (int i = 0; i < 3; i++) {
            Renderable puckImage =  imageReader.readImage("assets/mockBall.png",true);
            Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
            float puckSize = thisObj.getDimensions().x()/3;
            Vector2 puckPosision = thisObj.getCenter();
            Puck puck =  new Puck(puckPosision,
                    new Vector2(puckSize,puckSize),
                    puckImage,
                    collisionSound);
            float ballVelX = otherObj.getVelocity().x();
            float ballVelY = otherObj.getVelocity().y();
            Random rand = new Random();
            if(rand.nextBoolean()) {
                ballVelX *= -1;
            }
            if(rand.nextBoolean()) {
                ballVelY *= -1;
            }
            puck.setVelocity(new Vector2(ballVelX,ballVelY));
            toBeDecorated.getGameObjectCollection().addGameObject(puck);
        }

    }

}
