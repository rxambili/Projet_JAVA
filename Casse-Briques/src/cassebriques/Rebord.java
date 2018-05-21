/**
 * 
 */
package cassebriques;

/**
 * @author rxambili
 *
 */
public enum Rebord {


	BAS("ressources/elements/raquetteVerte.png", 108, 10, 13, 19,"Vert"),
	HAUT("ressources/elements/raquetteViolette.png", 108, 10, 17, 17, "Violet"),
	GAUCHE("ressources/elements/raquetteRouge.png", 10, 108, 18, 12, "Rouge"),
	DROITE("ressources/elements/raquetteBleue.png", 10, 108, 20, 10, "Bleu");
	
	private String fichierRaquette = "";
	private int largeur;
	private int hauteur;
	private int decXdess;
	private int decYdess;
	private String couleur = "";
	
	Rebord(String fichier, int l, int h, int decX, int decY, String couleur) {
		this.fichierRaquette = fichier;
		this.largeur = l;
		this.hauteur = h;
		this.decXdess = decX;
		this.decYdess = decY;
		this.couleur = couleur;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getDecXdess() {
		return decXdess;
	}
	
	public int getDecYdess() {
		return decYdess;
	}
	
	public String getFichierRaquette() {
		return fichierRaquette;
	}
	
	public String toString() {
		return couleur;
	}
	
}
