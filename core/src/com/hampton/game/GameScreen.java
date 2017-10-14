package com.hampton.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameScreen {
    protected Stage stage;
    private StateManager stateManager;
    protected Color backgroundColor = new Color();
    protected long numFrames;

    public void setData(Stage stage, StateManager stateManager) {
        this.stage = stage;
        this.stateManager = stateManager;
    }

    // This method is called when the GameScreen gets focus
    public abstract void initialize();

    public abstract void createActors();

    public abstract void setInputForActors();

    public abstract void setActionsForActors();

    public final void gotoScreen(String screenName) {
        stateManager.goToScreen(screenName);
    }

    /**
     * Called before actors act!
     */
    public final void calledDuringRender() {
        calledEveryFrame();
        numFrames++;
    }

    protected abstract void calledEveryFrame();
    public abstract void update(int width, int height);
    public Stage getStage() {
        return stage;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }


}
