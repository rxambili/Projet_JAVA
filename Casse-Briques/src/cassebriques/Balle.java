package cassebriques;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** La balle se déplace toute seul une fois qu'elle est lancée.
 * Une balle peut se coller à une raquette.
 */

/**
 * @author rxambili
 *
 */
public class Balle extends ObjetEnMouvement {
	
	/** Valeurs par defaut. */
	final static int largeurDefaut = 20;
	final static int hauteurDefaut = 20;
	final static int decXdefaut = 15;
	final static int decYdefaut = 16;
	final static double vitesseDefaut = 250;
	final static int tempsColleDefaut = 5000;
	
	
	/**indique si la balle est collée à une raquette*/
	private boolean colle;
	
	/** le dernier joueur a avoir touché la balle.*/
	private Joueur dernierJoueur;
	
	/** temps écoulé avec une balle collée (en ms).*/
	private int tempsColle;
	
	/** Constructeur de la classe Balle.
	 * @param j le jeu
	 * @param i image de l'objet
	 * @param leJoueur le dernier joueur à avoir touché la balle
	 */
	public Balle(Jeu j, Image i, Joueur leJoueur) {
		super(j, i, largeurDefaut, hauteurDefaut, 0, 0, 0, 0, decXdefaut, decYdefaut);
		if (leJoueur.getRebord().equals(Rebord.BAS)) {
			X = leJoueur.getRaquette().getX() + (leJoueur.getRaquette().getLargeur() - largeur) / 2 ;
			Y = leJoueur.getRaquette().getY() - hauteur;
			
		} else if (leJoueur.getRebord().equals(Rebord.HAUT)) {
			X = leJoueur.getRaquette().getX() + (leJoueur.getRaquette().getLargeur() - largeur) / 2;
			Y = leJoueur.getRaquette().getY() + leJoueur.getRaquette().getHauteur();
			
		} else if (leJoueur.getRebord().equals(Rebord.GAUCHE)) {
			X = leJoueur.getRaquette().getX() + leJoueur.getRaquette().getLargeur();
			Y = leJoueur.getRaquette().getY() + (leJoueur.getRaquette().getHauteur() - hauteur) / 2;
			
		} else if (leJoueur.getRebord().equals(Rebord.DROITE)) {
			X = leJoueur.getRaquette().getX() - largeur;
			Y = leJoueur.getRaquette().getY() + (leJoueur.getRaquette().getHauteur() - hauteur) / 2;
		} 
		colle = true;
		dernierJoueur = leJoueur;
		leJoueur.getRaquette().setBalleColle(this);
	}
	
	/** Constructeur de la classe Balle.
	 * @param j le jeu
	 * @param i image de l'objet
	 * @param leJoueur le dernier joueur à avoir touché la balle
	 * @param x position en X
	 * @param y position en Y
	 */
	public Balle(Jeu j, Image i, Joueur leJoueur, int x, int y) {
		super(j, i, largeurDefaut, hauteurDefaut, x, y, 0, 0, decXdefaut, decYdefaut);
		this.setDXDYAleatoire();
		colle = false;
		dernierJoueur = leJoueur;
	}
	
	
	@Override
	public void tuer() {
		super.tuer();
		jeu.retirerBalle(this);
	}
	
	/**
	 * Detecte les collisions avec les differents objets
	 * (raquette, blocs, limites de la zone de jeu)
	 * @param delta le temps écoulé entre deux update
	 */
	public void update(int delta) {
		
		if (colle) {
			if (dernierJoueur.getRebord().equals(Rebord.BAS)) {
				int nouveauX = this.dernierJoueur.getRaquette().getX()
						+ (this.dernierJoueur.getRaquette().getLargeur() - largeur) / 2 ;
				int nouveauY = this.dernierJoueur.getRaquette().getY() - hauteur;
				this.setXY(nouveauX, nouveauY);
				
			} else if (dernierJoueur.getRebord().equals(Rebord.HAUT)) {
				int nouveauX = this.dernierJoueur.getRaquette().getX()
						+ (this.dernierJoueur.getRaquette().getLargeur() - largeur) / 2 ;
				int nouveauY = this.dernierJoueur.getRaquette().getY()
						+ this.dernierJoueur.getRaquette().getHauteur();
				this.setXY(nouveauX, nouveauY);
				
			} else if (dernierJoueur.getRebord().equals(Rebord.GAUCHE)) {
				int nouveauX = this.dernierJoueur.getRaquette().getX()
						+ this.dernierJoueur.getRaquette().getLargeur();
				int nouveauY = this.dernierJoueur.getRaquette().getY()
						+ (this.dernierJoueur.getRaquette().getHauteur() - hauteur) / 2;
				this.setXY(nouveauX, nouveauY);
				
			} else if (dernierJoueur.getRebord().equals(Rebord.DROITE)) {
				int nouveauX = this.dernierJoueur.getRaquette().getX() - largeur;
				int nouveauY = this.dernierJoueur.getRaquette().getY()
						+ (this.dernierJoueur.getRaquette().getHauteur() - hauteur) / 2;
				this.setXY(nouveauX, nouveauY);
			}
			this.tempsColle = this.tempsColle + delta;
			if (this.tempsColle >= tempsColleDefaut) {
				this.lancer();
			}
		} else {
			ObjetGraphique objetTouche = jeu.getCollision(this);
			if (objetTouche != null) {
				this.rebondir(objetTouche);
				if(!(objetTouche.estVivant())) {
					objetTouche = null;
				}
			}
			
			int x = X + dX * delta / 1000;
			int y = Y + dY * delta / 1000;
			this.setXY(x, y);
		}
	}
	
