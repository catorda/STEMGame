package bunny.entity;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
 
import bunny.game.Direction;
import bunny.component.Component;
import bunny.component.render.RenderComponent;


public class Entity {
 
    String id;
 
    Vector2f position;
    private boolean[][] blocked;
	private static final int SIZE = 75;

    int [] duration = {100, 100, 100, 100, 100, 100};
	
	/*
	* false variable means do not auto update the animation.
	* By setting it to false animation will update only when
	* the user presses a key.
	*/
    
	Image upStrip;
	Image downStrip;
	Image sideStrip;

	private Image [] movementUp;
	private Animation up;
	
	private Image [] movementDown;
	private Animation down;
	
	private Image [] movementLeft;
	private Animation left;
	
	private Image [] movementRight;
	private Animation right; 

	private Animation sprite = null;
	
	private Direction direction;
	
    RenderComponent renderComponent = null;
 
    ArrayList<Component> components = null;
 
    public Entity(String id)
    {
        this.id = id;
 
        components = new ArrayList<Component>();
 
        position = new Vector2f(0,0);
        
    	upStrip = null;
    	downStrip = null;
    	sideStrip = null;

    	movementUp = new Image[6];
    	up = null;
    	
    	movementDown = new Image[6];
    	down = null;
    	
    	movementLeft = new Image[6];
    	left = null;
    	
    	movementRight = new Image[6];
    	right = null;
    	
        sprite = right;
        
        direction = Direction.RIGHT;
    }
 
    public void AddComponent(Component component)
    {
        if(RenderComponent.class.isInstance(component))
            renderComponent = (RenderComponent)component;
 
        component.setOwnerEntity(this);
        components.add(component);
    }
 
    public Component getComponent(String id)
    {
        for(Component comp : components)
        {
        	if ( comp.getId().equalsIgnoreCase(id) )
	        return comp;
        }
 
        return null;
    }
 
    public Vector2f getPosition()
    {
    	return position;
    }
 
    public Animation getSprite()
    {
    	return sprite;
    }
    
    public Direction getDirection()
    {
    	return direction;
    }
 
    public String getId()
    {
    	return id;
    }

	public boolean isBlocked(float x, float y)
	{
	    int xBlock = (int)x / SIZE;
	    int yBlock = (int)y / SIZE;
	    return blocked[xBlock][yBlock];
	}
    
    public void setImages(String upD, String downD, String sideD, Color t)
    {
    	try {
			upStrip = new Image (upD, t);
			downStrip = new Image (downD, t);
	    	sideStrip = new Image (sideD, t);
		} catch (SlickException e) {
			e.printStackTrace();
		}
    	
    	for (int i = 0; i < 6; i++)
    	{
    		movementUp[i] = upStrip.getSubImage((75*i), 0, 75, 75);
    		movementDown[i] = downStrip.getSubImage((75*i), 0, 75, 75);
    		movementLeft[i] = (sideStrip.getSubImage((75*i), 0, 75, 75)).getFlippedCopy(true, false);
    		movementRight[i] = sideStrip.getSubImage((75*i), 0, 75, 75);
    	}
    	
    	for (int i = 0; i<= 5; i++) {
    		System.out.println("Up Anim Frame");
    		System.out.println(movementUp[i]);
    	}
    	up = new Animation(movementUp, duration, false);
    	down = new Animation(movementDown, duration, false);
    	left = new Animation(movementLeft, duration, false);
    	right = new Animation(movementRight, duration, false); 
    	
    	sprite = right;
    }
    
    public void setBlocked(TiledMap Map)
    {
		blocked = new boolean[Map.getWidth()][Map.getHeight()];
		for (int xAxis=0;xAxis<Map.getWidth(); xAxis++)
		{
			for (int yAxis=0; yAxis < Map.getHeight(); yAxis++) {
				int tileID = Map.getTileId(xAxis, yAxis, 0);
				String value = Map.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value)) {
					blocked[xAxis][yAxis] = true;
				}
			}
		}
    }
 
    public void setPosition(Vector2f position) 
    {
    	this.position = position;
    }
 
    public void setSprite(Direction change)
    {
    	switch (change) {
    	case UP:
    		if (direction != Direction.UP) {
    			sprite = up;
    			direction = Direction.UP;
    		}
    		break;
    	case DOWN:
    		if (direction != Direction.DOWN) {
    			sprite = down;
    			direction = Direction.DOWN;
    		}
    		break;
    	case LEFT:
    		if (direction != Direction.LEFT) {
    			sprite = left;
    			direction = Direction.LEFT;
    		}
    		break;
    	case RIGHT:
    		if (direction != Direction.RIGHT) {
    			sprite = right;
    			direction = Direction.RIGHT;
    		}
    		break;
    	default:
    		System.out.println("What's going on?!");
    		break;
    	}
    }
 
    public void update(GameContainer gc, StateBasedGame sb, int delta)
    {
        for(Component component : components)
        {
            component.update(gc, sb, delta);
        }
    }
 
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
    {
        if(renderComponent != null)
            renderComponent.render(gc, sb, gr);
    }
}