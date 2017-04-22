package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by 24ari on 2/18/2017.
 */

public class BigLaser {


        public static final int SPEED = 1000;
        public static final int DEFAULT_X = 200;
        private static Texture texture;
        private Rectangle BigLaserBounds;


        float x,y;
        public boolean remove = false;

        public BigLaser(float y){ //pass y position as argument
            this.x = DEFAULT_X;
            this.y = y;



            if (texture == null) {
                texture = new Texture("biglaser3.png");
            }

            BigLaserBounds = new Rectangle(this.x,this.y,texture.getWidth(),texture.getHeight());
        }


        public void update(float dt) {
           // System.out.println("we are updating");
            x += SPEED * dt; //position
            if (x > Gdx.graphics.getWidth()) {
                remove = true;
            }
            BigLaserBounds.setPosition(this.x,this.y); //NEED THIS!
        }

        public void render (SpriteBatch batch){
            batch.draw(texture,x,y);
            //System.out.println("we are rendering");
        }

        public Rectangle getBounds(){
            return BigLaserBounds;
        }

}






