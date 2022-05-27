package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * Factory class for creating Collision strategies.
 */
public class BrickStrategyFactory {
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private  CollisionStrategy[] arrayStrategies = new CollisionStrategy[5];
    private  Random rand = new Random();

    /**
     * constructor
     * @param gameObjectCollection - gameObjectCollection
     * @param gameManager - gameManager
     * @param imageReader - imageReader
     * @param soundReader - soundReader
     * @param inputListener - inputListener
     * @param windowController - windowController
     * @param windowDimensions - windowDimensions
     */

    public BrickStrategyFactory(danogl.collisions.GameObjectCollection gameObjectCollection,
                                 BrickerGameManager gameManager,
                                 danogl.gui.ImageReader imageReader,
                                 danogl.gui.SoundReader soundReader,
                                 danogl.gui.UserInputListener inputListener,
                                 danogl.gui.WindowController windowController,
                                 danogl.util.Vector2 windowDimensions){
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;

        RemoveBrickStrategy basicCollisionStrategy =
                new RemoveBrickStrategy(gameObjectCollection);

        //building array of possible strategies for the double strategy:
        this.arrayStrategies[1] = new PuckStrategy(basicCollisionStrategy,
                imageReader,soundReader);

        this.arrayStrategies[2] = new AddPaddleStrategy(basicCollisionStrategy,
                imageReader,
                inputListener,
                windowDimensions);

        this.arrayStrategies[3] = new ChangeCameraStrategy(
                basicCollisionStrategy,windowController,gameManager);

        this.arrayStrategies[4] = new ChangeVelocityStrategy(basicCollisionStrategy,
                imageReader,
                windowController);

        this.arrayStrategies[0] = basicCollisionStrategy;

    }

    /**
     * public CollisionStrategy getStrategy()
     * method randomly selects between 5 strategies and returns one CollisionStrategy object
     * which is a RemoveBrickStrategy decorated by one
     * of the decorator strategies, or decorated by two
     * randomly selected strategies, or decorated by one of the decorator strategies
     * and a pair of additional two decorator strategies.
     * @return CollisionStrategy object.
     */
    public CollisionStrategy getStrategy(){
        // choose random strategy:
        int strategyNum = rand.nextInt(arrayStrategies.length + 1);
        if (strategyNum != arrayStrategies.length) {  // different from 5
            return arrayStrategies[strategyNum];
        }

        // the strategy is a double strategy = strategyNum is 5

        // the first strategy can  be double strategy:
        strategyNum = rand.nextInt(arrayStrategies.length) + 1;

        // the outer strategy can not be double strategy:
        // and we don't want it to be the basic
        int outerStrategyNum = rand.nextInt(arrayStrategies.length - 1) + 1;

        if(strategyNum != arrayStrategies.length){
            // double strategy is build from tow basic strategies:
            return returnFullDoubleStrategy(arrayStrategies[strategyNum], outerStrategyNum);
        }

        else {
            // double strategy is build from two strategies:
            // one is double and second is basic

            // find num for the inner strategy: only from basics:
            // and we don't want it to be the basic
            int innerStrategyNum = rand.nextInt(arrayStrategies.length - 1) + 1;
            // used arrayStrategies[outerStrategyNum] which can not be double strategy:
            CollisionStrategy innerStrategy =
                    returnFullDoubleStrategy(arrayStrategies[outerStrategyNum],
                            innerStrategyNum);

            return  returnFullDoubleStrategy(innerStrategy, strategyNum);
        }
    }

    /**
     * method that return Full DoubleStrategy
     * its return a strategy that build from two other strtegies. one that was given as a parameter and
     * a number indicate what is the second random strategy the double strategy will have.
     * @param innerStrategy - strategy that will be decorated with another strategy
     * @param StrategyNum - StrategyNum indicate what is the second random strategy the double strategy will have
     * @return Full DoubleStrategy
     */
    private CollisionStrategy returnFullDoubleStrategy(CollisionStrategy innerStrategy,
                                                       int StrategyNum) {
        if (StrategyNum == 1){
            //puck Strategy
            return new PuckStrategy(innerStrategy, imageReader,soundReader);
        }
        else if (StrategyNum == 2){
            //addPaddle Strategy
            return new AddPaddleStrategy(innerStrategy,
                    imageReader,inputListener,windowDimensions);
        }
        else if (StrategyNum == 3){
            //changeCameraStrategy
            return new ChangeCameraStrategy(innerStrategy, windowController,gameManager);
        }
        else if (StrategyNum == 4){
            //changeVelocityStrategy
            return new ChangeVelocityStrategy(innerStrategy, imageReader,windowController);
        }
        else {
            //As requested :
            //not supposed to be because chosen from 1,2,3,4
            // rand.nextInt(arrayStrategies.length - 1) + 1s
            return arrayStrategies[0];
        }
    }
}
