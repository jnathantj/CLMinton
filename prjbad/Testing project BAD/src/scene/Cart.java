package scene;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import com.mysql.cj.xdevapi.Result;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import navbar.Connect;
import navbar.UserNav;

public class Cart {
	private Label yourCart, cartTotal, nameCart, brandCart, priceCart;
	private TableView<cartData> tableList;
	private ObservableList<cartData> tableData;
	private Button checkout, checkoutBtn, delete;
	private GridPane paneKiri, paneKanan, paneAtas, paneBawah, gridPane, gridPane1, gridPane2;
	private Stage primaryStage, popupTrans;
	private BorderPane tampilan, borderPane;
	private cartData selected = null;
	private String userId;
	private Integer totalPrice = 0;

	public Cart(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		this.userId = userId;
		this.init();
		this.setLayout();
		initBtnAction();

		Scene scene = new Scene(tampilan, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Cart");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	private void init() {

		yourCart = new Label("Your Cart List");
		cartTotal = new Label("Total Price    :");
		nameCart = new Label("Name      :");
		brandCart = new Label("Brand     :");
		priceCart = new Label("Price      :");

		checkout = new Button("Checkout");
		delete = new Button("Delete Product");

		setTable();
		setData();
	}

	public void setLayout() {
		gridPane = new GridPane();
		gridPane2 = new GridPane();
		paneKiri = new GridPane();
		paneKanan = new GridPane();
		paneAtas = new GridPane();
		paneBawah = new GridPane();
		tampilan = new BorderPane();

		yourCart.setFont(new Font(30));
		paneAtas.add(yourCart, 0, 0);
		paneAtas.add(gridPane, 0, 1);

		paneKiri.add(tableList, 0, 0);

		paneKanan.add(nameCart, 0, 0);
		paneKanan.add(brandCart, 0, 1);
		paneKanan.add(priceCart, 0, 2);
		paneKanan.add(cartTotal, 0, 3);
		paneKanan.setVgap(10);

		gridPane.add(paneKiri, 0, 0);
		gridPane.add(paneKanan, 1, 0);
		gridPane.setVgap(12);

		paneBawah.add(checkout, 0, 0);
		paneBawah.add(delete, 0, 1);

		gridPane1 = new GridPane();
		gridPane2 = new GridPane();

		gridPane1.add(paneAtas, 0, 0);
		gridPane1.add(gridPane, 0, 1);
		gridPane2.add(gridPane1, 0, 0);

		tampilan.setCenter(paneBawah);
		gridPane2.add(paneBawah, 0, 1);

		checkout.setPrefSize(600, 20);
		delete.setPrefSize(600, 20);
		paneBawah.setVgap(10);
		gridPane2.setVgap(10);

		gridPane.setHgap(20);
		gridPane.setVgap(45);
		gridPane2.setAlignment(Pos.CENTER);
		tampilan.setCenter(gridPane2);
		tampilan.setTop(new UserNav(primaryStage, userId));
	}

	private void setTable() {

		tableData = FXCollections.observableArrayList();

		tableList = new TableView<>(tableData);
		TableColumn<cartData, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setPrefWidth(100);

		TableColumn<cartData, String> brandCol = new TableColumn<>("Brand");
		brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
		brandCol.setPrefWidth(100);

		TableColumn<cartData, Integer> stockCol = new TableColumn<>("Quantity");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
		stockCol.setPrefWidth(100);

		TableColumn<cartData, Integer> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceCol.setPrefWidth(100);

		TableColumn<cartData, Integer> totalCol = new TableColumn<>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));
		totalCol.setPrefWidth(100);

		cartTotal.setText("Total Price : " + totalPrice);

