package com.game.tiles;

import com.game.utils.Motion;
import javafx.scene.image.ImageView;


// This abstract class is the superclass of all Tile based classes in the com.game.tiles package.
// This class contains variables that control where tiles can move.

public abstract class Tile {

    // Created image variable to storage images for each tile class and for print on
    // the game board.
    protected ImageView image;

    // Created property variable to check types of tiles(Vertical,Horizontal...).
    protected String property;

    // Created to storage whether the tiles could move.
    protected boolean canMove;

    // These boolean variables created to determine which direction the objects will go.
    protected boolean isTopFree;
    protected boolean isBottomFree;
    protected boolean isRightFree;
    protected boolean isLeftFree;
    private final Motion motion;

    public Tile() {
        this.motion = new Motion();
    }

    public boolean isMove() {
        return canMove;
    }

    public ImageView getImage() {
        return image;
    }

    public String getProperty() {
        return property;
    }

    public boolean isTopFree() {
        return isTopFree;
    }

    public boolean isBottomFree() {
        return isBottomFree;
    }

    public boolean isRightFree() {
        return isRightFree;
    }

    public boolean isLeftFree() {
        return isLeftFree;
    }

    public Motion getMotion() {
        return motion;
    }

}
