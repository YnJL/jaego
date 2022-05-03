package ctrl;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class CtrlModBuyy implements Initializable {
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
    private Button btn_mod;


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
    
    @SuppressWarnings("unchecked")
	@FXML
    void mod(ActionEvent event) {
    	int oquan =  Integer.parseInt(txt_buyyqnty.getText());
		
		BuyyDao dao = new BuyyDao();
		String buyyid = txt_buyyid.getText();
		String prodid = txt_prodid.getText();
		String farmid = txt_farmid.getText();
		String harvdate = txt_harvdate.getText();
		String buyydate = txt_buyydate.getText();
		String buyyunit = txt_buyyunit.getText();
		int buyyqnty =  Integer.parseInt(txt_buyyqnty.getText());
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
		Date dd = new Date(year,month,day);
	
		Buyy vo = new Buyy();
		vo.setBuyyid(buyyid);
		vo.setProdid(prodid);
		vo.setFarmid(farmid);
		vo.setHarvdate(hd);
		vo.setBuyydate(dd);
		vo.setBuyyunit(buyyunit);
		vo.setBuyyqnty(buyyqnty);
		vo.setBuyyprce(buyyprce);
			
		try {
			dao.update(vo);
			
			ProdDao pd = new ProdDao();
			Prod pv = new Prod();
			pv = pd.select(prodid);
			
			int stocquan = pv.getStocqnty() - oquan + buyyqnty; 
			pv.setStocqnty(stocquan);
			
			Share.tableview.getItems().set(Share.tv_idx, vo);
			
			Stage stage0 = (Stage) btn_mod.getScene().getWindow();
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
//		farmid : farmname 콤보박스 입력    	
    	try {
			Connection conn = ConnFac.getConnection();
			String sql = "SELECT FARMID, FARMNAME FROM FARM order by farmid";
			PreparedStatement ppdstm = conn.prepareStatement(sql);
			ResultSet rs = ppdstm.executeQuery();
			
			while(rs.next()) {
				String fid = rs.getString(1);
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
    	
	
		Buyy buy = (Buyy) Share.tableview.getSelectionModel().getSelectedItem();
		txt_buyyid.setText(""+buy.getBuyyid());
		txt_prodid.setText(""+buy.getProdid());
		txt_farmid.setText(""+buy.getFarmid());
		txt_harvdate.setText(""+buy.getHarvdate());
		txt_buyydate.setText(""+buy.getBuyydate());
		txt_buyyunit.setText(""+buy.getBuyyunit());
		txt_buyyqnty.setText(""+buy.getBuyyqnty());
		txt_buyyprce.setText(""+buy.getBuyyprce());
		txt_buyytotl.setText(""+buy.getBuyytotl());
		
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
    	
	}
}