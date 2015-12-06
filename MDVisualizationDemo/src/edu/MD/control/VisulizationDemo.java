package edu.MD.control;

import edu.MD.model.MDSimulation;
import edu.MD.view.AnimationView;
import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VisulizationDemo extends Application {
	private MDSimulation simulation;
	private AnimationView animationView;
	private AnchorPane simulationPane;

	public static void main(String[] args) {
		launch(args);
	}

	public VisulizationDemo() {
		simulation = new MDSimulation(5);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		animationView = new AnimationView(simulation);
		simulationPane = animationView.getSimulationPane();
		Scene scene = new Scene(simulationPane);
		primaryStage.setScene(scene);
		hookupEvents();
		primaryStage.show();
	}

	private void hookupEvents() {
		animationView.getStartButton()
				.setOnAction(actionEvent -> ((ScheduledService<double[][]>) simulation.getWorker()).restart());
		
		animationView.getStopButton()
		.setOnAction(actionEvent -> ((ScheduledService<double[][]>) simulation.getWorker()).cancel());

		
		
		simulation.getWorker().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				double[][] newPosition = (double[][]) event.getSource().getValue();

				for (int i = 0; i < newPosition.length; i++) {
					for (int j = 0; j < newPosition[i].length; j++) {
						switch (i) {
						case 0:
							animationView.getParticles()[j].setTranslateX(newPosition[i][j]);
							break;
						case 1:
							animationView.getParticles()[j].setTranslateY(newPosition[i][j]);
							break;
						case 2:
							animationView.getParticles()[j].setTranslateZ(newPosition[i][j]);
							break;
						}

					}

				}

			}

		});
		
		

	}

}
