package ctrl;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cmn.ConnFac;
import dao.CustDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vo.Cust;

public class CtrlAddCust implements Initializable {
	@FXML
	private TextField txt_custid;
	@FXML
	private TextField txt_custname;
    @FXML
    private TextField txt_addr;
    @FXML
    private TextField txt_cont;
    
    @FXML
    private Button btn_add;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Connection c;
		String s=null;
		PreparedStatement ppsm=null;
		ResultSet r;
		int CID = 0;
		try {
			c = ConnFac.getConnection();
			s = "SELECT CustID FROM Cust order by CustID";
			ppsm = c.prepareStatement(s);
			r = ppsm.executeQuery();
			Cust vo = new Cust();
			while (r.next()) {
				vo.setCustid(r.getString(1));
			}
			String lastCustID = vo.getCustid();
			if (lastCustID == null)
				CID = 1;
			else {
				lastCustID = lastCustID.substring(2).trim();
				CID = Integer.parseInt(lastCustID) + 1;
			}
			String CSerial = String.format("%03d", CID);
			String custid = "C"+CSerial;
			txt_custid.setText(custid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
    void add(ActionEvent event) {
		
		CustDao dao = new CustDao();
		String custid = txt_custid.getText();
		String custname = txt_custname.getText();
		String addr = txt_addr.getText();
		String cont = txt_cont.getText();
		
//		int ym = ddate.indexOf('-');
//		int md = ddate.indexOf('-', ym + 1);
//		int year = Integer.parseInt(ddate.substring(0, ym))-1900;
//		int month = Integer.parseInt(ddate.substring(ym+1, md).trim())-1;
//		int day = Integer.parseInt(ddate.substring(md+1).trim());
//		@SuppressWarnings("deprecation")
//		Date dd = new Date(year,month,day);
//	
		Cust vo = new Cust();
		vo.setCustid(custid);
		vo.setCustname(custname);
		vo.setAddr(addr);
		vo.setCont(cont);
		
		try {
			dao.insert(vo);
			
			Stage stage0 = (Stage) btn_add.getScene().getWindow();
			Platform.runLater(()->{
				stage0.close();
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
