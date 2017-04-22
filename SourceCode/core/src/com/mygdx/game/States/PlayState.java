package com.mygdx.game.States;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.SpaceAbyss;
import com.mygdx.game.sprites.Asteroid;
import com.mygdx.game.sprites.BigLaser;
import com.mygdx.game.sprites.BigPowerups;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Laser;
import com.mygdx.game.sprites.Powerups;
import com.mygdx.game.sprites.Shield;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by 24ari on 2/15/2017.
 */


public class PlayState extends State {

    private Bird bird;
    private Texture bg;
    private Texture smallShield;
    private Texture smallExplosion;
    private Texture scoreBanner;
    private Texture bigLaserBanner;
    private Texture laserBanner;

    private float asteroidSpeed = 200;
    private float dtAsteroid = 0;

    BitmapFont scoreFont;
    BitmapFont scoreFont1;
    BitmapFont scoreFont2;
    int score;

    // LASER STUFF
    private float dtAdd = 0;
    private boolean shortLaserTouch = false;
    private boolean bigLaserTouch = false;
    private float laserTrackX = -1;
    private float laserTrackY = -1;

    private float dtAddSingle = 0;
    private boolean shortLaserTouchSingle = false;
    private boolean bigLaserTouchSingle = false;
    private float laserTrackXSingle = -1;
    private float laserTrackYSingle = -1;

    private float dtAdd0 = 0;
    private boolean bigLaserTouch0 = false;
    private boolean shortLaserTouch0 = false;
    private float laserTrackX0 = -1;
    private float laserTrackY0 = -1;


    // MOVEMENT STUFF
    private float saveInitialY = -1;
    private float saveInitialY1 = -1;
    private float saveInitialY1S = -1;


    // ZOOM STUFF
    private float zoomX1 = -1;
    private float zoomY1 = -1;
    private float zoomX2 = -1;
    private float zoomY2 = -1;
    private boolean isZooming = false;
    private boolean zoomSave0 = false;
    private boolean zoomSave1 = false;
    int counterSave = -1;

    private float zoomX1S = -1;
    private float zoomY1S = -1;
    private float zoomX2S = -1;
    private float zoomY2S = -1;
    private boolean isZoomingS = false;


    // SWIPING STUFF
    private boolean isSwiping0 = false;
    private boolean isSwiping1 = false;
    private float swipeX0 = -1;
    private float swipeY0 = -1;
    private float swipeX1 = -1;
    private float swipeY1 = -1;

    private float swipeX0M = -1;
    private float swipeY0M = -1;
    private float swipeX1M = -1;
    private float swipeY1M = -1;
    private boolean swipeSave0M = false;
    private boolean swipeSave1M = false;
    private float swipeSaveCounter = -1;
    private boolean zoomSwipeConflict = false;
    private float zoomSwipeConflictCounter = 0;

    ArrayList<Laser> lasers;
    ArrayList<BigLaser> biglasers;
    ArrayList<Asteroid> asteroids; //create arraylist of Asteroid object
    ArrayList<Shield> shields;
    ArrayList<Powerups> powerups;
    ArrayList<BigPowerups> bigPowerups;


    int counterLasers = 15;
    int counterBigLasers = 2;

    private long lastDropTime;
    private long lastDropTime1;
    private long lastDropTime2;
    private long ShieldActivationTime;
    private long ShieldDestructionTime;
    private long AsteroidDestructionTime;
    private long rechargePeriod;
    private boolean isExplosionUnlocked;

