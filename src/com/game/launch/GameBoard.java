package com.game.launch;

import java.io.InputStream;
import java.util.Scanner;

import com.game.tiles.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

public class GameBoard extends Pane {

    private final Tile[][] tiles = new Tile[4][4];

    // Width and height of each tile
    private final int size = 120;

    // Number of moves
    private int move = 0;

    private final AudioClip slideSound;
    private final AudioClip wrongSlideSound;
    private final AudioClip completeSound;

    private Path correctPath;

    // This boolean value is used for playing audio when it is not a correct move
    private boolean isFalse;

    public GameBoard(String level) {

        // Initializing audio
        slideSound = new AudioClip("file:src/resources/sounds/slideSound.wav");
        wrongSlideSound = new AudioClip("file:src/resources/sounds/ErrorSound.wav");
        completeSound = new AudioClip("file:src/resources/sounds/victorySound.wav");

        // Filling the game board
        this.initialize();

        InputStream inputStr = getClass().getClassLoader().getResourceAsStream("levels/" + level);

        if (inputStr == null)
            throw new RuntimeException("Level folder is not found!");

        Scanner input = new Scanner(inputStr);

        // Reading the input file
        while (input.hasNext()) {

            // Getting the information
            String[] text = input.next().split(",");
            int id = Integer.parseInt(text[0]);
            filler(text[1], text[2], id);
        }
        input.close();

    }

