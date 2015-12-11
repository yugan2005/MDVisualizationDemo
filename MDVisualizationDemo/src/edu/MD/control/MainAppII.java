package edu.MD.control;

import edu.MD.model.MDSimulation;
import edu.MD.view.AnimationViewII;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainAppII extends Application {
	
	private MDSimulation model;
	private AnimationViewII view;
	private BorderPane rootPane;
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public MainAppII() {
		model = new MDSimulation(3);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		view = new AnimationViewII(model);
		
		rootPane = view.getRootPane();
		
		Scene scene = new Scene(rootPane);
		scene.setCamera(null);
		primaryStage.setScene(scene);
		
		hookupEvents();

		primaryStage.show();
		
	}

	private void hookupEvents() {
		
	}
	
	
	

}