	/**
	 * Indique si la balle est lancee ou pas
	 * @return boolean (true si la balle est lanc�e)
	 */
	public boolean estLance() {
		return !colle;
	}
	
	public Joueur getDernierJoueur() {
		return dernierJoueur;
	}
	
	/**
	 * Lance la balle
	 */
	public void lancer() {
		if (colle) {
			this.setColle(false);
			if (this.dernierJoueur.getRebord().equals(Rebord.BAS)) {
				this.setDXAleatoire(-1);
								
			} else if (this.dernierJoueur.getRebord().equals(Rebord.HAUT)) {
				this.setDXAleatoire(1);
								
			} else if (this.dernierJoueur.getRebord().equals(Rebord.GAUCHE)) {
				this.setDYAleatoire(1);
								
			} else if (this.dernierJoueur.getRebord().equals(Rebord.DROITE)) {
				this.setDYAleatoire(-1);
			}
			this.tempsColle = 0;
			this.dernierJoueur.getRaquette().setBalleColle(null);
		}
	}
	
	/**
	 * Permet de coller la balle a la raquette
	 * @param g (true pour coller)
	 */
	public void setColle(boolean c) {
		colle = c;
	}
	
	public void RebondRaquette(Raquette raquette) {
		//System.out.println(this.getVitesse());
		//System.out.println(raquette.getLargeur());
		//System.out.println(raquette.getHauteur());
		int l;
		int newdX;
		int newdY;
		//largeur raquette
		if (raquette.getRebord().equals(Rebord.DROITE) || raquette.getRebord().equals(Rebord.GAUCHE)) {
			l = raquette.getHauteur();
			//x centre raquette
			int Yr = raquette.getY()+l/2;

			//x centre balle
			int Yb = super.getY() + super.getHauteur()/2;
			//incidence avec la tangente � la sph�re
			double beta = Math.acos(Math.sqrt(3)*(Yb-Yr)/l);
			
			newdX = (int) Math.round(this.vitesse*Math.sin(beta));
			newdY = (int) Math.round(this.vitesse*Math.cos(beta));
			if (raquette.getRebord().equals(Rebord.DROITE)) {
				newdX = -newdX;
			}
		}
		else {
			l = raquette.getLargeur();
			//x centre raquette
			int Xr = raquette.getX()+l/2;

			//x centre balle
			int Xb = super.getX() + super.getLargeur()/2;

			//incidence avec la tangente � la sph�re
			double beta = Math.acos(Math.sqrt(3)*(Xb-Xr)/l);
			
			newdX = (int) Math.round(this.vitesse*Math.cos(beta));
			newdY = (int) Math.round(this.vitesse*Math.sin(beta));
			if (raquette.getRebord().equals(Rebord.BAS)) {
				newdY = -newdY;
			}
		} 
	
		
		
		
		setDXDY(newdX, newdY);
		//System.out.println(vitesse);
		//System.out.println(Math.sqrt(3)*(Xb-Xr)/l);
		//System.out.println(beta);
		//System.out.println(this.vitesse*Math.cos(beta));
		//System.out.println(this.vitesse*Math.sin(beta));
		//System.out.println(dX);
		//System.out.println(dY);
		//System.out.println("dedans");
	}
	
	
	/**
	 * fait rebondir la balle
	 * @param o objet graphique touché
	 */
	public void rebondir(ObjetGraphique o) {
		if (o instanceof Brique) {
						
			if (o.estTouche(X + largeur / 2, Y) || o.estTouche(X + largeur / 2, Y + hauteur)) {
				inverserDY();
				o.tuer();
				this.dernierJoueur.changerScore(1);
				((Brique) o).jouerSon();
			} else if (o.estTouche(X + largeur, Y + hauteur / 2) || o.estTouche(X, Y + hauteur / 2)) {
				inverserDX();
				o.tuer();
				this.dernierJoueur.changerScore(1);
				((Brique) o).jouerSon();
			} else if (o.estTouche(X, Y)) {
				if (dX<0 & dY<0) {
					inverserDXDY();
				} else if (dX<0) {
					inverserDX();
				} else {
					inverserDY();
				}
				o.tuer();
				this.dernierJoueur.changerScore(1);
				((Brique) o).jouerSon();
			} else if (o.estTouche(X + largeur, Y)) {
				if (dX>0 & dY<0) {
					inverserDXDY();
				} else if (dY<0) {
					inverserDY();
				} else {
					inverserDX();
				}
				o.tuer();
				this.dernierJoueur.changerScore(1);
				((Brique) o).jouerSon();
			} else if (o.estTouche(X, Y + hauteur)) {
				if (dX<0 & dY>0) {
					inverserDXDY();
				} else if (dX<0) {
					inverserDX();
				} else {
					inverserDY();
				}
				o.tuer();
				this.dernierJoueur.changerScore(1);
				((Brique) o).jouerSon();
			} else if (o.estTouche(X + largeur, Y + hauteur)) {
				if (dX>0 & dY>0) {
					inverserDXDY();
				} else if (dY<0) {
					inverserDX();
				} else {
					inverserDY();
				}
				o.tuer();
				this.dernierJoueur.changerScore(1);
				((Brique) o).jouerSon();
			}
			
			
		}
		if (o instanceof Raquette) {
			if (((Raquette) o).getRebord().equals(Rebord.BAS)) {
				if (dY > 0) {
					this.RebondRaquette((Raquette) o);
					((Raquette) o).jouerSon();
					this.dernierJoueur = jeu.joueurDuRebord(Rebord.BAS);
				}
			} else if (((Raquette) o).getRebord().equals(Rebord.HAUT)) {
				if (dY < 0) {
					inverserDY();
					this.RebondRaquette((Raquette) o);
					this.dernierJoueur = jeu.joueurDuRebord(Rebord.HAUT);
				}
			} else if (((Raquette) o).getRebord().equals(Rebord.GAUCHE)) {
				if (dX < 0) {
					this.RebondRaquette((Raquette) o);
					((Raquette) o).jouerSon();
					this.dernierJoueur = jeu.joueurDuRebord(Rebord.GAUCHE);
				}
			} else if (((Raquette) o).getRebord().equals(Rebord.DROITE)) {
				if (dX > 0) {
					this.RebondRaquette((Raquette) o);
					((Raquette) o).jouerSon();
					this.dernierJoueur = jeu.joueurDuRebord(Rebord.DROITE);
				}
			} 
		}
	}
	
