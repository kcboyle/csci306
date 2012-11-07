package Board;

public class Card {
	//uml based
	public enum CardType { ROOM, WEAPON, PERSON };
	
	public String name;
	public CardType cardtype;
	
	public Card(String name, CardType cardtype) {
		this.name = name;
		this.cardtype = cardtype;
	}

	//getters
	public String getName() {
		return name;
	}

	public CardType getCardtype() {
		return cardtype;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
	}

	public void setCardtype(CardType cardtype) {
		this.cardtype = cardtype;
	}
	
	//methods
	public boolean equals(){
		return false;
	}
	
}
