package navbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scene.Login;
import scene.Register;

public class LogReg extends MenuBar implements EventHandler<ActionEvent> {

	private Menu page;
	private MenuItem login, register;
	private Stage primaryStage;
	private String userId;

	public LogReg(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		this.userId = userId;
		init();
		initButton();
	}

	public void init() {
		page = new Menu("Page");
		login = new MenuItem("Login");
		register = new MenuItem("Register");

		page.getItems().addAll(login, register);
		getMenus().add(page);

	}

	public void initButton() {
		login.setOnAction(event -> {
			new Login(primaryStage, userId);
		});

		register.setOnAction(event -> {
			new Register(primaryStage, userId);
		});
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

	}

}
