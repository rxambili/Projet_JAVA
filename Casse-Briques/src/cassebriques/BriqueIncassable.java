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
public class BriqueIncassable extends Brique {

	/**
	 * @param j
	 * @param i
	 * @param col
	 * @param lig
	 * @param r
	 * @throws SlickException
	 */
	public BriqueIncassable(Jeu j, Image i, int col, int lig, int r)
			throws SlickException {
		super(j, i, col, lig, r);
		
	}

	@Override
	public void tuer() {
		
	}
	
}
