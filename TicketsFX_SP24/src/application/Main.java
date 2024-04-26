package application;
	
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	

	public static Object stage;

	@Override
	public void start(Stage primaryStage) {
		try {
			//load LoginView
			FXMLLoader loader = new  FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
			//Inflate the view using the loader
            AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			//point to stylesheet for possible styles
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			 //Get the controller instance from the loader
            LoginController controller = loader.getController();
            //Set the parent stage in the controller
            controller.setDialogStage(primaryStage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean userLog(String text, String text2) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		launch(args);
	}

	


	}

