package com.game.tiles;

import javafx.scene.image.ImageView;

//This class is the subclass of Tile class. It stores the information of the Static tiles. (Static tiles cannot move)
public class PipeStaticTile extends Tile {

	// Creates a static tile object according to the "property" parameter of the
	// constructor and arranges the isFree variables in the Tile class.
	// It also creates imageView according to the "property parameter."
	public PipeStaticTile(String property) {

		this.property = property;

		switch (property) {
			case "Horizontal":
				this.image = new ImageView("resources/images/PipeStatic Horizontal.jpeg");
				this.isLeftFree = true;
				this.isRightFree = true;

				break;
			case "Vertical":
				this.image = new ImageView("resources/images/PipeStatic Vertical.jpeg");
				this.isTopFree = true;
				this.isBottomFree = true;

				break;
			case "00":

				this.image = new ImageView("resources/images/CurvedStatic 00.jpeg");
				this.isLeftFree = true;
				this.isTopFree = true;
				break;
			case "01":
				this.isTopFree = true;
				this.isRightFree = true;
				this.image = new ImageView("resources/images/CurvedStatic 01.jpeg");
				break;
			case "10":
				this.isLeftFree = true;
				this.isBottomFree = true;
				this.image = new ImageView("resources/images/CurvedStatic 10.jpeg");

				break;
			case "11":
				this.isRightFree = true;
				this.isBottomFree = true;
				this.image = new ImageView("resources/images/CurvedStatic 11.jpeg");
				break;
		}
		this.canMove = false; // This boolean variable is updated according to whether the object can move or
								// not.(Static tiles cannot move)
	}

}
