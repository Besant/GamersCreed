package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

/**
 * Created by 24ari on 2/18/2017.
 */

public class Asteroid {


    public static final int SPEED = 200;
    public static final int DEFAULT_X = 1800;
    private static Texture texture;
    //float x = Gdx.graphics.getWidth();
    private Rectangle asteroidBounds;

    float x, y;
    public boolean remove = false;

    public Asteroid(float y) {
        this.x = DEFAULT_X;
        this.y = y;

        if (texture == null) {
            texture = new Texture("asteroid.png");
        }

        asteroidBounds = new Rectangle(this.x, this.y, texture.getWidth(), texture.getHeight());
    }

    public void update(float dt,float speed) {
        x -= speed * Gdx.graphics.getDeltaTime();
        if (x <= 0) {
            remove = true;
        }
        asteroidBounds.setPosition(this.x, this.y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getBounds(){
      return asteroidBounds;
    }


    public float getX() {
        return this.x;
    }

}