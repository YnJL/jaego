package ctrl;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import cmn.ConnFac;
import dao.CustDao;
import dao.ProdDao;
import dao.SellDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vo.Cust;
import vo.Prod;
import vo.Sell;

public class CtrlAddSell implements Initializable {
    @FXML
    private TextField txt_sellid;
    @FXML
    private ComboBox<String> cb_custid;
    @FXML
    private TextField txt_custid;
    @FXML
    private TextField txt_custname;
    @FXML
    private ComboBox<String> cb_prodid;
    @FXML
    private TextField txt_prodid;
    @FXML
    private TextField txt_selldate;
    @FXML
    private DatePicker dp_selldate;
    @FXML
    private TextField txt_sellunit;
    @FXML
    private TextField txt_sellqnty;
    @FXML
    private TextField txt_sellprce;
    @FXML
    private TextField txt_selltotl;
    @FXML
    private Button btn_add;
    
	private String cid;
	private String cnm;
	int SID = 0;
	private String SSerial;

	Connection conn = null;
	String sql = null;
	PreparedStatement ppdstm = null;
	ResultSet rs;

    @FXML
    void sel_selldate(ActionEvent event) {
    	String selldate = dp_selldate.getValue().toString();
    	txt_selldate.setText(selldate);
    }
    
	@FXML
    void add(ActionEvent event) {
		
		SellDao dao = new SellDao();
		String sellid = txt_sellid.getText();
		String custid = cid;
//		String custname = txt_custname.getText();
		String prodid = txt_prodid.getText();
		String selldate = txt_selldate.getText();
		String sellunit = txt_sellunit.getText();
		int sellqnty =  Integer.parseInt(txt_sellqnty.getText());
		int sellprce = Integer.parseInt(txt_sellprce.getText());
//		int selltotl = Integer.parseInt(txt_selltotl.getText());
		
		int ym = selldate.indexOf('-');
		int md = selldate.indexOf('-', ym + 1);
		int year = Integer.parseInt(selldate.substring(0, ym))-1900;
		int month = Integer.parseInt(selldate.substring(ym+1, md).trim())-1;
		int day = Integer.parseInt(selldate.substring(md+1).trim());
		@SuppressWarnings("deprecation")
		Date sd = new Date(year,month,day);
	
		Sell vo = new Sell();
		vo.setSellid(sellid);
		vo.setProdid(prodid);
		vo.setCustid(custid);
		vo.setSelldate(sd);
		vo.setSellunit(sellunit);
		vo.setSellqnty(sellqnty);
		vo.setSellprce(sellprce);
			
		try {
			dao.insert(vo);
			
			ProdDao pd = new ProdDao();
			Prod pv = pd.select(prodid);
			int stocqnty = pv.getStocqnty() - vo.getSellqnty(); 
			pv.setStocqnty(stocqnty);
			pd.update(pv);
			
			Stage stage0 = (Stage) btn_add.getScene().getWindow();
			Platform.runLater(()->{
				stage0.close();
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LocalDate now = LocalDate.now();
		String today = now.toString();
    	txt_selldate.setText(today);

    	//sellid 생성하기
    	//마지막sellid구해서+1
		try {
			conn = ConnFac.getConnection();
			sql = "SELECT SellID FROM Sell where Selldate = ? order by sellid";
			ppdstm = conn.prepareStatement(sql);
			ppdstm.setString(1, today);
			rs = ppdstm.executeQuery();
			Sell vo = new Sell();
			while(rs.next()) {
			vo.setSellid(rs.getString(1));
			}
			String lastSellID = vo.getSellid();
			if (lastSellID==null) SID=1;
			else {
				lastSellID = lastSellID.substring(6).trim();
				SID = Integer.parseInt(lastSellID)+1;
			}
		SSerial = String.format("%04d", SID);
			
		
    	//sellid 날짜부분 생성
		String sd = txt_selldate.getText();
    	int ym = sd.indexOf('-');
    	int md = sd.indexOf('-', ym + 1);
		String y = sd.substring(2, ym).trim();
		String m = String.format("%02d", Integer.parseInt(sd.substring(ym + 1, md).trim()));
		String d = String.format("%02d", Integer.parseInt(sd.substring(md + 1).trim()));
		String sellid = y + m + d + SSerial;
		txt_sellid.setText(sellid);
		} catch (NumberFormatException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// selldate변화에 따른 sellid변화
		txt_selldate.textProperty().addListener((observable, oldValue, newValue) -> {
			int ym = newValue.indexOf('-');
			int md = newValue.indexOf('-', ym + 1);
			String y = newValue.substring(2, ym).trim();
			String m = String.format("%02d", Integer.parseInt(newValue.substring(ym + 1, md).trim()));
			String d = String.format("%02d", Integer.parseInt(newValue.substring(md + 1).trim()));

			try {
				conn = ConnFac.getConnection();
				sql = "SELECT SellID FROM Sell where Selldate = ? order by Sellid";
				ppdstm = conn.prepareStatement(sql);
				ppdstm.setString(1, newValue);
				rs = ppdstm.executeQuery();
				Sell vo = new Sell();
				while (rs.next()) {
					vo.setSellid(rs.getString(1));
				}
				String lastSellID = vo.getSellid();
				if (lastSellID == null)
					SID = 1;
				else {
					lastSellID = lastSellID.substring(6).trim();
					SID = Integer.parseInt(lastSellID) + 1;
				}
				SSerial = String.format("%04d", SID);

				String sellid = y + m + d + SSerial;
				txt_sellid.setText(sellid);
			} catch (NumberFormatException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String sellid = y + m + d + SSerial;
			txt_sellid.setText(sellid);
		});
		
		
		//selltotl 자동계산부분
    	txt_sellqnty.textProperty().addListener((observable, oldValue, newValue) -> {
        	String sq = newValue.trim();
        	String sp = txt_sellprce.getText().trim();
        
    		try {
				int sellprce = Integer.parseInt(sp);
				int sellqnty = Integer.parseInt(sq);
				txt_selltotl.setText((sellprce*sellqnty)+"원");
			} catch (NumberFormatException e) {		
			}
    	});
    	
    	txt_sellprce.textProperty().addListener((observable, oldValue, newValue) -> {
    		String sp = newValue.trim();
        	String sq = txt_sellqnty.getText().trim();
        	
        		try {
					int sellprce = Integer.parseInt(sp);
					int sellqnty = Integer.parseInt(sq);
					txt_selltotl.setText((sellprce*sellqnty)+"원");
				} catch (NumberFormatException e) {}
    	});
    	
//		custid : custname 콤보박스 입력
		CustDao cust = new CustDao();
		try {
			List<Cust> list = cust.selectAll();
			for(Cust x:list) {
				cid = x.getCustid();
				cnm = x.getCustname();
				cb_custid.getItems().add(cid+" : "+cnm);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	cb_custid.valueProperty().addListener((observable, oldValue, newValue) -> {
    		int cut = newValue.indexOf(':');
    		cid = newValue.substring(0,cut).trim();
    		cnm = newValue.substring(cut+1).trim();
    		txt_custname.setText(cnm);
    		txt_custid.setText(cid);
    	});
    	
    	
//		관리번호 콤보박스 입력
		ProdDao prod = new ProdDao();
		try {
			List<Prod> list = prod.selectAll();
			for(Prod x:list) {
				String pid = x.getProdid();
				String pnm = x.getProdname();
				cb_prodid.getItems().add(pid+" : "+pnm);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cb_prodid.valueProperty().addListener((observable, oldValue, newValue) -> {
    		int cut = newValue.indexOf(':');
    		String pid = newValue.substring(0,cut).trim();
    		txt_prodid.setText(pid);
    		
//    		관리번호 받아서 내용 입력 (거래단위/단위가격)
    		ProdDao pr = new ProdDao();
    		try {
    			Prod vo = pr.select(txt_prodid.getText().trim());
    			String prodid = vo.getProdid();
    			int stocprce = vo.getStocprce();
    			String stocunit = vo.getStocunit();
    			
    			txt_prodid.setText(prodid);
    			txt_sellunit.setText(stocunit);
    			txt_sellprce.setText(stocprce+"");
    			
    		} catch (NumberFormatException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	});

	}
	

}
