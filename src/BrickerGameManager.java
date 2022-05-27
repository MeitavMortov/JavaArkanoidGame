package src;


import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.gameobjects.*;

import static src.gameobjects.Clock.clockColorState;
import static src.gameobjects.MockPaddle.isInstantiated;

import java.awt.*;
import java.util.Random;


/**
 * Manger of the whole game:
 */
public class BrickerGameManager extends GameManager{

    /**
     * BORDER_WIDTH
     **/
    public static final int BORDER_WIDTH = 20;

    private static final int WINDOW_DIMENSION_X = 700;

    private static final int WINDOW_DIMENSION_Y = 500;

    private static final int BALL_RADIUS = 20;

    private static final int BALL_SPEED = 200;

    private static final int PADDLE_WIDTH = 100;

    private static final int PADDLE_HEIGHT = 15;

    private static final int BRICK_HEIGHT = 15;

    private static final Color BORDER_COLOR = Color.MAGENTA;

    private static final int NUM_OF_BRICKS_ROWS = 5;
    private static final int NUM_OF_BRICKS_IN_ROWS = 8;
    private static final int DISTANCE_BETWEEN_BRICKS = 10;
    private static final int DISTANCE_BETWEEN_BORDER_TO_FIRST_BRICK = 5;
    private static final int MAX_LIFE_NUM = 3;
    private static final int GRAPHIC_COUNTER_SIZE = 15;
    private static final int NUMERIC_COUNTER_WIDTH = 30;
    private static final int NUMERIC_COUNTER_HEIGHT = 20;
    private static final int SEVENTY = 70;
    private static final int THIRTY = 30;
    private static final int FOURTY = 40;
    private GameObject ball;
    private Vector2 windowDimensions;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Counter gameCounter;
    private Counter bricksCounter;
    private GameObject[] arrayGraphicHearts = new GameObject[MAX_LIFE_NUM];;
    private GameObject numericCounter;
    private BrickStrategyFactory brickStrategyFactory;


    /**
     * Constructor.
     * @param  windowTitle -
     * @param windowDimensions - pixel dimensions for game window height x width.
     * */

    public BrickerGameManager(java.lang.String windowTitle,
                              danogl.util.Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
    }

