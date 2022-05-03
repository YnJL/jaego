package ctrl;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import cmn.ConnFac;
import dao.BuyyDao;
import dao.ProdDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vo.Buyy;
import vo.Prod;

public class CtrlAddBuyy implements Initializable {
    @FXML
    private TextField txt_buyyid;
    @FXML
    private ComboBox<String> cb_farmid;
    @FXML
    private TextField txt_farmid;
    @FXML
    private TextField txt_prodid;
    @FXML
    private TextField txt_harvdate;
    @FXML
    private DatePicker dp_harvdate;
    @FXML
    private TextField txt_buyydate;
    @FXML
    private DatePicker dp_buyydate;
    @FXML
    private TextField txt_buyyunit;
    @FXML
    private TextField txt_buyyqnty;
    @FXML
    private TextField txt_buyyprce;
    @FXML
    private TextField txt_buyytotl;
    @FXML
    private Button btn_add;

	private String fid;
	int BID = 0;
	int PID = 0;
	private String BSerial;
	private String PSerial;

	Connection conn = null;
	String sql = null;
	PreparedStatement ppdstm = null;
	ResultSet rs;

    @FXML
    void sel_harvdate(ActionEvent event) {
    	String harvdate = dp_harvdate.getValue().toString();
    	txt_harvdate.setText(harvdate);
    }

    @FXML
    void sel_buyydate(ActionEvent event) {
    	String buyydate = dp_buyydate.getValue().toString();
    	txt_buyydate.setText(buyydate);
    }
    

