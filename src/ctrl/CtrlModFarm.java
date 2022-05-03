package ctrl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vo.Farm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.FarmDao;

public class CtrlModFarm implements Initializable{

	@FXML
	private TextField txt_farmid;
	@FXML
	private TextField txt_farmname;
    @FXML
    private TextField txt_addr;
    @FXML
    private TextField txt_cont;
    
    @FXML
    private Button btn_mod;
    
    @SuppressWarnings("unchecked")
	@FXML
    void mod(ActionEvent event) {
    	FarmDao dao = new FarmDao();
		String farmid = (txt_farmid.getText());
		String farmname = txt_farmname.getText();
		String addr = txt_addr.getText();
		String cont = txt_cont.getText();
		
		Farm vo = new Farm();
		vo.setFarmid(farmid);
		vo.setFarmname(farmname);
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
		Farm farm = (Farm) Share.tableview.getSelectionModel().getSelectedItem();
		txt_farmid.setText(farm.getFarmid());
		txt_farmname.setText(farm.getFarmname());
		txt_addr.setText(farm.getAddr());
		txt_cont.setText(farm.getCont());
		
	}

}
