package game.spatials;

import com.apollo.Layer;

//order in which entities are rendered, called and checked by the renderManager object in Core.java
public enum Layers implements Layer{
	Map, Npc, Player, NpcFront, Gui;

	@Override
	public int getLayerId() {
		return ordinal();
	}
}
