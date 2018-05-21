/**
 * 
 */
package cassebriques;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * @author rxambili
 *
 */
public class Carte {
	
	/** l'emplacement de l'image de la balle.*/
	private String fichierBrique1 = "ressources/elements/brique1.png";
	private String fichierBrique2 = "ressources/elements/brique2.png";
	private String fichierBrique3 = "ressources/elements/brique3.png";
	private String fichierBrique4 = "ressources/elements/brique4.png";
	private String fichierBrique5 = "ressources/elements/brique5.png";
	//private String fichierBrique6 = "ressources/elements/brique6.png";
	private String fichierBrique7 = "ressources/elements/brique7.png";

	/** le nombre de colonnes. */
	private int nbColonne;
	/** le nombre de lignes. */
	private int nbLigne;
	
	/** le nom de la carte.*/
	private String nom;
	
	/** la grille des briques. */
	private Brique grille[][];
	
	/** le jeu.*/
	private Jeu jeu;
	
	/**
	 * Constructeur de la classe Carte.
	 * @param carteFichier le fichier décrivant le niveau.
	 * @throws SlickException 
	 */
	public Carte(Jeu leJeu, String carteFichier) throws SlickException {

		jeu = leJeu;
		int nbLigneReel = 0;
		int nbColonneReel = 0;
		nbLigne = jeu.getHauteur() / Brique.hauteurDefaut;
		nbColonne = jeu.getLargeur() / Brique.largeurDefaut;
		grille = new Brique[nbColonne][nbLigne];
		
		// création de l'animation d'explosion.
		Animation animExplosion = new Animation();
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo1.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo2.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo3.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo4.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo5.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo6.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo7.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo8.png"), 75);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo9.png"), 50);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo10.png"), 20);
		animExplosion.setAutoUpdate(true);
		animExplosion.setLooping(false);
		
		
	    FileReader fr;	        

	    try {       

	    	//Création de l'objet de lecture
	    	
	    	fr = new FileReader(carteFichier);
	    	String str = "";
	    	int i = 0;
	    	boolean creation = false;
	    	int ind_lig = 0;
	    	int ind_col = 0;

	    	//Lecture des données
	    	while((i = fr.read()) != -1) {
	    		
	    		if ((char) i == '/') {
	    			if (nom == null) {
	    				nom = str;
	    				str = "";
	    			} else if (nbLigneReel == 0) {
	    				nbLigneReel = Integer.parseInt(str);
	    				str = "";
	    			} else if (nbColonneReel == 0) {
	    				nbColonneReel = Integer.parseInt(str);
	    				str = "";
	    			}
	    		} else if ((char) i == '$') {
	    			creation = true;
	    			ind_col = (nbColonne - nbColonneReel) / 2;
    				ind_lig = (nbLigne - nbLigneReel) / 2 - 1;
	    			str = "";
	    		} else {
	    			if (!creation) {
	    				str += (char)i;
	    			} else {
	    				switch ((char) i) {
	    					case '\n' : 
	    						ind_lig++;
	    						ind_col = (nbColonne - nbColonneReel) / 2;
	    						break;
	    					case '1' :
	    						grille[ind_col][ind_lig] = new Brique(jeu, new Image(fichierBrique1), ind_col, ind_lig, 1);
	    						ind_col++;
	    						break;
	    					case '2' :
	    						grille[ind_col][ind_lig] = new Brique(jeu, new Image(fichierBrique5), ind_col, ind_lig, 2);
	    						ind_col++;
	    						break;
	    					case 'x' :
	    						grille[ind_col][ind_lig] = new BriqueExplosive(jeu, new Image(fichierBrique4), ind_col, ind_lig, 1, animExplosion);
	    						ind_col++;
	    						break;
	    					case 'b' :
	    						grille[ind_col][ind_lig] = new BriqueBalle(jeu, new Image(fichierBrique7), ind_col, ind_lig, 1);
	    						ind_col++;
	    						break;
	    					case 'i' :
	    						grille[ind_col][ind_lig] = new BriqueIncassable(jeu, new Image(fichierBrique3), ind_col, ind_lig, 1);
	    						ind_col++;
	    						break;
	    					default :
	    						ind_col++;
	    						break;
	    				}
	    			}	    			
	    		}	    			
	    	}
	    	
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    	
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * Constructeur de la classe Carte. Creer une carte aléatoire.
	 * @param leJeu
	 * @throws SlickException 
	 */
	public Carte(Jeu leJeu) throws SlickException {
		//pBrique = 1 - Somme des proportions qui suivent
		final double pBriqueExplosive = 0.2;
		final double pBriqueBalle = 0.2;
		final double pBriqueIncassable = 0.05;
		
		// création de l'animation d'explosion.
		Animation animExplosion = new Animation();
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo1.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo2.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo3.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo4.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo5.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo6.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo7.png"), 100);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo8.png"), 75);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo9.png"), 50);
		animExplosion.addFrame(new Image("ressources/animations/bubble_explo10.png"), 20);
		animExplosion.setAutoUpdate(true);
		animExplosion.setLooping(false);
		
		jeu = leJeu;
		nbLigne = jeu.getHauteur() / Brique.hauteurDefaut;
		nbColonne = jeu.getLargeur() / Brique.largeurDefaut;
		grille = new Brique[nbColonne][nbLigne];
		
		for (int i=nbColonne / 4; i < nbColonne * 3/4; i++) { 
			for (int j=nbLigne / 4; j < nbLigne * 3/4; j++) {
				double random = Math.random();
				if (random <=  pBriqueIncassable) {
					grille[i][j] = new BriqueIncassable(jeu, new Image(fichierBrique3), i, j, 1);
				} else if (random >pBriqueIncassable && random  <= pBriqueIncassable + pBriqueBalle ){
					grille[i][j] = new BriqueBalle(jeu, new Image(fichierBrique2), i, j, 1);
				} else if (random > pBriqueIncassable + pBriqueBalle && random <= pBriqueIncassable + pBriqueBalle + pBriqueExplosive) {
					grille[i][j] = new BriqueExplosive(jeu, new Image(fichierBrique4), i, j, 1, animExplosion);
				} else {
					grille[i][j] = new Brique(jeu, new Image(fichierBrique1), i, j, 1);
				}
			} 
		}
	}

	/**
	 * retourne la brique aux coordonnées x,y
	 * @param l ligne
	 * @param c colonne
	 * @return la brique
	 */
	public Brique getBrique(int l, int c) {
		return grille[l][c];	
	}

	/**
	 * retourne le nombre de briques restantes.
	 * @return nombre de briques
	 */
	public int getNbBriques() {
		int nbBriques = 0;
		for (int i=0; i<nbColonne; i++) {
			for (int j=0; j<nbLigne; j++) {
				if (grille[i][j] != null) {
					if (grille[i][j].estVivant()) {
						nbBriques++;
					}
				}
			}
		}
		return nbBriques;				
	}
	
	
	/**
	 * Affichage de chaque brique.
	 */
	public void dessiner(Graphics g) { 
		for (int i = 0; i < nbColonne; i++) {
			for (int j = 0; j < nbLigne; j++) {
				if (grille[i][j] != null) {
					grille[i][j].dessiner(g);
				}
			}
		}
	}
	
	/**
	 * indique si il y a une brique aux coordonnées x,y.
	 * @param x abscisse
	 * @param y ordonnée
	 * @return true si il y a une brique, false sinon
	 */
	public boolean briqueTouche(int x, int y) {
		return this.getBrique(x, y).estTouche(x, y);
		
	}

	/**
	 * retourne le nombre de colonne.
	 * @return nombre de colonne
	 */
	public int getNbColonne() {
		return nbColonne;
	}
	
	/**
	 * retourne le nombre de lignes.
	 * @return nombre de lignes
	 */
	public int getNbLigne() {
		return nbLigne;
	}

	/**
	 * retourne ensemble des briques voisines
	 * @param i colonne de la brique.
	 * @param j ligne de la brique.
	 */
	public ArrayList<Brique> getEntourage(int i,int j) {
		ArrayList<Brique> listeBriques = new ArrayList<Brique>();
		if (grille[i][j+1] != null && grille[i][j+1].estVivant()) {
			listeBriques.add(grille[i][j+1]);
		}
		if (grille[i+1][j+1] != null && grille[i+1][j+1].estVivant()) {
			listeBriques.add(grille[i+1][j+1]);
		}
		if (grille[i-1][j+1] != null && grille[i-1][j+1].estVivant()) {
			listeBriques.add(grille[i-1][j+1]);
		}
		if (grille[i][j-1] != null && grille[i][j-1].estVivant()) {
			listeBriques.add(grille[i][j-1]);
		}
		if (grille[i+1][j-1] != null && grille[i+1][j-1].estVivant()) {
			listeBriques.add(grille[i+1][j-1]);
		}
		if (grille[i-1][j-1] != null && grille[i-1][j-1].estVivant()) {
			listeBriques.add(grille[i-1][j-1]);
		}
		if (grille[i-1][j] != null && grille[i-1][j].estVivant()) {
			listeBriques.add(grille[i-1][j]);
		}
		if (grille[i+1][j] != null && grille[i+1][j].estVivant()) {
			listeBriques.add(grille[i+1][j]);
		}		
		return listeBriques;
	}


	
}
