package scene;

public class productData  {
	private String productId, name, brand;
	private Integer stock, price;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public productData(String productId, String name, String brand, Integer stock, Integer price) {
		super();
		this.productId = productId;
		this.name = name;
		this.brand = brand;
		this.stock = stock;
		this.price = price;
	}
	
	
	
}
