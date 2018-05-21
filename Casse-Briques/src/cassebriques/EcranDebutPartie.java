/**
 * 
 */
package cassebriques;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author rxambili
 *
 */
public class EcranDebutPartie {
	
	/** commandes par défaut.*/
	private static int gDefautBas = Input.KEY_LEFT;
	private static int dDefautBas = Input.KEY_RIGHT;
	private static int aDefautBas = Input.KEY_UP;
	private static int gDefautHaut = Input.KEY_A;
	private static int dDefautHaut = Input.KEY_E;
	private static int aDefautHaut = Input.KEY_Z;
	private static int gDefautGauche = Input.KEY_Q;
	private static int dDefautGauche = Input.KEY_W;
	private static int aDefautGauche = Input.KEY_X;
	private static int gDefautDroite = Input.KEY_P;
	private static int dDefautDroite = Input.KEY_M;
	private static int aDefautDroite = Input.KEY_O;
	
	/** l'emplacement de l'image du bouton commencer.*/
	private String fichierCommencer = "ressources/menu/commencer.png";
	/** l'emplacement de l'image du bouton ajouter joueur.*/
	private String fichierAjoutBas = "ressources/menu/ajoutBas.png";
	private String fichierAjoutHaut = "ressources/menu/ajoutHaut.png";
	private String fichierAjoutDroite = "ressources/menu/ajoutDroite.png";
	private String fichierAjoutGauche = "ressources/menu/ajoutGauche.png";

    /** les boutons du début de partie.*/
    private MouseOverArea commencer;
    private MouseOverArea ajouterBas, ajouterHaut, ajouterGauche, ajouterDroite;
    
    private boolean selectionJoueur;
    private EcranSelectionJoueur ecranSelectionJoueur;
    private TextField nomTextField;
    
    private boolean selectionCarte;
    private EcranSelectionCarte ecranSelectionCarte;
    
    /** le jeu.*/
    private Jeu jeu;

	/**
	 * @throws SlickException 
	 * 
	 */
	public EcranDebutPartie(Jeu j) throws SlickException {
    	this.jeu = j;
    	this.nomTextField = new TextField(jeu.getContainer(), jeu.getContainer().getDefaultFont(), 
        		jeu.getX() + (jeu.getLargeur() - 200)/2, 300, 200, 20, jeu);
    	nomTextField.setFocus(true);
    	
		// initialisation boutons debut de partie
        
        Image imageCommencer = new Image(fichierCommencer);
        Image imageAjoutBas = new Image(fichierAjoutBas);
        Image imageAjoutDroite = new Image(fichierAjoutDroite);
        Image imageAjoutGauche = new Image(fichierAjoutGauche);
        Image imageAjoutHaut = new Image(fichierAjoutHaut);
        
        commencer = new MouseOverArea(jeu.getContainer(), imageCommencer,
        		jeu.getX() + (jeu.getLargeur() - imageCommencer.getWidth())/2, jeu.getY() + (jeu.getHauteur() - imageCommencer.getHeight())/2, jeu);
        commencer.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        ajouterBas = new MouseOverArea(jeu.getContainer(), imageAjoutBas,
        		jeu.getX() + (jeu.getLargeur() - imageAjoutBas.getWidth())/2,
        		jeu.getY() + jeu.getHauteur() - imageAjoutBas.getHeight() - 20, jeu);
        ajouterBas.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        ajouterHaut = new MouseOverArea(jeu.getContainer(), imageAjoutHaut,
        		jeu.getX() + (jeu.getLargeur() - imageAjoutHaut.getWidth())/2,
        		jeu.getY() + 20, jeu);
        ajouterHaut.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        ajouterDroite = new MouseOverArea(jeu.getContainer(), imageAjoutDroite,
        		jeu.getX() + jeu.getLargeur() - imageAjoutDroite.getWidth() - 20,
        		jeu.getY() + (jeu.getHauteur() - imageAjoutDroite.getHeight())/2, jeu);
        ajouterDroite.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        ajouterGauche = new MouseOverArea(jeu.getContainer(), imageAjoutGauche,
        		jeu.getX() + 20,
        		jeu.getY() + (jeu.getHauteur() - imageAjoutGauche.getHeight())/2, jeu);
        ajouterGauche.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        // initialisation ecran de selection carte
        ecranSelectionCarte = new EcranSelectionCarte(jeu, this);
        
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if (selectionJoueur) {
			ecranSelectionJoueur.render(container, game, g);
		} else if (selectionCarte) {
			ecranSelectionCarte.render(container, game, g);
		} else {			
			commencer.render(container, g);
			if (!jeu.aJoueur(Rebord.BAS)) {
				ajouterBas.render(container, g);
			}
			if (!jeu.aJoueur(Rebord.HAUT)) {
				ajouterHaut.render(container, g);
			}
			if (!jeu.aJoueur(Rebord.DROITE)) {
				ajouterDroite.render(container, g);
			}
			if (!jeu.aJoueur(Rebord.GAUCHE)) {
				ajouterGauche.render(container, g);
			}
			for (int i=0; i<Jeu.nbJoueurMax; i++) {
				if (jeu.getJoueurs()[i] != null) {
					jeu.getJoueurs()[i].dessiner(g);
				}
			}
		}
	}
	
	public void componentActivated(AbstractComponent source) throws SlickException {
		if (source == commencer) {
        	if (jeu.getNbJoueur() > 0) {
        		selectionCarte = true;
        	}
        }
		if (source == ajouterBas) {
			selectionJoueur = true;
			ecranSelectionJoueur = new EcranSelectionJoueur(jeu, this, Rebord.BAS, gDefautBas,
					aDefautBas, dDefautBas, this.nomTextField);
		}
		if (source == ajouterHaut) {
			selectionJoueur = true;
			ecranSelectionJoueur = new EcranSelectionJoueur(jeu, this, Rebord.HAUT, gDefautHaut,
					aDefautHaut, dDefautHaut, this.nomTextField);
		}
		if (source == ajouterGauche) {
			selectionJoueur = true;
			ecranSelectionJoueur = new EcranSelectionJoueur(jeu, this, Rebord.GAUCHE, gDefautGauche,
					aDefautGauche, dDefautGauche, this.nomTextField);
		}
		if (source == ajouterDroite) {
			selectionJoueur = true;
			ecranSelectionJoueur = new EcranSelectionJoueur(jeu, this, Rebord.DROITE, gDefautDroite,
					aDefautDroite, dDefautDroite, this.nomTextField);
		}
		if (selectionJoueur) {
			ecranSelectionJoueur.componentActivated(source);
		}
		if (selectionCarte) {
			ecranSelectionCarte.componentActivated(source);
		}
	}
	
	public void finirSelectionJoueur() {
		selectionJoueur = false;
		this.nomTextField.setText("");
	}
	
	public void finirSelectionCarte() {
		selectionCarte = false;
	}
	
	public void keyPressed(int key, char c) {
		if (selectionJoueur) {
			ecranSelectionJoueur.keyPressed(key, c);
		}
	}
}
