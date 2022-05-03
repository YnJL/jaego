package ctrl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vo.Cust;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.CustDao;

public class CtrlModCust implements Initializable{

	@FXML
	private TextField txt_custid;
	@FXML
	private TextField txt_custname;
    @FXML
    private TextField txt_addr;
    @FXML
    private TextField txt_cont;
    
    @FXML
    private Button btn_mod;
    
    @SuppressWarnings("unchecked")
	@FXML
    void mod(ActionEvent event) {
    	CustDao dao = new CustDao();
    	String custid = txt_custid.getText();
		String custname = txt_custname.getText();
		String addr = txt_addr.getText();
		String cont = txt_cont.getText();
		
		Cust vo = new Cust();
		vo.setCustid(custid);
		vo.setCustname(custname);
		vo.setAddr(addr);
		vo.setCont(cont);
		
		try {
			dao.update(vo);
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
		Cust cust = (Cust) Share.tableview.getSelectionModel().getSelectedItem();
		txt_custid.setText(cust.getCustid());
		txt_custname.setText(cust.getCustname());
		txt_addr.setText(cust.getAddr());
		txt_cont.setText(cust.getCont());
		
	}

}
