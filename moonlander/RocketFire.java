package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

import java.util.List;

public class RocketFire extends GameObject {
    private List<int[][]> frames; //Список матриц шагов анимаций
    private int frameIndex; //индекс текущей матрицы-анимации
    private boolean isVisible; //видимость огня
    public RocketFire(List <int[][]> frameList) {
        super(0,0,frameList.get(0));
        this.frames = frameList;
        frameIndex = 0;
        isVisible = false;
    }
    private void nextFrame(){ //переключать кадры для создания анимации
        frameIndex ++;
        if (frameIndex >= frames.size()){
            frameIndex = 0;
        }
        matrix = frames.get(frameIndex);
    }

    @Override
    public void draw(Game game) {
        if (!isVisible){
            return;
        }else {
            nextFrame();
            super.draw(game);
        }
    }
    public void show(){
        isVisible = true;
    }
    public void hide(){
        isVisible = false;
    }
}
