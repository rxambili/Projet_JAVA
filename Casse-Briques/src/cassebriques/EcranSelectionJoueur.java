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
public class EcranSelectionJoueur {
	
	/** l'emplacement des boutons du choix de touche.*/
	private String fichierToucheA = "ressources/menu/boutonAction.png";
	private String fichierToucheD = "ressources/menu/boutonDroite.png";
	private String fichierToucheG = "ressources/menu/boutonGauche.png";
	private String fichierAjouter = "ressources/menu/ajouter.png";
	private String fichierRetour = "ressources/menu/retour.png";
	
	/** les boutons d'ajout de joueur.*/    
    private boolean attendreInput;
    private Rebord rebordSelect;
    private int toucheGselect;
    private int toucheAselect;
    private int toucheDselect;
    private String toucheAttendu;
    private MouseOverArea choisirToucheG, choisirToucheA, choisirToucheD;
    private MouseOverArea ajouter;
    private MouseOverArea retour;
    private TextField nomTextField;
    
    /** le jeu.*/
    private Jeu jeu;
    
    /** l'ecran de début de partie.*/
    private EcranDebutPartie ecran;

	/**
	 * @throws SlickException 
	 * 
	 */
	public EcranSelectionJoueur(Jeu j, EcranDebutPartie debut, Rebord r, int tG, int tA, int tD, TextField text) throws SlickException {
		attendreInput = false;
		this.jeu = j;
		this.ecran = debut;
		this.rebordSelect = r;
		this.toucheGselect = tG;
		this.toucheAselect = tA;
		this.toucheDselect = tD;
		
		// initialisation boutons ajout de joueur
        nomTextField = text;
        
        Image imageToucheA = new Image(fichierToucheA);
        Image imageToucheD = new Image(fichierToucheD);
        Image imageToucheG = new Image(fichierToucheG);
        
        int x = jeu.getX() + jeu.getLargeur()/4 - imageToucheG.getWidth()/2;
        choisirToucheG = new MouseOverArea(jeu.getContainer(), imageToucheG,	x, 400, jeu);
        choisirToucheG.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        x = jeu.getX() + 2*jeu.getLargeur()/4 - imageToucheA.getWidth()/2;
        choisirToucheA = new MouseOverArea(jeu.getContainer(), imageToucheA,	x, 400, jeu);
        choisirToucheA.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        x = jeu.getX() + 3*jeu.getLargeur()/4 - imageToucheD.getWidth()/2;
        choisirToucheD = new MouseOverArea(jeu.getContainer(), imageToucheD,	x, 400, jeu);
        choisirToucheD.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        Image imageAjouter = new Image(fichierAjouter);
        Image imageRetour = new Image(fichierRetour);
        
        ajouter = new MouseOverArea(jeu.getContainer(), imageAjouter
        		, jeu.getX() + jeu.getLargeur()/3 - imageAjouter.getWidth()/2, 600, jeu);
        ajouter.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        retour = new MouseOverArea(jeu.getContainer(), imageRetour
        		, jeu.getX() + 2*jeu.getLargeur()/3 - imageRetour.getWidth()/2, 600, jeu);
        retour.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if (attendreInput) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(jeu.getX(), jeu.getY() + jeu.getHauteur() / 2, jeu.getLargeur(), 20);
			g.setColor(new Color(255, 255, 255));
			g.drawString("Choisir une touche", jeu.getX() + jeu.getLargeur()/2 - 50, jeu.getY() + jeu.getHauteur() / 2 + 2);
		} else {
			g.setColor(new Color(255, 255, 255));
			g.drawString("NOM", nomTextField.getX() - 50, nomTextField.getY());
			nomTextField.render(container, g);
			choisirToucheG.render(container, g);
			g.drawString(Input.getKeyName(toucheGselect), choisirToucheG.getX() + choisirToucheG.getWidth()/2,
					choisirToucheG.getY() + choisirToucheG.getHeight() + 10);
			choisirToucheA.render(container, g);
			g.drawString(Input.getKeyName(toucheAselect), choisirToucheA.getX() + choisirToucheA.getWidth()/2,
					choisirToucheA.getY() + choisirToucheA.getHeight() + 10);
			choisirToucheD.render(container, g);
			g.drawString(Input.getKeyName(toucheDselect), choisirToucheD.getX() + choisirToucheD.getWidth()/2,
					choisirToucheD.getY() + choisirToucheD.getHeight() + 10);
			ajouter.render(container, g);
			retour.render(container, g);
		}
	}
	
	public void componentActivated(AbstractComponent source) {
		if (source == choisirToucheG) {
        	attendreInput = true;
        	toucheAttendu = "gauche";
        }
        if (source == choisirToucheA) {
        	attendreInput = true;
        	toucheAttendu = "action";
        }
        if (source == choisirToucheD) {
        	attendreInput = true;
        	toucheAttendu = "droite";
        }
        if (source == ajouter) {
        	try {
				jeu.ajouterJoueur(new Joueur(jeu, 3, rebordSelect,
							nomTextField.getText(), toucheGselect, toucheDselect, toucheAselect));
			} catch (SlickException e) {
				e.printStackTrace();
			}
        	ecran.finirSelectionJoueur();
        }
        if (source == retour) {
        	ecran.finirSelectionJoueur();
        }
	}
	
	public void keyPressed(int key, char c) {
		if (attendreInput) {
			if (Input.KEY_ESCAPE == key) {
				attendreInput = false;
			} else if (toucheAttendu.equals("gauche")){
				toucheGselect = key;
			} else if (toucheAttendu.equals("action")){
				toucheAselect = key;
			} else if (toucheAttendu.equals("droite")){
				toucheDselect = key;
			}
			attendreInput = false;
		}
	}
}
