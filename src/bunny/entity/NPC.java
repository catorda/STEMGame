package bunny.entity;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class NPC extends Entity {
	
	Rectangle bounds;
	String[] messages; 
	int messageIndex = 0; 
	
	public NPC(String id, String[] messages, Rectangle bounds) {
		super(id);
		this.messages = messages; 
		this.bounds = bounds; 
	}
	
	public String getNextMessage() {
		int lastMessage = messageIndex; 
		messageIndex++; 
		return messages[lastMessage]; 
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public Rectangle getBounds() {
		return bounds; 
	}
	
	public boolean isNear(Entity ent) {
		// if to the Right of NPC 
		Rectangle nearBounds = new Rectangle(this.bounds.getX() - ent.getSprite().getWidth() - 5, 
				this.bounds.getY() - ent.getSprite().getHeight() -5, 
				this.bounds.getX() + (ent.getSprite().getWidth()*3) + 10, 
				this.bounds.getY() + (ent.getSprite().getHeight()*3) + 10);
		
		if(nearBounds.contains(ent.getPosition().x, ent.getPosition().y)){
			return true; 
		} else { 
			return false;
		}
	
	}
}
