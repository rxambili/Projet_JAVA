/**
 * 
 */
package cassebriques;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author rxambili
 *
 */
public class EcranPrincipal extends BasicGameState implements ComponentListener {
	
	public static final int ID = 1;
	
	/** l'emplacement de l'image de fond. */
	private String fichierFond = "ressources/backgrounds/fondSpace.png";
	/** l'emplacement de l'image du titre.*/
	private String fichierTitre = "ressources/menu/title.png";
	/** l'emplacement de l'image du bouton jouer. */
	private String fichierBoutonJouer = "ressources/menu/boutonJouer.png";
	/** l'emplacement de l'image du bouton quitter. */
	private String fichierBoutonQuitter = "ressources/menu/boutonQuitter.png";
	
	/** l'image de fond.*/
	private Image fond;
	/** l'image du titre.*/
	private Image titre;
	
	private MouseOverArea jouer;
    private MouseOverArea quitter;
    private GameContainer container;
    
	private StateBasedGame jeu;


	public EcranPrincipal() {
		super();
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.jeu = game;
		this.fond = new Image(fichierFond);
		this.titre = new Image(fichierTitre);
		this.container = container;
		
		Image imageBoutonJouer = new Image(fichierBoutonJouer);
		Image imageBoutonQuitter = new Image(fichierBoutonQuitter);
		
		jouer = new MouseOverArea(container, imageBoutonJouer,
				(container.getWidth() - imageBoutonJouer.getWidth()) / 2, 300, this);
        jouer.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
		quitter = new MouseOverArea(container, imageBoutonQuitter,
				(container.getWidth() - imageBoutonQuitter.getWidth()) / 2, 500, this);
        quitter.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
         
        

	}

	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		fond.draw(0, 0, container.getWidth(), container.getHeight());
		titre.draw((container.getWidth() - titre.getWidth()) / 2, 100);
		
		 // Affichage des boutons
        quitter.render(container, g);
        jouer.render(container, g);

	}

	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

	}

	
	@Override
	public int getID() {
		return ID;
	}

	public void componentActivated(AbstractComponent source) {
		if (source == quitter)
        {
            this.container.exit();
        }
        if (source == jouer)
        {
            jeu.enterState(Jeu.ID);
        }
		
	}

}
