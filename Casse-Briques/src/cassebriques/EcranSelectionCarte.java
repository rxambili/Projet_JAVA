package cassebriques;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

public class EcranSelectionCarte {
	/** l'emplacement des boutons du choix de carte.*/
	private String fichierFlecheG = "ressources/menu/boutonGauche.png";
	private String fichierFlecheD = "ressources/menu/boutonDroite.png";
	private String fichierRetour = "ressources/menu/retour.png";
	private String fichierJouer = "ressources/menu/boutonJouer.png";
	
	/** emplacements des fichiers de cartes.*/
	private String fichierCarte1 = "level1.dat";
	private String fichierCarte2 = "level2.dat";
	
	/** selection de la carte.*/
	private static final int nbCartes = 2;
	private int num;
	private Carte carteSelect;
	
	/** boutons de choix de carte.*/
	private MouseOverArea flecheG, flecheD, jouer, retour;
	
	/**le jeu.*/
	private Jeu jeu;
	
	/** l'ecran de début de partie.*/
    private EcranDebutPartie ecran;

	public EcranSelectionCarte(Jeu j, EcranDebutPartie deb) throws SlickException {
		this.jeu = j;
		this.num = 1;
		this.ecran = deb;
		carteSelect = new Carte(jeu,fichierCarte1);
		
		// initialisation boutons de choix de carte
        
        Image imageJouer = new Image(fichierJouer);
        Image imageFlecheG = new Image(fichierFlecheG);
        Image imageFlecheD = new Image(fichierFlecheD);
        Image imageRetour = new Image(fichierRetour);
               
        jouer = new MouseOverArea(jeu.getContainer(), imageJouer,
        		jeu.getX() + jeu.getLargeur()*1/3 - imageJouer.getWidth()/2,
        		jeu.getY() + jeu.getHauteur() - imageJouer.getHeight() - 20, jeu);
        jouer.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        flecheG = new MouseOverArea(jeu.getContainer(), imageFlecheG,
        		jeu.getX() + 20,
        		jeu.getY() + (jeu.getHauteur() - imageFlecheG.getHeight())/2, jeu);
        flecheG.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        flecheD = new MouseOverArea(jeu.getContainer(), imageFlecheD,
        		jeu.getX() + jeu.getLargeur() - imageFlecheD.getWidth() - 20,
        		jeu.getY() + (jeu.getHauteur() - imageFlecheD.getHeight())/2, jeu);
        flecheD.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
        
        retour = new MouseOverArea(jeu.getContainer(), imageRetour,
        		jeu.getX() + jeu.getLargeur()*2/3 - imageRetour.getWidth()/2,
        		jeu.getY() + jeu.getHauteur() - imageRetour.getHeight() - 20, jeu);
        retour.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if (num >= 0) {
			carteSelect.dessiner(g);
		}
		jouer.render(container, g);
		flecheG.render(container, g);
		flecheD.render(container, g);
		retour.render(container, g);
		
	}
	
	public void componentActivated(AbstractComponent source) throws SlickException {
		if (source == jouer) {
			if (carteSelect != null) {
				jeu.setCarte(carteSelect);
				jeu.commencer();
				for (int i=0; i<Jeu.nbJoueurMax; i++) {
            		if (jeu.getJoueurs()[i] != null) {
            			try {
							jeu.ajouterBalle(jeu.getJoueurs()[i]);
						} catch (SlickException e) {
							e.printStackTrace();
						}
            		}
        		}
				this.reset();
				ecran.finirSelectionCarte();
			}
		}
		if (source == flecheD) {
			num = (num + 1)%(nbCartes + 1);
			creerCarte();
		}
		if (source == flecheG) {
			num = Math.abs((num - 1))%(nbCartes + 1);
			creerCarte();
		}
		if (source == retour) {
			ecran.finirSelectionCarte();
		}
	}
	
	private void creerCarte() throws SlickException {
		switch(num) {
		case 0 : 
			carteSelect = new Carte(jeu);
			break;
		case 1 :
			carteSelect = new Carte(jeu,fichierCarte1);
			break;
		case 2 :
			carteSelect = new Carte(jeu,fichierCarte2);
		default :
			break;
		}
	}
	
	private void reset() throws SlickException {
		num = 1;
		creerCarte();
	}
}
