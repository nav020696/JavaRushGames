package com.javarush.games.racer.road;

import com.javarush.engine.cell.Game;
import com.javarush.games.racer.PlayerCar;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    private List<RoadObject> items = new ArrayList<>();
    private static final int PLAYER_CAR_DISTANCE = 12;
    private int passedCarsCount = 0;

    public int getPassedCarsCount() {
        return passedCarsCount;
    }

    private RoadObject createRoadObject(RoadObjectType type, int x, int y){
        if (type == RoadObjectType.THORN){
            return new Thorn(x,y);
        }else if (type == RoadObjectType.DRUNK_CAR){
            return new MovingCar(x,y);
        }else{
            return new Car(type, x,y);
        }
    }

    private void addRoadObject(RoadObjectType type, Game game){
        int x = game.getRandomNumber(FIRST_LANE_POSITION, FOURTH_LANE_POSITION);
        int y = -1 * RoadObject.getHeight(type);
        RoadObject roadObject = createRoadObject(type, x, y);
        if (isRoadSpaceFree(roadObject)){
            items.add(roadObject);
        }
    }

    public void draw(Game game){
        for (RoadObject roadobject: items) {
          roadobject.draw(game);
        }
    }

    public void move(int boost){
        for (RoadObject roadobject: items) {
            roadobject.move(boost + roadobject.speed, items);
        }
        deletePassedItems();
    }
    private boolean isThornExists(){
        boolean type = false;
        for (RoadObject roadobject: items) {
            if (roadobject.type == RoadObjectType.THORN){
                type = true;
                break;
            }
        }
        return type;
    }

    private void generateThorn(Game game){
        int i = game.getRandomNumber(100);
        if (!isThornExists() && i < 10){
            addRoadObject(RoadObjectType.THORN, game);
        }
    }
    public void generateNewRoadObjects(Game game){
        generateThorn(game);
        generateRegularCar(game);
        generateMovingCar(game);
    }

    private void deletePassedItems(){
        List<RoadObject> items1 = new ArrayList<>(items); //клонирование первого списка
        for (RoadObject roadobject: items1) {
            if (roadobject.y >= RacerGame.HEIGHT){
                items.remove(roadobject);
                if (roadobject.type != RoadObjectType.THORN){
                    passedCarsCount++;
                }
            }
        }
    }

    public boolean checkCrush(PlayerCar playerCar){
        boolean i = false;
        for (RoadObject roadobject: items) {
            if(roadobject.isCollision(playerCar)){
                i = true;
                break;
            }
        }
        return i;
    }

    private void generateRegularCar(Game game){
        int i = game.getRandomNumber(100);
        int carTypeNumber = game.getRandomNumber(4);
        if (i < 30){
            addRoadObject(RoadObjectType.values()[carTypeNumber], game);
        }
    }

    private boolean isRoadSpaceFree(RoadObject object){
        boolean i = false;
        for (RoadObject roadobject: items) {
            if (!roadobject.isCollisionWithDistance(object, PLAYER_CAR_DISTANCE)){
                i = true;
            }else{
                i = false;
            }
        }
        return i;
    }

    private boolean isMovingCarExists(){
        boolean i = false;
        for (RoadObject roadobject: items) {
            if (roadobject.type == RoadObjectType.DRUNK_CAR){
                i = true;
                break;
            }
        }
        return i;
    }
    private void generateMovingCar(Game game){
        int i = game.getRandomNumber(100);
        if (i < 10 && !isMovingCarExists()){
            addRoadObject(RoadObjectType.DRUNK_CAR, game);
        }
    }
}
