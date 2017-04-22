package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.audio.Music;

/**
 * Created by 24ari on 2/15/2017.
 */

public class SpaceAbyss extends ApplicationAdapter{
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 1000;

    public static final String TITLE = "Space Abyss";
    private GameStateManager gsm;
    private SpriteBatch batch;

    @Override
    public void create(){
        Music music;
        music = Gdx.audio.newMusic(Gdx.files.internal("data/Background.ogg"));
        music.play();
        music.setLooping(true);

        batch = new SpriteBatch();
        gsm = new GameStateManager();
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }


}