    Music music1;
    Music music2;
    Music music3;
    Music music4;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);

        music1 = Gdx.audio.newMusic(Gdx.files.internal("data/shortLaser.wav"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("data/longLaser.wav"));
        music3 = Gdx.audio.newMusic(Gdx.files.internal("data/zap.wav"));
        music4 = Gdx.audio.newMusic(Gdx.files.internal("data/zoomOut.mp3"));

        AsteroidDestructionTime = TimeUtils.millis();
        isExplosionUnlocked = false;

        scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
        score = 0;
        scoreFont1 = new BitmapFont(Gdx.files.internal("score.fnt"));
        scoreFont2 = new BitmapFont(Gdx.files.internal("score.fnt"));

        cam.setToOrtho(false, SpaceAbyss.WIDTH / 2, SpaceAbyss.HEIGHT / 2);

        bg = new Texture("world1.jpg");
        smallShield = new Texture("smallShield.png");
        smallExplosion = new Texture("explosion1.png");
        scoreBanner = new Texture("scoreBanner.png");
        laserBanner = new Texture("LaserBanner.png");
        bigLaserBanner = new Texture("bigLaserBanner.png");

        lasers = new ArrayList<Laser>();
        biglasers = new ArrayList<BigLaser>();
        asteroids = new ArrayList<Asteroid>();
        shields = new ArrayList<Shield>();
        powerups = new ArrayList<Powerups>();
        bigPowerups = new ArrayList<BigPowerups>();

    }

    @Override
    protected void handleInput() {

        // MOVEMENT SINGLE
        if (Gdx.input.isTouched(0)) {
            if (Gdx.input.isTouched(1) == false) {
                if (Gdx.input.getX(0) <= 300) {
                    int shipMoveX = Gdx.input.getX(0);
                    int shipMoveY = Gdx.input.getY(0);
                    if (saveInitialY == -1) {
                        saveInitialY = Gdx.input.getY(0);
                    }

                    Vector3 shipInputVector = new Vector3(shipMoveX, shipMoveY, 0);
                    cam.unproject(shipInputVector);

                    if (bird.getBounds().contains(shipInputVector.x, shipInputVector.y)) {

                        bird.setPosition(bird.getPosition().x, bird.getPosition().y - (Gdx.input.getY(0) - saveInitialY), 0);
                        saveInitialY = Gdx.input.getY(0);
                    }

                }
            } else if (Gdx.input.isTouched(0) == false) {
                saveInitialY = -1;
            } else if (Gdx.input.isTouched(1) == false) {
                saveInitialY1S = -1;
            }
        } else if (Gdx.input.isTouched(1)) {
            if (Gdx.input.isTouched(0) == false) {
                if (Gdx.input.getX(1) <= 300) {
                    int shipMoveX = Gdx.input.getX(1);
                    int shipMoveY = Gdx.input.getY(1);
                    if (saveInitialY1S == -1) {
                        saveInitialY1S = Gdx.input.getY(1);
                    }


                    Vector3 shipInputVector = new Vector3(shipMoveX, shipMoveY, 0);
                    cam.unproject(shipInputVector);

                    if (bird.getBounds().contains(shipInputVector.x, shipInputVector.y)) {

                        bird.setPosition(bird.getPosition().x, bird.getPosition().y - (Gdx.input.getY(1) - saveInitialY1S), 0);
                        saveInitialY1S = Gdx.input.getY(1);
                        saveInitialY1 = -1;

                    }

                }
            } else if (Gdx.input.isTouched(0) == false) {
                saveInitialY = -1;
                saveInitialY1 = -1;
            } else if (Gdx.input.isTouched(1) == false) {
                saveInitialY1S = -1;
                saveInitialY1 = -1;
            }
        }

        // MOVEMENT MULTI
        if (Gdx.input.isTouched(1)) {
            if (Gdx.input.isTouched(0)) {
                if (Gdx.input.getX(0) <= 300) {
                    int shipMoveX = Gdx.input.getX(0);
                    int shipMoveY = Gdx.input.getY(0);
                    if (saveInitialY == -1) {
                        saveInitialY = Gdx.input.getY(0);
                    }

                    Vector3 shipInputVector = new Vector3(shipMoveX, shipMoveY, 0);
                    cam.unproject(shipInputVector);

                    if (bird.getBounds().contains(shipInputVector.x, shipInputVector.y)) {

                        bird.setPosition(bird.getPosition().x, bird.getPosition().y - (Gdx.input.getY(0) - saveInitialY), 0);
                        saveInitialY = Gdx.input.getY(0);

                    }

                    //syncMovement = true;
                } else if (Gdx.input.getX(1) <= 300) {
                    int shipMoveX = Gdx.input.getX(1);
                    int shipMoveY = Gdx.input.getY(1);
                    if (saveInitialY1 == -1) {
                        saveInitialY1 = Gdx.input.getY(1);
                    }


                    Vector3 shipInputVector = new Vector3(shipMoveX, shipMoveY, 0);
                    cam.unproject(shipInputVector);

                    if (bird.getBounds().contains(shipInputVector.x, shipInputVector.y)) {

                        bird.setPosition(bird.getPosition().x, bird.getPosition().y - (Gdx.input.getY(1) - saveInitialY1), 0);
                        saveInitialY1 = Gdx.input.getY(1);
                        saveInitialY1S = -1;

                    }

                    //syncMovement = true;
                }
            } else if (Gdx.input.isTouched(0) == false) {
                saveInitialY = -1;
            } else if (Gdx.input.isTouched(1) == false) {
                saveInitialY1 = -1;
                saveInitialY1S = -1;
            }
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // LASERS SINGLE
        if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1) == false && Gdx.input.getX(0) > 300) {
            shortLaserTouchSingle = true;
            if (laserTrackXSingle == -1) {
                laserTrackXSingle = Gdx.input.getX(0);
            }
            if (laserTrackYSingle == -1) {
                laserTrackYSingle = Gdx.input.getY(0);
            }

            if (dtAddSingle >= 1 && bigLaserTouchSingle == false) {
                if (Math.abs(Gdx.input.getX(0) - laserTrackXSingle) < 40 && Math.abs(Gdx.input.getY(0) - laserTrackYSingle) < 40) {
                    shootLong();
                    bigLaserTouchSingle = true;
                    shortLaserTouchSingle = false;
                    bigLaserTouch0 = true;
                    //System.out.println("shootLong = true");
                    //System.out.println("isTouched(0) = " + Gdx.input.isTouched(0));
                }
            }
        } else if (Gdx.input.isTouched(0) == false && shortLaserTouchSingle == true && dtAddSingle < 1 && laserTrackXSingle > 300) {
            if (Math.abs(Gdx.input.getX(0) - laserTrackXSingle) < 40 && Math.abs(Gdx.input.getY(0) - laserTrackYSingle) < 40) {
                shootShort();
                shortLaserTouchSingle = false;
                bigLaserTouchSingle = false;
                dtAddSingle = 0;
                laserTrackXSingle = -1;
                laserTrackYSingle = -1;
                //System.out.println("shootShort = true");
            }
        } else if (Gdx.input.isTouched(0) == false) {
            dtAddSingle = 0;
            bigLaserTouchSingle = false;
            shortLaserTouchSingle = false;
            laserTrackXSingle = -1;
            laserTrackYSingle = -1;
        }


        // LASERS MULTI 1
        if (Gdx.input.isTouched(1) && Gdx.input.isTouched(0) && Gdx.input.getX(1) > 300) {
            shortLaserTouch = true;
            if (laserTrackX == -1) {
                laserTrackX = Gdx.input.getX(1);
            }
            if (laserTrackY == -1) {
                laserTrackY = Gdx.input.getY(1);
            }

            if (dtAdd >= 1 && bigLaserTouch == false) {
                if (Math.abs(Gdx.input.getX(1) - laserTrackX) < 40 && Math.abs(Gdx.input.getY(1) - laserTrackY) < 40) {
                    shootLong();
                    bigLaserTouch = true;
                    shortLaserTouch = false;
                    //System.out.println("shootLong = true");
                    //System.out.println("isTouched(0) = " + Gdx.input.isTouched(0));
                }
            }
        } else if (Gdx.input.isTouched(1) == false && shortLaserTouch == true && dtAdd < 1 && laserTrackX > 300) {
            if (Math.abs(Gdx.input.getX(1) - laserTrackX) < 40 && Math.abs(Gdx.input.getY(1) - laserTrackY) < 40) {
                shootShort();
                shortLaserTouch = false;
                bigLaserTouch = false;
                dtAdd = 0;
                laserTrackX = -1;
                laserTrackY = -1;
                //System.out.println("shootShort = true");
            }
        } else if (Gdx.input.isTouched(1) == false) {
            dtAdd = 0;
            bigLaserTouch = false;
            shortLaserTouch = false;
            laserTrackX = -1;
            laserTrackY = -1;
        }

        // LASERS MULTI 0
        if (Gdx.input.isTouched(1) && Gdx.input.isTouched(0) && Gdx.input.getX(0) > 300) {
            shortLaserTouch0 = true;
            if (laserTrackX0 == -1) {
                laserTrackX0 = Gdx.input.getX(0);
            }
            if (laserTrackY0 == -1) {
                laserTrackY0 = Gdx.input.getY(0);
            }

            if (dtAdd0 >= 1 && bigLaserTouch0 == false && Gdx.input.getX(0) > 300) {
                if (Math.abs(Gdx.input.getX(0) - laserTrackX0) < 40 && Math.abs(Gdx.input.getY(0) - laserTrackY0) < 40) {
                    shootLong();
                    bigLaserTouch0 = true;
                    shortLaserTouch0 = false;
                    bigLaserTouchSingle = true;
                    //System.out.println("shootLong = true");
                    //System.out.println("isTouched(0) = " + Gdx.input.isTouched(0));
                }
            }
        } else if (Gdx.input.isTouched(0) == false && shortLaserTouch0 == true && dtAdd0 < 1 && laserTrackX0 > 300) {
            if (Math.abs(Gdx.input.getX(0) - laserTrackX0) < 40 && Math.abs(Gdx.input.getY(0) - laserTrackY0) < 40) {
                shootShort();
                shortLaserTouch0 = false;
                bigLaserTouch0 = false;
                dtAdd0 = 0;
                laserTrackX0 = -1;
                laserTrackY0 = -1;
                //System.out.println("shootShort = true");
            }
        } else if (Gdx.input.isTouched(0) == false) {
            dtAdd0 = 0;
            bigLaserTouch0 = false;
            shortLaserTouch0 = false;
            laserTrackX0 = -1;
            laserTrackY0 = -1;
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // VARIABLE RESETS FOR LASER AND MOVEMENT
        if (Gdx.input.isTouched(0) == false) {
            saveInitialY = -1;
        }
        if (Gdx.input.isTouched(1) == false) {
            saveInitialY1 = -1;
            saveInitialY1S = -1;
        }
        //syncMovement = false;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // THREE FINGER ZOOM
        if (Gdx.input.isTouched(2) == false) {
            if (Gdx.input.isTouched(0)) {
                zoomSave0 = true;
                zoomSave1 = false;
            } else if (Gdx.input.isTouched(1)) {
                zoomSave1 = true;
                zoomSave0 = false;
            }
        }

        if (Gdx.input.isTouched(2)) {

            if (counterSave == -1) {
                if (zoomSave0) {
                    counterSave = 0;
                } else if (zoomSave1) {
                    counterSave = 1;
                }
            }


            if (isZooming == false) {
                if (counterSave == 0) {
                    if (zoomX1 == -1) {
                        zoomX1 = Gdx.input.getX(1);
                        zoomY1 = Gdx.input.getY(1);
                        zoomX2 = Gdx.input.getX(2);
                        zoomY2 = Gdx.input.getY(2);
                    }

                    double distanceFinal = Math.sqrt(Math.pow(Gdx.input.getX(2) - Gdx.input.getX(1), 2) + Math.pow(Gdx.input.getY(2) - Gdx.input.getY(1), 2));
                    double distanceInitial = Math.sqrt(Math.pow(zoomX2 - zoomX1, 2) + Math.pow(zoomY2 - zoomY1, 2));

                    double fingerOneMoves = Math.sqrt(Math.pow(Gdx.input.getX(1) - zoomX1, 2) + Math.pow(Gdx.input.getY(1) - zoomY1, 2));
                    double fingerTwoMoves = Math.sqrt(Math.pow(Gdx.input.getX(2) - zoomX2, 2) + Math.pow(Gdx.input.getY(2) - zoomY2, 2));

                    if (distanceFinal - distanceInitial >= 125 && fingerOneMoves >= 55 && fingerTwoMoves >= 55) {

                        if (isExplosionUnlocked || TimeUtils.millis() - AsteroidDestructionTime > 5000) {
                            checkAsteroids();
                        }

                        zoomSwipeConflict = true;
                        zoomSwipeConflictCounter = 1;
                        isZooming = true;
                        counterSave = -1;
                    }

                    System.out.println("distanceFinal = " + distanceFinal);
                    System.out.println("distanceInitial = " + distanceInitial);


                } else if (counterSave == 1) {
                    if (zoomX1 == -1) {
                        zoomX1 = Gdx.input.getX(0);
                        zoomY1 = Gdx.input.getY(0);
                        zoomX2 = Gdx.input.getX(2);
                        zoomY2 = Gdx.input.getY(2);
                    }

                    double distanceFinal = Math.sqrt(Math.pow(Gdx.input.getX(2) - Gdx.input.getX(0), 2) + Math.pow(Gdx.input.getY(2) - Gdx.input.getY(0), 2));
                    double distanceInitial = Math.sqrt(Math.pow(zoomX2 - zoomX1, 2) + Math.pow(zoomY2 - zoomY1, 2));

                    double fingerOneMoves = Math.sqrt(Math.pow(Gdx.input.getX(0) - zoomX1, 2) + Math.pow(Gdx.input.getY(0) - zoomY1, 2));
                    double fingerTwoMoves = Math.sqrt(Math.pow(Gdx.input.getX(2) - zoomX2, 2) + Math.pow(Gdx.input.getY(2) - zoomY2, 2));

                    if (distanceFinal - distanceInitial >= 125 && fingerOneMoves >= 55 && fingerTwoMoves >= 55) {

                        if (isExplosionUnlocked || TimeUtils.millis() - AsteroidDestructionTime > 5000) {
                            checkAsteroids();
                        }

                        zoomSwipeConflict = true;
                        zoomSwipeConflictCounter = 2;
                        isZooming = true;
                        counterSave = -1;
                    }

                    System.out.println("distanceFinalHard = " + distanceFinal);
                    System.out.println("distanceInitialHard = " + distanceInitial);

                }
            }
        } else if (Gdx.input.isTouched(2) == false) {
            isZooming = false;
            zoomX1 = -1;
            zoomY1 = -1;
            zoomX2 = -1;
            zoomY2 = -1;
        }


        // SINGLE ZOOM
        if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
            if (isZoomingS == false) {
                if (zoomX1S == -1) {
                    zoomX1S = Gdx.input.getX(0);
                    zoomY1S = Gdx.input.getY(0);
                    zoomX2S = Gdx.input.getX(1);
                    zoomY2S = Gdx.input.getY(1);
                }

                double distanceFinal = Math.sqrt(Math.pow(Gdx.input.getX(1) - Gdx.input.getX(0), 2) + Math.pow(Gdx.input.getY(1) - Gdx.input.getY(0), 2));
                double distanceInitial = Math.sqrt(Math.pow(zoomX2S - zoomX1S, 2) + Math.pow(zoomY2S - zoomY1S, 2));

                double fingerOneMoves = Math.sqrt(Math.pow(Gdx.input.getX(0) - zoomX1S, 2) + Math.pow(Gdx.input.getY(0) - zoomY1S, 2));
                double fingerTwoMoves = Math.sqrt(Math.pow(Gdx.input.getX(1) - zoomX2S, 2) + Math.pow(Gdx.input.getY(1) - zoomY2S, 2));

                if (distanceFinal - distanceInitial >= 125 && fingerOneMoves >= 55 && fingerTwoMoves >= 55) {
                    if (isExplosionUnlocked || TimeUtils.millis() - AsteroidDestructionTime > 5000) {
                        checkAsteroids();
                        rechargePeriod = TimeUtils.millis();
                    }
                    zoomSwipeConflict = true;
                    zoomSwipeConflictCounter = 3;
                    isZoomingS = true;
                }
            }
        } else if (Gdx.input.isTouched(0) == false && Gdx.input.isTouched(1) == false) {
            isZoomingS = false;
            zoomX1S = -1;
            zoomY1S = -1;
            zoomX2S = -1;
            zoomY2S = -1;
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // SWIPING
        if (zoomSwipeConflict == false && zoomSwipeConflictCounter == 0) {

            // SWIPING SINGLE
            if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1) == false) {
                if (isSwiping0 == false) {
                    if (swipeX0 == -1 && Gdx.input.getX(0) > 300) {
                        swipeX0 = Gdx.input.getX(0);
                        swipeY0 = Gdx.input.getY(0);
                    }

                    float distanceX = Math.abs(Gdx.input.getX(0) - swipeX0);
                    float distanceY = Math.abs(Gdx.input.getY(0) - swipeY0);


                    if (distanceX < 100 && distanceY > 300 && swipeX0 != -1) {

                        System.out.println("Swiping");
                        isSwiping0 = true;
                        if (TimeUtils.millis() - ShieldDestructionTime > 4000) {
                            if (swipeY0 > Gdx.input.getY(0))
                                activateShield(swipeX0, 1100 - swipeY0);
                            else
                                activateShield(swipeX0, 1100 - swipeY0 - 325);
                        }
                    }
                }
            } else if (Gdx.input.isTouched(1) && Gdx.input.isTouched(0) == false) {
                if (isSwiping1 == false) {
                    if (swipeX1 == -1 && Gdx.input.getX(1) > 300) {
                        swipeX1 = Gdx.input.getX(1);
                        swipeY1 = Gdx.input.getY(1);
                    }

                    float distanceX = Math.abs(Gdx.input.getX(1) - swipeX1);
                    float distanceY = Math.abs(Gdx.input.getY(1) - swipeY1);

                    if (distanceX < 100 && distanceY > 300 && swipeX1 != -1) {

                        System.out.println("Swiping");
                        isSwiping1 = true;
                        if (TimeUtils.millis() - ShieldDestructionTime > 4000) {
                            if (swipeY1 > Gdx.input.getY(1))
                                activateShield(swipeX1, 1100 - swipeY1);
                            else
                                activateShield(swipeX1, 1100 - swipeY1 - 325);
                        }
                    }
                }
            }

            if (Gdx.input.isTouched(0) == false) {
                isSwiping0 = false;
                swipeX0 = -1;
                swipeY0 = -1;
                swipeSave1M = false;
                swipeSave0M = true;
            }
            if (Gdx.input.isTouched(1) == false) {
                isSwiping1 = false;
                swipeX1 = -1;
                swipeY1 = -1;
                swipeSave1M = true;
                swipeSave0M = false;
            }


            // SWIPING MULTI
            if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1) && Gdx.input.isTouched(2) == false) {

                if (swipeSave1M == true) {
                    swipeSaveCounter = 1;
                } else if (swipeSave0M == true) {
                    swipeSaveCounter = 0;
                }

                if (swipeSaveCounter == 0) {
                    if (swipeX0M == -1 && Gdx.input.getX(0) > 300) {
                        swipeX0M = Gdx.input.getX(0);
                        swipeY0M = Gdx.input.getY(0);
                    }

                    float distanceX = Math.abs(Gdx.input.getX(0) - swipeX0M);
                    float distanceY = Math.abs(Gdx.input.getY(0) - swipeY0M);


                    if (distanceX < 100 && distanceY > 300 && swipeX0M != -1) {

                        System.out.println("Swiping Multi");

                        swipeSave0M = false;
                        swipeSaveCounter = -1;

                        if (TimeUtils.millis() - ShieldDestructionTime > 4000) {
                            if (swipeY0M > Gdx.input.getY(0))
                                activateShield(swipeX0M, 1100 - swipeY0M);
                            else
                                activateShield(swipeX0M, 1100 - swipeY0M - 325);
                        }
                    }

                } else if (swipeSaveCounter == 1) {
                    if (swipeX1M == -1 && Gdx.input.getX(1) > 300) {
                        swipeX1M = Gdx.input.getX(1);
                        swipeY1M = Gdx.input.getY(1);
                    }

                    float distanceX = Math.abs(Gdx.input.getX(1) - swipeX1M);
                    float distanceY = Math.abs(Gdx.input.getY(1) - swipeY1M);

                    if (distanceX < 100 && distanceY > 300 && swipeX1M != -1) {

                        System.out.println("Swiping Multi");
                        swipeSave1M = false;
                        swipeSaveCounter = -1;

                        if (TimeUtils.millis() - ShieldDestructionTime > 4000) {
                            if (swipeY1M > Gdx.input.getY(1))
                                activateShield(swipeX1M, 1100 - swipeY1M);
                            else
                                activateShield(swipeX1M, 1100 - swipeY1M - 325);
                        }
                    }

                }
            }
        }

        if (swipeSave0M == false && Gdx.input.isTouched(0) == false) {
            swipeX0M = -1;
            swipeY0M = -1;
        }
        if (swipeSave1M == false && Gdx.input.isTouched(1) == false) {
            swipeX1M = -1;
            swipeY1M = -1;
        }

        if (zoomSwipeConflictCounter == 1 && Gdx.input.isTouched(1) == false && Gdx.input.isTouched(2) == false) {
            zoomSwipeConflictCounter = 0;
            zoomSwipeConflict = false;
        } else if (zoomSwipeConflictCounter == 2 && Gdx.input.isTouched(0) == false && Gdx.input.isTouched(2) == false) {
            zoomSwipeConflictCounter = 0;
            zoomSwipeConflict = false;
        } else if (zoomSwipeConflictCounter == 3 && Gdx.input.isTouched(0) == false && Gdx.input.isTouched(1) == false) {
            zoomSwipeConflictCounter = 0;
            zoomSwipeConflict = false;
        }

    }

    public void shootShort() {
        if (counterLasers > 0) {
            music1.stop();
            music1.setVolume(0.5f);
            music1.play();

            lasers.add(new Laser(bird.getPosition().y + 95));
            counterLasers--;
        }
    }

    public void shootLong() {
        if (counterBigLasers > 0) {
            music2.stop();
            music2.setVolume(0.5f);
            music2.play();

            biglasers.add(new BigLaser(bird.getPosition().y + 55));
            counterBigLasers--;
        }

    }

    public void activateShield(float x, float y) {
        if (shields.isEmpty()) {
            music3.stop();
            music3.setVolume(0.25f);
            music3.play();

            shields.add(new Shield(x, y));
            ShieldActivationTime = TimeUtils.millis();
        }
    }

    public void spawnAste() {
        asteroids.add(new Asteroid(MathUtils.random(0, 800 - 64)));
        lastDropTime = TimeUtils.nanoTime();
    }

    public void spawnPowerup() {
        powerups.add(new Powerups(MathUtils.random(0, 800 - 64)));
        lastDropTime1 = TimeUtils.millis();
    }

    public void spawnBigPowerup() {
        bigPowerups.add(new BigPowerups(MathUtils.random(0, 800 - 64)));
        lastDropTime2 = TimeUtils.millis();
    }

    @Override
    public void update(float dt) {
        dtAdd = dtAdd + dt;
        dtAddSingle = dtAddSingle + dt;
        dtAdd0 = dtAdd0 + dt;
        dtAsteroid += dt;

        if (dtAsteroid <= 1) {
            dtAsteroid = 0;
            asteroidSpeed += .05;
        }

        handleInput();

        //update bullets
        ArrayList<Laser> laserToRemove = new ArrayList<Laser>();
        ArrayList<BigLaser> bigLaserToRemove = new ArrayList<BigLaser>();
        ArrayList<Asteroid> asteroidToRemove = new ArrayList<Asteroid>();
        ArrayList<Shield> shieldToRemove = new ArrayList<Shield>();
        ArrayList<Powerups> powerupToRemove = new ArrayList<Powerups>();
        ArrayList<BigPowerups> bigPowerupToRemove = new ArrayList<BigPowerups>();

        for (Shield shield : shields) {
            shield.update(dt);
            if (shield.remove)
                shieldToRemove.add(shield);
        }


        for (Shield shield : shields) {
            if (TimeUtils.millis() - ShieldActivationTime > 3000) {
                shieldToRemove.add(shield);
                ShieldDestructionTime = TimeUtils.millis();
            }
        }

        shields.removeAll(shieldToRemove);

        for (Laser laser : lasers) {
            laser.update(dt);
            if (laser.remove)
                laserToRemove.add(laser);
        }

        lasers.removeAll(laserToRemove);

        for (BigLaser bigLaser : biglasers) {
            bigLaser.update(dt);
            if (bigLaser.remove)
                bigLaserToRemove.add(bigLaser);
        }

        lasers.removeAll(bigLaserToRemove);

        for (Asteroid asteroid : asteroids) {
            asteroid.update(dt, asteroidSpeed);
            if (asteroid.remove)
                asteroidToRemove.add(asteroid);

            if (asteroid.getBounds().x <= 80) {
                gsm.set(new GameOverState(gsm, score));
            }
        }

        asteroids.removeAll(asteroidToRemove);


        for (Powerups powerup : powerups) {
            powerup.update(dt);
            if (powerup.remove)
                powerupToRemove.add(powerup);
        }

        powerups.removeAll(powerupToRemove);


        for (BigPowerups bigPowerup : bigPowerups) {
            bigPowerup.update(dt);
            if (bigPowerup.remove)
                bigPowerupToRemove.add(bigPowerup);
        }


        bigPowerups.removeAll(bigPowerupToRemove);

        for (Asteroid asteroid : asteroids) {
            for (Laser laser : lasers) {
                if (asteroid.getBounds().overlaps(laser.getBounds())) {
                    asteroidToRemove.add(asteroid);
                    laserToRemove.add(laser);
                    score += 100;
                }
            }

        }

        for (Asteroid asteroid : asteroids) {
            for (BigLaser bigLaser : biglasers) {
                if (asteroid.getBounds().overlaps(bigLaser.getBounds())) {
                    asteroidToRemove.add(asteroid);
                    score += 100;
                }
            }

        }


        for (Asteroid asteroid : asteroids) {
            for (Shield shield : shields) {
                if (asteroid.getBounds().overlaps(shield.getBounds())) {
                    asteroidToRemove.add(asteroid);
                }
            }
        }

        for (Powerups powerup : powerups) {
            for (Laser laser : lasers) {
                if (powerup.getBounds().overlaps(laser.getBounds())) {
                    powerupToRemove.add(powerup);
                    laserToRemove.add(laser);
                    counterLasers = 15;
                }
            }
        }


        for (Powerups powerup : powerups) {
            for (BigLaser bigLaser : biglasers) {
                if (powerup.getBounds().overlaps(bigLaser.getBounds())) {
                    powerupToRemove.add(powerup);
                    counterLasers = 15;
                }
            }
        }


        for (BigPowerups bigPowerup : bigPowerups) {
            for (Laser laser : lasers) {
                if (bigPowerup.getBounds().overlaps(laser.getBounds())) {
                    bigPowerupToRemove.add(bigPowerup);
                    laserToRemove.add(laser);
                    counterBigLasers = 2;
                }
            }
        }


        for (BigPowerups bigPowerup : bigPowerups) {
            for (BigLaser bigLaser : biglasers) {
                if (bigPowerup.getBounds().overlaps(bigLaser.getBounds())) {
                    bigPowerupToRemove.add(bigPowerup);
                    counterBigLasers = 2;
                }
            }
        }

        asteroids.removeAll(asteroidToRemove);
        lasers.removeAll(laserToRemove);
        biglasers.removeAll(bigLaserToRemove);
        powerups.removeAll(powerupToRemove);
        bigPowerups.removeAll(bigPowerupToRemove);

    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        if (TimeUtils.millis() - ShieldDestructionTime > 6000) {
            sb.draw(smallShield, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 80);
        }

        if (TimeUtils.millis() - rechargePeriod > 15000) {
            isExplosionUnlocked = true;
        }

        if (isExplosionUnlocked) {
            sb.draw(smallExplosion, Gdx.graphics.getWidth() / 2 + 100, Gdx.graphics.getHeight() - 90);
        }


        for (Asteroid asteroid : asteroids) {
            asteroid.render(sb);
        }

        for (Laser laser : lasers) {
            laser.render(sb);
        }

        for (BigLaser bigLaser : biglasers) {
            bigLaser.render(sb);
        }

        for (Powerups powerUp : powerups) {
            powerUp.render(sb);
        }

        for (BigPowerups bigpowerups : bigPowerups) {
            bigpowerups.render(sb);
        }


        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnAste();
        }

        if (TimeUtils.millis() - lastDropTime1 > 10000) { //every 10 seconds
            spawnPowerup();
        }

        if (TimeUtils.millis() - lastDropTime2 > 20000) //every 15 seconds
        {
            spawnBigPowerup();
        }

        for (Shield shield : shields) {
            shield.render(sb);
        }

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, " " + score);
        GlyphLayout laserCountLayout = new GlyphLayout(scoreFont1, " " + counterLasers);
        GlyphLayout bigLaserCountLayout = new GlyphLayout(scoreFont2, " " + counterBigLasers);

        scoreFont.draw(sb, laserCountLayout, Gdx.graphics.getWidth() / 2 - laserCountLayout.width / 2 - 650, Gdx.graphics.getHeight() - laserCountLayout.height - 10);
        scoreFont.draw(sb, bigLaserCountLayout, Gdx.graphics.getWidth() / 2 - laserCountLayout.width / 2 - 100, Gdx.graphics.getHeight() - laserCountLayout.height - 10);
        scoreFont.draw(sb, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2 + 600, Gdx.graphics.getHeight() - scoreLayout.height - 10);

        sb.draw(laserBanner, Gdx.graphics.getWidth() / 2 - 880, Gdx.graphics.getHeight() - scoreLayout.height - 50);
        sb.draw(bigLaserBanner, Gdx.graphics.getWidth() / 2 - 450, Gdx.graphics.getHeight() - scoreLayout.height - 50);
        sb.draw(scoreBanner, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2 + 400, Gdx.graphics.getHeight() - scoreLayout.height - 50);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();

        /***      asteroidImage.dispose(); */
        System.out.println("Play State Disposed");

    }


    public void checkAsteroids() { //destroys asteroids
        Iterator<Asteroid> iter = asteroids.iterator();
        while (iter.hasNext()) {
            Asteroid asteroid = iter.next();
            iter.remove();
            music4.stop();
            music4.play();
        }
        AsteroidDestructionTime = TimeUtils.millis(); //set Timer when Asteroids are destroyed
        isExplosionUnlocked = false;
    }

}