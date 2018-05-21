package cassebriques;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

/** Classe abstraite dont tout les objets à afficher héritent. */

/**
 * @author rxambili
 *
 */
public abstract class ObjetGraphique {
	
	/** Dimensions de l'objet. */
	protected int largeur, hauteur;

	/** Position initiale de l'objet. 
	* Les positions sont les coordonées du premier point dans le sens positif de x et de Y */
	protected int X0, Y0;
	/** Position courante. */
	protected int X, Y;
	/** Le décalage de la position de dessin.*/
	protected int decXdess, decYdess;

	/** Limites de position. */
	protected int minX, maxX;
	protected int minY, maxY;

	/** true si l'objet est toujours en vie. */
	protected boolean vivant;

	/** Image de l'objet. */
	protected Image image;
	
	/** le jeu.*/
	protected Jeu jeu;

	/** Constructeur de la classe ObjetGraphique.
	 * @param j le jeu
	 * @param i image de l'objet
	 * @param l largeur
	 * @param h hauteur
	 * @param x position en X
	 * @param y position en Y
	 */
	public ObjetGraphique(Jeu j, Image i, int l, int h, int x, int y) {

		// Initialisation de tous les parametres
		jeu = j;
		image = i; 
		largeur = l; 
		hauteur = h;
		X0 = x; 
		Y0 = y;
		X = x; 
		Y = y;
		decXdess = 0;
		decYdess = 0;
		minX = jeu.getX(); 
		maxX = jeu.getX() + jeu.getLargeur() - largeur;
		minY = jeu.getY(); 
		maxY = jeu.getY() + jeu.getHauteur() - hauteur;
		vivant = true;
	}
	
	/** Constructeur de la classe ObjetGraphique.
	 * @param j le jeu
	 * @param i image de l'objet
	 * @param l largeur
	 * @param h hauteur
	 * @param x position en X
	 * @param y position en Y
	 * @param decXdess décalage de la position de dessin en X
	 * @param decYdess decalage la position de dessin en Y
	 */
	public ObjetGraphique(Jeu j, Image i, int l, int h, int x, int y, int decXdess, int decYdess) {
		this(j, i, l, h, x, y);
		this.decXdess = decXdess;
		this.decYdess = decYdess;
	}
		
	/**
	 * Lorsque l'objet meurt.
	 */
	public void tuer() { 
		vivant = false;
	}
	
	
	/**
	 * Affichage de l'objet si il est vivant
	 * @param g Graphics
	 */
	public void dessiner(Graphics g) { 
		if (vivant) {
			g.drawImage(image, X - decXdess, Y - decYdess);
		}
	}
		/**
	 * Renvoie la hauteur de l'objet
	 * @return int
	 */
	public int getHauteur() {
		return hauteur;
		
	}
	/**
	 * Renvoie la largeur de l'objet
	 * @return int
	 */
	public int getLargeur() {
		return largeur;
		
	}
	/**
	 * Renvoie la position en X de l'objet
	 * @return int
	 */
	public int getX() { 
		 return X;
	}
	/**
	 * Renvoie la position en Y de l'objet
	 * @return int
	 */
	public int getY() { 
		 return Y;
	}
	/**
	 * Renvoie l'etat de l'objet (vivant ou mort)
	 * @return boolean true si vivant, false si mort
	 */
	public boolean estVivant() {
		return vivant;
	}
	
	
	/**
	 * Permet de detecter les collisions de l'objet
	 * @return boolean (true si collision)
	 *
	 * @param x position en x
	 * @param y position en y
	 */
	public boolean estTouche(int x, int y) { 
		return (this.vivant) && (x > this.X) && (x < this.X + largeur)
		&& (y > this.Y) && (y < this.Y + hauteur);

	}

	/**
	 * Permet de detecter les collisions de l'objet
	 * @return boolean (true si collision)
	 *
	 * @param o objet graphique
	 */
	public boolean estTouche(ObjetGraphique o) { 
		return this.estTouche(o.getX(), o.getY())|| this.estTouche(o.getX() + o.getLargeur(), o.getY())
				|| this.estTouche(o.getX() + o.getLargeur(), o.getY() + o.getHauteur())
				|| this.estTouche(o.getX(), o.getY() + o.getHauteur());
	}
	
	
	/**
	 * On remet les coordonnees courantes aux valeurs initiales.
	 */
	public void reset() { 
		this.X = this.X0;
		this.Y = this.Y0;
	}
	/**
	 * On change la coordonnee en X
	 * (dans la limite du deplacement autorisee (par les min et les max))
	 *
	 * @param x nouvelle position en X
	 */
	 
	public void setX(int x) {
		if (x > maxX) {
			this.X = maxX;
		} else if (x < minX) {
			this.X = minX;
		} else {
			this.X = x;
		}
	}

	/**
	 * On change la coordonnee en Y
	 * (dans la limite du deplacement autorisee (par les min et les max))
	 *
	 * @param y nouvelle position en Y
	 */
		
	public void setY(int y) {
		if (y > maxY) {
			this.Y = maxY;
		} else if (y < minY) {
			this.Y = minY;
		} else {
			this.Y = y;
		}
	}
}


	
	
