package application;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
	
	int x=0;
	int y=0;
	char lettre;
	
	ArrayList<Node> listeVoisins;
	
	public Node(){
		
	}
	
	
	public Node( int x,int y){
		
		this.x=x;
		this.y=y;
		listeVoisins=new ArrayList<Node>();
		
	}
	
	public Node (int x,int y, char lettre){
		this.x=x;
		this.y=y;
		this.lettre=lettre;
		listeVoisins=new ArrayList<Node>();
	}
	
	public char getLettre() {
		return lettre;
	}


	public void setLettre(char lettre) {
		this.lettre = lettre;
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	public void setListeVoisins(Node voisin) {
		this.listeVoisins.add(voisin);
	}

	public ArrayList<Node> getListeVoisins() {
		return listeVoisins;
	}


	@Override
	public int compareTo(Node node) {
		if (this.y == node.y) {
			return this.x - node.x;
		}
		return this.y - node.y;
	}

}
