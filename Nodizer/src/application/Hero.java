package application;

public class Hero extends Character {

	public Hero() {
		caseCourante = new Node(1, 1);
	}

	public Hero(Node n) {
		this.setCaseCourante(n);
	}

}
