package com.hampton.game.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.hampton.game.GameScreen;
import com.hampton.game.utils.ActorUtils;

/**
 * Created by MichaelS on 10/15/2017.
 */

public class EndScreen extends GameScreen {
    private Actor background;
    Actor buttonFromText;
    Actor youLose;
    private Label points;
    private String nextScreenName;
    private BubbleDrop score;

    public EndScreen(String nextScreenName, BubbleDrop score) {
        this.nextScreenName = nextScreenName;
        this.score = score;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void createActors() {
       points= ActorUtils.createButtonFromText("Score" +score.score, new Color(1,1,1,1)
        );
        points.setPosition(500,500);
        stage.addActor(points);

        background=ActorUtils.createActorFromImage("vomit.jpg");
        background.setSize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
        youLose = ActorUtils.createButtonFromText("YOU LOSE", new Color(1, 1, 1, 1));
        youLose.setPosition(300, 300);
        stage.addActor(youLose);
        buttonFromText = ActorUtils.createButtonFromText("Click to go to " + nextScreenName,
                new Color(1, 1, 1, 1));
        stage.addActor(buttonFromText);
    }

    @Override
    public void setInputForActors() {

    }

    @Override
    public void setActionsForActors() {
        buttonFromText.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gotoScreen(nextScreenName);
            }
        });
    }

    @Override
    protected void calledEveryFrame() {

    }

    @Override
    public void update(int width, int height) {

    }
}
