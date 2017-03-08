package application;

import application.Character.Direction;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

;

/**
 * Hold down an arrow key to have your hero move around the screen. Hold down
 * the shift key to have the hero run.
 */
public class Graph extends Application {

	private static double MAP_LARGEUR = 1000;
	private static double MAP_HAUTEUR = 1000;
	private static boolean isHero = true;

	public Node hero;
	public Node mob;
	private static final String IMAGECOULOIR = "images/IN_1.png";
	private static final String VIRAGEDROITBAS = "images/LN_1.png";
	private static final String CROISEMENT3 = "images/TN_1.png";
	private static final String END = "images/EN_1.png";
	private static final String ARR = "images/VTOL_2.png";
	private static final String HERO_IMAGE_LOC = "images/hero.png";
	private static final String MOB = "images/mob3.png";
	private static final String WINNER = "images/winner.jpg";

	final Image image1 = new Image(IMAGECOULOIR);
	final Image image2 = new Image(VIRAGEDROITBAS);
	final Image image3 = new Image(CROISEMENT3);
	final Image image4 = new Image(END);
	final Image image5 = new Image(ARR);
	public Image winner = new Image(WINNER);
	public Image heroImage;
	public Image mobImage;

	ImageView iv;

	Carte carte = null;

	/* nombre de pixels representant une unité dedéplacement */
	static int inc = 50;

	int cpt = 0;
	boolean running, goNorth, goSouth, goEast, goWest;
	public boolean trouveSortie = false;

	public Graph() {

	}

	public Graph(Carte c) {
		this.carte = c;
	}

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		carte = new Carte("src/application/carte.txt");

		MAP_LARGEUR = carte.nbCharParLigne * inc;
		MAP_HAUTEUR = carte.nbLigne * inc;

		// Creation de l'image du hero
		heroImage = new Image(HERO_IMAGE_LOC, 30, 30, false, false);
		hero = new ImageView(heroImage);
		// on retourne l'image pour que le chevalier regarde vers la drroite
		hero.setRotate(180);

		// Creation de l'image du mob
		mobImage = new Image(MOB, 30, 30, false, false);
		mob = new ImageView(mobImage);
		// on retourne l'image pour que le chevalier regarde vers la drroite
		// hero.setRotate(180);

		// Creation de l'interface graphique
		Canvas canvas = new Canvas(MAP_LARGEUR, MAP_HAUTEUR);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		Group root = new Group();
		// creation du labyrinthe graphique
		drawShapes(gc);
		root.getChildren().add(canvas);
		// root.getChildren().add(iv);

		// Affichage de l'interface graphique
		Scene scene = new Scene(root, MAP_LARGEUR, MAP_HAUTEUR, Color.WHITE);
		// root.getChildren().add(canvas);
		root.getChildren().add(hero);
		root.getChildren().add(mob);
		// root.setChild(hero, 1);

		// Création de l'objet chevalier et deplacement à la position initiale
		Character alphonse = new Hero(carte.getNodeStarter());
		alphonse.setDirection(Direction.DROITE);

		Mob blurp = new Mob(carte, carte.existNode(15, 6));
		blurp.setDirection(Direction.HAUT);

