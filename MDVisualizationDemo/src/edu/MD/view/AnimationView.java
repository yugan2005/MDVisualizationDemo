package edu.MD.view;

import edu.MD.model.MDSimulation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Sphere;

public class AnimationView {
	
	private MDSimulation simulation;
	private int particleNumber;
	private AnchorPane simulationPane;
	private Group particleGroup;
	private double[][] particlePositions;
	private Button startButton;
	private Sphere[] particles;
	
	public Sphere[] getParticles() {
		return particles;
	}

	public Button getStartButton() {
		return startButton;
	}

	public AnimationView(MDSimulation simulation){
		simulationPane = new AnchorPane();
		simulationPane.setPrefHeight(600);
		simulationPane.setPrefWidth(800);
		
		this.simulation = simulation;
		particleNumber = this.simulation.getParticleNumber();
		particlePositions = this.simulation.getPositions();
		particleGroup = new Group();
		particles = new Sphere[particleNumber];
		for (int i=0; i<particleNumber; i++){
			particles[i] = new Sphere(10, 128);
			particles[i].setTranslateX(particlePositions[0][i]);
			particles[i].setTranslateY(particlePositions[1][i]);
			particles[i].setTranslateZ(particlePositions[2][i]);
		}
		particleGroup.getChildren().addAll(particles);
		
		startButton = new Button("Start");
		simulationPane.getChildren().add(particleGroup);
		
		AnchorPane.setTopAnchor(particleGroup, 10.0);
		AnchorPane.setBottomAnchor(particleGroup, 50.0);
		AnchorPane.setLeftAnchor(particleGroup, 10.0);
		AnchorPane.setRightAnchor(particleGroup, 10.0);
		
		simulationPane.getChildren().add(startButton);
		AnchorPane.setBottomAnchor(startButton, 10.0);
	}

	public AnchorPane getSimulationPane() {
		return simulationPane;
	}

}
