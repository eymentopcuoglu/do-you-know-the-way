package com.game.tiles;

import javafx.scene.image.ImageView;

//This class is the subclass of Tile class. It stores the information of the empty tile.
public class EmptyTile extends Tile {

	// Creates an empty tile object according to the "property" parameter of the
	// constructor and arranges the isFree variables in the Tile class.
	// It also creates imageView according to the "property parameter."
	public EmptyTile(String property) {

		this.property = property;

		if (property.equals("none")) {

			this.image = new ImageView("resources/images/Empty.jpg");

			this.canMove = true;
		}

		else if (property.equals("Free")) {

			this.image = new ImageView("resources/images/EmptyFree.jpeg");

			this.canMove = false; // This boolean variable is updated according to whether the object can move or
									// not.
		}

	}

}