		moveHeroBy(60, 60);
		moveHeroBy(760, 300);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (isHero) {
					Character character;
					character = alphonse;

					switch (event.getCode()) {
					case UP:
						if (carte.isValidTile(character.getX(), character.getY() - 1)) {
							character.setCasePrecedente(character.getCaseCourante());
							character.setCaseCourante(carte.getNextNode(character.getX(), character.getY() - 1));
							DeplacerHero(character);
						}
						break;
					case DOWN:
						if (carte.isValidTile(character.getX(), character.getY() + 1)) {
							character.setCasePrecedente(character.getCaseCourante());
							character.setCaseCourante(carte.getNextNode(character.getX(), character.getY() + 1));
							DeplacerHero(character);
						}
						break;
					case LEFT:
						if (carte.isValidTile(character.getX() - 1, character.getY())) {
							character.setCasePrecedente(character.getCaseCourante());
							character.setCaseCourante(carte.getNextNode(character.getX() - 1, character.getY()));
							DeplacerHero(character);
						}
						break;
					case RIGHT:
						if (carte.isValidTile(character.getX() + 1, character.getY())) {
							character.setCasePrecedente(character.getCaseCourante());
							character.setCaseCourante(carte.getNextNode(character.getX() + 1, character.getY()));
							DeplacerHero(character);

						}
						break;
					default:
						break;
					}

					if (alphonse.getCaseCourante().getLettre() == '1' || alphonse.getCaseCourante().getLettre() == '2'
							|| alphonse.getCaseCourante().getLettre() == '3'
							|| alphonse.getCaseCourante().getLettre() == '4') {
						gc.drawImage(winner, 0, 0, MAP_LARGEUR, MAP_HAUTEUR);
						hero.setVisible(false);
						trouveSortie = true;

					}
				}
			}

		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!isHero) {

					blurp.deplacerMob();
					DeplacerHero(blurp);
				}
			}
		});

		stage.setScene(scene);
		stage.show();

		/*
		 * AnimationTimer timer = new AnimationTimer() {
		 * 
		 * @Override public void handle(long now) { int dx = 0, dy = 0;
		 * 
		 * // if (goNorth) dy -= 1; // if (goSouth) dy += 1; if (goEast) dx +=
		 * 1; if (goWest) dx -= 1; if (running) { dx *= 3; dy *= 3; }
		 * 
		 * moveHeroBy(dx, dy); } }; timer.start();
		 */
	}

	public void DeplacerHero(Character character) {
		int deplacementx = character.getCaseCourante().getX() - character.getCasePrecedente().getX();
		int deplacementy = character.getCaseCourante().getY() - character.getCasePrecedente().getY();

		moveHeroBy(deplacementx * inc, deplacementy * inc);
	}

	private void drawShapes(GraphicsContext gc) {

		gc.setFill(Color.GRAY);
		gc.setStroke(Color.GRAY);

		// Double boucle pour ecrire les coordonnees des cases
		int x = 0, y = 0;
		for (int i = 20; i < MAP_HAUTEUR; i = i + 50) {
			for (int j = 20; j < MAP_LARGEUR; j = j + 50) {
				gc.fillText(" " + x + "," + y + " ", j, i);
				x++;
				if (j >= (MAP_LARGEUR - 50))
					x = 0;
			}
			y++;
		}
		// 2 boucles pour dessiner le quadrillage
		for (int i = 0; i < MAP_LARGEUR; i = i + inc) {
			gc.strokeLine(i, 0, i, MAP_LARGEUR);

		}
		for (int i = 0; i < MAP_HAUTEUR; i = i + inc) {
			gc.strokeLine(0, i, MAP_HAUTEUR, i);
		}

		if (cpt > 10) {
			gc.fillText(" TEST ", 20, 20);

		}
		iv = new ImageView(image1);

		gc.drawImage(image1, 50, 50, inc, inc);

		for (application.Node n : carte.getCarteNode()) {
			Image rotatedImage = null;
			SnapshotParameters params = new SnapshotParameters();
			switch (n.getLettre()) {
			case 'L':
				gc.drawImage(image1, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'M':
				iv = new ImageView(image1);
				iv.setRotate(90);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'R':
				gc.drawImage(image2, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'S':
				iv = new ImageView(image2);
				iv.setRotate(90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'T':
				iv = new ImageView(image2);
				iv.setRotate(-90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'U':
				iv = new ImageView(image2);
				iv.setRotate(180);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'G':
				gc.drawImage(image3, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'H':
				iv = new ImageView(image3);
				iv.setRotate(90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'I':
				iv = new ImageView(image3);
				iv.setRotate(-90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'J':
				iv = new ImageView(image3);
				iv.setRotate(180);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case '1':
				gc.drawImage(image5, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case '2':
				iv = new ImageView(image5);
				iv.setRotate(90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case '3':
				iv = new ImageView(image5);
				iv.setRotate(-90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case '4':
				iv = new ImageView(image5);
				iv.setRotate(180);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'C':
				gc.drawImage(image4, inc * n.getX(), inc * n.getY(), inc, inc);
				break;

			case 'D':
				iv = new ImageView(image4);
				iv.setRotate(90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'E':
				iv = new ImageView(image4);
				iv.setRotate(-90);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;
			case 'F':
				iv = new ImageView(image4);
				iv.setRotate(180);
				params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				rotatedImage = iv.snapshot(params, null);
				gc.drawImage(rotatedImage, inc * n.getX(), inc * n.getY(), inc, inc);
				break;

			}
		}

	}

	/***
	 * Deplace le chevalier en fonction des coordonnées en entree
	 * 
	 * @param dx
	 *            : déplacement x en pixels
	 * @param dy
	 *            : déplacement y en pixels
	 */
	private void moveHeroBy(int dx, int dy) {

		Node n;

		if (dx == 0 && dy == 0)
			return;

		// calcul largeur de l'image du héro
		// double hero_largeur = hero.getBoundsInLocal().getWidth();
		// double hero_hauteur = hero.getBoundsInLocal().getHeight();

		// Calcul position d'arrivée (position du coin haut gauche de l'image du
		// héro)

		if (isHero) {
			n = hero;
			double pos_finale_x = n.getLayoutX() + dx;
			double pos_finale_y = n.getLayoutY() + dy;
			n.relocate(pos_finale_x, pos_finale_y);
			isHero = false;
		} else {
			n = mob;
			double pos_finale_x = n.getLayoutX() + dx;
			double pos_finale_y = n.getLayoutY() + dy;
			n.relocate(pos_finale_x, pos_finale_y);
			isHero = true;
		}

	}

}