	@FXML
    void add(ActionEvent event) {
		//새로운 관리번호 자동등록
		ProdDao pd = new ProdDao();
		Prod pv = new Prod();
		try {		
		String pid = txt_prodid.getText();
		pv.setProdid(pid);
			pd.insert(pv);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BuyyDao dao = new BuyyDao();
		String buyyid = txt_buyyid.getText();
		String farmid = txt_farmid.getText();
		String prodid = txt_prodid.getText();
		String prodname = "";
		String harvdate = txt_harvdate.getText();
		String buyydate = txt_buyydate.getText();
		String buyyunit = txt_buyyunit.getText();
		int buyyqnty = Integer.parseInt(txt_buyyqnty.getText());
		int buyyprce = Integer.parseInt(txt_buyyprce.getText());
//		int buyytotl = Integer.parseInt(txt_buyytotl.getText());
		
		int ym = harvdate.indexOf('-');
		int md = harvdate.indexOf('-', ym + 1);
		int year = Integer.parseInt(harvdate.substring(0, ym))-1900;
		int month = Integer.parseInt(harvdate.substring(ym+1, md).trim())-1;
		int day = Integer.parseInt(harvdate.substring(md+1).trim());
		@SuppressWarnings("deprecation")
		Date hd = new Date(year,month,day);
		
		ym = buyydate.indexOf('-');
		md = buyydate.indexOf('-', ym + 1);
		year = Integer.parseInt(buyydate.substring(0, ym))-1900;
		month = Integer.parseInt(buyydate.substring(ym+1, md).trim())-1;
		day = Integer.parseInt(buyydate.substring(md+1).trim());
		@SuppressWarnings("deprecation")
		Date bd = new Date(year,month,day);
		
		//기본 prodname 생성 (prodid substring)
		String pna = prodid.substring(2,6);
		String pnb = prodid.substring(6);
		prodname=pnb+pna;
	
		
		Buyy vo = new Buyy();
		vo.setBuyyid(buyyid);
		vo.setFarmid(farmid);
		vo.setProdid(prodid);
		vo.setHarvdate(hd);
		vo.setBuyydate(bd);
		vo.setBuyyunit(buyyunit);
		vo.setBuyyqnty(buyyqnty);
		vo.setBuyyprce(buyyprce);
		
		try {
			dao.insert(vo);
			
			pd = new ProdDao();
			String stocunit = vo.getBuyyunit();
			int stocqnty = vo.getBuyyqnty();
			int stocprce = vo.getBuyyprce();
			
			pv = new Prod();
			pv.setProdid(prodid);
			pv.setProdname(prodname);
			pv.setStocunit(stocunit);
			pv.setStocqnty(stocqnty);
			pv.setStocprce(stocprce);
			
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
    	txt_harvdate.setText(today);
    	txt_buyydate.setText(today);
    	
    	//buyyid 생성하기
    	//마지막buyyid구해서+1

		try {
			conn = ConnFac.getConnection();
			sql = "SELECT BuyyID FROM BUYY where Buyydate = ? order by buyyid";
			ppdstm = conn.prepareStatement(sql);
			ppdstm.setString(1, today);
			rs = ppdstm.executeQuery();
			Buyy vo = new Buyy();
			while (rs.next()) {
				vo.setBuyyid(rs.getString(1));
			}
			String lastBuyID = vo.getBuyyid();
			if (lastBuyID == null) BID = 1;
			else {
				lastBuyID = lastBuyID.substring(6).trim();
				BID = Integer.parseInt(lastBuyID) + 1;
			}
			BSerial = String.format("%04d", BID);

			// buyyid 날짜부분 생성
			String bd = txt_buyydate.getText();
			int ym = bd.indexOf('-');
			int md = bd.indexOf('-', ym + 1);
			String y = bd.substring(2, ym).trim();
			String m = String.format("%02d", Integer.parseInt(bd.substring(ym + 1, md).trim()));
			String d = String.format("%02d", Integer.parseInt(bd.substring(md + 1).trim()));
			String buyyid = y + m + d + BSerial;
			txt_buyyid.setText(buyyid);
		} catch (NumberFormatException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		// buyydate변화에 따른 buyyid변화
		txt_buyydate.textProperty().addListener((observable, oldValue, newValue) -> {
			int ym = newValue.indexOf('-');
			int md = newValue.indexOf('-', ym + 1);
			String y = newValue.substring(2, ym).trim();
			String m = String.format("%02d", Integer.parseInt(newValue.substring(ym + 1, md).trim()));
			String d = String.format("%02d", Integer.parseInt(newValue.substring(md + 1).trim()));

			try {
				conn = ConnFac.getConnection();
				sql = "SELECT BuyyID FROM BUYY where Buyydate = ? order by Buyyid";
				ppdstm = conn.prepareStatement(sql);
				ppdstm.setString(1, newValue);
				rs = ppdstm.executeQuery();
				Buyy vo = new Buyy();
				while (rs.next()) {
					vo.setBuyyid(rs.getString(1));
				}
				String lastBuyID = vo.getBuyyid();
				if (lastBuyID == null)
					BID = 1;
				else {
					lastBuyID = lastBuyID.substring(6).trim();
					BID = Integer.parseInt(lastBuyID) + 1;
				}
				BSerial = String.format("%04d", BID);

				String buyyid = y + m + d + BSerial;
				txt_buyyid.setText(buyyid);
			} catch (NumberFormatException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String buyyid = y + m + d + BSerial;
			txt_buyyid.setText(buyyid);
		});


		
		//prodid생성(harvdate+farmid+pserial)

		try {
			conn = ConnFac.getConnection();
			sql = "SELECT PRODID FROM BUYY where BUYYDATE = ? order by PRODid";
			ppdstm = conn.prepareStatement(sql);
			ppdstm.setString(1, today);
			rs = ppdstm.executeQuery();
			Buyy vo = new Buyy();
			while (rs.next()) {
				vo.setProdid(rs.getString(1));
			}
			String lastProdID = vo.getProdid();
			if (lastProdID == null)
				PID = 1;
			else {
				lastProdID = lastProdID.substring(9).trim();
				PID = Integer.parseInt(lastProdID) + 1;
			}
			PSerial = String.format("%02d", PID);
		} catch (NumberFormatException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//harvdate변화에 따른 prodid변화
    	txt_harvdate.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				conn = ConnFac.getConnection();
				sql = "SELECT PRODID FROM BUYY where HARVDATE = ? AND FARMID = ? order by PRODid";
				ppdstm = conn.prepareStatement(sql);
				ppdstm.setString(1, newValue);
				ppdstm.setString(2, txt_farmid.getText());
				rs = ppdstm.executeQuery();
				Buyy vo = new Buyy();
				while (rs.next()) {
					vo.setProdid(rs.getString(1));
				}
				String lastProdID = vo.getProdid();
				if (lastProdID == null)
					PID = 1;
				else {
					lastProdID = lastProdID.substring(9).trim();
					PID = Integer.parseInt(lastProdID) + 1;
				}
				PSerial = String.format("%02d", PID);
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	int ym = newValue.indexOf('-');
        	int md = newValue.indexOf('-', ym + 1);
        	String m = String.format("%02d",Integer.parseInt(newValue.substring(ym+1, md).trim()));
        	String d = String.format("%02d",Integer.parseInt(newValue.substring(md+1).trim()));
    		String prodid = txt_farmid.getText()+m+d+PSerial;
    		txt_prodid.setText(prodid);
    	});
    	
		//farmid변화에 따른 prodid변화
    	txt_farmid.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				conn = ConnFac.getConnection();
				sql = "SELECT PRODID FROM BUYY where FARMID = ? AND HARVDATE = ? order by PRODid";
				ppdstm = conn.prepareStatement(sql);
				ppdstm.setString(1, newValue);
				ppdstm.setString(2, txt_harvdate.getText());
				rs = ppdstm.executeQuery();
				Buyy vo = new Buyy();
				while (rs.next()) {
					vo.setProdid(rs.getString(1));
				}
				String lastProdID = vo.getProdid();
				if (lastProdID == null)
					PID = 1;
				else {
					lastProdID = lastProdID.substring(9).trim();
					PID = Integer.parseInt(lastProdID) + 1;
				}
				PSerial = String.format("%02d", PID);
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		String hd = txt_harvdate.getText();
        	int ym = hd.indexOf('-');
        	int md = hd.indexOf('-', ym + 1);
        	String m = String.format("%02d",Integer.parseInt(hd.substring(ym+1, md).trim()));
        	String d = String.format("%02d",Integer.parseInt(hd.substring(md+1).trim()));
    		String prodid = newValue+m+d+PSerial;
    		txt_prodid.setText(prodid);
    	});
    	

    	
    	//buyytotl 자동계산부분
    	txt_buyyqnty.textProperty().addListener((observable, oldValue, newValue) -> {
        	String bq = newValue.trim();
        	String bp = txt_buyyprce.getText().trim();
        
    		try {
				int buyyprce = Integer.parseInt(bp);
				int buyyqnty = Integer.parseInt(bq);
				txt_buyytotl.setText((buyyprce*buyyqnty)+"원");
			} catch (NumberFormatException e) {		
			}
    	});
    	txt_buyyprce.textProperty().addListener((observable, oldValue, newValue) -> {
    		String bp = newValue.trim();
        	String bq = txt_buyyqnty.getText().trim();
        	
        		try {
					int buyyprce = Integer.parseInt(bp);
					int buyyqnty = Integer.parseInt(bq);
					txt_buyytotl.setText((buyyprce*buyyqnty)+"원");
				} catch (NumberFormatException e) {}
    	});
    	
    	
//		farmid : farmname 콤보박스 입력    	
    	try {
			conn = ConnFac.getConnection();
			sql = "SELECT FARMID, FARMNAME FROM FARM order by farmid";
			ppdstm = conn.prepareStatement(sql);
			rs = ppdstm.executeQuery();
			
			while(rs.next()) {
				fid = rs.getString(1);
				String fnm = rs.getString(2);
				cb_farmid.getItems().add(fid+" : "+fnm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	cb_farmid.valueProperty().addListener((observable, oldValue, newValue) -> {
    		int cut = newValue.indexOf(':');
    		String fid = newValue.substring(0,cut).trim();
    		txt_farmid.setText(fid);
    	});
    	
	}
	

		
}