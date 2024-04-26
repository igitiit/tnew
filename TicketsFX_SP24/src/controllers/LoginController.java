package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.LoginModel;

public class LoginController {
    //decLare objects to relate to controls in your fxml file
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
 
	
	public void login() throws IOException {
	
		String username = this.username.getText();
		String password = this.password.getText();
	 
		//Get user
		LoginModel l = new LoginModel();
		boolean blnUser = l.findByUsername(username);
		l.close();
		//If user not found
		if(blnUser == false) {
			System.out.println("Username cannot be found");
			return;
		}
	//Set user as Logged in user + valid user by role
		try {
			if(l.getRole().equals("1")) {
				System.out.println("Username logged in...");
				Parent myParent = FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
                Scene scene = new Scene(myParent);
                scene.getStylesheets().add("application/application.css");
                Stage appStage = new Stage(); 
                appStage.setScene(scene);
                appStage.show();
			}
		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}  

	}

	public void setDialogStage(Stage primaryStage) {
		// TODO Auto-generated method stub
	}
}