/**
 * 
 */
package cassebriques;

import java.util.Comparator;


/**
 * @author rxambili
 *
 */
public class JoueurComparator implements Comparator<Joueur> {

	/**
	 * 
	 */
	public JoueurComparator() {
		super();
	}
	
	public int compare(Joueur j1, Joueur j2) {
		int res;
		if (j1 == null && j2 == null) {
			res = 0;
		} else if (j2 == null) {
			res = -1;
		} else if (j1 == null) {
			res = 1;
		} else {
			if (j1.getScore() > j2.getScore()) {
				res = -1;
			} else if (j1.getScore() < j2.getScore()) {
				res = 1;
			} else {
				if (j1.getVies() > j2.getVies()) {
					res = -1;
				} else if (j1.getVies() < j2.getVies()) {
					res = 1;
				} else {
					res = 0;
				}
			}
		}
		return res;				
	}
}
