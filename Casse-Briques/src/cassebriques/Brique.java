package cassebriques;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/** La brique est un objet statique.
 * Elle possède une certaine résistance.
 */

/**
 * @author rxambili
 *
 */
public class Brique extends ObjetGraphique {

	/** valeur par defaut.*/
	final static int largeurDefaut = 30;
	final static int hauteurDefaut = 30;
	
	/** l'emplacement du fichier son.
	 * Son du rebond entre une balle et la raquette */
	private String fichierSon = "ressources/sounds/qubodup-crash.ogg";

	
	/** Nombre de fois qu'une brique peut etre heurté par une balle.*/
	protected int resistance;
	
	/** le son du rebond entre la balle et la raquette. */
	private Sound son;
	
	/** la ligne */
	protected int ligne;
	
	/** la colonne  */
	protected int colonne;
	
	
	/**
	 * Constructeur de la classe Brique
	 * @param j le jeu
	 * @param i image de la brique
	 * @param col colonne dans laquelle se trouve le bloc
	 * @param lig ligne dans laquelle se trouve le bloc
	 * @param r resistance de la brique
	 * @throws SlickException 
	 */
	public Brique(Jeu j, Image i, int col, int lig, int r) throws SlickException {
		super(j, i, largeurDefaut, hauteurDefaut,j.getX() + col * largeurDefaut,
				j.getY() + lig * hauteurDefaut);
		this.resistance = r;
		this.ligne = lig;
		this.colonne = col;
		son = new Sound(fichierSon);
	}
	
	
	/**
	 * Fait mourir le bloc ou baisse sa resistance
	 */
	public void tuer() {
		if (resistance == 1) {
			super.tuer();
		}
		resistance--;		
	}
	
	/**
	 * Fait mourir le bloc directement
	 */
	public void tuerDirect() {
		resistance = 1; 
		this.tuer();		

	}
	
	public int getLigne() {
		return ligne;
	}
	
	public int getColonne() {
		return colonne;
	}
	
	
	/**
	 * Joue le son du rebond.
	 */
	public void jouerSon() {
		this.son.play();
	}
	
}
