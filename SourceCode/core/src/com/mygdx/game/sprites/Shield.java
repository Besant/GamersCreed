package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class Shield {

    public static final int SPEED = 1000;
    public static final int DEFAULT_X = 50;
    private static Texture texture;
    private Rectangle shieldBounds;
    private long activationTime;


    float x,y;
    public boolean remove = false;

    public Shield(float x1, float y1){
        this.x = x1;
        this.y = y1;
        this.activationTime = TimeUtils.millis();

        if (texture == null) {
            texture = new Texture("shield1.png");
            //texture = new Texture("laser1.png");
        }

        shieldBounds = new Rectangle(this.x,this.y,texture.getWidth(),texture.getHeight());
    }



    //public long getActivationTime(){
      //  return this.activationTime;
    //}


    public void update(float dt) {

       // x += SPEED * dt; //position
        //if (x > Gdx.graphics.getWidth()) {
          //  remove = true;
        //}


        shieldBounds.setPosition(this.x,this.y); //NEED THIS!
    }

    public void render (SpriteBatch batch){
        batch.draw(texture,x,y);

    }

    public Rectangle getBounds(){
        return shieldBounds;
    }



}
