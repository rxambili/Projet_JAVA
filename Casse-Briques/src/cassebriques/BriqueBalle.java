/**
 * 
 */
package cassebriques;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author rxambili
 *
 */
public class BriqueBalle extends Brique {

	/**
	 * @param j
	 * @param i
	 * @param col
	 * @param lig
	 * @param r
	 * @throws SlickException
	 */
	public BriqueBalle(Jeu j, Image i, int col, int lig, int r)
			throws SlickException {
		super(j, i, col, lig, r);
	}
	
	/**
	 * tue la brique et ajoute entre 1 et 3 balles en mourant.
	 */
	@Override
	public void tuer() {
		super.tuer();
		if (resistance == 0) {
			this.vivant = true;
			Balle balleTouchant = jeu.getBalleSurBrique(this);
			this.vivant = false;

			if (balleTouchant !=null) {
				int nombreAjoute = (int)(Math.random()*2) + 1;
				for (int i=0; i<nombreAjoute; i++) {	
					try {
						jeu.ajouterBalle(balleTouchant.getX(), balleTouchant.getY(), balleTouchant.getDernierJoueur());
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
