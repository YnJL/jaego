package vo;
// VO class
public class Cust {
	private String custid;
	private String custname;
	private String addr;
	private String cont;
	
	public Cust(String custid, String custname, String addr, String cont) {
		super();
		this.custid = custid;
		this.custname = custname;
		this.addr = addr;
		this.cont = cont;
	}

	@Override
	public String toString() {
		return "Cust [custid=" + custid + ", custname=" + custname + ", addr=" + addr + ", cont=" + cont + "]";
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
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

	public Cust() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}
