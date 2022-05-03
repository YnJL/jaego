package vo;
// VO class
public class Farm {
	private String farmid;
	private String farmname;
	private String addr;
	private String cont;

	
	public Farm(String farmid, String farmname, String addr, String cont) {
		super();
		this.farmid = farmid;
		this.farmname = farmname;
		this.addr = addr;
		this.cont = cont;
	}


	@Override
	public String toString() {
		return "Farm [farmid=" + farmid + ", farmname=" + farmname + ", addr=" + addr + ", cont=" + cont + "]";
	}


	public String getFarmid() {
		return farmid;
	}


	public void setFarmid(String farmid) {
		this.farmid = farmid;
	}


	public String getFarmname() {
		return farmname;
	}


	public void setFarmname(String farmname) {
		this.farmname = farmname;
	}


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getCont() {
		return cont;
	}


	public void setCont(String cont) {
		this.cont = cont;
	}


	public Farm() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}
