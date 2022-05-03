package ctrl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.ProdDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vo.Prod;

public class CtrlModStoc implements Initializable {
    @FXML
    private TextField txt_prodid;
    @FXML
    private TextField txt_prodname;
    @FXML
    private TextField txt_stocunit;
    @FXML
    private TextField txt_stocqnty;
    @FXML
    private TextField txt_stocprce;
    @FXML
    private Button btn_mod;
    
    @FXML
    void mod(ActionEvent event) {
		
		ProdDao dao = new ProdDao();
		String prodid = txt_prodid.getText();
		String prodname = txt_prodname.getText();
		String stocunit = txt_stocunit.getText();
		int stocqnty =  Integer.parseInt(txt_stocqnty.getText());
		int stocprce = Integer.parseInt(txt_stocprce.getText());
	
		Prod vo = new Prod();
		vo.setProdid(prodid);
		vo.setProdname(prodname);
		vo.setStocunit(stocunit);
		vo.setStocqnty(stocqnty);
		vo.setStocprce(stocprce);
			
		try {
			dao.update(vo);
			
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
		Prod prod = (Prod) Share.tableview.getSelectionModel().getSelectedItem();
		txt_prodid.setText(prod.getProdid());
		txt_prodname.setText(prod.getProdname());
		txt_stocunit.setText(prod.getStocunit());
		txt_stocqnty.setText(""+prod.getStocqnty());
		txt_stocprce.setText(""+prod.getStocprce());
	
	}
}