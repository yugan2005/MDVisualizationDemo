package edu.MD.model;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class MDSimulation {
	private double[][] positions;

	private int particleNumber;
	
	private ScheduledService<Double> worker;


	public ScheduledService<Double> getWorker() {
		return worker;
	}

	public MDSimulation(int particleNumber) {
		this.particleNumber = particleNumber;
		positions = new double[3][particleNumber];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < particleNumber; j++) {
				positions[i][j] = j * 100;
			}
		}
		worker = new ScheduledService<Double>(){
			@Override
			protected Task<Double> createTask() {
				return new Task<Double>() {

					@Override
					protected Double call() throws Exception {
						positions[0][1] += 10;
//						double[][] newPosition = new double[3][particleNumber];
//						for (int i = 0; i < 3; i++) {
//							for (int j = 0; j < particleNumber; j++) {
//								newPosition[i][j] = (j % 2 == 0) ? positions[i][j] - 10 : positions[i][j] + 10;
//							}
//						}
						return positions[0][1];
					}

				};
			}
			
		};
		worker.setPeriod(Duration.millis(100));
	}

	public double[][] getPositions() {
		return positions;
	}

	

	public int getParticleNumber() {
		return particleNumber;
	}
}
