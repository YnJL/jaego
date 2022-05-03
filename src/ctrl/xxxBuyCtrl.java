package ctrl;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import cmn.TableViewFactory;
import dao.BuyyDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import vo.Buyy;

public class xxxBuyCtrl implements Initializable{

 //   @FXML
    private TableView<Buyy> tableview;

    @FXML
    private Button btn_mod;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_del;

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void mod(ActionEvent event) {

    }

    @FXML
    void del(ActionEvent event) {

    }
	
	@FXML
	private BorderPane borderPane;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tableview = TableViewFactory.getTable(Buyy.class);
		
		borderPane.setCenter(tableview);
    	BuyyDao dao = new BuyyDao();
		try {
			List<Buyy> data = dao.selectAll();
			tableview.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
