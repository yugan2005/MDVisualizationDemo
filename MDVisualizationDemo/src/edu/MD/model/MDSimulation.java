package edu.MD.model;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class MDSimulation {
	private double[][] positions;

	private int particleNumber;
	
	private ScheduledService<double[][]> worker;


	public ScheduledService<double[][]> getWorker() {
		return worker;
	}

	public MDSimulation(int particleNumber) {
		this.particleNumber = particleNumber;
		positions = new double[3][particleNumber];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < particleNumber; j++) {
				positions[i][j] = j * 100+25;
			}
		}
		worker = new ScheduledService<double[][]>(){
			@Override
			protected Task<double[][]> createTask() {
				return new Task<double[][]>() {

					@Override
					protected double[][] call() throws Exception {
						double[][] newPosition = new double[3][particleNumber];
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < particleNumber; j++) {
								newPosition[i][j] = positions[i][j] + 10*(Math.random()-0.5);
							}
						}
						positions = newPosition;
						return newPosition;
					}

				};
			}
			
		};
		worker.setPeriod(Duration.millis(40));
	}

	public double[][] getPositions() {
		return positions;
	}

	

	public int getParticleNumber() {
		return particleNumber;
	}

	public double[] getSystemBoundary() {
		// TODO Auto-generated method stub
		return null;
	}
}
