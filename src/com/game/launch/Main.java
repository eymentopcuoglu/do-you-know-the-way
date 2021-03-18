package com.game.launch;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

// Main class is the subclass of Application class. 
// This is where the game was launched as an java application.

public class Main extends Application {

    private double firstX, lastX, firstY, lastY; //  Created to keep the coordinates of the mouse cursor and tiles.
    private int levelCounter = 1; // Created levelCounter to print it on the left bottom screen.
    private int row, column; // Created to storage the tiles in the tiles array .
    private double initialLayoutY, initialLayoutX; //  Created to keep the initial coordinates of the tiles.
    private String level = "level1.txt"; // Created level string to take level informations from the leve.txt file.
    private String userName; // Created userName to print user's nickName on the bottom of the screen.
    private Stage stage; // Created stage here to use setScene function.
    private Scene scene;// Created scene here to use setScene function. By doing this , program changes
    // the scenes on the same screen instead of closing and reopening.

    public static void main(String[] args) {
        Application.launch(args); // The application launches from here.
    }

    // Here the create MainMenu method is called. Just because the other create
    // methods call each other,  the execution of the createMainMenu method is enough to start the game.
    @Override
    public void start(Stage stage) {
        createMainMenu();
    }

    // This method creates main menu screen.
    // The start screen of the game is created here.
    public void createMainMenu() {

        // Created audio clip to play in the background for main menu screen.
        AudioClip mainMenuSound = new AudioClip("file:src/resources/sounds/sound.wav");

        mainMenuSound.setCycleCount(999); // When music is finished, it starts again.
        mainMenuSound.play();

        stage = new Stage(); // Creates new stage.
        stage.setTitle("Do You Know The Way?"); // Set the title of the game.(On the top left )
        stage.setResizable(false); // Disables resizing function to avoid errors.
        stage.getIcons().add(new Image("resources/images/ugandan_knuckles.png")); // Set the icon (On the top left)

        Pane pane = new Pane(); // Creates new pane.

        // Creates background for main menu screen.
        BackgroundImage back = new BackgroundImage(new Image("resources/images/background.jpg", 480, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(back));

        ImageView gameName = new ImageView("resources/images/Game Name.gif"); // Creates image view to print the name
        // of the game on the
        // main menu screen as a logo.
        Button newGame = new Button("", new ImageView("resources/images/playButton.png")); // Creates newGame button
        // to use it for play
        // function.
        Button exit = new Button("", new ImageView("resources/images/quitButton.png")); // Creates exit button to use
        // it for exit
        // function.

        newGame.setMaxSize(300, 80); // Sets size of the newGame button.
        newGame.setMinSize(300, 80);

        exit.setMaxSize(300, 80); // Sets the size of the exit button.
        exit.setMinSize(300, 80);

        pane.getChildren().addAll(newGame, exit, gameName); // It adds all created objects to the pane.

        gameName.relocate(0, 100); // It relocates the name of the game logo on the screen.
        newGame.relocate(225, 250); // It relocates the play button on the screen.
        exit.relocate(225, 400); // It relocates the exit button on the screen.

        // Creates new scene and adds pane into it .
        Scene scene = new Scene(pane, 750, 600);
        stage.setScene(scene);
        stage.show();

        // newGame button stops the background sound of the main menu and executes
        // createNickNameBoard method. By using this button scene will be changed from
        // mainMenu to nickNameScreen.

        newGame.setOnAction(gameboard -> {
            mainMenuSound.stop();
            createNickNameBoard();
        });

        // exit button simply terminates the program.

        exit.setOnAction(gameboard -> System.exit(1));
    }

    // This method creates the nickName screen.
    // It runs after pressing the play button in the main menu.

    public void createNickNameBoard() {

        Scene sceneText; // Creates new scene.

        stage.setResizable(false); // Disables resizing function to avoid errors.

        Pane paneText = new Pane(); // Creates new pane .

        TextField textField = new TextField(); // A text field is created where the user will enter the nickname.
        textField.setMaxWidth(300); // Resizes the text field.
        textField.setMinWidth(200);

        ImageView nickNameText = new ImageView("resources/images/Nick Name.png"); // Creates image view to print
        // "Enter your nickname"
        // message to the screen.
        // It is printed as a logo.

        Button startButton = new Button("Start"); // Creates start button to start the game after user entered nickname.

        paneText.getChildren().add(nickNameText);
        paneText.getChildren().add(startButton); // Adds all objects to the pane.
        paneText.getChildren().add(textField);

        nickNameText.relocate(0, 30);
        textField.relocate(50, 90); // Relocates objects in the screen.
        startButton.relocate(130, 150);

        // Creates background for nickName screen.

        BackgroundImage back = new BackgroundImage(new Image("resources/images/background.jpg", 295, 250, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        paneText.setBackground(new Background(back));

        sceneText = new Scene(paneText, 295, 250);
        stage.setScene(sceneText);
        stage.show();

        // When the start button is pressed, the "userName" variable is updated with the
        // user's nickName.

        startButton.setOnAction(text -> {
            userName = textField.getText();
            stage.setScene(scene); // It changes the scene for starting the game.
            createGameBoard(); // It executes createGameBoard method to start the game.
        });
    }


    public void createGameBoard() {

        GameBoard gameboard = new GameBoard(level);

        Label labelNick = new Label(userName); // Created label to print the user's nickname on the screen.
        labelNick.setFont(Font.font("Arial", FontWeight.BOLD, 25)); //  Changed the font of the nickName.
        labelNick.setTextFill(Color.rgb(35, 243, 255)); // Changed the color of the nickName.
        labelNick.relocate(170, 550); // Relocated the nickName on the screen.

        Label labelMove = new Label("Moves : 0");  // Created label to print the user's moves on the screen.
        labelMove.setFont(Font.font("Arial", FontWeight.BOLD, 25)); // Changed the font of the user's moves text.
        labelMove.setTextFill(Color.rgb(35, 243, 255)); // Changed the color of the user's moves text.
        labelMove.relocate(345, 500); // Relocated the user's moves text on the screen.

        ImageView ball = new ImageView("resources/images/ball.png"); // Created ball image view to use it on the game
        // board.

        Label labelLevel = new Label("Level"); // Created new label to print which level is currently user is playing
        // on the screen.

        labelLevel.setText("Level  " + levelCounter); // Updates level text with the levelCounter variable.
        labelLevel.setFont(Font.font("Arial", FontWeight.BOLD, 25)); // Changes the font of the level counter .
        labelLevel.relocate(20, 500);// Relocates the level counter on the screen.
        labelLevel.setTextFill(Color.rgb(35, 243, 255)); // Changes the color of the level counter text.


        // Created new background to use it on the game screen.
        BackgroundImage back = new BackgroundImage(new Image("resources/images/background.jpg", 480, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        gameboard.setBackground(new Background(back));

        // relocates the ball at the beginning of the game based on starter tile .
        ball.relocate(
                (gameboard.getStarter().getImage().getLayoutX() + gameboard.getSize() / 2)
                        - (ball.getImage().getWidth() - gameboard.getSize() / 5.2),
                gameboard.getStarter().getImage().getLayoutY() + gameboard.getSize() / 2
                        - (ball.getImage().getHeight() - gameboard.getSize() / 4.5));

        // Adds all objects to the game board pane.
        gameboard.getChildren().add(labelMove);
        gameboard.getChildren().add(labelLevel);
        gameboard.getChildren().add(ball);
        gameboard.getChildren().add(labelNick);


        // When the setOnMousePressed event occurs, the coordinates of the mouse cursor are stored and  determines
        // which tile the user presses.
        gameboard.setOnMousePressed(e -> {

            // Storages the coordinates of the mouse cursor

            firstX = e.getX();
            firstY = e.getY();

            // Calculates the row and column based on firstX and firstY.

            row = (int) firstY / gameboard.getSize();
            column = (int) firstX / gameboard.getSize();

            // Based on row and column values , it storages the initial coordinates of the desired tile.

            if (row < 4 && row >= 0 && column < 4 && column >= 0) {
                initialLayoutY = gameboard.getTiles()[row][column].getImage().getLayoutY();
                initialLayoutX = gameboard.getTiles()[row][column].getImage().getLayoutX();
            }
        });

        //During the setOnMouseDragged event , lastX and lastY variables keeps updating itself and used to determine
        // which direction is desired to drag the tiles.
        gameboard.setOnMouseDragged(e -> {

            //Coordinates of the mouse cursor are constantly updated.
            lastX = e.getX();
            lastY = e.getY();

            //	According to the coordinates of the mouse cursor, the method is determined where the user drags the
            //	object.
            if (row < 4 && row >= 0 && column < 4 && column >= 0) {

                if (lastY - firstY > 30 && gameboard.getTiles()[row][column].getMotion().canMoveDown()) {
                    gameboard.moveDown(gameboard.getTiles()[row][column], e, row, column, firstY, initialLayoutY,
                            labelMove);
                } else if (lastX - firstX > 30 && gameboard.getTiles()[row][column].getMotion().canMoveRight()) {
                    gameboard.moveRight(gameboard.getTiles()[row][column], e, row, column, firstX, initialLayoutX,
                            labelMove);
                } else if (firstX - lastX > 30 && gameboard.getTiles()[row][column].getMotion().canMoveLeft()) {
                    gameboard.moveLeft(gameboard.getTiles()[row][column], e, row, column, firstX, initialLayoutX,
                            labelMove);
                } else if (firstY - lastY > 30 && gameboard.getTiles()[row][column].getMotion().canMoveUp()) {
                    gameboard.moveUp(gameboard.getTiles()[row][column], e, row, column, firstY, initialLayoutY,
                            labelMove);
                }
            }
        });

        // Each time the setOnMouseReleased event occurs, the isCompleted method is called and checked.
        gameboard.setOnMouseReleased(finish -> {


            // if user makes wrong move wrongSlidSound will be played.
            if (gameboard.isFalse()) {
                gameboard.getWrongSlideSound().play();
                gameboard.setFalse(false);
            }

            if (gameboard.isCompleted()) {

                ball.relocate(0, 0);
                ball.toFront();

                PathTransition pt = new PathTransition(); // Creates path transition to set path for the ball.
                pt.setPath(gameboard.getCorrectPath()); // Uses correctPath to set path .
                pt.setNode(ball); // Sets ball as a node .
                pt.setDuration(Duration.seconds(3));
                pt.play(); // Plays the rolling ball animation.

                Button nextBut = new Button("Next Stage"); // Creates next Stage button to play the next level.
                nextBut.relocate(400, 550); // Relocates the button on the screen.
                gameboard.getChildren().add(nextBut); // Adds the button to the game board pane.

                // These events are defined as "null" to lock the screen when the level is completed.

                gameboard.setOnMouseDragged(null);
                gameboard.setOnMouseReleased(null);

                if (levelCounter == 15) { // Represent the maximum value of the level that user can play.

                    gameboard.getChildren().remove(nextBut); // Removed the nextButton

                    Button exitButton = new Button("Exit"); // Created the exit button to terminate the program.
                    gameboard.getChildren().add(exitButton); // Adds the exit button to the game board pane.

                    exitButton.relocate(400, 550); // Relocates the exit button on the screen.

                    // When the user pressed exitButton program terminates itself.

                    exitButton.setOnAction(exit -> System.exit(1));

                }

                // When user pressed nextButton , the user's moves is defines as 0 , levelCounter updates itself
                // based on which level is completed and
                // createGameBoard method executes to create new level.
                nextBut.setOnAction(goNext -> {
                    gameboard.setMove(0);
                    stage.setScene(scene);
                    level = "level" + (++levelCounter) + ".txt";
                    createGameBoard();
                });
            }
        });
        scene = new Scene(gameboard, 480, 600); // Adds game board pane to the scene.
        stage.setResizable(false); // Disables resizing function to avoid errors.
        stage.setScene(scene);
        stage.show();
    }

}