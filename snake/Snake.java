package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;

    private Direction direction = Direction.LEFT;

    public void setDirection(Direction direction) {
        if ( ((this.direction==Direction.RIGHT) || (this.direction==Direction.LEFT)) && (snakeParts.get(0).x == snakeParts.get(1).x)  ) {
            return;
        }

        if ( (this.direction==Direction.DOWN || this.direction==Direction.UP) && (snakeParts.get(0).y == snakeParts.get(1).y) ) {
            return;
        }
        this.direction = direction;
    }


    public Snake(int x, int y) {
        GameObject n0 = new GameObject(x, y);
        GameObject n1 = new GameObject(x + 1, y);
        GameObject n2 = new GameObject(x + 2, y);
        snakeParts.add(n0);
        snakeParts.add(n1);
        snakeParts.add(n2);
    }

    public void draw(Game game) {
        if (isAlive){
        game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.ORANGE, 75);
        for (int i = 1; i < snakeParts.size(); i++) {
            game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.ORANGE, 75);
        }
        }else{
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }

    public void move(Apple apple){
        GameObject head = createNewHead();
        if (apple.x == head.x && apple.y == head.y){
            apple.isAlive = false;
            snakeParts.add(0,head);
        }else if (head.x < 0 || head.x >= 15 || head.y < 0 || head.y >= 15){
            isAlive = false;
        }else{
            if (!checkCollision(head)) {
                snakeParts.add(0, head);
                removeTail();
            }else {
                isAlive = false;
            }

        }
    }

    public GameObject createNewHead(){
      if (direction == Direction.LEFT){
          return new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
      }
      if (direction == Direction.DOWN){
          return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y +1);
      }
      if (direction == Direction.RIGHT){
          return new GameObject(snakeParts.get(0).x +1, snakeParts.get(0).y);
      }else {
          return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
      }
    }

    public void removeTail(){ //удаление последнего элемента тела
        snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision(GameObject gameObject){ //проверять новосозданную голову змейки на совпадение со всеми остальными элементами её тела
        boolean s = false;
        for (int i = 0; i < snakeParts.size(); i++) {
            if (gameObject.x == snakeParts.get(i).x && gameObject.y == snakeParts.get(i).y){
                s = true;
                break;
            }
        }
        return s;
    }

    public int getLength(){
        return snakeParts.size();
    }
}
