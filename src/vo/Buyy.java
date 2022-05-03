package vo;

import java.sql.Date;

public class Buyy {
	private String buyyid;
	private String prodid;
	private String farmid;
	private Date harvdate;
	private Date buyydate;
	private String buyyunit;
	private int buyyqnty;
	private int buyyprce;
	private int buyytotl;
	
	public Buyy(String buyyid, String prodid, String farmid, Date harvdate, Date buyydate,
			String buyyunit, int buyyqnty, int buyyprce) {
		super();
		this.buyyid = buyyid;
		this.prodid = prodid;
		this.farmid = farmid;
		this.harvdate = harvdate;
		this.buyydate = buyydate;
		this.buyyunit = buyyunit;
		this.buyyqnty = buyyqnty;
		this.buyyprce = buyyprce;
		this.buyytotl = buyyprce*buyyqnty;
	}
	

	public String getBuyyid() {
		return buyyid;
	}


	public void setBuyyid(String buyyid) {
		this.buyyid = buyyid;
	}


	public String getProdid() {
		return prodid;
	}


	public void setProdid(String prodid) {
		this.prodid = prodid;
	}


	public String getFarmid() {
		return farmid;
	}


	public void setFarmid(String farmid) {
		this.farmid = farmid;
	}


	public Date getHarvdate() {
		return harvdate;
	}


	public void setHarvdate(Date harvdate) {
		this.harvdate = harvdate;
	}


	public Date getBuyydate() {
		return buyydate;
	}


	public void setBuyydate(Date buyydate) {
		this.buyydate = buyydate;
	}


	public String getBuyyunit() {
		return buyyunit;
	}


	public void setBuyyunit(String buyyunit) {
		this.buyyunit = buyyunit;
	}


	public int getBuyyqnty() {
		return buyyqnty;
	}


	public void setBuyyqnty(int buyyqnty) {
		this.buyyqnty = buyyqnty;
		this.buyytotl = buyyqnty*buyyprce;
	}


	public int getBuyyprce() {
		return buyyprce;
	}


	public void setBuyyprce(int buyyprce) {
		this.buyyprce = buyyprce;
		this.buyytotl = buyyprce*buyyqnty;
	}


	public int getBuyytotl() {
		return buyytotl;
	}


	@Override
	public String toString() {
		return "Buyy [buyyid=" + buyyid + ", prodid=" + prodid + ", farmid=" + farmid + ", harvdate=" + harvdate
				+ ", buyydate=" + buyydate + ", buyyunit=" + buyyunit + ", buyyqnty=" + buyyqnty + ", buyyprce="
				+ buyyprce + ", buyytotl=" + buyytotl + "]";
	}


	public Buyy() {
		super();
		// TODO Auto-generated constructor stub
	}
}
