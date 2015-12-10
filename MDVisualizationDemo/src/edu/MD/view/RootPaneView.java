package edu.MD.view;

import edu.MD.model.MDSimulation;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.transform.Rotate;

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
	
	@FXML
	private SubScene simulationScene;
	
	@FXML
	private Group simulationGroup;

	private PerspectiveCamera simulationCamera = new PerspectiveCamera(true);

	private MDSimulation model;

	private DoubleProperty simulationViewRotateAngleX = new SimpleDoubleProperty(0);

	private DoubleProperty simulationViewRotateAngleY = new SimpleDoubleProperty(0);

	public RootPaneView() {
	}

	@FXML
	private void initialize() {

	}
	
	public void setRootPaneView(MDSimulation simulationModel){
		this.model = simulationModel;

		systemBounday = model.getSystemBoundary();
		invisibleBox = new Box(systemBounday[0], systemBounday[1], systemBounday[2]);
		invisibleBox.setTranslateX(systemBounday[0] / 2);
		invisibleBox.setTranslateY(systemBounday[1] / 2);
		invisibleBox.setTranslateZ(systemBounday[2] / 2);
		invisibleBox.setVisible(false);
		simulationGroup.getChildren().add(invisibleBox);

		Node[] axes = buildAxes();
		simulationGroup.getChildren().addAll(axes);

		double[][] positions = model.getPositions();
		int num = model.getParticleNumber();
		particles = new Sphere[num];
		for (int i = 0; i < num; i++) {
			particles[i] = new Sphere(SPHERE_SIZE, SPHERE_DIV);
			particles[i].setTranslateX(positions[0][i]);
			particles[i].setTranslateY(positions[1][i]);
			particles[i].setTranslateZ(positions[2][i]);
		}
		simulationGroup.getChildren().addAll(particles);

//		double simulationSceneHeight = simulationPane.getPrefHeight() - AnchorPane.getBottomAnchor(startButton) * 2
//				- 25;
//		simulationScene = new SubScene(simulationGroup, simulationPane.getPrefWidth(), simulationSceneHeight, true,
//				SceneAntialiasing.BALANCED);
//		simulationScene.setCamera(simulationCamera);
//		simulationPane.getChildren().add(simulationScene);
		
		Rotate xRotate;
		Rotate yRotate;
		simulationPane.getTransforms().setAll(xRotate = new Rotate(0, Rotate.X_AXIS),
				yRotate = new Rotate(0, Rotate.Y_AXIS));
		xRotate.angleProperty().bind(simulationViewRotateAngleX);
		yRotate.angleProperty().bind(simulationViewRotateAngleY);

	}

	private Node[] buildAxes() {
		
		Node[] axes = new Node[3];
		
		
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
		
		axes[0] = xAxis;
		axes[1] = yAxis;
		axes[2] = zAxis;
		
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
	
	public DoubleProperty getSimulationRotateAngleX() {
		return simulationViewRotateAngleX;
	}

	public DoubleProperty getSimulationRotateAngleY() {
		return simulationViewRotateAngleY;
	}

}
