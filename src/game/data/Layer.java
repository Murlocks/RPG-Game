package game.data;

import java.util.HashMap;

public class Layer {
	
	private HashMap<Integer, Item> Items;
	private float scrollX, scrollY;
	
	public HashMap<Integer, Item> getItems() {
		return Items;
	}
	public void setItems(HashMap<Integer, Item> items) {
		Items = items;
	}
	public float getScrollX() {
		return scrollX;
	}
	public void setScrollX(float scrollX) {
		this.scrollX = scrollX;
	}
	public float getScrollY() {
		return scrollY;
	}
	public void setScrollY(float scrollY) {
		this.scrollY = scrollY;
	}

}
