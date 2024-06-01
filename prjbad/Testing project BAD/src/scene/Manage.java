																																																																																																																																package scene;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import navbar.AdminNav;
import navbar.Connect;

public class Manage extends BorderPane {
	private TableView<productData> tableList;
	private ObservableList<productData> tableData;
	private Label productList, name, brand, price, productName, add, delete;
	private TextField nameInput, priceInput;
	private ComboBox<String> brandBox;
	private Spinner<Integer> qty;
	private Button productBtn, stockBtn, deleteBtn;
	private GridPane paneAtas, paneKiri, paneKanan, paneAtas2, paneKiri2, paneKanan2, gridPane, gridPane2, formPane;
	private Stage primaryStage;
	private BorderPane tampilan;
	private ArrayList<productData> iData;
	private String userId;
	private productData selected = null;

	public Manage(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		iData = new ArrayList<productData>();
		this.init();
		this.setLayout();
		setAction();
//		setTableData();

		Scene scene = new Scene(tampilan, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Manage");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void init() {

		productList = new Label("Product List");
		name = new Label("Product Name");
		brand = new Label("Product Brand");
		price = new Label("Product Price");
		productName = new Label("Name     :");
		add = new Label("Add Stock");
		delete = new Label("Delete Product");

		productBtn = new Button("Add Product");
		stockBtn = new Button("Add Stock");
		deleteBtn = new Button("Delete");

		nameInput = new TextField();
		priceInput = new TextField();

		brandBox = new ComboBox<>();
		brandBox.getItems().addAll("Yonex", "Li-ning", "Victor");
		brandBox.getSelectionModel().selectFirst();

		qty = new Spinner<>(0, 100, 1);
		qty.setEditable(true);

		setTable();
		setTableData();
	}

	public void setLayout() {
		paneAtas = new GridPane();
		paneKiri = new GridPane();
		paneKanan = new GridPane();
		paneKiri2 = new GridPane();
		paneKanan2 = new GridPane();
		paneAtas2 = new GridPane();
		gridPane = new GridPane();
		gridPane2 = new GridPane();
		formPane = new GridPane();
		tampilan = new BorderPane();
		VBox vbox = new VBox(20);

		paneAtas.add(productList, 0, 0);
		productList.setFont(new Font(20));

		paneKiri.add(tableList, 0, 0);

		paneKanan.add(name, 0, 0);
		paneKanan.add(nameInput, 0, 1);
		paneKanan.add(brand, 0, 2);
		paneKanan.add(brandBox, 0, 3);
		paneKanan.add(price, 0, 4);
		paneKanan.add(priceInput, 0, 5);
		paneKanan.add(productBtn, 0, 6);
		paneKanan.setVgap(15);

		gridPane.add(paneKiri, 0, 0);
		gridPane.add(paneKanan, 1, 0);
		gridPane.setHgap(15);

		paneAtas2.add(productName, 0, 0);

		paneKiri2.add(add, 0, 0);
		paneKiri2.add(qty, 0, 1);
		paneKiri2.add(stockBtn, 0, 2);
		paneKiri2.setVgap(15);

		paneKanan2.add(delete, 0, 0);
		paneKanan2.add(deleteBtn, 0, 1);
		paneKanan2.setVgap(50);

		gridPane2.add(paneKiri2, 0, 0);
		gridPane2.add(paneKanan2, 1, 0);
		gridPane2.setHgap(40);
		paneAtas2.setAlignment(Pos.CENTER);
		gridPane2.setAlignment(Pos.CENTER);

//		formPane.add(paneAtas, 0, 0);
//		formPane.add(gridPane, 0, 1);
//		formPane.add(paneAtas2, 0, 2);
//		formPane.add(gridPane2, 0, 3);
		vbox.getChildren().addAll(paneAtas, gridPane, paneAtas2, gridPane2);
		formPane.add(vbox, 0, 0);
		tampilan.setCenter(formPane);
		formPane.setAlignment(Pos.CENTER);
		tampilan.setTop(new AdminNav(primaryStage, userId));

	}

	public void setTable() {

		tableData = FXCollections.observableArrayList();

		tableList = new TableView<>(tableData);
//		tableList.setMaxWidth(265);
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

	private productData getFormData() {
		String id = "", name, brandd = "";
		Integer maxId = 0, price, quantity;
		Random rand = new Random();

		String query1 = "SELECT COUNT(*) AS total FROM msproduct";
        try {
            ResultSet hasil = Connect.getInstance().executeQuery(query1);

            while (hasil.next()) {
                maxId = hasil.getInt("total");
            }
        } catch (SQLException e1) {
            // TODO: handle exception
        }

        if (maxId < 10) {
            id = "PD"+"00"+ (maxId+1);
        } else if (maxId < 100) {
            id = "PD"+"0"+ (maxId+1);
        }


		name = nameInput.getText();
		brandd = brandBox.getValue();
		price = Integer.parseInt(priceInput.getText());
		quantity = 0;

		return new productData(id, name, brandd, quantity, price);

	}

	private void setAction() {
		productBtn.setOnAction(e -> {
			productData data = getFormData();
			
			String query = String.format(
					"INSERT INTO `msproduct`(`ProductID`, `ProductName`, `ProductMerk`, `ProductPrice`, `ProductStock`) "
							+ "VALUES ('%s','%s','%s','%s','%s')",
					data.getProductId(), data.getName(), data.getBrand(), data.getPrice(), data.getStock());

			try {
				Connect.getInstance().executeUpdate(query);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// clear form
			nameInput.setText("");
			priceInput.setText("");
			setTableData();
			
			
		});

		tableList.setOnMouseClicked(e -> {
			selected = tableList.getSelectionModel().getSelectedItem();
			productName.setText("Name     : " + selected.getName());
		});

		stockBtn.setOnAction(e -> {
			Integer stock = qty.getValue() + selected.getStock();
			String update = String.format("UPDATE `msproduct` SET `ProductStock`='%s' WHERE ProductID = '%s' ", stock,
					selected.getProductId());


			try {
				Connect.getInstance().executeUpdate(update);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			setTableData();

		});

		 deleteBtn.setOnAction(e -> {
		        if (selected != null) {
		            String deleteQuery = String.format("DELETE FROM `msproduct` WHERE ProductID = '%s'", selected.getProductId());

		            try {
		                Connect.getInstance().executeUpdate(deleteQuery);
		                setTableData(); 
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
		        }
		    });
	}

	private void setTableData() {
		tableData.removeAll(tableData);
		String query = "SELECT * FROM `msproduct`";

		try {
			ResultSet hasil = Connect.getInstance().executeQuery(query);

			while (hasil.next()) {

				String id = hasil.getString("ProductID");
				String name = hasil.getString("ProductName");
				String brand = hasil.getString("ProductMerk");
				Integer price = hasil.getInt("ProductPrice");
				Integer stock = hasil.getInt("ProductStock");

				productData data = new productData(id, name, brand, stock, price);
				tableData.add(data);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