    // This method fills the game board with empty free tiles
    public void initialize() {

        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                EmptyTile empty = new EmptyTile("Free");
                ImageView emptyimage = empty.getImage();
                emptyimage.relocate(size * column, size * row);
                this.getChildren().add(emptyimage);
            }
        }
    }

    // This method takes 3 parameters and obtains these parameters from the
    // level.txt file and used to create game board.
    // Calculates where to position the tile on the game board by using the
    // parameters it takes.

    public void filler(String type, String property, int id) {

        // Calculates row and column values with simple math operations using "id"

        // Multiplying row and column with the size(pixel of each tile) variable will
        // determine the coordinates of the tiles on the game board.

        int row = id / 4;
        int column = id % 4 - 1;

        if (id == 16) {
            row = 3;
            column = 3;
        }

        if (id == 12) {
            row = 2;
            column = 3;
        }

        if (id == 8) {
            row = 1;
            column = 3;
        }
        if (id == 4) {
            row = 0;
            column = 3;
        }

        int x = column * size;
        int y = row * size;

        // If the type parameter equals "Starter", it calls the StarterTile class.
        switch (type) {
            case "Starter":

                // It creates starter tile object based on property.
                if (property.equals("Horizontal"))
                    property = "HorizontalR";
                else if (property.equals("Vertical"))
                    property = "VerticalB";

                StarterTile starter = new StarterTile(property);

                // Relocates the tile on the game board with x,y values that we found
                starter.getImage().relocate(x, y);

                // recently from "id" value.
                this.getChildren().add(starter.getImage()); // Adds the tile to the game board.

                this.tiles[row][column] = starter; // Adds the tile to the tiles array.
                break;

            // If the "type" parameter equals "Pipe", it calls the PipeTile class.
            case "Pipe":

                // It creates pipe tile object based on property.
                PipeTile pipe = new PipeTile(property);

                // Relocates the tile on the game board with x,y values that we found recently from "id" value.
                pipe.getImage().relocate(x, y);

                this.getChildren().add(pipe.getImage()); // Adds the tile to the game board.

                this.tiles[row][column] = pipe; // Adds the tile to the tiles array.
                break;

            // If the "type" parameter equals "PipeStatic", it calls the PipeStaticTile
            // class.
            case "PipeStatic":

                // It creates pipe static tile object based on property.
                PipeStaticTile pipestatic = new PipeStaticTile(property);
                pipestatic.getImage().relocate(x, y); // Relocates the tile on the game board with x,y values that we
                // found recently from "id" value.

                this.getChildren().add(pipestatic.getImage()); // Adds the tile to the game board.

                this.tiles[row][column] = pipestatic; // Adds the tile to the tiles array.
                break;

            // If the "type" parameter equals "End", it calls the EndTile class.
            case "End":

                // It creates end tile object based on property.
                if (property.equals("Horizontal"))
                    property = "HorizontalL";
                else if (property.equals("Vertical"))
                    property = "VerticalB";

                EndTile end = new EndTile(property);

                // Relocates the tile on the game board with x,y values that we found recently from "id" value.
                end.getImage().relocate(x, y);

                this.getChildren().add(end.getImage()); // Adds the tile to the game board.

                this.tiles[row][column] = end; // Adds the tile to the tiles array.
                break;

            // If the "type" parameter equals "Empty", it calls the EmptyTile class.
            case "Empty":

                // It creates empty tile object based on property.
                EmptyTile empty = new EmptyTile(property);

                // Relocates the tile on the game board with x,y values that we found recently from "id" value.
                empty.getImage().relocate(x, y);

                this.getChildren().add(empty.getImage()); // Adds the tile to the game board.

                this.tiles[row][column] = empty; // Adds the tile to the tiles array.
                break;
        }

    }

    public void moveDown(Tile tile, MouseEvent e, int row, int column, double firstY, double layY, Label label) {

        EmptyTile empty = new EmptyTile("Free");

        // If the tile is in the last row, don't let it move down
        if (row == 3) {
            isFalse = true;
        }

        // If the property of the tile is static, don't let any motion
        else if (!(tile.isMove())) {
            isFalse = true;
        }

        // If the tile which is on the bottom side of the current tile is empty free
        // tile, let it move down
        else if (this.tiles[row + 1][column].getProperty().equals("Free")) {

            // Update the motion properties
            tile.getMotion().setMoving(true);
            tile.getMotion().setMovingDown(true);

            this.getTiles()[row][column].getImage().toFront();

            // Dragging tile
            tile.getImage().setLayoutY(layY + (e.getY() - firstY));

            // Don't let the user move more than one block
            if (tile.getImage().getLayoutY() > layY + size) {
                tile.getImage().setLayoutY(layY + size);
            }

            // Checking whether it is a correct move or not
            tile.getImage().setOnMouseReleased(release -> {

                // If the tile which is on the bottom side of the current tile is empty free tile
                if (this.tiles[row + 1][column].getProperty().equals("Free")) {

                    // If the user dragged the tile down enough to move it
                    if (release.getSceneY() > layY + 50) {

                        // Update the position of the tile in the scene and in the array, play the slideSound
                        tile.getImage().setLayoutY(layY + size);
                        this.tiles[row + 1][column] = tile;
                        this.tiles[row][column] = empty;
                        slideSound.play();

                        // Update the number of moves
                        move++;
                        label.setText("Moves : " + move);
                    }
                    // If the drag movement is not enough, move back to the original position
                    else
                        tile.getImage().setLayoutY(layY);
                }
                // End of the movement, update the motion properties
                tile.getMotion().setMoving(false);
                tile.getMotion().setMovingDown(false);
            });
        }
        // If the tile under the current tile is not empty free, update the isFalse and return
        else {
            isFalse = true;
        }
    }

    public void moveUp(Tile tile, MouseEvent e, int row, int column, double firstY, double layY, Label label) {

        EmptyTile empty = new EmptyTile("Free");

        // If the tile is in the first row, don't let it move up
        if (row == 0) {
            isFalse = true;
        }

        // If the property of the tile is static, don't let any motion
        else if (!(tile.isMove())) {
            isFalse = true;
        }

        // If the tile which is on the top side of the current tile is empty free
        // tile, let it move up
        else if (this.tiles[row - 1][column].getProperty().equals("Free")) {

            // Update the motion properties
            tile.getMotion().setMoving(true);
            tile.getMotion().setMovingUp(true);

            this.getTiles()[row][column].getImage().toFront();

            // Dragging tile
            tile.getImage().setLayoutY(layY + (e.getY() - firstY));

            // Don't let the user move more than one block
            if (tile.getImage().getLayoutY() < layY - size) {
                tile.getImage().setLayoutY(layY - size);
            }

            // Checking whether it is a correct move or not
            tile.getImage().setOnMouseReleased(release -> {

                // If the tile which is on the top side of the current tile is empty free
                // tile
                if (this.tiles[row - 1][column].getProperty().equals("Free")) {

                    // If the user dragged the tile up enough to move it
                    if (release.getSceneY() < layY - 50) {

                        // Update the position of the tile in the scene and in the array, play the slideSound
                        tile.getImage().setLayoutY(layY - size);
                        this.tiles[row - 1][column] = tile;
                        this.tiles[row][column] = empty;
                        slideSound.play();

                        // Update the number of moves
                        move++;
                        label.setText("Moves : " + move);

                    }
                    // If the drag movement is not enough, move back to the original position
                    else
                        tile.getImage().setLayoutY(layY);
                }
                // End of the movement, update the motion properties
                tile.getMotion().setMoving(false);
                tile.getMotion().setMovingUp(false);
            });

        }
        // If the tile under the current tile is not empty free, update the isFalse and
        // return
        else {
            isFalse = true;
        }
    }

    public void moveLeft(Tile tile, MouseEvent e, int row, int column, double firstX, double layX, Label label) {

        EmptyTile empty = new EmptyTile("Free");

        // If the tile is in the first column, don't let it move left
        if (column == 0) {
            isFalse = true;
        }

        // If the property of the tile is static, don't let any motion
        else if (!(tile.isMove())) {
            isFalse = true;
        }

        // If the tile which is on the left side of the current tile is empty free
        // tile, let it move left
        else if (this.tiles[row][column - 1].getProperty().equals("Free")) {

            // Update the motion properties
            tile.getMotion().setMoving(true);
            tile.getMotion().setMovingLeft(true);

            this.getTiles()[row][column].getImage().toFront();

            // Dragging tile
            tile.getImage().setLayoutX(layX + (e.getX() - firstX));

            // Don't let the user move more than one block
            if (tile.getImage().getLayoutX() < layX - size) {
                tile.getImage().setLayoutX(layX - size);
            }

            // Checking whether it is a correct move or not
            tile.getImage().setOnMouseReleased(release -> {

                // If the tile which is on the left side of the current tile is empty free
                // tile
                if (this.tiles[row][column - 1].getProperty().equals("Free")) {

                    // If the user dragged the tile left enough to move it
                    if (release.getSceneX() < layX - 50) {

                        // Update the position of the tile in the scene and in the array, play the
                        // slideSound
                        tile.getImage().setLayoutX(layX - size);
                        this.tiles[row][column - 1] = tile;
                        this.tiles[row][column] = empty;
                        slideSound.play();

                        // Update the number of moves
                        move++;
                        label.setText("Moves : " + move);
                    }
                    // If the drag movement is not enough, move back to the original position
                    else
                        tile.getImage().setLayoutX(layX);
                }
                // End of the movement, update the motion properties
                tile.getMotion().setMoving(false);
                tile.getMotion().setMovingLeft(false);
            });
        }
        // If the tile under the current tile is not empty free, update the isFalse and
        // return
        else {
            isFalse = true;
        }
    }

    public void moveRight(Tile tile, MouseEvent e, int row, int column, double firstX, double layX, Label label) {

        EmptyTile empty = new EmptyTile("Free");

        // If the tile is in the last column, don't let it move right
        if (column == 3) {
            isFalse = true;
        }

        // If the property of the tile is static, don't let any motion
        else if (!(tile.isMove())) {
            isFalse = true;
        }

        // If the tile which is on the right side of the current tile is empty free
        // tile, let it move right
        else if (this.tiles[row][column + 1].getProperty().equals("Free")) {

            // Update the motion properties
            tile.getMotion().setMoving(true);
            tile.getMotion().setMovingRight(true);

            this.getTiles()[row][column].getImage().toFront();

            // Dragging tile
            tile.getImage().setLayoutX(layX + (e.getX() - firstX));

            // Don't let the user move more than one block
            if (tile.getImage().getLayoutX() > layX + size) {
                tile.getImage().setLayoutX(layX + size);
            }

            // Checking whether it is a correct move or not
            tile.getImage().setOnMouseReleased(release -> {

                // If the tile which is on the right side of the current tile is empty free
                // tile
                if (this.tiles[row][column + 1].getProperty().equals("Free")) {

                    // If the user dragged the tile left enough to move it
                    if (release.getSceneX() > layX + 50) {

                        // Update the position of the tile in the scene and in the array, play the
                        // slideSound
                        tile.getImage().setLayoutX(layX + size);
                        this.tiles[row][column + 1] = tile;
                        this.tiles[row][column] = empty;
                        slideSound.play();

                        // Update the number of moves
                        move++;
                        label.setText("Moves : " + move);

                    }
                    // If the drag movement is not enough, move back to the original position
                    else
                        tile.getImage().setLayoutX(layX);

                }
                // End of the movement, update the motion properties
                tile.getMotion().setMoving(false);
                tile.getMotion().setMovingRight(false);
            });
        }
        // If the tile under the current tile is not empty free, update the isFalse and
        // return
        else {
            isFalse = true;
        }
    }

    public boolean isCompleted() {

        // Getting the starter tile
        Tile tile = getStarter();

        // Creating a new path and starting with the center of the starter tile
        Path path = new Path();
        path.getElements()
                .add(new MoveTo(tile.getImage().getLayoutX() + size / 2.0, tile.getImage().getLayoutY() + size / 2.0));

        boolean isEndTile = true;

        // Getting the position information of the starter tile
        int row = (int) tile.getImage().getLayoutY() / size;
        int column = (int) tile.getImage().getLayoutX() / size;

        // Directions: 0 downward, 1 left, 2 upward, 3 right
        int direction;

        // Initializing the direction depending on the shape of the starter tile
        switch (tile.getProperty()) {
            case "VerticalB":
                direction = 0;
                break;
            case "VerticalT":
                direction = 2;
                break;
            case "HorizontalR":
                direction = 3;
                break;
            default:
                direction = 1;
                break;
        }

        // While the path is not constructed up to end tile
        while (isEndTile) {

            // If the right of the tile is free and the direction is right
            if (tile.isRightFree() && direction == 3) {

                // If it is a starter tile, move to the right by the amount of (size / 2)
                if (tile.getClass().getSimpleName().equals("StarterTile")) {
                    path.getElements().add(new HLineTo(tile.getImage().getLayoutX() + size / 2.0));
                }
                // Else, move to the right by the amount of the size
                else
                    path.getElements().add(new HLineTo(tile.getImage().getLayoutX() + size));

                // Update the tile, if its left is not free return false
                if (column + 1 == 4)
                    return false;
                tile = this.tiles[row][++column];
                if (!tile.isLeftFree())
                    return false;
            }

            // If the right of the tile is free and the direction is down (curved pipe 01
            // case)
            else if (tile.isRightFree() && direction == 0) {

                // Draw the appropriate arc for curved pipe 01
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(false);
                arc.setX(tile.getImage().getLayoutX() + size);
                arc.setY(tile.getImage().getLayoutY() + size / 2.0);

                // Add it to the path and make the direction right
                path.getElements().add(arc);
                direction = 3;

                // Update the tile, if its left is not free return false
                if (column + 1 == 4)
                    return false;
                tile = this.tiles[row][++column];
                if (!tile.isLeftFree())
                    return false;
            }

            // If the right of the tile is free and the direction is up (curved pipe 11
            // case)
            else if (tile.isRightFree() && direction == 2) {

                // Draw the appropriate arc for curved pipe 11
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(true);
                arc.setX(tile.getImage().getLayoutX() + size);
                arc.setY(tile.getImage().getLayoutY() + size / 2.0);

                // Add it to the path and make the direction right
                path.getElements().add(arc);
                direction = 3;

                // Update the tile, if its left is not free return false
                if (column + 1 == 4)
                    return false;
                tile = this.tiles[row][++column];
                if (!tile.isLeftFree())
                    return false;
            }

            // If the left of the tile is free and the direction is left
            else if (tile.isLeftFree() && direction == 1) {
                // If it is a starter tile, move to the left by the amount of (size / 2)
                if (tile.getClass().getSimpleName().equals("StarterTile")) {
                    path.getElements().add(new HLineTo(tile.getImage().getLayoutX()));
                }
                // Else, move to the left by the amount of the size
                else
                    path.getElements().add(new HLineTo(tile.getImage().getLayoutX()));

                // Update the tile, if its right is not free return false
                if (column - 1 == -1)
                    return false;
                tile = this.tiles[row][--column];
                if (!tile.isRightFree())
                    return false;

            }

            // If the left of the tile is free and the direction is down (curved pipe 00
            // case)
            else if (tile.isLeftFree() && direction == 0) {

                // Draw the appropriate arc for curved pipe 00
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(true);
                arc.setX(tile.getImage().getLayoutX());
                arc.setY(tile.getImage().getLayoutY() + size / 2.0);

                // Add it to the path and make the direction left
                path.getElements().add(arc);
                direction = 1;

                // Update the tile, if its right is not free return false
                if (column - 1 == -1)
                    return false;
                tile = this.tiles[row][--column];
                if (!tile.isRightFree())
                    return false;
            }

            // If the left of the tile is free and the direction is up (curved pipe 10 case)
            else if (tile.isLeftFree() && direction == 2) {

                // Draw the appropriate arc for curved pipe 10
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(false);
                arc.setX(tile.getImage().getLayoutX());
                arc.setY(tile.getImage().getLayoutY() + size / 2.0);

                // Add it to the path and make the direction left
                path.getElements().add(arc);
                direction = 1;

                // Update the tile, if its right is not free return false
                if (column - 1 == -1)
                    return false;
                tile = this.tiles[row][--column];
                if (!tile.isRightFree())
                    return false;
            }

            // If the bottom of the tile is free and the direction is down
            else if (tile.isBottomFree() && direction == 0) {

                // If it is a starter tile, move to the down by the amount of (size / 2)
                if (tile.getClass().getSimpleName().equals("StarterTile")) {
                    path.getElements().add(new VLineTo(tile.getImage().getLayoutY() + size / 2.0));
                }
                // Else, move to down by the amount of the size
                else
                    path.getElements().add(new VLineTo(tile.getImage().getLayoutY() + size));

                // Update the tile, if its top is not free return false
                if (row + 1 == 4)
                    return false;
                tile = this.tiles[++row][column];
                if (!tile.isTopFree())
                    return false;
            }

            // If the bottom of the tile is free and the direction is right (curved pipe 10
            // case)
            else if (tile.isBottomFree() && direction == 3) {

                // Draw the appropriate arc for curved pipe 10
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(true);
                arc.setX(tile.getImage().getLayoutX() + size / 2.0);
                arc.setY(tile.getImage().getLayoutY() + size);

                // Add it to the path and make the direction down
                path.getElements().add(arc);
                direction = 0;

                // Update the tile, if its top is not free return false
                if (row + 1 == 4)
                    return false;
                tile = this.tiles[++row][column];
                if (!tile.isTopFree())
                    return false;
            }

            // If the bottom of the tile is free and the direction is left (curved pipe 11
            // case)
            else if (tile.isBottomFree() && direction == 1) {

                // Draw the appropriate arc for curved pipe 11
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(false);
                arc.setX(tile.getImage().getLayoutX() + size / 2.0);
                arc.setY(tile.getImage().getLayoutY() + size);

                // Add it to the path and make the direction down
                path.getElements().add(arc);
                direction = 0;

                // Update the tile, if its top is not free return false
                if (row + 1 == 4)
                    return false;
                tile = this.tiles[++row][column];
                if (!tile.isTopFree())
                    return false;
            }

            // If the top of the tile is free and the direction is up
            else if (tile.isTopFree() && direction == 2) {

                // If it is a starter tile, move to up by the amount of (size / 2)
                if (tile.getClass().getSimpleName().equals("StarterTile")) {
                    path.getElements().add(new VLineTo(tile.getImage().getLayoutY()));
                }
                // Else, move to down by the amount of the size
                else
                    path.getElements().add(new VLineTo(tile.getImage().getLayoutY()));

                // Update the tile, if its bottom is not free return false
                if (row - 1 == -1)
                    return false;
                tile = this.tiles[--row][column];
                if (!tile.isBottomFree())
                    return false;
            }

            // If the top of the tile is free and the direction is right (curved pipe 01
            // case)
            else if (tile.isTopFree() && direction == 3) {

                // Draw the appropriate arc for curved pipe 01
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(false);
                arc.setX(tile.getImage().getLayoutX() + size / 2.0);
                arc.setY(tile.getImage().getLayoutY());

                // Add it to the path and make the direction up
                path.getElements().add(arc);
                direction = 2;

                // Update the tile, if its bottom is not free return false
                if (row - 1 == -1)
                    return false;
                tile = this.tiles[--row][column];
                if (!tile.isBottomFree())
                    return false;
            }

            // If the top of the tile is free and the direction is left (curved pipe 00
            // case)
            else if (tile.isTopFree() && direction == 1) {

                // Draw the appropriate arc for curved pipe 00
                ArcTo arc = new ArcTo();
                arc.setRadiusX(size / 2.0);
                arc.setRadiusY(size / 2.0);
                arc.setSweepFlag(true);
                arc.setX(tile.getImage().getLayoutX() + size / 2.0);
                arc.setY(tile.getImage().getLayoutY());

                // Add it to the path and make the direction up
                path.getElements().add(arc);
                direction = 2;

                // Update the tile, if its bottom is not free return false
                if (row - 1 == -1)
                    return false;
                tile = this.tiles[--row][column];
                if (!tile.isBottomFree())
                    return false;
            }

            // If it is an end tile
            else if (tile.getClass().getSimpleName().equals("EndTile")) {

                // If it is horizontal
                if (tile.getProperty().equals("HorizontalR") || tile.getProperty().equals("HorizontalL"))
                    path.getElements().add(new HLineTo(tile.getImage().getLayoutX() + size / 2.0));
                    // If it is vertical
                else
                    path.getElements().add(new VLineTo(tile.getImage().getLayoutY() + size / 2.0));

                // End of the construction of the path
                isEndTile = false;
            }
        }

        // Play the completeSound and update the correctPath, return true
        slideSound.stop();
        completeSound.play();
        this.correctPath = path;
        return true;
    }

    // This method finds the starter tile in the board and returns it
    public Tile getStarter() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.tiles[i][j].getClass().getSimpleName().equals("StarterTile")) {
                    return this.tiles[i][j];
                }
            }
        }
        return null;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getSize() {
        return size;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public AudioClip getWrongSlideSound() {
        return wrongSlideSound;
    }

    public Path getCorrectPath() {
        return correctPath;
    }

    public boolean isFalse() {
        return isFalse;
    }

    public void setFalse(boolean aFalse) {
        isFalse = aFalse;
    }
}