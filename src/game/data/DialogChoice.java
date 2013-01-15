package game.data;

public class DialogChoice {
	private int key, pointsTo;
	private String choice;
	
	public DialogChoice(int p, String c){
		setPointsTo(p);
		setChoice(c);
	}
	
	public DialogChoice(int k, int p, String c){
		setKey(k);
		setPointsTo(p);
		setChoice(c);
	}
	
	public int getPointsTo() {
		return pointsTo;
	}
	
	public void setPointsTo(int pointsTo) {
		this.pointsTo = pointsTo;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public String getChoice() {
		return choice;
	}
	
	public void setChoice(String choice) {
		this.choice = choice;
	}
}
