package vo;

import java.sql.Date;

public class Sell {
	private String sellid;
	private String prodid;
	private String custid;
	private Date selldate;
	private String sellunit;
	private int sellqnty;
	private int sellprce;
	private int selltotl;
	
	public Sell(String sellid, String prodid, String custid, Date selldate,
			String sellunit, int sellqnty, int sellprce, int selltotl) {
		super();
		this.sellid = sellid;
		this.prodid = prodid;
		this.custid = custid;
		this.selldate = selldate;
		this.sellunit = sellunit;
		this.sellqnty = sellqnty;
		this.sellprce = sellprce;
		this.selltotl = sellprce*sellqnty;
	}

	
	public String getSellid() {
		return sellid;
	}


	public void setSellid(String sellid) {
		this.sellid = sellid;
	}


	public String getProdid() {
		return prodid;
	}


	public void setProdid(String prodid) {
		this.prodid = prodid;
	}


	public String getCustid() {
		return custid;
	}


	public void setCustid(String custid) {
		this.custid = custid;
	}


	public Date getSelldate() {
		return selldate;
	}


	public void setSelldate(Date selldate) {
		this.selldate = selldate;
	}


	public String getSellunit() {
		return sellunit;
	}


	public void setSellunit(String sellunit) {
		this.sellunit = sellunit;
	}


	public int getSellqnty() {
		return sellqnty;
	}


	public void setSellqnty(int sellqnty) {
		this.sellqnty = sellqnty;
		this.selltotl = sellqnty*sellprce;
	}


	public int getSellprce() {
		return sellprce;
	}


	public void setSellprce(int sellprce) {
		this.sellprce = sellprce;
		this.selltotl = sellprce*sellqnty;
	}


	public int getSelltotl() {
		return selltotl;
	}


	@Override
	public String toString() {
		return "Sell [sellid=" + sellid + ", prodid=" + prodid + ", custid=" + custid + ", selldate=" + selldate
				+ ", sellunit=" + sellunit + ", sellqnty=" + sellqnty + ", sellprce=" + sellprce + ", selltotl="
				+ selltotl + "]";
	}


	public Sell() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
