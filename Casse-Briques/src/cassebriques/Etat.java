package cassebriques;



public class Etat {
	
	private int score, posX, posY, vies; 
	
	public Etat(int posX, int posY, int nbVies) {
		this.score = 0;
		this.vies = nbVies;
		this.posX= posX;
		this.posY= posY;
	}
	
	
	public int getScore() {
		return this.score;
	}
	
	public int getVies() {
		return this.vies;
	}
	
	public int getposX() {
		return this.posX;
	}
	
	public int getposY() {
		return this.posY;
	}
	
	/**
	 * augmenter le score de valeur
	 * @param valeur
	 */
	public void changerScore(int valeur) {
		if (this.score + valeur <0) {
			this.score = 0;
		}
		else {
			this.score = this.score + valeur;
		}
	}
	
	/**
	 * Changer les vies
	 * @param valeur
	 */
	public void changerVies(int valeur) {
		if (this.vies + valeur <0) {
			this.vies = 0;
		}
		else {
			this.vies = this.vies + valeur;
		}
		
	}

}
