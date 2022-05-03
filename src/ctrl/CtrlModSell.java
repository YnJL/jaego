package ctrl;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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

public class CtrlModSell implements Initializable {
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
    private Button btn_mod;
    
    private String cuid, prid;
    
    @FXML
    void sel_selldate(ActionEvent event) {
    	String selldate = dp_selldate.getValue().toString();
    	txt_selldate.setText(selldate);
    }
    	
	@SuppressWarnings("unchecked")
	@FXML
    void mod(ActionEvent event) {
		int oquan =  Integer.parseInt(txt_sellqnty.getText());
		
		SellDao dao = new SellDao();
		String sellid = txt_sellid.getText();
		String custid = txt_custid.getText();
//		String custname = txt_custname.getText();
		String prodid = txt_prodid.getText();
		String selldate = txt_selldate.getText();
		String sellunit = txt_sellunit.getText();
		int sellqnty =  Integer.parseInt(txt_sellqnty.getText());
		int sellprce = Integer.parseInt(txt_sellprce.getText());
//		int selltotl = Integer.parseInt(txt_buyamount.getText());
		
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
			dao.update(vo);
			
			ProdDao pd = new ProdDao();
			pd.select(prodid);
			
			Prod pv = new Prod();
			
			int stocquan = pv.getStocqnty() + oquan - sellqnty; 
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
//    	기본값 설정
		Sell sell = (Sell) Share.tableview.getSelectionModel().getSelectedItem();
		txt_sellid.setText(""+sell.getSellid());
		cuid = sell.getCustid();
		prid = sell.getProdid();
		txt_selldate.setText(""+sell.getSelldate());
		txt_sellunit.setText(""+sell.getSellunit());
		txt_sellqnty.setText(""+sell.getSellqnty());
		txt_sellprce.setText(""+sell.getSellprce());
		txt_selltotl.setText(""+sell.getSelltotl());
		
//		custid : custname 콤보박스 입력
		CustDao cust = new CustDao();
		try {
			List<Cust> list = cust.selectAll();
			int i=0;
			for(Cust x:list) {
				String cid = x.getCustid();
				String cnm = x.getCustname();
				cb_custid.getItems().add(cid+" : "+cnm);
				if(cid.equals(cuid)) {
					cb_custid.getSelectionModel().select(i);
		    		txt_custid.setText(cid);
		    		txt_custname.setText(cnm);
				}
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
//		관리번호 콤보박스    	
		ProdDao prod = new ProdDao();
		try {
			List<Prod> list = prod.selectAll();
			int i=0;
			for(Prod x:list) {
				String pid = x.getProdid();
				String pnm = x.getProdname();
				cb_prodid.getItems().add(pid+" : "+pnm);
				if(pid.equals(prid)) {
					cb_prodid.getSelectionModel().select(i);
		    		txt_prodid.setText(pid);
				}
				i++;
			}
//			txt_prodid.setText(prid+":"+prnm);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	cb_custid.valueProperty().addListener((observable, oldValue, newValue) -> {
    		int cut = newValue.indexOf(':');
    		String cid = newValue.substring(0,cut).trim();
    		String cnm = newValue.substring(cut+1).trim();
    		txt_custid.setText(cid);
    		txt_custname.setText(cnm);
    	});
    
    	cb_prodid.valueProperty().addListener((observable, oldValue, newValue) -> {
    		int cut = newValue.indexOf(':');
    		int pid = Integer.parseInt(newValue.substring(0,cut).trim());
    		txt_prodid.setText(pid+"");
    		
    	});
    	
//    	관리번호 받아서 내용 입력 (거래단위/단위가격)
    		prod = new ProdDao();
    		try {
    			String pid = sell.getProdid();
    			Prod vo = prod.select(pid);
    			String prodid = vo.getProdid();
    			String stocunit = vo.getStocunit();
    			
    			txt_prodid.setText(prodid);
    			txt_sellunit.setText(stocunit);
    						
    		} catch (NumberFormatException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	

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
    	
    	
    	
	}
}
