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
		animationView.getStartButton().setOnAction(
				actionEvent -> ((ScheduledService<Double>) simulation.getWorker()).start());
//		for (int i=0; i<animationView.getParticles().length; i++){
//			animationView.getParticles()[i].translateXProperty().bind(simulation.getWorker().valueProperty());
//		}
		simulation.getWorker().setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				animationView.getParticles()[0].setTranslateX((Double) event.getSource().getValue());
			}
			
		});
//		animationView.getParticles()[0].translateXProperty().bind(simulation.getWorker().valueProperty());
		
	}

}
