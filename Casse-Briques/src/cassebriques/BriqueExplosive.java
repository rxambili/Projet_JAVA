package cassebriques;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class BriqueExplosive extends Brique {
	
	private Animation explosion;
	private boolean explose;
	
	/**
	 * Constructeur de la classe Brique Explosive
	 * @param j le jeu
	 * @param i image de la brique
	 * @param col colonne dans laquelle se trouve le bloc
	 * @param lig ligne dans laquelle se trouve le bloc
	 * @param r resistance de la brique
	 * @param explo l'animation de l'explosion
	 * @throws SlickException 
	 */
	public BriqueExplosive(Jeu j, Image i, int col, int lig, int r, Animation explo) throws SlickException {
			super(j,i,col,lig,r);
			explosion = explo.copy();
			explose = false;
	}	
	
	@Override
	/**
	 * Fait mourir le bloc ou baisse sa resistance
	 * si bloc mort il tue les briques voisines directement
	 */
	public void tuer() {
		super.tuer();
		if (resistance == 0) {

				ArrayList <Brique> entourage = jeu.getCarte().getEntourage(colonne,ligne);

				for (Brique br : entourage) {
					br.tuerDirect();
				}
		}
		explose = true;
	}

	@Override
	public void dessiner(Graphics g) {
		super.dessiner(g);
		if (explose) {
			g.drawAnimation(explosion, X, Y);
			if (explosion.isStopped()) {
				explose = false;
			}
		}
	}

}
