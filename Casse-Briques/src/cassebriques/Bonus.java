package cassebriques;

import org.newdawn.slick.Image;

public abstract class Bonus extends ObjetEnMouvement {

	public Bonus(Jeu j, Image i, int l, int h, int x, int y, int dx, int dy) {
		super(j, i, l, h, x, y, dx, dy);
	}

	public Bonus(Jeu j, Image i, int l, int h, int x, int y, int dx, int dy,
			int decXdess, int decYdess) {
		super(j, i, l, h, x, y, dx, dy, decXdess, decYdess);
	}
	
	abstract public void effetBonus();

}
