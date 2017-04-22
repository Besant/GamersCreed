package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by 24ari on 2/15/2017.
 */

public class Bird {
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Vector3 boundsPosition;
    private Rectangle bounds;
    private Rectangle boundsBird;

    Sprite bird;

    public Bird(int x, int y){
        position = new Vector3(x,y,0);
        boundsPosition = new Vector3(x,y,0);
        bird = new Sprite(new Texture(Gdx.files.internal("spaceship1.png")));
        boundsBird = new Rectangle(x,y,bird.getWidth(),bird.getHeight());
        bounds = new Rectangle(0, -600, 130, 2000);

    }

    public Vector3 getPosition() {
        return position;
    }



    public Sprite getTexture() {
        return bird;
    }


    public Rectangle getBounds(){
        return bounds;
    }

    public Rectangle getBoundsBird(){
        return boundsBird;
    }

    public void setPosition(float x, float y,float deltaY){

        if(y > 950)
            y = 950;
        if(y < -75)
            y = -75;

        position = new Vector3(x, y, 0);

    }

    public void setBoundsPosition(float x, float y , float deltaY){
        boundsPosition = new Vector3(x,y,0);
    }


}
