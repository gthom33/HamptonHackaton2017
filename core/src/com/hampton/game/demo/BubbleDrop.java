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
    private Actor hippo;
    private Actor background;
    private Actor cheeseburger;
    private Label scoreLabel;
    private Label.LabelStyle scoreStyle;
    private int score = 0;
    private int life= 3;
    private int dropSpeed = 3;
    private int pauseTime = 1;
    private int newDropInterval = 60;
    private boolean gameOn = false;


    private Sound crunchSound;
    private Sound burpSound;
    private Music workoutMusic;

    @Override
    public void initialize() {
        // load the drop sound effect and the rain background "music"
        crunchSound = Gdx.audio.newSound(Gdx.files.internal("carrotNom.wav"));
        crunchSound.setVolume(150, 150f);
        burpSound = Gdx.audio.newSound(Gdx.files.internal("loud_burp.mp3"));
        burpSound.setVolume(150,150f);
        workoutMusic = Gdx.audio.newMusic(Gdx.files.internal("New Music.mp3"));
        workoutMusic.setVolume(0.5f);

        // start the playback of the background music immediately
        workoutMusic.setLooping(true);
        workoutMusic.play();
        gameOn = true;
        score = 0;
        life=3;
        dropSpeed = 3;
        newDropInterval = 60;
        numFrames = 0;
        // Clear any raindrops from previous games


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
        scoreStyle = new Label.LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1));
        scoreStyle.font.getData().setScale(4);
        scoreLabel = new Label("0", scoreStyle);
        scoreLabel.setPosition(0, stage.getViewport().getScreenHeight() - scoreLabel.getHeight());
        stage.addActor(scoreLabel);
    }

    @Override
    public void createActors() {
        background = ActorUtils.createActorFromImage("Restaurant.jpg");
        background.setSize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());

        hippo = ActorUtils.createActorFromImage("hippo.png");
        hippo.setSize(325, 325);
        stage.addActor(background);
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
            hippo.setX(Gdx.input.getX() - 64 / 2);
        }
        if (gameOn && numFrames % newDropInterval == 0) {
            Actor carrot = ActorUtils.createActorFromImage("carrot.png");
            carrot.setSize(100, 100);
            carrot.setPosition(
                    randomNumberGenerator.nextInt(stage.getViewport().getScreenWidth() - (int) carrot.getWidth()),
                    stage.getViewport().getScreenHeight());
            carrot.setName("carrot");
            stage.addActor(carrot);
        }
        if (gameOn && numFrames % newDropInterval == 30) {
            cheeseburger = ActorUtils.createActorFromImage("Cheeseburger.png");
            cheeseburger.setSize(100, 100);
            cheeseburger.setPosition(
                    randomNumberGenerator.nextInt(stage.getViewport().getScreenWidth() - (int) cheeseburger.getWidth()),
                    stage.getViewport().getScreenHeight());
            cheeseburger.setName("cheeseburger");
            stage.addActor(cheeseburger);
        }

        if (gameOn && numFrames % pauseTime == 0) {
            // move the carrots, remove any that are beneath the bottom edge of
            // the screen or that hit the hippo. In the later case we play back
            // a sound effect as well.
            for (Actor food : stage.getActors()) {

                if ((food.getName() != null && (food.getName().equals("carrot") || food.getName().equals("cheeseburger")))) {
                    food.setPosition(food.getX(), food.getY() - dropSpeed * 3);

                    if (ActorUtils.actorsCollided(food, hippo) && (food.getY()>(hippo.getY()+hippo.getHeight()-10)))  {
                        food.remove();
                        if(food.getName().equals("carrot")) {
                             crunchSound.play();
                            score++;
                            if (score % 10 == 0) {
                                nextLevel();

                            }
                        }else {
                          burpSound.play();
                            life--;
                        }

                    }
                }

            }
        }

        scoreLabel.setText("Score: " + score + " Level: " + (dropSpeed - 2));
        if (life==0) {
            gameOn=false;
            loseGame();
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
            if (carrot.getName() != null && carrot.getName().equals("carrot")) {
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


