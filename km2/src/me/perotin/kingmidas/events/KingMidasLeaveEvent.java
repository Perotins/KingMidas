package me.perotin.kingmidas.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KingMidasLeaveEvent extends Event {

		private Player midas;
		private static final HandlerList handlers = new HandlerList();

	    public KingMidasLeaveEvent(Player p) {
	    	this.setMidas(p);
	    }
	   

	    public HandlerList getHandlers() {
	        return handlers;
	    }

	    public static HandlerList getHandlerList() {
	        return handlers;
	    }


		public Player getMidas() {
			return midas;
		}


		public void setMidas(Player midas) {
			this.midas = midas;
		}
}
