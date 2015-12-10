package edu.MD.view;

import java.util.ArrayList;
import java.util.List;

import edu.MD.model.MDSimulation;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class AnimationViewII {
	
	private static final double WIDTH = 800;
	private static final double HEIGHT = 400;
	
	private static final double SPHERE_SIZE = 5;
	private static final int SPHERE_DIV = 64;

	private static final double AXIS_LENGTH = 50;
	private static final double AXIS_WIDTH = 1;
	
	private static final double BUTTON_HEIGHT = 40;

	private BorderPane rootPane;

	private HBox buttonBar;

	private Button startButton;

	private Button pauseButton;

	private Box invisibleBox;

	private List<Sphere> particles;

	private double[] systemBounday;
	
	private SubScene simulationScene;
	
	private Group simulationGroup;

	private PerspectiveCamera simulationCamera = new PerspectiveCamera(true);

	private MDSimulation model;

	private DoubleProperty simulationViewRotateAngleX = new SimpleDoubleProperty(0);

	private DoubleProperty simulationViewRotateAngleY = new SimpleDoubleProperty(0);

	public AnimationViewII(MDSimulation simulationModel) {
		this.model = simulationModel;
		
		List<Node> insideGroup = new ArrayList<>();
		
		systemBounday = model.getSystemBoundary();
		invisibleBox = new Box(systemBounday[0], systemBounday[1], systemBounday[2]);
		invisibleBox.setTranslateX(systemBounday[0] / 2);
		invisibleBox.setTranslateY(systemBounday[1] / 2);
		invisibleBox.setTranslateZ(systemBounday[2] / 2);
		invisibleBox.setVisible(true);
		insideGroup.add(invisibleBox);

		List<Node> axes = buildAxes();
		insideGroup.addAll(axes);

		double[][] positions = model.getPositions();
		int num = model.getParticleNumber();
		particles = new ArrayList<Sphere>(num);
		for (int i = 0; i < num; i++) {
			Sphere particle = new Sphere(SPHERE_SIZE*i, SPHERE_DIV);
			particle.setTranslateX(positions[0][i]);
			particle.setTranslateY(positions[1][i]);
			particle.setTranslateZ(positions[2][i]);
			particles.add(particle);
		}
		insideGroup.addAll(particles);
		
		simulationGroup = new Group(insideGroup);
		simulationGroup.setAutoSizeChildren(false);
		
		double simulationSceneHeight = HEIGHT-BUTTON_HEIGHT;
		simulationScene = new SubScene(simulationGroup, WIDTH, simulationSceneHeight, true, SceneAntialiasing.BALANCED);
		simulationScene.setCamera(simulationCamera);
		Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
		xRotate.setPivotX(WIDTH/2);
		xRotate.setPivotY(simulationSceneHeight/2);
		Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
		yRotate.setPivotX(WIDTH/2);
		yRotate.setPivotY(simulationSceneHeight/2);		
		simulationScene.getTransforms().setAll(xRotate, yRotate );
		xRotate.angleProperty().bind(simulationViewRotateAngleX);
		yRotate.angleProperty().bind(simulationViewRotateAngleY);
		
		buttonBar = new HBox(25);
		buttonBar.setPrefHeight(BUTTON_HEIGHT);
		buttonBar.setPrefWidth(WIDTH);
		startButton = new Button("Start");
		pauseButton = new Button("Pause");
		buttonBar.getChildren().addAll(startButton, pauseButton);
		
		rootPane = new BorderPane(simulationScene, null, null, buttonBar, null);
	}

	private List<Node> buildAxes() {
		
		List<Node> axes = new ArrayList<>();
		
		
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
		
		axes.add(xAxis);
		axes.add(yAxis);
		axes.add(zAxis);
		
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

	public List<Sphere> getParticles() {
		return particles;
	}
	
	public DoubleProperty getSimulationRotateAngleX() {
		return simulationViewRotateAngleX;
	}

	public DoubleProperty getSimulationRotateAngleY() {
		return simulationViewRotateAngleY;
	}
	
	public BorderPane getRootPane() {
		return rootPane;
	}

}
