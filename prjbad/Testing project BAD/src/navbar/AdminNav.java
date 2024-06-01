package navbar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import scene.HistoryAdmin;
import scene.Login;
import scene.Manage;

public class AdminNav extends MenuBar {
	private Menu admin;
	private MenuItem manage, view, logout;
	private Stage primaryStage;
	private String userId;
	
	public AdminNav(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		this.userId = userId;
		init();
		setLayout();
		initButton();
	}
	
	public void init(){
		admin = new Menu("Admin");
		
		manage = new MenuItem("Manage Product");
		view = new MenuItem("View History");
		logout = new MenuItem("Logout");
		
		admin.getItems().addAll(manage, view, logout);
		getMenus().add(admin);
		
		
	}
	
	public void setLayout() {
		
	}
	
	public void initButton(){
		manage.setOnAction(event -> {
			new Manage(primaryStage, userId);	
		});
		
		view.setOnAction(event -> {
			new HistoryAdmin(primaryStage, userId);
		});
		
		logout.setOnAction(event -> {
			new Login(primaryStage, userId);
		});
	}
	
}
