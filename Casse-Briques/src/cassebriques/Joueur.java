package cassebriques;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Le Joueur possède des vies, une raquette, ... 
 * Le joueur gère ses états (nombre de vies, ...)
 */

/**
 * @author rxambili
 *
 */
public class Joueur {

	/** la raquette du joueur.*/
	private Raquette raquette;
	
	/** Le nom du joueur.*/
	private String nom;
	
	/** indique si le joueur a perdu.*/
	private boolean perdu;

	
	/** Commandes.*/
	private int toucheGauche;
	private int toucheDroite;
	private int toucheAction;
	
	/** Le score du joueur */
	private Etat etat;
	
	/** indique si le joueur joue avec le clavier.*/
	private boolean clavier;
	
	
	/**
	 * Constructeur de la classe Joueur
	 * @param j le jeu
	 * @param nb les vies
	 * @param r la rebord
	 * @param leNom le nom du joueur
	 * @param gauche la touche gauche
	 * @param droite la touche droite
	 * @param action la touche action
	 * @throws SlickException 
	 */
	public Joueur(Jeu j, int nb, Rebord r, String leNom, int gauche, int droite, int action) throws SlickException {
		Image imageRaquette = new Image(r.getFichierRaquette());
		raquette = new Raquette(j, imageRaquette, r);
		nom = leNom;
		clavier = true;
		perdu = false;
		toucheGauche = gauche;
		toucheDroite = droite;
		toucheAction = action;
		if (r.equals(Rebord.BAS)) {
			etat = new Etat(1,50, nb);
		} else if (r.equals(Rebord.HAUT)) {
			etat = new Etat(1,100, nb);
		} else if (r.equals(Rebord.GAUCHE)) {
			etat = new Etat(1,150, nb);
		} else {
			etat = new Etat(1,200, nb);
		}
	}
	
	/**Afficher du score.
	 * @throws SlickException */
	public void dessiner(Graphics g) throws SlickException { 
		raquette.dessiner(g);
		g.setColor(new Color(255, 255, 255));
		g.drawString(this.nom + " : " + Integer.toString(this.getScore()), this.etat.getposX(), this.etat.getposY());
		g.drawString("Vies restantes : " + this.getVies(), this.etat.getposX(), this.etat.getposY() +15);
	}
	
	public void update(GameContainer container, int delta) {
		if (clavier) {
			this.getRaquette().suivreTouche(delta);
		} else {
			this.getRaquette().suivreSouris(container);
		}
	}
	
	
	/**
	 * retourne la raquette du joueur
	 * @return la raquette
	 */
	public Raquette getRaquette() {
		return raquette;
	}
	
	
	/**
	 * retourne le score du joueur.
	 * @return
	 */
	public int getScore() {
		return etat.getScore();
	}
	
	/**
	 * retourne le nombre de vies du joueur
	 * @return le nombre de vies
	 */
	public int getVies() {
		return etat.getVies();
	}
	
	/**
	 * retourne le nom du joueur
	 * @return le nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * enlève une vie au joueur
	 */
	public void enleverVie() {
		etat.changerVies(-1);
		if (this.getVies() <= 0) {
			perdre();
		}
	}
	
	
	/**
	 * augmenter le score de valeur
	 * @param valeur
	 */
	public void changerScore(int valeur) {
		etat.changerScore(valeur);
	}
	
	/**
	 * retourne si le joueur a perdu ou non
	 * @return true si le joueur a perdu, false sinon
	 */
	public boolean aPerdu() {
		return perdu;
	}
	
	/**
	 * Fait perdre le joueur
	 */
	public void perdre() {
		perdu = true;
		raquette.tuer();
	}
	
	/**
	 * retourne la touche gauche
	 * @return la touche gauche
	 */
	public int getToucheGauche() {
		return toucheGauche;
	}
	
	/**
	 * retourne la touche droite
	 * @return la touche droite
	 */
	public int getToucheDroite() {
		return toucheDroite;
	}
	
	/**
	 * retourne la touche action
	 * @return la touche action
	 */
	public int getToucheAction() {
		return toucheAction;
	}
	
	/**
	 * retourne le rebord de la raquette.
	 * @return rebord
	 */
	public Rebord getRebord() {
		return raquette.getRebord();
	}
	
	/**
	 * change la touche gauche
	 * @param gauche la touche gauche
	 */
	public void setToucheGauche(int gauche) {
		toucheGauche = gauche;
	}
	
	/**
	 * change la touche droite
	 * @param droite la touche droite
	 */
	public void setToucheDroite(int droite) {
		toucheDroite = droite;
	}
	
	/**
	 * change la touche action
	 * @param action la touche action
	 */
	public void setToucheAction(int action) {
		toucheAction = action;
	}
	
	public void keyPressed(int key, char c) {
		if (this.getToucheGauche() == key) {
			this.getRaquette().setDir(-1);
		} else if (this.getToucheDroite() == key) {
			this.getRaquette().setDir(1);
		} else if (this.getToucheAction() == key) {
			this.getRaquette().lancerBalle();
		}
	}
	
	public void keyReleased(int key, char c, GameContainer container) {
		if (this.getToucheGauche() == key) {
        	if (!container.getInput().isKeyDown(this.getToucheDroite())) {
      			this.getRaquette().setDir(0);
        	}
		} else if (this.getToucheDroite() == key) {
			if (!container.getInput().isKeyDown(this.getToucheGauche())) {
				this.getRaquette().setDir(0);
			}
		}
	}
}
