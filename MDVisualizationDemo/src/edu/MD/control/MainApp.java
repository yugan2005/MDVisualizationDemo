package edu.MD.control;

import java.net.URL;

import edu.MD.DAO.MDDataObject;
import edu.MD.view.RootPaneView;
import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private MDDataObject model;
	private RootPaneView view;

	public static void main(String[] args) {
		launch(args);
	}

	public MainApp() {
		model = MDDataObject.getInstance();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		URL fxmlUrl = MainApp.class.getResource("/edu/MD/view/RootPane.fxml");
		loader.setLocation(fxmlUrl);

		Parent root = loader.load();
		view = loader.<RootPaneView> getController();
		view.setView(this);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		hookupEvents();

		primaryStage.show();

	}

	private void hookupEvents() {

		view.getStartButton().setOnAction(actionEvent -> ((ScheduledService<double[][]>) model.getWorker()).restart());

		view.getPauseButton().setOnAction(actionEvent -> ((ScheduledService<double[][]>) model.getWorker()).cancel());

		model.getWorker().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				double[][] newPosition = (double[][]) event.getSource().getValue();

				for (int i = 0; i < newPosition.length; i++) {
					for (int j = 0; j < newPosition[i].length; j++) {
						switch (i) {
						case 0:
							view.getParticles()[j].setTranslateX(newPosition[i][j]);
							break;
						case 1:
							view.getParticles()[j].setTranslateY(newPosition[i][j]);
							break;
						case 2:
							view.getParticles()[j].setTranslateZ(newPosition[i][j]);
							break;
						}

					}

				}

			}

		});

	}

	public double[] getSystemBoundary() {
		return model.getSystemBoundary();
	}

	public double[][] getPositions() {
		return model.getPositions();
	}

	public int getParticleNumber() {
		return model.getParticleNumber();
	}

}
