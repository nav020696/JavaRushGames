package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15, HEIGHT = 15; //ширина и высота игрового поля
    private Snake snake;
    private Apple apple;
    private int turnDelay; //скорость движения змейки
    private boolean isGameStopped;
    private final static int GOAL = 28; //макисмальная длина змейки, которую можно достичь
    private int score; //игровые очки

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() { //действия, которые нужно выполнить для создания игры
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }

    private void drawScene(){ //отрисовка экрана
        for (int i = 0; i < WIDTH ; i++) {
            for (int j = 0; j < HEIGHT ; j++) {
                setCellValueEx(i,j, Color.BLACK, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) { //всё, что должно происходить в игре на протяжении одного хода
        //super.onTurn(step);
        snake.move(apple);
        if (apple.isAlive == false){
            score +=5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL){
            win();
        }
        drawScene(); //перерисовка экрана

    }

    @Override
    public void onKeyPress(Key key) { //нажатие клавиш
        //super.onKeyPress(key);
        if (key == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        }
        if (key == Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }
        if (key == Key.UP){
            snake.setDirection(Direction.UP);
        }
        if (key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
        if (key == Key.SPACE && isGameStopped){
           createGame();
        }
    }

    private void createNewApple(){
        do{
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }while (snake.checkCollision(apple));
    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "Game Over", Color.YELLOW, 75);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.YELLOW, 75);
    }
}
