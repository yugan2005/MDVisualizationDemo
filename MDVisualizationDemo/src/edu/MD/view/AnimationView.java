package edu.MD.view;

import edu.MD.model.MDSimulation;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Sphere;

public class AnimationView {
	
	private MDSimulation simulation;
	private int particleNumber;
	private AnchorPane simulationPane;
	private Pane particlePane;
	private double[][] particlePositions;
	private Button startButton;
	private Button stopButton;

	public Button getStopButton() {
		return stopButton;
	}

	private Sphere[] particles;
	
	public Sphere[] getParticles() {
		return particles;
	}

	public Button getStartButton() {
		return startButton;
	}

	public AnimationView(MDSimulation simulation){
		simulationPane = new AnchorPane();
		simulationPane.setPrefHeight(768);
		simulationPane.setPrefWidth(1024);
		
		this.simulation = simulation;
		particleNumber = this.simulation.getParticleNumber();
		particlePositions = this.simulation.getPositions();
		particlePane = new Pane();
		particles = new Sphere[particleNumber];
		for (int i=0; i<particleNumber; i++){
			particles[i] = new Sphere(10, 128);
			particles[i].setTranslateX(particlePositions[0][i]);
			particles[i].setTranslateY(particlePositions[1][i]);
			particles[i].setTranslateZ(particlePositions[2][i]);
		}
		particlePane.getChildren().addAll(particles);
		particlePane.setLayoutX(0);
		particlePane.setLayoutY(0);
		
		startButton = new Button("Start");
		simulationPane.getChildren().add(particlePane);
		stopButton = new Button("Stop");
		simulationPane.getChildren().add(stopButton);
		
		AnchorPane.setTopAnchor(particlePane, 10.0);
		AnchorPane.setBottomAnchor(particlePane, 50.0);
		AnchorPane.setLeftAnchor(particlePane, 10.0);
		AnchorPane.setRightAnchor(particlePane, 10.0);
		
		simulationPane.getChildren().add(startButton);
		AnchorPane.setBottomAnchor(startButton, 25.0);
		AnchorPane.setLeftAnchor(startButton, 10.0);
		AnchorPane.setRightAnchor(startButton, 10.0);


		AnchorPane.setBottomAnchor(stopButton, 0.0);
		AnchorPane.setLeftAnchor(stopButton, 10.0);
		AnchorPane.setRightAnchor(stopButton, 10.0);

	}

	public AnchorPane getSimulationPane() {
		return simulationPane;
	}

}
