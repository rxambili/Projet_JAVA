package cassebriques;


import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/** Classe principale du jeu, elle gère touts les objets et les évènements
 * provoqués par le jeu et le joueur.
 */

/**
 * @author rxambili
 *
 */
public class Jeu extends BasicGameState implements ComponentListener {

	/** l'emplacement de l'image de fond. */
	private String fichierFond = "ressources/backgrounds/fondSpace.png";	
	/** l'emplacement de l'image de la balle.*/
	private String fichierBalle = "ressources/elements/balle.png";
	/** l'emplacement de l'image de pause.*/
	private String fichierPause = "ressources/menu/pause.png";
	/** l'emplacement de l'image du bouton reprendre.*/
	private String fichierReprendre = "ressources/menu/resume.png";
	/** l'emplacement de l'image de retour ecran principal.*/
	private String fichierRetourEcran = "ressources/menu/ecranPrincipal.png";
	/** l'emplacement de l'image du bouton quitter.*/
	private String fichierQuitter = "ressources/menu/quitterJeu.png";

	
	public static final int ID = 2;
	public static final int nbJoueurMax = 4;
	
	
	/** le conteneur du jeu.*/
    private GameContainer container;
    
    /** le jeu de base */
    private StateBasedGame stateGame;
    
    /** Dimensions du jeu. */
    private int hauteur;
    private int largeur;
    
    /** Position de la partie.*/
    private int X;
    private int Y;
    
    /** la carte de jeu.*/
    private Carte carte;
    
    /** le nombre de balle. */
    private int nbBalle;
    
    /** les joueurs. */
    private Joueur[] joueurs;
    
    /** les balles.*/
    private ArrayList<Balle> balles;
    
    /** les images.*/
    private Image fond;
    private Image imageBalle;
    private Image imagePause;
  
    /** les boutons de pause.*/
    private MouseOverArea reprendre;
    private MouseOverArea retourEcran;
    private MouseOverArea quitterJeu;
    
    /** indique si la partie a commencé.*/
    private boolean commence;
    
    /** indique si la partie est fini.*/
    private boolean fin;
    
    /** l'écran de début de partie.*/
    EcranDebutPartie ecranDebut;    
    
    /** l'écran de fin de partie. */
    EcranFinPartie ecranFin;


	public Jeu() {
        super();
    }
	
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	this.container = container;
    	this.stateGame = game;
    	
    	// initialisation fenetre de jeu
    	this.hauteur = container.getHeight() - 30;
    	this.largeur = hauteur;
    	this.X = (container.getWidth() - this.largeur) / 2;
    	this.Y = 0;
    	
    	// initialisation des images
    	this.fond = new Image(fichierFond);
    	this.imageBalle = new Image(fichierBalle);
    	this.imagePause = new Image(fichierPause);
    	
    	// initialisation boutons pause
    	
    	Image imageReprendre = new Image(fichierReprendre);
    	Image imageRetourEcran = new Image(fichierRetourEcran);
		Image imageQuitterJeu = new Image(fichierQuitter);
		
		int x = container.getWidth() / 2 - 200;
		int y = container.getHeight() / 2 - 250 + imagePause.getHeight() + 60;
		
    	reprendre = new MouseOverArea(container, imageReprendre, 
    			x + (400 - imageReprendre.getWidth()) / 2, y, this);
        reprendre.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        y = y + imageReprendre.getHeight() + 30;
        
        retourEcran = new MouseOverArea(container, imageRetourEcran, 
        		x + (400 - imageRetourEcran.getWidth()) / 2, y, this);
        retourEcran.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        y = y + imageRetourEcran.getHeight() + 30;
        
        quitterJeu = new MouseOverArea(container, imageQuitterJeu, 
        		x + (400 - imageQuitterJeu.getWidth()) / 2, y, this);
        quitterJeu.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        // initialisation ecran de debut de partie
        ecranDebut = new EcranDebutPartie(this);
        
