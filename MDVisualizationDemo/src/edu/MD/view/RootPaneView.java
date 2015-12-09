package edu.MD.view;

import edu.MD.model.MDSimulation;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

public class RootPaneView {

	private static final double SPHERE_SIZE = 10;

	private static final int SPHERE_DIV = 64;

	private static final double AXIS_LENGTH = 50;
	private static final double AXIS_WIDTH = 1;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private AnchorPane simulationPane;

	@FXML
	private Button startButton;

	@FXML
	private Button pauseButton;

	private Box invisibleBox;

	private Sphere[] particles;

	private double[] systemBounday;

	private SubScene simulationScene;

	private PerspectiveCamera simulationCamera = new PerspectiveCamera(true);

	private MDSimulation model;

	public RootPaneView(MDSimulation simulationModel) {
		this.model = simulationModel;
	}

	@FXML
	private void initialize() {

		systemBounday = model.getSystemBoundary();
		invisibleBox = new Box(systemBounday[0], systemBounday[1], systemBounday[2]);
		invisibleBox.setTranslateX(systemBounday[0] / 2);
		invisibleBox.setTranslateY(systemBounday[1] / 2);
		invisibleBox.setTranslateZ(systemBounday[2] / 2);
		invisibleBox.setVisible(false);
		Group simulationGroup = new Group(invisibleBox);

		simulationGroup.getChildren().addAll(buildAxes());

		double[][] positions = model.getPositions();
		int num = model.getParticleNumber();
		particles = new Sphere[num];
		for (int i = 0; i < num; i++) {
			particles[i] = new Sphere(SPHERE_SIZE, SPHERE_DIV);
			particles[i].setTranslateX(positions[i][0]);
			particles[i].setTranslateY(positions[i][1]);
			particles[i].setTranslateZ(positions[i][2]);
		}
		simulationGroup.getChildren().addAll(particles);

		double simulationSceneHeight = simulationPane.getHeight() - AnchorPane.getBottomAnchor(startButton) * 2
				- startButton.getHeight();
		simulationScene = new SubScene(simulationGroup, simulationPane.getWidth(), simulationSceneHeight, true,
				SceneAntialiasing.BALANCED);
		simulationScene.setCamera(simulationCamera);
		simulationPane.getChildren().add(simulationScene);

	}

	private Node[] buildAxes() {
		Box[] axes = new Box[3];
		
		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		final Box xAxis = new Box(AXIS_LENGTH, AXIS_WIDTH, AXIS_WIDTH);
		final Box yAxis = new Box(AXIS_WIDTH, AXIS_LENGTH, AXIS_WIDTH);
		final Box zAxis = new Box(AXIS_WIDTH, AXIS_WIDTH, AXIS_LENGTH);
		xAxis.setTranslateX(systemBounday[0]/2);
		xAxis.setTranslateY(systemBounday[1]/2);
		xAxis.setTranslateZ(systemBounday[2]/2);
		yAxis.setTranslateX(systemBounday[0]/2);
		yAxis.setTranslateY(systemBounday[1]/2);
		yAxis.setTranslateZ(systemBounday[2]/2);
		zAxis.setTranslateX(systemBounday[0]/2);
		zAxis.setTranslateY(systemBounday[1]/2);
		zAxis.setTranslateZ(systemBounday[2]/2);

		xAxis.setMaterial(redMaterial);
		yAxis.setMaterial(greenMaterial);
		zAxis.setMaterial(blueMaterial);
		
		return axes;
	}

	public SubScene getSimulationScene() {
		return simulationScene;
	}

	public Button getStartButton() {
		return startButton;
	}

	public Button getPauseButton() {
		return pauseButton;
	}

	public Sphere[] getParticles() {
		return particles;
	}

}
