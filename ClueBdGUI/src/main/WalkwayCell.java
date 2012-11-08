package main;

public class WalkwayCell extends BoardCell{
	
	public WalkwayCell() {
		super();
		cellType = 'W';
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	public void draw() {}
}
