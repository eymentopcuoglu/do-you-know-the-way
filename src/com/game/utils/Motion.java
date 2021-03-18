package com.game.utils;

//This class is used to block the tiles from moving in two directions
public class Motion {

	private boolean isMoving;
	private boolean isMovingUp;
	private boolean isMovingDown;
	private boolean isMovingLeft;
	private boolean isMovingRight;
	
	//If the tile is currently moving down or the tile is not moving, let it move down
	public boolean canMoveDown() {
		return (!this.isMoving || this.isMovingDown);
	}
	
	//If the tile is currently moving up or the tile is not moving, let it move up
	public boolean canMoveUp() {
		return (!this.isMoving || this.isMovingUp);
	}
	
	//If the tile is currently moving left or the tile is not moving, let it move left
	public boolean canMoveLeft() {
		return (!this.isMoving || this.isMovingLeft);
	}
	
	//If the tile is currently moving right or the tile is not moving, let it move right
	public boolean canMoveRight() {
		return (!this.isMoving || this.isMovingRight);
	}

	public void setMoving(boolean moving) {
		isMoving = moving;
	}

	public void setMovingUp(boolean movingUp) {
		isMovingUp = movingUp;
	}

	public void setMovingDown(boolean movingDown) {
		isMovingDown = movingDown;
	}

	public void setMovingLeft(boolean movingLeft) {
		isMovingLeft = movingLeft;
	}

	public void setMovingRight(boolean movingRight) {
		isMovingRight = movingRight;
	}
}
