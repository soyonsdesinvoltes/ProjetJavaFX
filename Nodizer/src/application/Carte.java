package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carte {

	int nbLigne = 0;
	int nbCharParLigne = 0;
	List<Node> carteNode = new ArrayList<Node>();
	char [][]donjon = null;
	public int getNbLigne() {
		return nbLigne;
	}

	public int getNbCharParLigne() {
		return nbCharParLigne;
	}

	public Carte() {

	}

	public Carte(String url) {
		try {
			extractNode(importerCarte(url));

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	public char[][] importerCarte(String url) throws FileNotFoundException {

		

		String ligne = "", total = "";

		InputStream flux = new FileInputStream(url);
		InputStreamReader lecture = new InputStreamReader(flux);
		BufferedReader buff = new BufferedReader(lecture);
		try {
			while ((ligne = buff.readLine()) != null) {
				total = total + ligne;
				nbCharParLigne = ligne.length();
				nbLigne = nbLigne + 1;
			}
			buff.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Découpage de la chaine pour la mettre dans un tableau de caractères
		 * représentant le labyrinthe
		 */
		char tab[] = total.toCharArray();
		donjon = new char[nbLigne][nbCharParLigne];

		// Création de la matrice
		int i, j, k = 0;

		for (i = 0; i < nbLigne; i++) {
			for (j = 0; j < nbCharParLigne; j++) {
				donjon[i][j] = tab[k];
				/*
				 * Pour afficher la matrice (test)
				 */
				if ((j + 1) % (nbCharParLigne) == 0) {
					System.out.println(donjon[i][j]);
				} else {
					System.out.print(donjon[i][j]);
				}

				k++;
			}
		}

		return donjon;
	}

	public void extractNode(char[][] carte) {

		Node n = null;

		for (int i = 0; i < carte.length; i++) {
			for (int j = 0; j < carte[i].length; j++) {
				char caract = carte[i][j];
				if (caract != 'X') {
					if (existNode(j, i) == null) {
						n = new Node(j, i, caract);
						carteNode.add(n);
					} else {
						n = existNode(j, i);
					}

					switch (caract) {
					case 'E':
						if (carte[i + 1][j] != 'X') {
							Node bas = new Node(j, i + 1, carte[i + 1][j]);
							bas.setListeVoisins(n);
							carteNode.add(bas);
							n.setListeVoisins(bas);
						}
						break;
					case 'F':
					case '3':
						if (carte[i][j + 1] != 'X') {
							Node droite = new Node(j + 1, i, carte[i][j+1]);
							droite.setListeVoisins(n);
							carteNode.add(droite);
							n.setListeVoisins(droite);
						}
						break;
					case 'G':
						if (carte[i][j + 1] != 'X') {
							Node droite = new Node(j + 1, i, carte[i][j + 1]);
							droite.setListeVoisins(n);
							carteNode.add(droite);
							n.setListeVoisins(droite);
						}
						if (carte[i + 1][j] != 'X') {
							Node bas = new Node(j, i + 1, carte[i + 1][j]);
							bas.setListeVoisins(n);
							carteNode.add(bas);
							n.setListeVoisins(bas);
						}
						break;
					case 'H':
						if (carte[i + 1][j] != 'X') {
							Node bas = new Node(j, i + 1, carte[i + 1][j]);
							bas.setListeVoisins(n);
							carteNode.add(bas);
							n.setListeVoisins(bas);
						}
						break;
					case 'I':
						if (carte[i][j + 1] != 'X') {
							Node droite = new Node(j + 1, i, carte[i][j + 1]);
							droite.setListeVoisins(n);
							carteNode.add(droite);
							n.setListeVoisins(droite);
						}
						if (carte[i + 1][j] != 'X') {
							Node bas = new Node(j, i + 1, carte[i + 1][j]);
							bas.setListeVoisins(n);
							carteNode.add(bas);
							n.setListeVoisins(bas);
						}
						break;
					case 'J':
						if (carte[i][j + 1] != 'X') {
							Node droite = new Node(j + 1, i, carte[i][j + 1]);
							droite.setListeVoisins(n);
							carteNode.add(droite);
							n.setListeVoisins(droite);
						}
						break;
					case 'L':
						if (carte[i][j + 1] != 'X') {
							Node droite = new Node(j + 1, i, carte[i][j + 1]);
							droite.setListeVoisins(n);
							carteNode.add(droite);
							n.setListeVoisins(droite);
						}
						break;
					case 'M':
						if (carte[i + 1][j] != 'X') {
							Node bas = new Node(j, i + 1, carte[i + 1][j]);
							bas.setListeVoisins(n);
							carteNode.add(bas);
							n.setListeVoisins(bas);
						}
						break;
					case 'R':
						if (carte[i + 1][j] != 'X') {
							Node bas = new Node(j, i + 1, carte[i + 1][j]);
							bas.setListeVoisins(n);
							carteNode.add(bas);
							n.setListeVoisins(bas);
						}
						break;
					case 'U':
						if (carte[i][j + 1] != 'X') {
							Node droite = new Node(j + 1, i, carte[i][j + 1]);
							droite.setListeVoisins(n);
							carteNode.add(droite);
							n.setListeVoisins(droite);
						}
						break;

					}

				}
			}
		}

		Collections.sort(carteNode);

	}

	public List<Node> getCarteNode() {
		return carteNode;
	}

	public Node getNodeStarter() {
		for (Node n : carteNode) {
			if (n.x == 1 && n.y == 1) {
				return n;
			}
		}
		return null;
	}

	public Node existNode(int x, int y) {
		if (carteNode != null) {
			for (Node n : carteNode) {
				if (n.getX() == x && n.getY() == y) {
					return n;
				}
			}
		}

		return null;

	}

	public boolean isValidTile(int x, int y) {

		for (Node n : carteNode) {
			if (n.getX() == x && n.getY() == y) {
				return true;
			}
		}
		return false;

	}

	public Node getNextNode(int x, int y) {

		for (Node n : carteNode) {
			if (n.getX() == x && n.getY() == y) {
				return n;
			}
		}
		return null;

	}

}
