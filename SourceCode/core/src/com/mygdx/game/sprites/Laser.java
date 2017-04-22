package com.mygdx.game.sprites;

/**
 * Created by 24ari on 2/16/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Laser {

    public static final int SPEED = 500;
    public static final int DEFAULT_X = 180;
    private static Texture texture;
    private Rectangle laserBounds;

    float x,y;
    public boolean remove = false;

    public Laser(float y){
        this.x = DEFAULT_X;
        this.y = y;

        if (texture == null) {
            texture = new Texture("laser1.png");
        }

        laserBounds = new Rectangle(this.x,this.y,texture.getWidth(),texture.getHeight());

    }

    public void update(float dt) {
        x += SPEED * dt;
        if (x > Gdx.graphics.getWidth()) {
            remove = true;
        }
        laserBounds.setPosition(this.x,this.y);
    }

     public void render (SpriteBatch batch){
         batch.draw(texture,x,y);
    }

    public Rectangle getBounds(){
        return laserBounds;
    }

}



