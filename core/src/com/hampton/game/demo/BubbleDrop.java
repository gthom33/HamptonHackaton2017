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
    private Actor bucket;
    private Label scoreLabel;
    private Label.LabelStyle scoreStyle;
    private int score = 0;
    private int dropSpeed = 3;
    private int pauseTime = 1;
    private int newDropInterval = 60;
    private boolean gameOn = false;

    //private Sound dropSound;
    private Sound mmmSound;
    //private Bad burpSound;
    private Music workoutMusic;

    @Override
    public void initialize() {
        // load the drop sound effect and the rain background "music"
        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        mmmSound = Gdx.audio.newSound(Gdx.files.internal("mmm-girl.mp3"));
        //burpSound = Gdx.audio.newSound(Gdx.files.internal("loud_burp.mp3"));
        workoutMusic = Gdx.audio.newMusic(Gdx.files.internal("body_lang.mp3"));

        // start the playback of the background music immediately
        workoutMusic.setLooping(true);
        workoutMusic.play();
        gameOn = true;
        score = 0;
        dropSpeed = 3;
        newDropInterval = 60;
        numFrames = 0;
        // Clear any raindrops from previous games
        for (Actor raindrop : stage.getActors()) {
            if (raindrop.getName() != null && raindrop.getName().equals("drop")) {
                raindrop.remove();
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
        backgroundColor = new Color(0, 0, .2f, 1);
        bucket = ActorUtils.createActorFromImage("bucket.png");
        bucket.setPosition(20, 20);
        stage.addActor(bucket);
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
            bucket.setX(Gdx.input.getX() - 64 / 2);
        }
        if (gameOn && numFrames % newDropInterval == 0) {
            Actor drop = ActorUtils.createActorFromImage("droplet.png");
            drop.setPosition(
                    randomNumberGenerator.nextInt(stage.getViewport().getScreenWidth() - (int)drop.getWidth()),
                    stage.getViewport().getScreenHeight());
            drop.setName("drop");
            stage.addActor(drop);
        }
        if (gameOn && numFrames % pauseTime == 0) {
            // move the raindrops, remove any that are beneath the bottom edge of
            // the screen or that hit the bucket. In the later case we play back
            // a sound effect as well.
            for (Actor raindrop : stage.getActors()) {
                if (raindrop.getName() != null && raindrop.getName().equals("drop")) {
                    raindrop.setPosition(raindrop.getX(), raindrop.getY() - dropSpeed*3);

                    if (raindrop.getY() + 64 < 0) {
                        gameOn = false;
                        break;
                    }
                    if (ActorUtils.actorsCollided(raindrop, bucket)) {
                        raindrop.remove();
                        mmmSound.play();
                        score++;
                        if (score % 10 == 0) {
                            nextLevel();
                        }
                    }
                }
            }
            scoreLabel.setText("Score: " + score + " Level: " + (dropSpeed-2));
            if (!gameOn) {
                loseGame();
            }
        }
    }

    private void nextLevel() {
        dropSpeed++;
        newDropInterval = 180 / dropSpeed;
    }

    private void loseGame() {
        workoutMusic.stop();
        for (Actor raindrop : stage.getActors()) {
            if (raindrop.getName() != null && raindrop.getName().equals("drop")) {
                raindrop.remove();
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