    /**
     *  Calling this function should initialize the game window. It should initialize objects in the game window - ball, paddle, walls, life counters, bricks.
     * This version of the game has 5 rows, 8 columns of bricks.
     * @param imageReader - an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader - a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener - an InputListener instance for reading user input.
     * @param windowController - controls visual rendering of the game window and object renderables.
     **/
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        createBrickStrategyFactory();
        createBall();
        createPaddle();
        createLeftRightUpWalls();
        createBackground();
        //createBrick();
        createBricks();
        createGraphicCounter();
        createNumericCounter();
        createBrickStrategyFactory();
        windowController.setTargetFramerate(40);
        clockColorState = ClockColor.None; //for clock for the change vilocity strategy
        windowController.setTimeScale(1f);
    }


    /**
     * repositionBall - DO NOT have to implements
     * @param ball -  a ball
     */
    public void repositionBall(GameObject ball){}

    /**
     *  the main function
     * @param args -  the args for the program.
     */
    public static void main(String[] args) {
        new BrickerGameManager("Briker",
                new Vector2(WINDOW_DIMENSION_X,WINDOW_DIMENSION_Y)).run();
    }

    /**
     * create Brick Strategy Factory
     */
    private void createBrickStrategyFactory() {
        this.brickStrategyFactory =
                new BrickStrategyFactory(gameObjects(),
                        this,
                        imageReader,
                        soundReader,
                        inputListener,
                        windowController,
                        windowDimensions);
    }

    /**
     * update the game; update everything using inners methods
     * @param deltaTime the time amount  that passed
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkGameState();
        removePucksAndClocksOutOfGameScreen();
    }

    /**
     * remove Pucks And Clocks Out Of Game Screen-
     * in each delta time checks if one of th objects (pucks \ clock)
     * is out of the screen border and remove it if it is out of the borders.
     */
    private void removePucksAndClocksOutOfGameScreen() {
        for (GameObject object: this.gameObjects()) {
            if (object.getDimensions().x()  > windowDimensions.x() ||
                    object.getDimensions().y() > windowDimensions.y()) {
                this.gameObjects().removeGameObject(object);
            }
        }
    }

    /**
     * checkGameState and update according to the state al the needed things:
     */
    private void checkGameState(){
        float ballHeight = ball.getCenter().y();
        String prompt = "";

        if (bricksCounter.value() == 0){
            //win
            prompt = "YOU WIN";
        }

        else if (ballHeight > windowDimensions.y()){
            //lose in specific game
            gameCounter.decrement();
            //update graphic counter
            this.gameObjects().removeGameObject(arrayGraphicHearts[gameCounter.value()], Layer.STATIC_OBJECTS);
            //update numeric counter:
            this.gameObjects().removeGameObject(numericCounter, Layer.STATIC_OBJECTS);
            createNumericCounter();
            if (gameCounter.value() == 0){
                //lose
                prompt = "YOU LOSE";
            }
            ball.setCenter(windowDimensions.mult(0.5F)); //put the ball in the center of the screen.
        }
        if (!prompt.isEmpty()){
            prompt += " play again?";
            if (windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
                isInstantiated = false; // for the camera strategy
                clockColorState = ClockColor.None; //for clock for the change vilocity strategy
                windowController.setTimeScale(1f);
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * create Ball for the game.
     */
    private void createBall(){
        //create ball:
        Renderable ballImage =  imageReader.readImage("assets/ball.png",true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball =  new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS,BALL_RADIUS),ballImage,collisionSound);
        ball.setCenter(windowDimensions.mult(0.5F));

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if(rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX,ballVelY));
        this.ball = ball;
        this.gameObjects().addGameObject(ball);
    }

    /**
     * create Paddle
     */
    private void createPaddle() {
        //create paddles:
        Renderable paddleImage =  imageReader.readImage("assets/paddle.png",true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH,PADDLE_HEIGHT), paddleImage,
                inputListener, windowDimensions, BORDER_WIDTH );
        paddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y() - THIRTY));
        this.gameObjects().addGameObject(paddle);
    }

    /**
     * create Background
     */
    private void createBackground(){
        //create background:
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                imageReader.readImage( "assets/DARK_BG2_small.jpeg",false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * create Left Walls and Right Walls and Up Walls
     */
    private void createLeftRightUpWalls() {
        //left wall:  Vector2.ZERO
        gameObjects().addGameObject(

                new GameObject(
                        //anchored at top-left corner of the screen
                        Vector2.ZERO,

                        //height of border is the height of the screen
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),

                        new RectangleRenderable(BORDER_COLOR)
                )
        );

        //right wall:
        gameObjects().addGameObject(

                new GameObject(
                        //anchored at top-right corner of the screen
                        new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),

                        //height of border is the height of the screen
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),

                        new RectangleRenderable(BORDER_COLOR)
                )
        );

        //up wall:
        gameObjects().addGameObject(
                new GameObject(
                        //anchored at top-right corner of the screen
                        Vector2.ZERO,
                        //width of border is the width of the screen
                        new Vector2(windowDimensions.x(), BORDER_WIDTH),

                        //this game object is invisible; it doesn’t have a Renderable

                        new RectangleRenderable(BORDER_COLOR)
                )
        );
    }



    /**
     * create Bricks
     */
    private void createBricks() {
        //create Bricks:
        this.bricksCounter = new Counter(0);
        Renderable brickImage =  imageReader.readImage("assets/brick.png",false);
        float brickWidth = windowDimensions.x() - 2*BORDER_WIDTH
                - NUM_OF_BRICKS_IN_ROWS * DISTANCE_BETWEEN_BRICKS - 2*DISTANCE_BETWEEN_BORDER_TO_FIRST_BRICK;
        brickWidth /= NUM_OF_BRICKS_IN_ROWS;

        float brickXPosition = DISTANCE_BETWEEN_BORDER_TO_FIRST_BRICK + BORDER_WIDTH;
        float brickYPosition = BORDER_WIDTH + DISTANCE_BETWEEN_BRICKS;

        for (int row = 0; row < NUM_OF_BRICKS_ROWS; row++){
            for (int brickNum = 0; brickNum < NUM_OF_BRICKS_IN_ROWS; brickNum++) {
                GameObject brick = new Brick(new Vector2(brickXPosition, brickYPosition),
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage,
                        brickStrategyFactory.getStrategy(),
                        bricksCounter);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                brickXPosition += (DISTANCE_BETWEEN_BRICKS + brickWidth);
            }
            brickXPosition = DISTANCE_BETWEEN_BORDER_TO_FIRST_BRICK + BORDER_WIDTH;
            brickYPosition += (BRICK_HEIGHT + DISTANCE_BETWEEN_BRICKS);
        }

    }

    /**
     * create Graphic Counter
     */
    private void createGraphicCounter() {
        Renderable graphicCounterImage =  imageReader.readImage("assets/heart.png",false);
        this.gameCounter =  new Counter(MAX_LIFE_NUM);
        float xPosition = BORDER_WIDTH*2;
        float yPosition = windowDimensions.y() - FOURTY;
        Vector2 graphicCounterSize = new Vector2(GRAPHIC_COUNTER_SIZE,GRAPHIC_COUNTER_SIZE);
        for (int graphicHeartNum = 1 ; graphicHeartNum <= MAX_LIFE_NUM ; graphicHeartNum++) {
            GameObject graphicHeart = new GraphicLifeCounter(new Vector2(xPosition, yPosition),
                    graphicCounterSize,gameCounter, graphicCounterImage,
                    new GameObjectCollection(), graphicHeartNum );
            arrayGraphicHearts[graphicHeartNum-1] = graphicHeart;
            gameObjects().addGameObject(graphicHeart, Layer.STATIC_OBJECTS);
            xPosition += THIRTY;
        }
    }


    /**
     * createNumericCounter
     */
    private void createNumericCounter() {
        float xPosition = BORDER_WIDTH*2;
        float yPosition = windowDimensions.y() - SEVENTY;
        Vector2 numericCounterSize = new Vector2(NUMERIC_COUNTER_WIDTH,NUMERIC_COUNTER_HEIGHT);
        GameObject numericCounter = new NumericLifeCounter(gameCounter,new Vector2(xPosition, yPosition),
                numericCounterSize, gameObjects());
        gameObjects().addGameObject(numericCounter, Layer.STATIC_OBJECTS);
        this.numericCounter = numericCounter;
    }

}
