package edu.MD.view;

import edu.MD.model.MDSimulation;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class RootPaneView {

	private static final double SPHERE_SIZE = 10;
	private static final int SPHERE_DIV = 64;

	private static final double LINE_SIZE = 1;

	private static final double AXIS_LENGTH = 20;

	@FXML
	private SplitPane rootPane;

	@FXML
	private AnchorPane simulationPane;

	@FXML
	private AnchorPane chartPane;

	@FXML
	private ButtonBar buttonBar;

	@FXML
	private Button startButton;

	@FXML
	private Button pauseButton;

	private SubScene simulationScene;

	private Sphere[] particles;

	private double[] systemBounday;

	private double[][] positions;

	private int numOfParticles;

	private Group simulationGroup;

	private ParallelCamera simulationCamera;

	private MDSimulation model;

	private DoubleProperty simulationViewRotateAngleX = new SimpleDoubleProperty(0);

	private DoubleProperty simulationViewRotateAngleY = new SimpleDoubleProperty(0);

	public RootPaneView() {
	}

	@FXML
	private void initialize() {

	}

	public void setRootPaneView(MDSimulation simulationModel) {
		// obtain data from model
		this.model = simulationModel;
		systemBounday = model.getSystemBoundary();
		positions = model.getPositions();
		numOfParticles = model.getParticleNumber();

		// obtain data from the FXML view for constructing the subscene and
		// center the group;
		double simulationSceneWidth = simulationPane.getPrefWidth();
		double simulationSceneHeight = simulationPane.getPrefHeight() - buttonBar.getPrefHeight()
				- AnchorPane.getBottomAnchor(buttonBar) - 5;

		// building the Group as the root of subscene
		simulationGroup = new Group();

		// Visualize the simulation boundary
		Cylinder[] simulationBoundayBox = getSimulationBoundaryBox(systemBounday[0], systemBounday[1],
				systemBounday[2]);
		simulationGroup.getChildren().addAll(simulationBoundayBox);

		// Add the axes to identify x,y,z directions at the bottom of simulation
		// boundary
		Cylinder[] axes = getAxes(new Point3D(systemBounday[0] * 1.1, systemBounday[1] * 1.1, 0));
		simulationGroup.getChildren().addAll(axes);

		// Add the particles
		particles = new Sphere[numOfParticles];
		for (int i = 0; i < numOfParticles; i++) {
			particles[i] = new Sphere(SPHERE_SIZE, SPHERE_DIV);
			particles[i].setTranslateX(positions[0][i]);
			particles[i].setTranslateY(positions[1][i]);
			particles[i].setTranslateZ(positions[2][i]);
		}
		simulationGroup.getChildren().addAll(particles);

		simulationScene = new SubScene(simulationGroup, simulationSceneWidth, simulationSceneHeight, true,
				SceneAntialiasing.BALANCED);

		// move Group to the center of the scene
		Bounds simulationGroupBounds = simulationGroup.getLayoutBounds();

		double centerX = (simulationSceneWidth - simulationGroupBounds.getWidth()) / 2
				- simulationGroupBounds.getMinX();
		double centerY = (simulationSceneHeight - simulationGroupBounds.getHeight()) / 2
				- simulationGroupBounds.getMinY();
		double moveZ = 1 - simulationGroupBounds.getMinX(); // for putting the
															// camera
		simulationGroup.setTranslateX(centerX);
		simulationGroup.setTranslateY(centerY);
		simulationGroup.setTranslateZ(moveZ);
		
		simulationCamera = new ParallelCamera();
		simulationCamera.setRotate(20);
		simulationCamera.setRotationAxis(new Point3D(1,1,0));

		simulationScene.setCamera(simulationCamera);
		simulationPane.getChildren().add(simulationScene);
		AnchorPane.setTopAnchor(simulationScene, 0.0);
		AnchorPane.setLeftAnchor(simulationScene, 0.0);
		AnchorPane.setRightAnchor(simulationScene, 0.0);

		// Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
		// xRotate.setPivotX(400);
		// xRotate.setPivotY(150);
		// Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
		// yRotate.setPivotX(400);
		// yRotate.setPivotY(150);
		// simulationScene.getTransforms().setAll(xRotate, yRotate);
		// xRotate.angleProperty().bind(simulationViewRotateAngleX);
		// yRotate.angleProperty().bind(simulationViewRotateAngleY);

	}

	private Cylinder[] getAxes(Point3D origin) {

		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		Cylinder[] axes = new Cylinder[3];
		axes[0] = build3DLine(origin, new Point3D(origin.getX() + AXIS_LENGTH, origin.getY(), origin.getZ()));
		axes[0].setMaterial(redMaterial);
		axes[1] = build3DLine(origin, new Point3D(origin.getX(), origin.getY() + AXIS_LENGTH, origin.getZ()));
		axes[1].setMaterial(greenMaterial);
		axes[2] = build3DLine(origin, new Point3D(origin.getX(), origin.getY(), origin.getZ() + AXIS_LENGTH));
		axes[2].setMaterial(blueMaterial);
		return axes;
	}

	private Cylinder build3DLine(Point3D origin, Point3D target) {
		// took from http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/

		Point3D yAxis = new Point3D(0, 1, 0);
		Point3D diff = target.subtract(origin);
		double height = diff.magnitude();

		Point3D mid = target.midpoint(origin);
		Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

		Point3D axisOfRotation = diff.crossProduct(yAxis);
		double angle = Math.acos(diff.normalize().dotProduct(yAxis));
		Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

		Cylinder line = new Cylinder(LINE_SIZE, height);

		line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

		return line;
	}

	private Cylinder[] getSimulationBoundaryBox(double x, double y, double z) {
		Cylinder[] boundaryBox = new Cylinder[12];

		boundaryBox[0] = build3DLine(new Point3D(0, 0, 0), new Point3D(x, 0, 0));
		boundaryBox[1] = build3DLine(new Point3D(0, 0, 0), new Point3D(0, y, 0));
		boundaryBox[2] = build3DLine(new Point3D(x, 0, 0), new Point3D(x, y, 0));
		boundaryBox[3] = build3DLine(new Point3D(0, y, 0), new Point3D(x, y, 0));

		boundaryBox[4] = build3DLine(new Point3D(0, 0, z), new Point3D(x, 0, z));
		boundaryBox[5] = build3DLine(new Point3D(0, 0, z), new Point3D(0, y, z));
		boundaryBox[6] = build3DLine(new Point3D(x, 0, z), new Point3D(x, y, z));
		boundaryBox[7] = build3DLine(new Point3D(0, y, z), new Point3D(x, y, z));

		boundaryBox[8] = build3DLine(new Point3D(0, 0, 0), new Point3D(0, 0, z));
		boundaryBox[9] = build3DLine(new Point3D(x, 0, 0), new Point3D(x, 0, z));
		boundaryBox[10] = build3DLine(new Point3D(0, y, 0), new Point3D(0, y, z));
		boundaryBox[11] = build3DLine(new Point3D(x, y, 0), new Point3D(x, y, z));

		return boundaryBox;
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
