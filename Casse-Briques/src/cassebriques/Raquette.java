package cassebriques;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/** La raquette peut être déplacé à l'aide du clavier ou de la souris.
 * C'est elle qui determine la direction de la balle lorsqu'il y a
 * contact.
 */

/**
 * @author rxambili
 *
 */
public class Raquette extends ObjetGraphique {
	
	/** l'emplacement du fichier son.
	 * Son du rebond entre une balle et la raquette */
	private String fichierSon = "ressources/sounds/qubodup-crash.ogg";
	
	/** le decalage avec le rebord.*/
	private static int alt = 30;
	
	/** le pas du déplacement de la raquette. */
	private int pas = 500;
	
	/** la direction de la raquette. */
	private int direction;
	
	/** le rebord.*/
	private Rebord rebord;
	
	/** la balle colle à la raquette.*/
	private Balle balleColle;
	
	/** le son du rebond entre la balle et la raquette. */
	private Sound son;
	

	/**
	 * Constructeur de la classe Raquette
	 * @param j le jeu pour l'affichage
	 * @param i image de la raquette
	 * @param r le rebord
	 * @throws SlickException 
	 */
	public Raquette(Jeu j, Image i, Rebord r) throws SlickException {
		super(j, i, r.getLargeur(), r.getHauteur(), 0, 0, r.getDecXdess(), r.getDecYdess());
		this.rebord = r;
		if (r.equals(Rebord.BAS)) {
			this.X = j.getX() + (j.getLargeur() - largeur) / 2;
			this.Y = j.getY() + j.getHauteur() - alt;
		} else if (r.equals(Rebord.HAUT)) {
			this.X = j.getX() + (j.getLargeur() - largeur) / 2;
			this.Y = j.getY() + alt;
		} else if (r.equals(Rebord.GAUCHE)) {
			this.X = j.getX() + alt;
			this.Y = j.getY() + (j.getHauteur() - hauteur) / 2;
		} else {
			this.X = j.getX() + j.getLargeur() - alt;
			this.Y = j.getY() + (j.getHauteur() - hauteur) / 2;
		}
		direction = 0;
		maxX = maxX - alt;
		minX = minX + alt;
		maxY = maxY - alt;
		minY = minY + alt;
		son = new Sound(fichierSon);
		balleColle = null;
	}
	

	@Override
	public void tuer() {
		super.tuer();
		this.lancerBalle();
	}

	/**
	 * Deplace la raquette
	 */
	public void suivreTouche(int delta) {
		if (rebord.equals(Rebord.BAS) || rebord.equals(Rebord.HAUT)) {
			this.setX(X + direction * pas * delta / 1000);
		} else {
			this.setY(Y + direction * pas * delta / 1000);
		}
	}
	
	/**
	 * change la direction de la raquette.
	 * @param dir int
	 */
	public void setDir(int dir) {
			this.direction = dir;

		
	}
	
	/**
	 * Deplace la raquette a la position en X de la souris
	 * @param container le container
	 */
	public void suivreSouris(GameContainer container) {
		if (rebord.equals(Rebord.BAS) || rebord.equals(Rebord.HAUT)) {
			this.setX(container.getInput().getMouseX());
		} else {
			this.setY(container.getInput().getMouseY());
		}	
	}
	
	/**
	 * retourne la balle collée à la raquette
	 * @return la balle collée
	 */
	public Balle getBalleColle() {
		return balleColle;		
	}
	
	/**
	 * change la balle collée à la raquette
	 * @param b la balle collée
	 * @return 
	 */
	public void setBalleColle(Balle b) {
		balleColle = b;		
	}
	
	
	/**
	 * lancer la balle collée
	 */
	public void lancerBalle() {
		if (this.balleColle != null) {
			this.balleColle.lancer();
		}
	}
	
	/**
	 * retourne le rebord
	 * @return rebord
	 */
	public Rebord getRebord() {
		return rebord;
	}
	
	/**
	 * Joue le son du rebond.
	 */
	public void jouerSon() {
		this.son.play();
	}
}
