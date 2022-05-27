package src.brick_strategies;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.gameobjects.Clock;
import src.gameobjects.ClockColor;

import java.util.Random;

import static src.gameobjects.Clock.clockColorState;

public class ChangeVelocityStrategy  extends  RemoveBrickStrategyDecorator{
    private static final float CLOCK_VELOCITY = 200;
    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;
    private WindowController windowController;

    /**
     * constructor
     *
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public ChangeVelocityStrategy(CollisionStrategy toBeDecorated,
                                  danogl.gui.ImageReader imageReader,
                                  danogl.gui.WindowController windowController) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.windowController = windowController;
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
        Clock clock;
        Vector2 topLeftCorner = thisObj.getCenter();
        Vector2 dimensions = thisObj.getDimensions();
        Renderable greenClockImage =  imageReader.readImage("assets/slow.png",true);
        Renderable redClockImage =  imageReader.readImage("assets/quicken.png",true);

        if (clockColorState == ClockColor.None) {// NEED TO CHOOSE random color
            Random rand = new Random();
            if (rand.nextBoolean()){
                //create green clock
                clock = new Clock(topLeftCorner,dimensions,greenClockImage, ClockColor.GREEN,windowController);
            }
            else {
                //create red clock
                clock = new Clock(topLeftCorner,dimensions,redClockImage, ClockColor.RED,windowController);
            }
        }
        else if (clockColorState == ClockColor.GREEN){
            //create only red clocks
            clock = new Clock(topLeftCorner,dimensions,redClockImage, ClockColor.RED,windowController);
        }
        else { //clockColorState == RED
             //create only green clocks
            clock = new Clock(topLeftCorner,dimensions,greenClockImage, ClockColor.GREEN,windowController);
        }
        clock.setVelocity(new Vector2(0, CLOCK_VELOCITY));
        toBeDecorated.getGameObjectCollection().addGameObject(clock);
        }
}
