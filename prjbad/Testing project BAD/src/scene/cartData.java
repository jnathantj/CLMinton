package scene;

public class cartData  {
	
	private String userId, productId, name, brand;
	private Integer qty, price, total;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
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
	public cartData(String userId, String productId, String name, String brand, Integer qty, Integer price,
			Integer total) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.name = name;
		this.brand = brand;
		this.qty = qty;
		this.price = price;
		this.total = total;
	}
	

	
	


}
