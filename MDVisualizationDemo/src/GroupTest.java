import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class GroupTest extends Application {
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox rootBox = new HBox(5);
		double[] systemBounday = new double[] {400,100,100};
		
		//*****
		Box invisibleBox1 = new Box(systemBounday[0], systemBounday[1], systemBounday[2]);
		invisibleBox1.setTranslateX(systemBounday[0] / 2);
		invisibleBox1.setTranslateY(systemBounday[1] / 2);
		invisibleBox1.setTranslateZ(systemBounday[2] / 2);
		invisibleBox1.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
		
		Sphere test1 = new Sphere(10, 64);
		Group group1 = new Group(invisibleBox1, test1);
		group1.setTranslateX(systemBounday[0]*0.1);
		group1.setTranslateY(systemBounday[1]*0.1);
		group1.setTranslateZ(systemBounday[2]*0.1);
		
		SubScene scene1 = new SubScene(group1, systemBounday[0]*1.2, systemBounday[1]*1.2, true, SceneAntialiasing.BALANCED);
		scene1.setFill(Color.LIGHTCYAN);
		
		System.out.println("layout bounds");
		System.out.println(group1.getLayoutBounds());
		System.out.println("bounds in Local");
		System.out.println(group1.getBoundsInLocal());
		System.out.println("bounds in parent");
		System.out.println(group1.getBoundsInParent());
		
		System.out.println();
		
		//*****
		Box invisibleBox2 = new Box(systemBounday[0]*1.2, systemBounday[1]*1.2, systemBounday[2]*1.2);
		invisibleBox2.setTranslateX(systemBounday[0] / 2);
		invisibleBox2.setTranslateY(systemBounday[1] / 2);
		invisibleBox2.setTranslateZ(systemBounday[2] / 2);
		invisibleBox2.setMaterial(new PhongMaterial(Color.LIGHTYELLOW));
		
		Sphere test2 = new Sphere(10, 64);
		Group group2 = new Group(invisibleBox2, test2);
		group2.setTranslateX(systemBounday[0]*0.1);
		group2.setTranslateY(systemBounday[1]*0.1);
		group2.setTranslateZ(systemBounday[2]*0.1);
		
		SubScene scene2 = new SubScene(group2, systemBounday[0]*1.2, systemBounday[1]*1.2, true, SceneAntialiasing.BALANCED);
		scene2.setFill(Color.LIGHTCORAL);

		System.out.println("layout bounds");
		System.out.println(group2.getLayoutBounds());
		System.out.println("bounds in Local");
		System.out.println(group2.getBoundsInLocal());
		System.out.println("bounds in parent");
		System.out.println(group2.getBoundsInParent());
		
		//*****
		rootBox.getChildren().addAll(scene1, scene2);

		Scene scene = new Scene(rootBox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
