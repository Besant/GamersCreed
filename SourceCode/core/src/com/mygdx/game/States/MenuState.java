package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SpaceAbyss;

/**
 * Created by 24ari on 2/15/2017.
 */

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;
    private Texture gameTitle;
    private Texture zoomGesture1;
    private Texture longtapGesture;
    private Texture swipeDownGesture;
    private Texture swipeUpGesture;
    private Texture tapGesture;
    private Texture laser;
    private Texture bigLaser;
    private Texture movement;
    private Texture bomb;


    public MenuState(GameStateManager gsm){
        super(gsm);
        background = new Texture("background.png");
        playBtn = new Texture ("tap.png");
        gameTitle = new Texture("gameTitle2.png");
        zoomGesture1 = new Texture("zoom.png");
        longtapGesture = new Texture("longTap.png");
        swipeDownGesture = new Texture("swipeDown.png");
        swipeUpGesture = new Texture("swipeUp.png");
        tapGesture = new Texture("tapGesture.png");
        movement = new Texture("movementBanner.png");
        laser = new Texture("LaserBanner.png");
        bigLaser = new Texture("bigLaserBanner.png");
        bomb = new Texture("bomb.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
            handleInput();
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0); //bottom left hand corner
        sb.draw(playBtn, SpaceAbyss.WIDTH/2, SpaceAbyss.HEIGHT/2 );
        sb.draw(gameTitle, SpaceAbyss.WIDTH/2 - 600 , SpaceAbyss.HEIGHT/2 + 100,1500,300);

        sb.draw(tapGesture, SpaceAbyss.WIDTH/2  - 500, SpaceAbyss.HEIGHT/2 - 400);
        sb.draw(longtapGesture, SpaceAbyss.WIDTH/2 - 200, SpaceAbyss.HEIGHT/2 - 400);
        sb.draw(zoomGesture1, SpaceAbyss.WIDTH/2 + 100, SpaceAbyss.HEIGHT/2 - 400);
        sb.draw(swipeDownGesture, SpaceAbyss.WIDTH/2 + 400, SpaceAbyss.HEIGHT/2 - 400);
        sb.draw(swipeUpGesture, SpaceAbyss.WIDTH/2  + 600, SpaceAbyss.HEIGHT/2 - 400);

        sb.draw(laser, SpaceAbyss.WIDTH/2  - 500, SpaceAbyss.HEIGHT/2 - 500);
        sb.draw(bigLaser, SpaceAbyss.WIDTH/2  - 230, SpaceAbyss.HEIGHT/2 - 500);
        sb.draw(bomb, SpaceAbyss.WIDTH/2 + 160 , SpaceAbyss.HEIGHT/2 - 500);
        sb.draw(movement, SpaceAbyss.WIDTH/2  + 400, SpaceAbyss.HEIGHT/2 - 500);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State disposed");
    }

}
