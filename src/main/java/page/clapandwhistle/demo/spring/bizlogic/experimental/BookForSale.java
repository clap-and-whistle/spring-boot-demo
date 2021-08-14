package page.clapandwhistle.demo.spring.bizlogic.experimental;

import java.math.BigDecimal;

public final class BookForSale {
	private int id;
	private String name;
	private String author;
	private BigDecimal price;

	public BookForSale(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public BookForSale(int id, String name, String author, BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
