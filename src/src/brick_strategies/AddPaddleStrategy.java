package src.brick_strategies;

import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.MockPaddle;

import static src.gameobjects.MockPaddle.isInstantiated;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Introduces extra
 * paddle to game window which remains until colliding
 * NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE with other game objects.
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{
    public static final int MAX_COLLISION_NUM = 3;
    private static final int PACK_PADDLE_WIDTH = 100;
    private static final int PACK_PADDLE_HEIGHT = 15;
    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;


    /**
     * constructor.
     * @param toBeDecorated - a  Collision Strategy to decorate
     * @param imageReader - render able
     * @param inputListener - inputListener
     * @param windowDimensions - windowDimensions
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             danogl.gui.ImageReader imageReader,
                             danogl.gui.UserInputListener inputListener,
                             danogl.util.Vector2 windowDimensions) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * on Collision
     * @param thisObj - this Object
     * @param otherObj - other Object
     * @param counter - global brick counter.
     */
    @Override
    public void onCollision(danogl.GameObject thisObj,
                            danogl.GameObject otherObj,
                            danogl.util.Counter counter){
        super.onCollision(thisObj,otherObj,counter);
        if (!isInstantiated) {
            Renderable puckPaddleImage =  imageReader.readImage("assets/paddle.png",true);
            Vector2 puckPaddlePosition = new Vector2(windowDimensions.x() * 0.5f, windowDimensions.y() * 0.5f);
            Vector2 puckPaddleDimensions = new Vector2(PACK_PADDLE_WIDTH, PACK_PADDLE_HEIGHT);
            MockPaddle mockPaddle = new MockPaddle(puckPaddlePosition,
                    puckPaddleDimensions,
                    puckPaddleImage,
                    inputListener,
                    windowDimensions,
                    toBeDecorated.getGameObjectCollection(),
                    BrickerGameManager.BORDER_WIDTH,
                    MAX_COLLISION_NUM);
            toBeDecorated.getGameObjectCollection().addGameObject(mockPaddle);
        }
    }
}
