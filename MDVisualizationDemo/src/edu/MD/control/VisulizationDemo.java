package edu.MD.control;

import edu.MD.model.MDSimulation;
import edu.MD.view.AnimationView;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class VisulizationDemo extends Application {
	private MDSimulation simulation;
	private AnimationView animationView;
	private AnchorPane simulationPane;
	private Scene scene;
	private double anchorX;
	private double anchorY;
	private double anchorAngleX = 0;
	private double anchorAngleY = 0;
	private final DoubleProperty angleX = new SimpleDoubleProperty(0);
	private final DoubleProperty angleY = new SimpleDoubleProperty(0);
	
    PerspectiveCamera camera = new PerspectiveCamera(false);


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
		Box box = new Box(300, 100, 100);
		box.setTranslateX(100);
		box.setTranslateY(100);
		box.setTranslateZ(100);
		box.setMaterial(new PhongMaterial(Color.RED));
		
		simulationPane.getChildren().add(box);
		scene = new Scene(simulationPane);
		Rotate xRotate;
        Rotate yRotate;
        simulationPane.getTransforms().setAll(
            xRotate = new Rotate(0, Rotate.X_AXIS),
            yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);
        
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
