package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SpaceAbyss;

/**
 * Created by 24ari on 2/15/2017.
 */

public class GameOverState extends State{


    private Texture gameOverBanner;
    private Texture background;

    BitmapFont scoreLayout;
    BitmapFont scoreFont;

    private int score1;

    public GameOverState(GameStateManager gsm,int score){
        super(gsm);
        background = new Texture("background.png");
        gameOverBanner =  new Texture("gameOverBanner.png");
        scoreLayout = new BitmapFont(Gdx.files.internal("score.fnt"));
        score1 = score;
        scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new MenuState(gsm));
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
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont," " + score1);
        sb.draw(gameOverBanner, SpaceAbyss.WIDTH/2-180, SpaceAbyss.HEIGHT/2);
        scoreFont.draw(sb,scoreLayout,Gdx.graphics.getWidth()/2 -40 ,Gdx.graphics.getHeight() - 600);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }

}
