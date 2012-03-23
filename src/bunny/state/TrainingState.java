package bunny.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import bunny.component.attack.EnemyAttack;
import bunny.component.attack.HeroAttack;
import bunny.component.movement.ArrowKeyMovement;
import bunny.component.render.RenderComponent;
import bunny.entity.Entity;
import bunny.entity.characters.Bunny;
import bunny.entity.characters.Wolf;
import bunny.game.GamePlayState;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import bunny.game.BunnyGame;


public class TrainingState extends BasicGameState
{
	public static final int ID = 2; 


	private GamePlayState currentState;
	private BunnyGame game; 
	private TiledMap trainingMap; 
	private float bx = 75f, by = 450f; // used for bunny's initial position;
	protected Bunny bunny;
	private float wx = 525f, wy = 150f; // used for wolf's initial position;
	private Wolf wolf;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		trainingMap = new TiledMap("data/training_room.tmx");  // sets the homeMap from file.
    	this.game = (BunnyGame) game; 
    	String up = "data/rabbit_back.bmp"; 			// only gets the file name strings instead of creating image
    	String down = "data/rabbit_forward.bmp";
    	String side = "data/rabbit_side.bmp";
    	Color Transparent = (new Image(up)).getColor(0, 0);   // this gets the color from the top-left pixel so we know which color to make transparent
    	bunny = new Bunny("bunny"); // create our bunny object

    	bunny.setImages(up, down, side, Transparent); // inside of setImages is where the actual image loading happens and we pass the color
    	bunny.setBlocked(trainingMap);
    	bunny.setPosition(new Vector2f(bx,by));
    	bunny.AddComponent(new ArrowKeyMovement("BunnyControl")); // add movement
    	bunny.AddComponent(new RenderComponent("BunnyRender")); // add render (almost like a toString, but not)
    	bunny.AddComponent(new HeroAttack("BunnyAttack")); // add attack
    	
//    	up = "";
//    	down = "";
//    	side = "";
//    	Transparent = (new Image(up)).getColor(0,0);
    	wolf = new Wolf("wolf1");
    	wolf.setImages(up, down, side, Transparent);
    	wolf.setBlocked(trainingMap);
    	wolf.setPosition(new Vector2f(wx, wy));
    	wolf.AddComponent(new RenderComponent("WolfRender"));
    	wolf.AddComponent(new EnemyAttack("WolfAttack"));

    	if(this.game.getLastStateId() == HomeState.ID){
    		this.bx = 30; 
    		this.by = 288;
    	}
    	bunny.setPosition(new Vector2f(bx,by)); // where the bunny will first appear on screen

    	this.game.setLastStateId(this.ID);

	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		trainingMap.render(0,0); // homeMap is rendered first so it stays in the background
    	bunny.render(container, null, g); // bunny is second so it stays on top of homeMap

	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		bunny.update(container, null, delta);
		wolf.update(container, null, delta);

		if(bunny.getPosition().x < 4) {
			if(bunny.getPosition().y > 228 && bunny.getPosition().y < 296) {
				this.bx = 30; 
	    		this.by = 288;
	    		bunny.setPosition(new Vector2f(bx,by));
				game.enterState(HomeState.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			}
		}
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

