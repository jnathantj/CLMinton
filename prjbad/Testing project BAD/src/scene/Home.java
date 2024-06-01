package scene;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

//import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import navbar.Connect;
import navbar.UserNav;

public class Home {
	private Label productList, productName, productBrand, productPrice, productPriceTotal;
	private TableView<productData> tableList;
	private Spinner<Integer> quantity;
	private ObservableList<productData> tableData;
	private Button addBtn;
	private GridPane gridPane, paneKiri, paneKanan, paneAtas;
	private Stage primaryStage;
	private BorderPane tampilan;
	private ArrayList<productData> iData;
	private productData selected = null;
	private String userId;

	public Home(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		this.userId = userId;
		this.init();
		this.setLayout();
		this.initButtonAction();

		Scene scene = new Scene(tampilan, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Home");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	private void init() {

		productList = new Label("Product List");
		productName = new Label("Product Name: ");
		productBrand = new Label("Product Brand: ");
		productPrice = new Label("Price     : ");
		productPriceTotal = new Label("Total Price   : ");

		quantity = new Spinner<>(1, 100, 1);
		quantity.setEditable(true);

		addBtn = new Button("Add to Cart");

		setTable();
		setData();

	}

	public void setLayout() {
		tampilan = new BorderPane();
		gridPane = new GridPane();
		paneKiri = new GridPane();
		paneKanan = new GridPane();
		paneAtas = new GridPane();

		gridPane.add(paneKiri, 0, 0);
		gridPane.add(paneKanan, 1, 0);
		gridPane.setVgap(12);

		paneKiri.add(tableList, 0, 1);

		paneKanan.add(productName, 0, 0);
		paneKanan.add(productBrand, 0, 1);
		paneKanan.add(productPrice, 0, 2);
		paneKanan.add(quantity, 0, 3);
		paneKanan.add(productPriceTotal, 0, 4);
		paneKanan.add(addBtn, 0, 5);
		paneKanan.setVgap(10);

		productList.setFont(new Font(30));
		paneAtas.add(productList, 0, 0);
		paneAtas.add(gridPane, 0, 1);

		gridPane.setHgap(20);
		paneAtas.setAlignment(Pos.CENTER);
		tampilan.setCenter(paneAtas);
		tampilan.setTop(new UserNav(primaryStage, userId));
	}

	private void setTable() {

		tableData = FXCollections.observableArrayList();

		tableList = new TableView<>(tableData);

		TableColumn<productData, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setPrefWidth(100);

		TableColumn<productData, String> brandCol = new TableColumn<>("Brand");
		brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
		brandCol.setPrefWidth(100);

		TableColumn<productData, Integer> stockCol = new TableColumn<>("Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		stockCol.setPrefWidth(100);

		TableColumn<productData, Integer> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceCol.setPrefWidth(100);

		tableList.getColumns().addAll(nameCol, brandCol, stockCol, priceCol);

	}

	private void setData() {
		tableData.removeAll(tableData);
		String query = "SELECT * FROM `msproduct` WHERE ProductStock > 0";

		try {
			ResultSet hasil = Connect.getInstance().executeQuery(query);

			while (hasil.next()) {

				String productId = hasil.getString("ProductID");
				String name = hasil.getString("ProductName");
				String brand = hasil.getString("ProductMerk");
				Integer stock = hasil.getInt("ProductStock");
				Integer price = hasil.getInt("ProductPrice");

				productData data = new productData(productId, name, brand, stock, price);
				tableData.add(data);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initButtonAction() {

		tableList.setOnMouseClicked(e -> {
			selected = tableList.getSelectionModel().getSelectedItem();

			productName.setText("Product Name: " + selected.getName());
			productBrand.setText("Product Brand: " + selected.getBrand());
			productPrice.setText("Price       : " + selected.getPrice());
			productPriceTotal.setText("Total       : " + selected.getPrice() * quantity.getValue());
		});

		quantity.setOnMouseClicked(e -> {

			productPriceTotal.setText("Total       : " + selected.getPrice() * quantity.getValue());
		});

		addBtn.setOnAction(e -> {

			if (selected == null) {
				Alert alert = new Alert(AlertType.WARNING, "Please Choose 1 Item", ButtonType.OK);
				alert.setTitle("WARNING");
				alert.show();
			}

			ResultSet rs;
			Integer stck = 0;
			String id = "";
			String query = String.format("SELECT * FROM `msproduct` WHERE `ProductName`= '%s'", selected.getName());
			try {
				rs = Connect.getInstance().executeQuery(query);

				while (rs.next()) {
					id = rs.getString("ProductID");
					stck = rs.getInt("ProductStock");
				}
			} catch (SQLException e1) {

			}

			Integer qty = quantity.getValue();
			Integer updateStock = stck - qty;

			String add = String.format(
					"INSERT INTO `carttable`(`UserID`, `ProductID`, `Quantity`) " + "VALUES ('%s','%s','%s')", userId,
					id, qty);

			String update = String.format("UPDATE `msproduct` SET `ProductStock`='%s' WHERE ProductID = '%s'",
					updateStock, id);

			try {
				Connect.getInstance().executeUpdate(update);
				Connect.getInstance().executeUpdate(add);
			} catch (Exception e2) {
				// TODO: handle exception
			}

			setData();
			refreshForm();

		});

	}

	private void refreshForm() {
		productName.setText("Product Name: ");
		productBrand.setText("Product Brand: ");
		productPrice.setText("Price       : ");
		productPriceTotal.setText("Total       : ");
	}

}