	/**
	 * Changement de la position en X
	 *
	 * @param x nouvelle position en X
	 */	
	@Override
	public void setX(int x) {
		if (x > maxX) { 
			if (jeu.aJoueur(Rebord.DROITE)) {
				Joueur j = jeu.joueurDuRebord(Rebord.DROITE);
				j.enleverVie();
				this.tuer(); // l'objet sort de l'ecran vers la droite
				try {
					jeu.ajouterBalle(j);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			} else {
				X = maxX; 
				inverserDX();
			}
		} 
		else if (x < minX) {
			if (jeu.aJoueur(Rebord.GAUCHE)) {
				Joueur j = jeu.joueurDuRebord(Rebord.GAUCHE);
				j.enleverVie();
				this.tuer(); // l'objet sort de l'ecran vers la gauche
				try {
					jeu.ajouterBalle(j);
				} catch (SlickException e) {
					e.printStackTrace();
				}
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
	@Override
	public void setY(int y) {
		if (y > maxY) {
			if (jeu.aJoueur(Rebord.BAS)) {
				Joueur j = jeu.joueurDuRebord(Rebord.BAS);
				j.enleverVie();
				this.tuer(); // l'objet sort de l'ecran vers le bas
				try {
					jeu.ajouterBalle(j);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			} else {
				Y = maxY;
				inverserDY();
			}
		} else if (y < minY) { 
			if (jeu.aJoueur(Rebord.HAUT)) {
				Joueur j = jeu.joueurDuRebord(Rebord.HAUT);
				j.enleverVie();
				this.tuer(); // l'objet sort de l'ecran vers le haut
				try {
					jeu.ajouterBalle(j);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			} else {
				Y = minY;
				inverserDY();
			} 
		} else {
			Y = y;
		}
	}

	
	/**
	 * Cree un deplacement aleatoire en X d'angle limité
	 * @param sens le sens de deplacement en Y (pre : sens vaut -1 ou 1)
	 */
	public void setDXAleatoire(int sens) {
		this.setDX((int)((vitesseDefaut - 50) * (2 * Math.random() - 1)));
		this.setDY((int) (sens * Math.sqrt(Math.pow(vitesseDefaut,2) - this.dX*this.dX)));
	}
	
	/**
	 * Cree un deplacement aleatoire en Y d'angle limité
	 *  @param sens le sens de deplacement en X (pre : sens vaut -1 ou 1)
	 */
	public void setDYAleatoire(int sens) {
		this.setDY((int)((vitesseDefaut - 50) * (2 * Math.random() - 1)));
		this.setDX((int) (sens * Math.sqrt(Math.pow(vitesseDefaut,2) - this.dY*this.dY)));
	}
	
	/**
	 * Cree un deplacement aleatoire en X et Y
	 */
	public void setDXDYAleatoire() {
		this.setDX((int)(vitesseDefaut * (Math.random() - 1)));
		if (Math.random() < 0.5) {
			this.setDY((int) Math.sqrt(Math.pow(vitesseDefaut,2) - this.dX*this.dX));
		} else {
			this.setDY((int) -Math.sqrt(Math.pow(vitesseDefaut,2) - this.dX*this.dX));
		}
	}
	
}
