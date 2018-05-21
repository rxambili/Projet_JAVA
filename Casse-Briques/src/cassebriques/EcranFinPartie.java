/**
 * 
 */
package cassebriques;

import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author rxambili
 *
 */
public class EcranFinPartie {
	
	/** l'emplacement de l'image de retour ecran principal.*/
	private String fichierRetourEcran = "ressources/menu/ecranPrincipal.png";
	
	/** le classement.*/
	private Joueur[] classement;
	
	private MouseOverArea retourEcran;
	
	/** le jeu.*/
	private Jeu jeu;

	/**
	 * @throws SlickException 
	 * 
	 */
	public EcranFinPartie(Jeu j) throws SlickException {
		this.jeu = j;
		this.classement = new Joueur[Jeu.nbJoueurMax];
		
		Image imageRetourEcran = new Image(fichierRetourEcran);
		
		retourEcran = new MouseOverArea(jeu.getContainer(), imageRetourEcran, 
        		jeu.getX() + (jeu.getLargeur() - imageRetourEcran.getWidth())/2,
        		jeu.getY() + jeu.getHauteur() - imageRetourEcran.getHeight() - 50, jeu);
        retourEcran.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		int x = container.getWidth() / 2 - 200;
		int y = container.getHeight() / 2 - 250;
		int num;
		g.setColor(new Color(255,255,255));
		g.drawString("Classement", x + 20, y);
		for (int i=0; i<Jeu.nbJoueurMax; i++) {
			if (classement[i] != null) {
				y = y + 50;
				num = i + 1;
				g.drawString(num + ") " + classement[i].getNom() + " : " + classement[i].getScore(), x, y);
			}
		}
		retourEcran.render(container, g);
	}
	
	public void componentActivated(AbstractComponent source) throws SlickException {
		if (source == retourEcran) {
			jeu.getStateGame().enterState(EcranPrincipal.ID);
			jeu.reset();
		}
		
	}
	
	public void setClassement() {
		this.classement = jeu.getJoueurs();
		Arrays.sort(classement, new JoueurComparator());
	}
}
