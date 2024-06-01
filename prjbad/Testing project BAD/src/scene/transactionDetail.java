package scene;

public class transactionDetail {
	private String productId, transactionId, productName, productBrand ;
	private Integer price, qty, total;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	
	public transactionDetail(String productId, String transactionId, String productName, String productBrand, Integer price, Integer qty,
			Integer total) {
		super();
		this.productId = productId;
		this.transactionId = transactionId;
		this.productName = productName;
		this.productBrand = productBrand;
		this.price = price;
		this.qty = qty;
		this.total = total;
	}
	
	
	
	
	
}
