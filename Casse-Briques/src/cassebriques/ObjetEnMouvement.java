package cassebriques;

import org.newdawn.slick.Image;

/** Classe abstraite dont les objets qui se déplace tout seuls héritent.
 * Ex : balle.
 */

/**
 * @author rxambili
 *
 */
public abstract class ObjetEnMouvement extends ObjetGraphique {

	/** Deplacement initial.*/
	protected int dX0, dY0;
	/** Deplacement courant. */
	protected int dX, dY;
	/** la vitesse de déplacement (v² = dx² + dy²). */
	protected double vitesse;
	
	
	/** Constructeur de la classe ObjetGraphique.
	 * @param j le jeu
	 * @param i image de l'objet
	 * @param l largeur
	 * @param h hauteur
	 * @param x position en X
	 * @param y position en Y
	 * @param dx déplacement en X
	 * @param dy déplacement en Y
	 */
	public ObjetEnMouvement(Jeu j, Image i, int l, int h, int x, int y, int dx, int dy) {
		super(j, i, l, h, x, y);
		dX = dx;
		dY = dy;
		dX0 = dx;
		dY0 = dy;
		vitesse = Math.sqrt(dX^2 + dY^2);
	}
	
	/** Constructeur de la classe ObjetGraphique.
	 * @param j le jeu
	 * @param i image de l'objet
	 * @param l largeur
	 * @param h hauteur
	 * @param x position en X
	 * @param y position en Y
	 * @param dx déplacement en X
	 * @param dy déplacement en Y
	 * @param decXdess décalage de la position de dessin en X
	 * @param decYdess decalage la position de dessin en Y
	 */
	public ObjetEnMouvement(Jeu j, Image i, int l, int h, int x, int y, int dx, int dy, int decXdess, int decYdess) {
		this(j, i, l, h, x, y, dx, dy);
		this.decXdess = decXdess;
		this.decYdess = decYdess;		
	}

	/**
	 * Lorsque l'objet meurt...
	 */
	public void tuer() {
		dX = 0;
		dY = 0;
		vitesse = 0;
		super.tuer();
	}
	
	
	/**
	 * Renvoie le deplacement en X
	 * @return int
	 */
	public int getDX() { 
		return dX;
	}
	
	
	/**
	 * Renvoie le deplacement en Y
	 * @return int
	 */
	public int getDY() { 
		return dY;
	}
	
	/**
	 * Renvoie la vitesse de deplacement
	 * @return double
	 */
	public double getVitesse() { 
		return vitesse;
	}
	
	
	/**
	 * Change le sens du deplacement en X
	 */
	public void inverserDX() { 
		dX = -dX;
	}
	
	
	/**
	 * Change le sens du deplacement en X et en Y
	 */
	public void inverserDXDY() { 
		inverserDX();
		inverserDY();
	}
	
	
	/**
	 * Change le sens du deplacement en Y.
	 */
	public void inverserDY() { 
		dY = -dY;
	}
	
	
	/**
	 * Deplace l'objet.
	 */
	public void update() {
	
	}
	
	
	/**
	 * Reinitialisation...
	 */
	public void reset() {
		dX = 0;
		dY = 0;
		vitesse = 0;
		super.reset();
	}
	
	
	
	/**
	 * Changement du deplacement en X
	 *
	 * @param dx nouveau deplacement en X
	 */
	protected void setDX(int dx) { 
		dX = dx;
		vitesse = Math.sqrt(dX*dX + dY*dY);
	}
	
	
	/**
	 * Changement du deplacement en X et en Y
	 *
	 * @param dx nouveau deplacement en X
	 * @param dy nouveau deplacement en Y
	 */
	public void setDXDY(int dx, int dy) { 
		setDX(dx);
		setDY(dy);
	}
	
	
	/**
	 * Changement du deplacement en Y
	 *
	 * @param dx nouveau deplacement en Y
	 */	
	protected void setDY(int dy) { 
		dY = dy;
		vitesse = Math.sqrt(dX*dX + dY*dY);
	}
	
	
	/**
	 * Changement de la position en X
	 *
	 * @param x nouvelle position en X
	 */	
	public void setX(int x) {
		if (x > maxX) { 
			if (jeu.aJoueur(Rebord.DROITE)) {
				this.tuer(); // l'objet sort de l'ecran vers la droite
			} else {
				X = maxX; 
				inverserDX();
			}
		} 
		else if (x < minX) {
			if (jeu.aJoueur(Rebord.GAUCHE)) {
				this.tuer(); // l'objet sort de l'ecran vers la gauche
			} else {
				X = minX; 
				inverserDX(); 
			}
		} else { 
			X = x;
		}
	}
	
	
	/**
	 * Changement de la position en Y
	 *
	 * @param y nouvelle position en Y
	 */	
	public void setY(int y) {
		if (y > maxY) {
			if (jeu.aJoueur(Rebord.BAS)) {
				this.tuer(); // l'objet sort de l'ecran vers le bas
			} else {
				Y = maxY;
				inverserDY();
			}
		} else if (y < minY) { 
			if (jeu.aJoueur(Rebord.HAUT)) {
				this.tuer(); // l'objet sort de l'ecran vers le haut
			} else {
				Y = minY;
				inverserDY();
			} 
		} else {
			Y = y;
		}
	}
	
	/**
	 * Changement de la position en X et Y
	 *
	 * @param x nouvelle position en X
	 * @param y nouvelle position en Y
	 */	
	public void setXY(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Changement de la vitesse en gardant la meme direction
	 *
	 * @param v nouvelle vitesse
	 */	
	public void setVitesse(int v) {
		dX = (int) (v * dX / vitesse);
		dY = (int) (v * dY / vitesse);
		vitesse = v;
	}

}

	

