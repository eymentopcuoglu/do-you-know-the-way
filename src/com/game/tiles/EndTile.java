package com.game.tiles;

import javafx.scene.image.ImageView;

//This class is the subclass of Tile class. It stores the information of the end tile.
// Rolling the ball ends in this tile .
public class EndTile extends Tile {

	// Creates an end tile object according to the "property" parameter of the
	// constructor and arranges the isFree variables in the Tile class.
	// It also creates imageView according to the "property parameter."
	public EndTile(String property) {

		this.property = property;

		switch (property) {
			case "HorizontalL":
				this.image = new ImageView("resources/images/End HorizontalL.jpeg");
				this.isLeftFree = true;
				break;
			case "HorizontalR":
				this.image = new ImageView("resources/images/End HorizontalR.jpeg");
				this.isRightFree = true;
				break;
			case "VerticalB":
				this.image = new ImageView("resources/images/End VerticalB.jpeg");
				this.isBottomFree = true;
				break;
			case "VerticalT":
				this.image = new ImageView("resources/images/End VerticalT.jpeg");
				this.isTopFree = true;
				break;
		}
		this.canMove = false; // This boolean variable is updated according to whether the object can move or
								// not.(End tiles cannot move)
	}
}
