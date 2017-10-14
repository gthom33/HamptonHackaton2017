package com.hampton.game.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.hampton.game.GameScreen;
import com.hampton.game.utils.ActorUtils;

/**
 * Created by turnerd on 10/13/17.
 */

public class MainMenu extends GameScreen {
   private Actor buttonFromImage;
    private Actor frontPage;
    private String nextScreenName;

    public MainMenu(String nextScreenName) {
        this.nextScreenName = nextScreenName;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void createActors() {
        frontPage= ActorUtils.createActorFromImage("eating.jpg");
        frontPage.setSize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
        stage.addActor(frontPage);
        buttonFromImage = ActorUtils.createActorFromImage("start.jpg");
        buttonFromImage.setSize(50,50);
        buttonFromImage.setPosition(
                stage.getViewport().getScreenWidth()/2 - buttonFromImage.getWidth()/2,
                stage.getViewport().getScreenHeight()/2 - buttonFromImage.getHeight()/2);
        stage.addActor(buttonFromImage);
    }

    @Override
    public void setInputForActors() {

    }

    @Override
    public void setActionsForActors() {
        buttonFromImage.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gotoScreen(nextScreenName);
            }
        });
    }

    @Override
    protected void calledEveryFrame() {

    }
}
