package bunny.state;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import bunny.component.movement.ArrowKeyMovement;
import bunny.component.render.RenderComponent;
import bunny.entity.Entity;

public class HomeState extends BasicGameState
{
	// ID for given state 
	public static final int ID = 1; 
	// game holding this state 
	private StateBasedGame game;
	private TiledMap homeMap; 
	private float x = 75f, y = 450f; // used for bunny's initial position
	private Entity bunny;
	
    public int getId()
    {
        return ID; 
    }
 
    @Override // all slick games need an init that sets the initial configurations
    public void init(GameContainer gc, StateBasedGame game) 
			throws SlickException 
	{
    	homeMap = new TiledMap("data/home_room.tmx");  // sets the homeMap from file.
    	this.game = game; 
    	String up = "data/rabbit_back.bmp"; 			// only gets the file name strings instead of creating image
    	String down = "data/rabbit_forward.bmp";
    	String side = "data/rabbit_side.bmp";
    	Color Transparent = (new Image(up)).getColor(0, 0);   // this gets the color from the top-left pixel so we know which color to make transparent
    	
    	bunny = new Entity("bunny"); // create our bunny object
    	bunny.setImages(up, down, side, Transparent); // inside of setImages is where the actual image loading happens and we pass the color
    	bunny.setBlocked(homeMap);
    	bunny.AddComponent(new ArrowKeyMovement("BunnyControl")); // add movement
    	bunny.AddComponent(new RenderComponent("BunnyRender")); // add render (almost like a toString, but not)
    	bunny.setPosition(new Vector2f(x,y)); // where the bunny will first appear on screen
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		homeMap.render(0,0); // homeMap is rendered first so it stays in the background
    	bunny.render(container, null, g); // bunny is second so it stays on top of homeMap
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		bunny.update(container,null,delta);
		System.out.println("x: " + bunny.getPosition().x + ", y: " + bunny.getPosition().y);
		if(bunny.getPosition().x > 655) {
			if(bunny.getPosition().y > 246 && bunny.getPosition().y < 311) {
				game.enterState(TrainingState.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			}
		}
	}
}