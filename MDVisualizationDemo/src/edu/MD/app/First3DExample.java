package edu.MD.app;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

public class First3DExample extends Application {
	private DoubleProperty translateX = new SimpleDoubleProperty();
	private DoubleProperty translateZ = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(makeScene());
		primaryStage.show();
		animate();
	}

	private Scene makeScene() {
		Sphere s = new Sphere(100);
		s.setTranslateX(320);
		s.setTranslateY(240);
//		s.translateXProperty().bind(translateX);
		s.translateZProperty().bind(translateZ);
		
		Pane root = new Pane(s);
		
		Scene scene = new Scene(root, 640, 480);
		
		return scene;
	}
	
	private void animate(){
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0), new KeyValue(translateZ, 0)),
				new KeyFrame(Duration.seconds(2), new KeyValue(translateZ, 640))
				);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	public static void main(String[] args){
		launch(args);
	}

}
