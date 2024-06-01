package scene;


import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import navbar.Connect;
import navbar.LogReg;


public class Login {
	private GridPane formPane;
	private Label username, password;
	private TextField userInput;
	private PasswordField passwordInput;
	private Button loginBtn;
	private Stage primaryStage;
	private String userId;
	
	private BorderPane tampilan;
	
	public GridPane getPane() {
		return formPane;
	}
		
	
	public Login(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		this.userId = userId;
		this.init();
		this.setLayout();
		this.initButtonAction();
		
		Scene scene = new Scene(tampilan, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		
		
	}
	
	private void init() {
		formPane = new GridPane();
		username = new Label("Email");
		password = new Label("Password");
		userInput = new TextField();
		passwordInput = new PasswordField();
		loginBtn = new Button("Login");
		
		tampilan = new BorderPane();
		
	}
	
	private void setLayout() {
		
		formPane.add(username, 0, 0);
		formPane.add(userInput, 0, 1);
		formPane.add(password, 0, 2);
		formPane.add(passwordInput, 0, 3);
		formPane.add(loginBtn, 0, 4);
		loginBtn.setPrefSize(100, 20);
		
		userInput.setPrefSize(200, 30);
		username.setFont(new Font(15));
		password.setFont(new Font(15));
		
		formPane.setVgap(10);
		formPane.setAlignment(Pos.CENTER);
		tampilan.setCenter(formPane);
		tampilan.setTop(new LogReg(primaryStage, userId));
		
	}
	
	private void initButtonAction() {
		loginBtn.setOnMouseClicked( event -> {
			String user = userInput.getText();
			String pass = passwordInput.getText();

			if(user.isEmpty() || pass.isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING, "Email or Password must be filled!", ButtonType.OK);
				alert.setTitle("WARNING");
				alert.show();
			}
			
			ResultSet logUser = null;
			String userEmail = "";
			String userPassword = "";
			String userRole = "";
			String userId = "";
			String query = String.format("SELECT * FROM msuser WHERE UserEmail = '%s' AND UserPassword = '%s'", user, pass);
			try {
				logUser = Connect.getInstance().executeQuery(query);
				
				while(logUser.next()){
					userId = logUser.getString("UserID");
					userEmail = logUser.getString("UserEmail");
					userPassword = logUser.getString("UserPassword");
					userRole = logUser.getString("UserRole");
				}
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
			}
			
			boolean userVerified = false;
			
			if(userEmail.equals(user) && userPassword.equals(pass)) {
				userVerified = true;
			}
			
			if (userVerified == false) {
				Alert alert = new Alert(AlertType.WARNING, "Wrong Email or Password", ButtonType.OK);
				alert.setTitle("WARNING");
				alert.show();
			}
			
			if(userRole.equals("Admin")) {
				new Manage(primaryStage, userId);
			} 
			else if(userRole.equals("User")) {
				new Home(primaryStage, userId);
			}
		});
	
	

}
}
