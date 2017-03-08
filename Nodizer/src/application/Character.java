package application;

/*  Projet Alphonse
* Participants au projet : Mathieu, Karine, Lucien et Bertrand
* Alphonse doit se déplacer dans un labyrinthe, on récupére le labyrinthe par un fichier texte dongeon.txt
*/

public abstract class Character {

	/*
	 * Coordonnées et direction du personnage au début du jeu
	 */
	Node caseCourante=null;
	Node casePrecedente=null;

	public Node getCaseCourante() {
		return caseCourante;
	}

	public void setCaseCourante(Node caseCourante) {
		casePrecedente = this.caseCourante;
		this.caseCourante = caseCourante;
	}

	public Node getCasePrecedente() {
		return casePrecedente;
	}

	public void setCasePrecedente(Node casePrecedente) {
		this.casePrecedente = casePrecedente;
	}

	enum Direction {
		DROITE, GAUCHE, HAUT, BAS
	}
	Direction direction = Direction.DROITE;
	
	
	public Character(){
		
	}

	public Character(Node caseDebut) {
		this.setCaseCourante(caseDebut);
		
	}
	
	public int getX() {
		return caseCourante.getX();
	}


	public int getY() {
		return caseCourante.getY();
	}

	public Direction getDirection() {
		return this.direction;
	}

	void setDirection(Direction dir) {
		
		this.direction = dir;
		
	}
	
	}
