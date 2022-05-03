package vo;
// VO class
public class Prod {
	private String prodid;
	private String prodname;
	private String stocunit;
	private int stocqnty;
	private int stocprce;
	// 기본생성자/getter/setter/toString
	// 일반생성자는 잘 사용하지 않음 : business logic 수행을 위해

	public Prod() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Prod(String prodid, String prodname, String stocunit, int stocqnty, int stocprce) {
		super();
		this.prodid = prodid;
		this.prodname = prodname;
		this.stocunit = stocunit;
		this.stocqnty = stocqnty;
		this.stocprce = stocprce;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public String getStocunit() {
		return stocunit;
	}

	public void setStocunit(String stocunit) {
		this.stocunit = stocunit;
	}

	public int getStocqnty() {
		return stocqnty;
	}

	public void setStocqnty(int stocqnty) {
		this.stocqnty = stocqnty;
	}

	public int getStocprce() {
		return stocprce;
	}

	public void setStocprce(int stocprce) {
		this.stocprce = stocprce;
	}

	@Override
	public String toString() {
		return "Prod [prodid=" + prodid + ", prodname=" + prodname + ", stocunit=" + stocunit + ", stocqnty=" + stocqnty
				+ ", stocprce=" + stocprce + "]";
	}

	
}