		tableList.getColumns().addAll(nameCol, brandCol, stockCol, priceCol, totalCol);

	}

	private void setData() {
		tableData.removeAll(tableData);
		String query = String.format(
				"SELECT * FROM `carttable` JOIN msproduct ON carttable.ProductID = msproduct.ProductID WHERE UserID = '%s'",
				userId);

		try {
			ResultSet hasil = Connect.getInstance().executeQuery(query);

			while (hasil.next()) {
				String productId = hasil.getString("productID");
				String name = hasil.getString("ProductName");
				String brand = hasil.getString("ProductMerk");
				Integer qty = hasil.getInt("Quantity");
				Integer price = hasil.getInt("ProductPrice");
				Integer total = qty * price;
				totalPrice = totalPrice + total;

				cartData data = new cartData(userId, productId, name, brand, qty, price, total);
				tableData.add(data);

			}
			cartTotal.setText("Total Price : " + totalPrice);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void popup() {
		popupTrans = new Stage();
		popupTrans.initModality(Modality.APPLICATION_MODAL);
		popupTrans.setTitle("Transaction Card");
		checkoutBtn = new Button("Checkout");
		Label card = new Label("Transaction Card");
		Label list = new Label("List");

		ResultSet recingSport;
		String prName = "";
		Integer prPrice = 0;
		Integer prQty = 0;
		String itemCart = "";
		String que = String.format(
				"SELECT * FROM msproduct JOIN carttable ON msproduct.ProductID = carttable.ProductID WHERE UserID = '%s'",
				userId);
		try {
			recingSport = Connect.getInstance().executeQuery(que);
			while (recingSport.next()) {
				prName = recingSport.getString("ProductName");
				prPrice = recingSport.getInt("ProductPrice");
				prQty = recingSport.getInt("Quantity");
				Integer totPrice = prPrice * prQty;
				itemCart += prName + " : " + totPrice + "\n";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		Label cartItem = new Label();
		cartItem.setText(itemCart);
		Label courier = new Label("Courier");
		Label totPrice = new Label();

		ComboBox<String> courierInput = new ComboBox<>();
		courierInput.getItems().addAll("Nanji Express", "Gejok", "J&E", "JET");
		courierInput.getSelectionModel().selectFirst();
		CheckBox insurance = new CheckBox("Use Insurance");
		VBox vbox = new VBox(20);

		borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: #759cb4;");

		card.setStyle("-fx-background-color: #424648; -fx-border-color: black; "
				+ "-fx-border-width: 2px; -fx-border-radius: 4px; ");
		card.setTextFill(Color.WHITE);
		card.setPrefWidth(1000);
		card.setPrefHeight(25);
		card.setFont(new Font(18));

		list.setFont(new Font(16));
		courier.setFont(new Font(16));
		insurance.setFont(new Font(16));
		totPrice.setFont(new Font(16));
		courierInput.setPrefWidth(100);
		checkoutBtn.setPrefSize(200, 30);

		totPrice.setText("Total Price    : " + this.totalPrice);

		insurance.setOnAction(event -> {
			Integer finalePrice = 0;
			Integer insuranceYesNo = 0;
			if (insurance.isSelected()) {
				finalePrice = this.totalPrice + 90000;
				insuranceYesNo = 1;
				// TODO Auto-generated method stub
			} else {
				finalePrice = this.totalPrice;
				insuranceYesNo = 0;
			}

			totPrice.setText("Total Price    : " + finalePrice);
		});

		vbox.getChildren().addAll(list, cartItem, courier, courierInput, insurance, totPrice, checkoutBtn);
		vbox.setAlignment(Pos.CENTER);

		borderPane.setCenter(vbox);
		borderPane.setTop(card);
		card.setAlignment(Pos.TOP_CENTER);

		Scene popupScene = new Scene(borderPane, 1000, 600);
		popupTrans.setScene(popupScene);
		popupTrans.show();

		checkoutBtn.setOnAction(event -> {
			Alert information = new Alert(AlertType.INFORMATION);
			information.setTitle("Confirmation");
			information.setHeaderText("Are you sure want to checkout all the item?");
			information.setContentText("Need Confirmation");
			ButtonType btnYes = new ButtonType("Yes");
			ButtonType btnNo = new ButtonType("No");
			information.getButtonTypes().setAll(btnYes, btnNo);
			Optional<ButtonType> res = information.showAndWait();

			res.ifPresent(buttonType -> {
				if (buttonType == btnYes) {
					popupTrans.close();

					ResultSet resingSport;
					String query = String.format("SELECT * FROM 'carttable'");

					try {
						resingSport = Connect.getInstance().executeQuery(query);

					} catch (SQLException e) {

						Integer insuranceYesNo;
						if (insurance.isSelected()) {
							insuranceYesNo = 1;
							// TODO Auto-generated method stub
						} else {
							insuranceYesNo = 0;
						}

						String id = "";
						Integer maxId = 0;
						String query1 = "SELECT COUNT(*) AS total FROM transactionheader";
						try {
							ResultSet hasil = Connect.getInstance().executeQuery(query1);

							while (hasil.next()) {
								maxId = hasil.getInt("total");
							}
						} catch (SQLException e1) {
							// TODO: handle exception
						}

						if (maxId < 10) {
							id = "TH" + "00" + (maxId + 1);
						} else if (maxId < 100) {
							id = "TH" + "0" + (maxId + 1);
						}

						String add = String.format(
								"INSERT INTO `transactionheader`(`TransactionID`, `UserID`, `TransactionDate`, `DeliveryInsurance`, `CourierType`) VALUES ('%s','%s', NOW(),'%s','%s')",
								id, userId, insuranceYesNo, courierInput.getSelectionModel().getSelectedItem());

						try {
							Connect.getInstance().executeUpdate(add);
						} catch (SQLException e2) {
							// TODO: handle exception
						}

						ResultSet rs;
						ArrayList<transactionDetail> detailArrayList = new ArrayList<>();
						String query11 = String.format("SELECT * FROM `carttable` WHERE UserID = '%s'", userId);
						try {
							rs = Connect.getInstance().executeQuery(query11);

							while (rs.next()) {
								String prodId = rs.getString("ProductID");
								Integer qty = rs.getInt("Quantity");

								transactionDetail detail = new transactionDetail(prodId, id, "", "", 0, qty, 0);
								detailArrayList.add(detail);
							}
						} catch (Exception ez) {

						}

						for (transactionDetail detail : detailArrayList) {
							String prodId = detail.getProductId();
							String transId = detail.getTransactionId();
							Integer qty = detail.getQty();
							String addDetail = String.format(
									"INSERT INTO `transactiondetail`(`ProductID`, `TransactionID`, `Quantity`) VALUES ('%s','%s','%s')",
									prodId, transId, qty);

							try {
								Connect.getInstance().executeUpdate(addDetail);
							} catch (Exception e2) {
								// TODO: handle exception
							}

						}

						String remove = String.format("DELETE FROM `carttable` WHERE UserID = '%s'", userId);

						try {
							Connect.getInstance().executeUpdate(remove);
						} catch (SQLException e1) {
							// TODO: handle exception
						}
						setData();
						nameCart.setText("Product Name: ");
						brandCart.setText("Product Brand: ");
						priceCart.setText("Price       : ");
						cartTotal.setText("Total       : ");

					}
				} else {

				}
			});

		});

	}

	private void showTransactionDetails(String transactionID, VBox vbox) {

	}

	private void alert() {

	}

	private void initBtnAction() {
		tableList.setOnMouseClicked(e -> {
			selected = tableList.getSelectionModel().getSelectedItem();
			nameCart.setText("Product Name: " + selected.getName());
			brandCart.setText("Product Brand: " + selected.getBrand());
			priceCart.setText("Price       : " + selected.getPrice());
			cartTotal.setText("Total       : " + selected.getPrice() * selected.getQty());

		});

		delete.setOnMouseClicked(e -> {
			if (selected == null) {
				new Exception("Error");
			}

			ResultSet rs;
			Integer stck = 0;
			String id = "";
			String query = String.format("SELECT * FROM `msproduct` WHERE `ProductName`= '%s'", selected.getName());
			try {
				rs = Connect.getInstance().executeQuery(query);

				while (rs.next()) {
					stck = rs.getInt("ProductStock");
					id = rs.getString("ProductID");

				}
			} catch (SQLException e1) {

			}

			Integer revisedStock = stck + selected.getQty();

			String remove = String.format("DELETE FROM `carttable` WHERE UserID = '%s' AND ProductID = '%s' ", userId,
					id);

			String updateStock = String.format("UPDATE `msproduct` SET `ProductStock`='%s' WHERE `ProductID`= '%s'",
					revisedStock, id);

			try {
				Connect.getInstance().executeUpdate(updateStock);

			} catch (Exception e3) {
				// TODO: handle exception
			}

			try {

				Connect.getInstance().executeUpdate(remove);

			} catch (Exception e2) {
				// TODO: handle exception
			}
//	

			nameCart.setText("Product Name: ");
			brandCart.setText("Product Brand: ");
			priceCart.setText("Price       : ");
			cartTotal.setText("Total       : ");
			setData();
		});

		checkout.setOnAction(e -> {
			if (tableData.isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING, "Please insert item to your cart", ButtonType.OK);
				alert.setTitle("WARNING");
				alert.show();
			} else {
				popup();
			}

		});

	}

}
