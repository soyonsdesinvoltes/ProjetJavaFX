package application;

public class Mob extends Character {

	private Carte carte;

	public Mob() {
		setCaseCourante(new Node(15, 6));
	}

	public Mob(Carte carte, Node n) {
		this.carte = carte;
		setCaseCourante(n);
	}

	public Mob(int x, int y) {
		Node n = new Node(x, y);
		setCaseCourante(n);
	}

	public void deplacerMob() {
		switch (caseCourante.getLettre()) {
		case 'C':
		case 'D':
		case 'E':
		case 'F':
			demiTour();
			break;
		case 'G':
		case 'H':
		case 'I':
		case 'J':
			carrefour();
			break;
		case 'L':
		case 'M':
			toutDroit();
			break;
		case 'R':
		case 'S':
		case 'T':
		case 'U':
			angle90();
			break;
		case '1':
			setDirection(Direction.BAS);
			setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() + 1));

			break;
		case '2':
			setDirection(Direction.GAUCHE);
			setCaseCourante(carte.existNode(caseCourante.getX() - 1, caseCourante.getY()));

			break;
		case '3':
			setDirection(Direction.DROITE);
			setCaseCourante(carte.existNode(caseCourante.getX() + 1, caseCourante.getY()));

			break;
		case '4':

			setDirection(Direction.HAUT);
			setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() - 1));

			break;
		}

	}

	/**
	 * 
	 * Met à jour la direction, les coordoonees et affiche le deplacement
	 *
	 * 
	 * 
	 * @param char[][]
	 * 
	 * @retour vide
	 * 
	 */

	private void toutDroit() {

		setCasePrecedente(caseCourante);
		switch (caseCourante.getLettre()) {

		case 'L':
			if (getDirection() == Direction.DROITE) {
				setCaseCourante(carte.existNode(caseCourante.getX() + 1, caseCourante.getY()));
				setDirection(Direction.DROITE);
			} else {
				setCaseCourante(carte.existNode(caseCourante.getX() - 1, caseCourante.getY()));
				setDirection(Direction.GAUCHE);
			}

			break;

		case 'M':
			if (getDirection() == Direction.HAUT) {
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() - 1));
				setDirection(Direction.HAUT);
			} else {
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() + 1));
				setDirection(Direction.BAS);
			}

			break;

		default:

			break;

		}

	}

	/**
	 * fonction DemiTour Alphonse est arrivé dans un cul de sac, il doit faire
	 * demi tour. elle modifie la case courante, la case_precedente, la
	 * direction et les coordonnées d'Alphonse
	 * 
	 * @param vide
	 * @retour vide
	 */

	private void demiTour() {

		switch (caseCourante.getLettre()) {
		case 'C':
			setDirection(Direction.GAUCHE);
			setCaseCourante(carte.existNode(casePrecedente.getX() - 1, casePrecedente.getY()));
			break;
		case 'D':
			setDirection(Direction.HAUT);
			setCaseCourante(carte.existNode(casePrecedente.getX(), casePrecedente.getY() - 1));
			break;
		case 'E':
			setDirection(Direction.BAS);
			setCaseCourante(carte.existNode(casePrecedente.getX(), casePrecedente.getY() + 1));

			break;
		case 'F':
			setDirection(Direction.DROITE);
			setCaseCourante(carte.existNode(casePrecedente.getX() + 1, casePrecedente.getY()));

			break;
		}

		// System.out.println("GGA");
	}

	/**
	 * 
	 * fonction Angle90 Alphonse est arrivé dans un coin, il doit tourner. elle
	 * modifie la case courante, la case_precedente, la direction et les
	 * coordonnées d'Alphonse
	 * 
	 * @param vide
	 * @retour vide
	 * 
	 */

	private void angle90() {

		switch (caseCourante.getLettre()) {
		case 'R':
			if (getDirection() == Direction.DROITE) {
				setDirection(Direction.BAS);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() + 1));

				// System.out.println("DA");
			} else {
				setDirection(Direction.GAUCHE);
				setCaseCourante(carte.existNode(caseCourante.getX() - 1, caseCourante.getY()));

				// System.out.println("GA");
			}
			break;
		case 'S':
			if (getDirection() == Direction.BAS) {
				setDirection(Direction.GAUCHE);
				setCaseCourante(carte.existNode(caseCourante.getX() - 1, caseCourante.getY()));

				System.out.println("DA");
			} else {
				setDirection(Direction.HAUT);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() - 1));

				System.out.println("GA");
			}
			break;
		case 'T':
			if (getDirection() == Direction.HAUT) {
				setDirection(Direction.DROITE);
				setCaseCourante(carte.existNode(caseCourante.getX() + 1, caseCourante.getY()));

				System.out.println("DA");
			} else {
				setDirection(Direction.BAS);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() + 1));

				System.out.println("GA");
			}
			break;
		case 'U':
			if (getDirection() == Direction.BAS) {
				setDirection(Direction.DROITE);
				setCaseCourante(carte.existNode(caseCourante.getX() + 1, caseCourante.getY()));

				System.out.println("GA");
			} else {
				setDirection(Direction.HAUT);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() - 1));

				System.out.println("DA");
			}
			break;
		}

	}

	/**
	 * fonction Carrefour Alphonse est arrivé à un carrefour, il doit décider de
	 * sa direction. elle modifie la case courante, la case_precedente, la
	 * direction et les coordonnées d'Alphonse
	 * 
	 * @param vide
	 * @retour vide
	 */

	private void carrefour() {

		switch (caseCourante.getLettre()) {
		case 'G':
			if (getDirection() == Direction.DROITE) {
				setDirection(Direction.BAS);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() + 1));
				System.out.println("DA");
			} else if (getDirection() == Direction.HAUT) {
				setDirection(Direction.DROITE);
				setCaseCourante(carte.existNode(caseCourante.getX() + 1, caseCourante.getY()));
				System.out.println("GA");
			} else {
				setCaseCourante(carte.existNode(caseCourante.getX() - 1, caseCourante.getY()));

				System.out.println("A");
			}
			break;
		case 'H':
			if (getDirection() == Direction.DROITE) {
				setDirection(Direction.BAS);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() + 1));

				System.out.println("DA");
			} else if (direction == Direction.HAUT) {
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() - 1));
			} else {
				setDirection(Direction.GAUCHE);
				setCaseCourante(carte.existNode(caseCourante.getX() - 1, caseCourante.getY()));
			}
			break;
		case 'I':
			if (getDirection() == Direction.GAUCHE) {
				setDirection(Direction.HAUT);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() - 1));
			} else if (direction == Direction.HAUT) {
				setDirection(Direction.DROITE);
				setCaseCourante(carte.existNode(caseCourante.getX() + 1, caseCourante.getY()));
			} else {
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() + 1));
			}
			break;
		case 'J':
			if (getDirection() == Direction.DROITE) {
				setCaseCourante(carte.existNode(caseCourante.getX() + 1, caseCourante.getY()));
			} else if (direction == Direction.BAS) {
				setDirection(Direction.GAUCHE);
				setCaseCourante(carte.existNode(caseCourante.getX() - 1, caseCourante.getY()));
			} else {
				setDirection(Direction.HAUT);
				setCaseCourante(carte.existNode(caseCourante.getX(), caseCourante.getY() - 1));

			}
			break;
		}

	}
}