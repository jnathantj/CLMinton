package navbar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import scene.Cart;
import scene.History;
import scene.Home;
import scene.Login;

public class UserNav extends MenuBar {

	private Menu page;
	private MenuItem home, cart, history, logout;
	private Stage primaryStage;
	private String userId;

	public UserNav(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		this.userId = userId;
		init();
		initButton();
	}

	public void init() {
		page = new Menu("Page");

		home = new MenuItem("Home");
		cart = new MenuItem("Cart");
		history = new MenuItem("History");
		logout = new MenuItem("Logout");

		page.getItems().addAll(home, cart, history, logout);
		getMenus().add(page);

	}

	public void initButton() {
		home.setOnAction(event -> {
			new Home(primaryStage, userId);
		});

		cart.setOnAction(event -> {
			new Cart(primaryStage, userId);
		});

		history.setOnAction(event -> {
			new History(primaryStage, userId);
		});

		logout.setOnAction(event -> {
			new Login(primaryStage, userId);
		});
	}

}
