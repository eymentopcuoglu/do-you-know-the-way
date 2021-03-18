package com.game.tiles;

import javafx.scene.image.ImageView;

// This class is the subclass of Tile class. It stores the information of the
// starter tile.
public class StarterTile extends Tile {

	// Creates a starter tile object according to the "property" parameter of the
	// constructor and arranges the isFree variables in the Tile class.
	// It also creates imageView according to the "property" parameter.
	public StarterTile(String property) {

		this.property = property;

		switch (property) {
			case "HorizontalR":
				this.image = new ImageView("resources/images/Starter HorizontalR.jpeg");
				this.isRightFree = true;
				break;
			case "HorizontalL":
				this.image = new ImageView("resources/images/Starter HorizontalL.jpeg");
				this.isLeftFree = true;
				break;
			case "VerticalB":
				this.image = new ImageView("resources/images/Starter VerticalB.jpeg");
				this.isBottomFree = true;
				break;
			case "VerticalT":
				this.image = new ImageView("resources/images/Starter VerticalT.jpeg");
				this.isTopFree = true;
				break;
		}

		this.canMove = false; // This boolean variable is updated according to whether the object can move or
								// not.(Starter tiles cannot move)

	}

}
