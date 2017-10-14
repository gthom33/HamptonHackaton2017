package com.hampton.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.hampton.game.GameScreen;
import com.hampton.game.utils.ActorUtils;

import java.util.Random;

/**
 * Created by turnerd on 10/13/17.
 */
public class BubbleDrop extends GameScreen {

    private Random randomNumberGenerator = new Random();
    //private Actor bucket;
    private Actor hippo;
    private Actor background;
    private Label scoreLabel;
    private Label.LabelStyle scoreStyle;
    private int score = 0;
    private int dropSpeed = 3;
    private int pauseTime = 1;
    private int newDropInterval = 60;
    private boolean gameOn = false;


    private Sound crunchSound;
    //private Bad burpSound;
    private Music workoutMusic;

    @Override
    public void initialize() {
        // load the drop sound effect and the rain background "music"
        crunchSound = Gdx.audio.newSound(Gdx.files.internal("carrotNom.wav"));
        crunchSound.setVolume(150,150f);
        //burpSound = Gdx.audio.newSound(Gdx.files.internal("loud_burp.mp3"));
        //burpSound.setVolume(150,150f);
        workoutMusic = Gdx.audio.newMusic(Gdx.files.internal("body_lang.mp3"));
        workoutMusic.setVolume(0.5f);

        // start the playback of the background music immediately
        workoutMusic.setLooping(true);
        workoutMusic.play();
        gameOn = true;
        score = 0;
        dropSpeed = 3;
        newDropInterval = 60;
        numFrames = 0;
        // Clear any raindrops from previous games

        //Change raindrop to cheeseburger?
        for (Actor cheeseburger : stage.getActors()) {
            if (cheeseburger.getName() != null && cheeseburger.getName().equals("drop2")) {
                cheeseburger.remove();
            }
        }

        for (Actor carrot : stage.getActors()) {
            if (carrot.getName() != null && carrot.getName().equals("drop")) {
                carrot.remove();

            }
        }
        scoreStyle = new Label.LabelStyle(new BitmapFont(), new Color(1,1,1,1));
        scoreStyle.font.getData().setScale(4);
        scoreLabel = new Label("0", scoreStyle);
        scoreLabel.setPosition(0, stage.getViewport().getScreenHeight() - scoreLabel.getHeight());
        stage.addActor(scoreLabel);
    }

    @Override
    public void createActors() {
        background = ActorUtils.createActorFromImage("Restaurant.jpg");
        background.setSize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
<<<<<<< HEAD
        //bucket = ActorUtils.createActorFromImage("bucket.png");
        hippo = ActorUtils.createActorFromImage("hippo.png");
        //bucket.setPosition(20,20);
        hippo.setSize(40, 40);
=======
        bucket = ActorUtils.createActorFromImage("hippo.png");
        bucket.setPosition(20, 20);
>>>>>>> 5e30287099f73663d4fce160a13f9dbb036194f1
        stage.addActor(background);
        //stage.addActor(bucket);
        stage.addActor(hippo);
    }

    @Override
    public void setInputForActors() {
    }

    @Override
    public void setActionsForActors() {
    }

    @Override
    protected void calledEveryFrame() {
        // process user input
        if (Gdx.input.isTouched()) {
            //bucket.setX(Gdx.input.getX() - 64 / 2);
            hippo.setX(Gdx.input.getX() - 64 / 2);
        }
        if (gameOn && numFrames % newDropInterval == 0) {
            Actor drop = ActorUtils.createActorFromImage("carrot.png");drop.setSize(100, 100);
            drop.setPosition(
                    randomNumberGenerator.nextInt(stage.getViewport().getScreenWidth() - (int)drop.getWidth()),
                    stage.getViewport().getScreenHeight());
            drop.setName("drop");
          stage.addActor(drop);
        }
            if (gameOn && numFrames % newDropInterval == 30) {
                Actor drop2 = ActorUtils.createActorFromImage("Cheeseburger.png");drop2.setSize(100, 100);
                drop2.setPosition(
                        randomNumberGenerator.nextInt(stage.getViewport().getScreenWidth() - (int)drop2.getWidth()),
                        stage.getViewport().getScreenHeight());
                drop2.setName("drop2");
                stage.addActor(drop2);
            }

        if (gameOn && numFrames % pauseTime == 0) {
            // move the carrots, remove any that are beneath the bottom edge of
            // the screen or that hit the bucket. In the later case we play back
            // a sound effect as well.
            for (Actor carrot : stage.getActors()) {
                if ((carrot.getName() != null && (carrot.getName().equals("drop") || carrot.getName().equals("drop2"))))  {
                    carrot.setPosition(carrot.getX(), carrot.getY() - dropSpeed*3);

                    if (carrot.getY() + 64 < 0) {
                        gameOn = false;
                        break;
                    }


                    if (ActorUtils.actorsCollided(carrot, hippo)) {
                        carrot.remove();
                        crunchSound.play();
                        score++;
                        if (score % 10 == 0) {
                            nextLevel();
                        }//else (ActorUtils.actorsCollided(cheeseburger, hippo)){
                            //cheeseburger.remove();
                            //burpSound.play();
                            //score++;
                    }
                }
            }
            scoreLabel.setText("Score: " + score + " Level: " + (dropSpeed-2));
            if (!gameOn) {
                loseGame();
            }
        }
    }

    @Override
    public void update(int width, int height) {

    }

    private void nextLevel() {
        dropSpeed++;
        newDropInterval = 1000 / dropSpeed;
    }

    private void loseGame() {
        workoutMusic.stop();
        for (Actor carrot : stage.getActors()) {
            if (carrot.getName() != null && carrot.getName().equals("drop")) {
                carrot.remove();
            }
        }
        scoreLabel.remove();
        final Actor backButton = ActorUtils.createButtonFromText(
                "Final score: " + score + " Click to go to back to menu",
                new Color(1, 1, 1, 1));
        backButton.setPosition(0, stage.getViewport().getScreenHeight() - backButton.getHeight());
        backButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButton.remove();
                gotoScreen("Menu");
            }
        });
        stage.addActor(backButton);
    }
}
