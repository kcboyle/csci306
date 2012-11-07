package Board;

public class HumanPlayer extends Player{

	public HumanPlayer() {
		super();
	}
	
	public HumanPlayer(String name, String color, int initpos) {
		this.name = name;
		this.color = color;
		this.initpos = initpos;
	}

}
