package scene;

import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import navbar.AdminNav;
import navbar.Connect;


public class HistoryAdmin {
	private Label myTransactions, transactionDetail, totalPrice;
	private TableView<transactionDetail> detailList;
	private ObservableList<transactionDetail> detailData;
	private TableView<transactionHeaderDetail> transList;
	private ObservableList<transactionHeaderDetail> transData;
	private GridPane paneKiri, paneKanan, gridPane;
	private Stage primaryStage;
	private BorderPane tampilan;
	private String userId;
	private transactionHeaderDetail selected = null;
	private transactionDetail selectItem = null;
	

	public HistoryAdmin(Stage primaryStage, String userId) {
		this.primaryStage = primaryStage;
		this.userId = userId;
		init();
		setLayout();
		setOnAction();
		
		Scene scene = new Scene(tampilan, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.setTitle("History");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void init() {
		myTransactions = new Label("All Transactions");
		transactionDetail = new Label("Transactions Detail");
		totalPrice = new Label ("Total Price    :");
		
		setTableTrans();
		setDataTrans();
		setTableDetail();
	}
	
	public void setLayout() {
		paneKiri = new GridPane();
		paneKanan = new GridPane();
		gridPane = new GridPane();
		tampilan = new BorderPane();
		
		myTransactions.setFont(new Font(18));
		transactionDetail.setFont(new Font(18));
		totalPrice.setFont(new Font(15));
		
		totalPrice.setPrefSize(450, 40);
		
		paneKiri.add(myTransactions, 0, 0);
		paneKiri.add(transList, 0, 1);
		paneKiri.setVgap(10);
		
		paneKanan.add(transactionDetail, 0, 0);
		paneKanan.add(detailList, 0, 1);
		paneKanan.add(totalPrice, 0, 2);
		paneKanan.setVgap(10);
		
		gridPane.add(paneKiri, 0, 0);
		gridPane.add(paneKanan, 1, 0);
		gridPane.setHgap(30);
		
		gridPane.setAlignment(Pos.CENTER);
		tampilan.setCenter(gridPane);
		tampilan.setTop(new AdminNav(primaryStage, userId));
		
	}
	
	public void setTableDetail() {
		detailData = FXCollections.observableArrayList();
		
		
		detailList = new TableView<>(detailData);
	//	tableList.setMaxWidth(530);
		TableColumn<transactionDetail, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		idCol.setPrefWidth(80);
		
		TableColumn<transactionDetail, String> brandCol = new TableColumn<>("Brand");
		brandCol.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
		brandCol.setPrefWidth(110);
	
		TableColumn<transactionDetail, Integer> stockCol = new TableColumn<>("Quantity");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
		stockCol.setPrefWidth(110);
		
		TableColumn<transactionDetail, Integer> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceCol.setPrefWidth(110);
		
		TableColumn<transactionDetail, Integer> totalCol = new TableColumn<>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		totalCol.setPrefWidth(110);
		
		
		detailList.getColumns().addAll(idCol, brandCol, stockCol, priceCol, totalCol);
		
	}
	
	public void setTableTrans() {
		transData = FXCollections.observableArrayList();
		
		transList = new TableView<>(transData);
		transList.setPrefWidth(300);
		TableColumn<transactionHeaderDetail, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		idCol.setPrefWidth(80);
		
		TableColumn<transactionHeaderDetail, String> emailCol = new TableColumn<>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailCol.setPrefWidth(80);
		
		TableColumn<transactionHeaderDetail, String> dateCol = new TableColumn<>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		dateCol.setPrefWidth(80);
		
		transList.getColumns().addAll(idCol, emailCol, dateCol);
	}
	
	public void setDataTrans() {
String query = "SELECT * FROM `transactionheader` JOIN msuser ON transactionheader.UserID = msuser.UserID";
		
		try {
			ResultSet hasil = Connect.getInstance().executeQuery(query);
			
			while(hasil.next()) {
				String transId = hasil.getString("TransactionID");
				String userId = hasil.getString("userID");
				String userEmail = hasil.getString("UserEmail");
				String date = hasil.getString("TransactionDate");
				Integer insurance = hasil.getInt("DeliveryInsurance");
				String courier = hasil.getString("CourierType");
				
				transactionHeaderDetail data = new transactionHeaderDetail(transId, userId, userEmail, date, insurance, courier);
				transData.add(data);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void  setDataDetail() {
		detailData.removeAll(detailData);
		String choose = String.format("SELECT * FROM `transactiondetail` JOIN msproduct ON transactiondetail.ProductID = "
				+ "msproduct.ProductID WHERE TransactionID = '%s'", selected.getTransactionId());
		
		try {
			ResultSet hasil = Connect.getInstance().executeQuery(choose);
			
			while(hasil.next()) {
				String productId = hasil.getString("ProductID");
				String transId = hasil.getString("TransactionID");
				Integer qty = hasil.getInt("Quantity");
				String name = hasil.getString("ProductName");
				String brand = hasil.getString("ProductMerk");
				Integer price = hasil.getInt("ProductPrice");
				Integer total = price*qty;
				

				
				transactionDetail data = new transactionDetail(productId, transId, name, brand, price, qty, total);
				detailData.add(data);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void setOnAction() {
		transList.setOnMouseClicked(e->{
			selected = transList.getSelectionModel().getSelectedItem();
			totalPrice.setText("Total Price    : " );
			setDataDetail();
		});
		
		detailList.setOnMouseClicked(e->{
			selectItem = detailList.getSelectionModel().getSelectedItem();
			totalPrice.setText("Total Price    : " + selectItem.getQty()*selectItem.getPrice());
		});
	}
	
	
	
	
}