        // initialisation ecran de fin de partie
        ecranFin = new EcranFinPartie(this);
        
    }
    
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    	
    	commence = false;
    	    	
    	// initialisation des joueurs
    	this.joueurs = new Joueur[nbJoueurMax];
    	
    	// initialisation des balles
    	this.balles = new ArrayList<Balle>();
    	nbBalle = 0;
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(fond, X, Y, X + this.largeur, this.hauteur,	X, Y,
					X + this.largeur, this.hauteur);
		
		if (!commence) {
			ecranDebut.render(container, game, g);				
		} else if (fin) {
			ecranFin.render(container, game, g);
		} else {		
			this.carte.dessiner(g);
			for (int i=0; i<nbJoueurMax; i++) {
				if (joueurs[i] != null) {
					this.joueurs[i].dessiner(g);
				}
			}
		
			for (Balle balle : balles) {
				balle.dessiner(g);
			}
						
			if (container.isPaused()) {
				g.setColor(new Color(0,0,0));
				int x = container.getWidth() / 2 - 200;
				int y = container.getHeight() / 2 - 250;
				g.fillRoundRect(x, y, 400, 450, 20);
				g.drawImage(imagePause, x + (400 - imagePause.getWidth()) / 2, y + 20);
				reprendre.render(container, g);
				retourEcran.render(container, g);
				quitterJeu.render(container, g);
			}
		}
		
    }
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (commence && !fin) {
			for (int i=0; i<nbJoueurMax; i++) {
				if (joueurs[i] != null) {
					if (!joueurs[i].aPerdu()) {
						this.joueurs[i].update(container, delta);
					}
				}
			}
				
			ArrayList<Balle> ballesCopie = new ArrayList<Balle>(balles);
			for (Balle balle : ballesCopie) {
				if (balle != null) {
					balle.update(delta);
				}
			}
			if (carte.getNbBriques() == 0 || this.getNbJoueur() == 0 ) {
				fin = true;
				ecranFin.setClassement();
			}
		}
    }
	
	public void componentActivated(AbstractComponent source) {
		if (source == reprendre) {
            this.container.resume();
        }
        if (source == retourEcran) {
        	this.container.resume();
            stateGame.enterState(EcranPrincipal.ID);
        }
        if (source == quitterJeu) {
        	this.container.exit();
        }
        if (!commence) {
        	try {
				ecranDebut.componentActivated(source);
			} catch (SlickException e) {
				e.printStackTrace();
			}
        }
        if (fin) {
        	try {
				ecranFin.componentActivated(source);
			} catch (SlickException e) {
				e.printStackTrace();
			}
        }
		
	}

	@Override
    public void keyReleased(int key, char c) {
		for (int i=0; i<nbJoueurMax; i++) {
			if (joueurs[i] != null) {
				joueurs[i].keyReleased(key, c, container);
			}
		}
    }
	
	
	@Override
    public void keyPressed(int key, char c) {
		for (int i=0; i<nbJoueurMax; i++) {
			if (joueurs[i] != null) {
				joueurs[i].keyPressed(key, c);
			}
		}
		if (Input.KEY_ESCAPE == key) {
			if (!container.isPaused()) {
				container.pause();
			} else {
				container.resume();
			}
		}
		if (!commence) {
			ecranDebut.keyPressed(key, c);
		}
	}
	
	/**
	 * Ajoute une nouvelle balle sur une raquette.
	 * @throws SlickException 
	 */
	public void ajouterBalle(Joueur j) throws SlickException {
		if (!j.aPerdu() && nbBalle < this.getNbJoueur()) {
			if (j.getRaquette().getBalleColle() != null) {
				j.getRaquette().lancerBalle();
			}
			this.balles.add(new Balle(this, imageBalle, j));	
			nbBalle++;
		}
	}

	/**
	 * Ajoute une nouvelle balle.
	 * @throws SlickException 
	 */
	public void ajouterBalle(int x, int y, Joueur j) throws SlickException {
		this.balles.add(new Balle(this, imageBalle, j, x, y));	
		nbBalle++;
	}
	
	/**
	 * commence la partie
	 */
	public void commencer() {
		commence = true;
	}
	
	/**
	 * retourne la balle touchant la brique.
	 * @param b la Brique
	 * @return la balle
	 */
	public Balle getBalleSurBrique(Brique b) {
		Balle res = null;
		for (Balle balle : balles) {
			if (b.estTouche(balle)) {
				res = balle;
			}
		}
		return res;
	}
	
	
	
	/**
	 * retourne la hauteur du jeu
	 * @return la hauteur
	 */
	public int getHauteur() {
		return hauteur;
	}
	
	/**
	 * retourne la largeur du jeu
	 * @return la largeur
	 */
	public int getLargeur() {
		return largeur;
	}
	
	/**
	 * retourne la position en X
	 * @return X
	 */
	public int getX() {
		return X;
	}
	
	/**
	 * retourne la position en Y
	 * @return Y
	 */
	public int getY() {
		return Y;
	}
	
	/**
	 * retourne le nombre de balle en jeu.
	 * @return le nombre de balle.
	 */
	public int getNbBalle() {
		return nbBalle;
	}
	
	/**
	 * retourne les joueurs
	 * @return la liste de joueurs
	 */
	public Joueur[] getJoueurs() {
		return joueurs;
	}
	
	/**
	 * retourne le nombre de joueur.
	 * @return le nombre de joueur
	 */	
	public int getNbJoueur() {
		int nbJoueur = 0;
		for (int i=0; i<nbJoueurMax; i++) {
			if (joueurs[i] != null) {
				if (!joueurs[i].aPerdu()) {
					nbJoueur++;
				}
			}
		}
		return nbJoueur;
	}
	
	
	/**
	 * retire une balle.
	 * @param balle
	 */
	public void retirerBalle(Balle balle) {
		balles.remove(balle);
		nbBalle--;
	}
	
	/**
	 * retourne l'objet graphique touché par la balle
	 * @param b
	 * @return l'objet graphique touché, null si aucun n'est touché
	 */
	public ObjetGraphique getCollision(Balle b) {
		ObjetGraphique objetTouche = null;
		for (int i=0; i<nbJoueurMax; i++) {
			if (this.joueurs[i] != null) {
				if (this.joueurs[i].getRaquette().estTouche(b)) {
					objetTouche = this.joueurs[i].getRaquette();
				}
			}
		} 
		if (objetTouche == null) {
			for (int i = 0; i < carte.getNbColonne(); i++) {
				for (int j = 0; j < carte.getNbLigne(); j++) {
					Brique brique = carte.getBrique(i,j);
					if (brique != null) {
						if (brique.estTouche(b)) {
							objetTouche = carte.getBrique(i,j);
						}
					}
				}
			}
		}
		return objetTouche;
	}
	
	/**
	 * indique si il ya un joueur sur un rebord
	 * @param r
	 * @return vrai si il y a un joueur sur ce rebord, faux sinon
	 */
	public boolean aJoueur(Rebord r) {
		boolean res = false;
		int i = 0;
		while (i<nbJoueurMax && res == false) {
			if (joueurs[i] != null) {
				if (!joueurs[i].aPerdu()) {
					res = joueurs[i].getRebord().equals(r);
				}
			}
			i++;
		}
		return res;
	}
	
	
	/** 
	 * Renvoie le joueur correspondant au rebord.
	 * @param rebord le rebord
	 */
	public Joueur joueurDuRebord(Rebord rebord) {
		Joueur retour;
		if (rebord.equals(Rebord.BAS)) {
			retour = this.joueurs[0];
		}
		else if (rebord.equals(Rebord.HAUT)) {
			retour = this.joueurs[1];
		}
		else if (rebord.equals(Rebord.GAUCHE)) {
			retour = this.joueurs[2];
		}
		else {
			retour = this.joueurs[3];
		}
		return retour;
	}
	
	/**
	 * Ajoute un joueur.
	 * Pre : il n'y a pas deja un joueur sur le meme rebord.
	 */
	public void ajouterJoueur(Joueur j) {
		if (j.getRebord().equals(Rebord.BAS)) {
    		joueurs[0] = j;
    	} else if (j.getRebord().equals(Rebord.HAUT)) {
    		joueurs[1] = j;
    	} else if (j.getRebord().equals(Rebord.GAUCHE)) {
    		joueurs[2] = j;
    	} else if (j.getRebord().equals(Rebord.DROITE)) {
    		joueurs[3] = j;
    	}
	}
	
	/**
	 * retourne le container du jeu
	 * @return le container
	 */
	public GameContainer getContainer() {
		return container;
	}
	
	/**
	 * retourne la carte du jeu
	 * @return carte
	 */
	public Carte getCarte() {
		return carte;
	}
	
	/**
	 * change la carte de jeu
	 * @param c la nouvelle carte
	 */
	public void setCarte(Carte c) {
		this.carte = c;
	}
	
	public StateBasedGame getStateGame() {
		return stateGame;
	}
	
	public void reset() {
		commence = false;
		fin = false;
	}
	

	@Override
	public int getID() {
		return ID;
	}
}
