package ctrl;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cmn.ConnFac;
import dao.FarmDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vo.Farm;

public class CtrlAddFarm implements Initializable {
    @FXML
    private TextField txt_farmid;
    @FXML
    private TextField txt_farmname;
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
		int FID = 0;
		try {
			c = ConnFac.getConnection();
			s = "SELECT FarmID FROM Farm order by FarmID";
			ppsm = c.prepareStatement(s);
			r = ppsm.executeQuery();
			Farm vo = new Farm();
			while (r.next()) {
				vo.setFarmid(r.getString(1));
			}
			String lastFarmID = vo.getFarmid();
			if (lastFarmID == null)
				FID = 1;
			else {
				lastFarmID = lastFarmID.substring(2).trim();
				FID = Integer.parseInt(lastFarmID) + 1;
			}
			String FSerial = String.format("%03d", FID);
			String farmid = "F"+FSerial;
			txt_farmid.setText(farmid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
    void add(ActionEvent event) {
		
		FarmDao dao = new FarmDao();
		String farmid = txt_farmid.getText();
		String farmname = txt_farmname.getText();
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
		Farm vo = new Farm();
		vo.setFarmid(farmid);
		vo.setFarmname(farmname);
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
