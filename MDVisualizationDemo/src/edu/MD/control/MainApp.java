package edu.MD.control;

import java.net.URL;

import edu.MD.model.MDSimulation;
import edu.MD.view.RootPaneView;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private MDSimulation model;
	private AnchorPane rootPane;
	private RootPaneView rootPaneView;
	private double anchorX, anchorY, anchorAngleX, anchorAngleY;
	private DoubleProperty angleX, angleY;
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public MainApp() {
		model = new MDSimulation(5);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		URL fxmlUrl = MainApp.class.getResource("/edu/MD/view/RootPane.fxml");
		loader.setLocation(fxmlUrl);
		
		rootPane = loader.<AnchorPane>load();
		rootPaneView = loader.<RootPaneView>getController();
		rootPaneView.setRootPaneView(model);
		
		angleX = rootPaneView.getSimulationRotateAngleX();
		angleY = rootPaneView.getSimulationRotateAngleY();
		
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		hookupEvents();

		primaryStage.show();
		
	}

	private void hookupEvents() {
		SubScene scene = rootPaneView.getSimulationScene();
		
		scene.setOnMousePressed((MouseEvent event) -> {
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();

		});

		scene.setOnMouseDragged((MouseEvent event) -> {
			angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
			angleY.set(anchorAngleY + anchorX - event.getSceneX());
		});		
	}
	
	
	

}
