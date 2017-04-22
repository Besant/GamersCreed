package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

/**
 * Created by 24ari on 2/18/2017.
 */

public class Powerups {

    public static final int DEFAULT_Y = 1000;
    private static Texture texture;
    //float x = Gdx.graphics.getWidth();
    private Rectangle asteroidBounds;

    float x, y;
    public boolean remove = false;

    public Powerups(float x) {
        this.y = DEFAULT_Y;
        this.x = x+300;

        if (texture == null) {
            texture = new Texture("powerup.png");
        }


        asteroidBounds = new Rectangle(this.x, this.y, texture.getWidth(), texture.getHeight());

    }

    public void update(float dt) {
        y -= 100 * Gdx.graphics.getDeltaTime();

        if (y <= 0) {
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