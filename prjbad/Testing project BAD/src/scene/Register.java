package scene;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import navbar.Connect;
import navbar.LogReg;


public class Register {
	private GridPane gridPane, paneKiri, paneKanan;
	private Label email, password, conPassword, age, gender, nationality;
	private TextField emailInput;
	private PasswordField passwordInput, conPasswordInput;
	private RadioButton maleBtn, femaleBtn;
	private ToggleGroup genderGroup;
	private ComboBox<String> nationalityInput;
	private Button regBtn;
	private Spinner<Integer> ageInput;
	private FlowPane genderPane;
	private Stage primaryStage;
	private String userId;
	
	private BorderPane tampilan;
	
	public GridPane getPane() {
		return gridPane;
	}
	
	public Register(Stage primaryStage, String userId){
		this.primaryStage = primaryStage;
		this.userId = userId;
		this.init();
		this.setLayout();
		this.initButtonAction();
		
		Scene scene = new Scene(tampilan, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Register");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private void init() {
		email = new Label("Email");
		password = new Label("Password");
		conPassword = new Label("Confirm Password");
		age = new Label("Age");
		gender = new Label("Gender");
		nationality = new Label("Nationality");
		
		emailInput = new TextField();
		passwordInput = new PasswordField();
		conPasswordInput = new PasswordField();
		
		genderPane = new FlowPane();
		genderGroup = new ToggleGroup();
		genderPane.setPrefWidth(100);
		maleBtn = new RadioButton("Male");
		femaleBtn = new RadioButton("Female");
		maleBtn.setUserData("Male");
		femaleBtn.setUserData("Female");
		genderPane.getChildren().addAll(maleBtn, femaleBtn);
		genderPane.setVgap(5);
		maleBtn.setToggleGroup(genderGroup);
		femaleBtn.setToggleGroup(genderGroup);
		
		nationalityInput = new ComboBox<>();
		nationalityInput.getItems().addAll("Indonesia", "Malaysia", "Singapore");
		nationalityInput.getSelectionModel().selectFirst();
		
		ageInput = new Spinner<>(1, 70, 1);
		ageInput.setEditable(true);
		
		regBtn = new Button("Register");
		regBtn.setPrefSize(100, 20);
		
		tampilan = new BorderPane();
	}
	
	private void setLayout() {
		paneKiri = new GridPane();
		paneKanan = new GridPane();
		gridPane = new GridPane();
		
		gridPane.add(paneKiri, 0, 0);
		gridPane.add(paneKanan, 1, 0);
		
		paneKiri.add(email, 0, 0);
		paneKiri.add(emailInput, 0, 1);
		paneKiri.add(password, 0, 2);
		paneKiri.add(passwordInput, 0, 3);
		paneKiri.add(conPassword, 0, 4);
		paneKiri.add(conPasswordInput, 0, 5);
		paneKiri.add(age, 0, 6);
		paneKiri.add(ageInput, 0, 7);
		
		paneKanan.add(gender, 0, 0);
		paneKanan.add(genderPane, 0, 1);
		paneKanan.add(nationality, 0, 2);
		paneKanan.add(nationalityInput, 0, 3);
		paneKanan.add(regBtn, 0, 4);
		
		gridPane.setHgap(25);
		paneKiri.setVgap(10);
		paneKanan.setVgap(10);
		
		email.setFont(new Font(15));
		gender.setFont(new Font(15));
		nationality.setFont(new Font(15));
		password.setFont(new Font(15));
		conPassword.setFont(new Font(15));
		age.setFont(new Font(15));
		
		gridPane.setAlignment(Pos.CENTER);
		tampilan.setCenter(gridPane);
		tampilan.setTop(new LogReg(primaryStage, userId));
		
	}
	
	private userData getFormData() throws Exception{
		String id = null, email, pass, role, gender, nation;
		Integer age, maxId = 0;
		
		String query = "SELECT COUNT(*) AS total FROM msuser";
        try {
            ResultSet hasil = Connect.getInstance().executeQuery(query);

            while (hasil.next()) {
                maxId = hasil.getInt("total");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (maxId < 10) {
            id = "UA"+"00"+ (maxId+1);
        } else if (maxId < 100) {
            id = "UA"+"0"+ (maxId+1);
        }
		
		
			
        String emailCheck = emailInput.getText();

            ResultSet logUser = null;
            String userEmail = "";
            String query1 = String.format("SELECT * FROM msuser WHERE UserEmail = '%s'", emailCheck);
            try {
                logUser = Connect.getInstance().executeQuery(query1);

                while(logUser.next()) {
                    userEmail = logUser.getString("UserEmail");

                }

            } catch (SQLException e) {
                    // TODO Auto-generated catch block
            }

            boolean userExist = false;
           
			
            if(userEmail.equals(emailCheck)) {
                userExist = true;
            }

            if (userExist == true) {
                throw new Exception("Email already been registered");
            } 
            
            
            if (!emailCheck.endsWith("@gmail.com")) {
                throw new Exception("Email must ends with @gmail.com");
            }
            
            if(passwordInput.getText().length() < 6) {
                throw new Exception("Password must be minimum of 6 characters");
            }
            
            if(!conPasswordInput.getText().equals(passwordInput.getText())) {
                throw new Exception("Confirm password must be the same as Password");
            }
            
            if(ageInput.getValue() < 0) {
                throw new Exception("Age must be greater than 0");
            }
            
            if(genderGroup.getSelectedToggle() == null) {
                throw new Exception("Gender is empty");
            }
            
            if(nationalityInput.getValue() == null) {
            	throw new Exception("Nationality is empty");
            }
			
		email = emailInput.getText();
		pass = passwordInput.getText();
		role = "User";
		age = ageInput.getValue();
		RadioButton selectgender = (RadioButton) genderGroup.getSelectedToggle();
		gender = genderGroup.getSelectedToggle().getUserData().toString();
		nation = nationalityInput.getValue();
		
		
		return new userData(id, email, pass, age, gender, nation, role);	
	}
	
		private void initButtonAction() {
			regBtn.setOnAction(event ->{


	            
	            
	            	try {
	            		userData data = getFormData();
	            		
		            	String register = String.format("INSERT INTO `msuser`(`UserID`, `UserEmail`, `UserPassword`, `UserAge`, `UserGender`, `UserNationality`, `UserRole`) VALUES"
		            			+ " ('%s','%s','%s','%s','%s','%s','%s')", data.getUserId(), data.getUserEmail(), data.getUserPassword(), data.getUserAge(), data.getUserGender(),
		            			data.getUserNationality(), data.getUserRole());
						Connect.getInstance().executeUpdate(register);
						
						emailInput.setText("");
		            	passwordInput.setText("");
		            	conPasswordInput.setText("");
		            	new Login(primaryStage, userId);
					} catch (Exception e) {
						// TODO: handle exception
						Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
		                alert.setTitle("WARNING");
		                alert.show();
						
					}
	            });
		}
			
		
}
