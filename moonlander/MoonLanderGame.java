package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 64, HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape, platform;
    private boolean isUpPressed, isRightPressed, isLeftPressed, isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        showGrid(false);
    }

    private void createGame() {
        isLeftPressed = false;
        isRightPressed = false;
        isUpPressed = false;
        isGameStopped = false;
        score = 1000;
        createGameObjects();
        drawScene();
        setTurnTimer(50);
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) { //закрашиваем все ячейки поля
            for (int j = 0; j < HEIGHT; j++) {
                setCellColor(i, j, Color.ORANGE);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }

    private void createGameObjects() {
        rocket = new Rocket(WIDTH / 2, 0);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
        platform = new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        drawScene();
        if (score > 0) {
            score -= 1;
        }
        setScore(score);
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x > 0 && x < WIDTH && y > 0 && y < HEIGHT) {
            super.setCellColor(x, y, color);
        }
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.UP) {
            isUpPressed = true;
        }
        if (key == Key.LEFT) {
            isLeftPressed = true;
            isRightPressed = false;
        }
        if (key == Key.RIGHT) {
            isLeftPressed = false;
            isRightPressed = true;
        }
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.UP) {
            isUpPressed = false;
        }
        if (key == Key.LEFT) {
            isLeftPressed = false;
        }
        if (key == Key.RIGHT) {
            isRightPressed = false;
        }
    }

    private void check() { //пересечение координат ракеты и ландшавта
        if (rocket.isCollision(landscape) && !rocket.isCollision(platform)) {
            gameOver();
        }
        if (rocket.isCollision(platform)) {
            win();
        }
    }

    private void win() {
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "You Win", Color.BLACK, 75);
        stopTurnTimer();
    }

    private void gameOver() {
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "Game Over", Color.BLACK, 75);
        stopTurnTimer();
        score = 0;
    }
}

