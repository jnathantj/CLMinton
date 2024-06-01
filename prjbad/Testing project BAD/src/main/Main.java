package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.Cart;
import scene.History;
import scene.HistoryAdmin;
import scene.Home;
import scene.Login;
import scene.Manage;
import scene.Register;

public class Main extends Application {

	
	public static void main(String[] args) {
		launch (args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		new Login(primaryStage, "");
	}
	
	

}
