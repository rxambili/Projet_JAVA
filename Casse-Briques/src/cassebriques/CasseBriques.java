package cassebriques;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author rxambili
 *
 */
public class CasseBriques extends StateBasedGame {

	/**
	 * Constructeur de la classe CasseBriques.
	 */
	public CasseBriques() {
		super("Casse-Briques");
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new EcranPrincipal());
		addState(new Jeu());
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer appgc = new AppGameContainer(new CasseBriques());
		appgc.setDisplayMode(1280, 720, false);
		appgc.setShowFPS(true);
        appgc.setTargetFrameRate(60);
        appgc.setVSync(true);
        appgc.start();		
	}

}
