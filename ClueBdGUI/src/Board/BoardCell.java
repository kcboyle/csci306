package Board;

public abstract class BoardCell {
	
	BoardCell()
	{
		row = 0;
		column = 0;
	}
	
	private int row, column;
	
	public Boolean isWalkway(){
		return false;
	}
	
	public Boolean isRoom(){
		return false;
	}
	
	public Boolean isDoorway(){
		return false;
	}
	
}
