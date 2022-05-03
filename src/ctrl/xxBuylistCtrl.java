package ctrl;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import dao.ProdDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import vo.Prod;

public class xxBuylistCtrl implements Initializable{

    @FXML
    private Button btn_mod;
    @FXML
    private Button btn_del;
    @FXML
    private TextArea textarea;
	@FXML
	private BorderPane table_bp;
    @FXML
    private TableView<Prod> tableview;
    @FXML
    private TableColumn<Prod, Integer> col_buyid;
    @FXML
    private TableColumn<Prod, String> col_prodname;
    @FXML
    private TableColumn<Prod, String> col_farmname;
    @FXML
    private TableColumn<Prod, String> col_ddate;
    @FXML
    private TableColumn<Prod, Integer> col_buyweig;
    @FXML
    private TableColumn<Prod, Integer> col_buyquan;
    @FXML
    private TableColumn<Prod, Integer> col_buyprice;
    @FXML
    private TableColumn<Prod, Integer> col_buyamount;

    @FXML
    void mod(ActionEvent event) {

    }

    @FXML
    void del(ActionEvent event) {

    }

	
	@FXML
	private BorderPane borderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// tableview 각 열과 vo class의 필드를 매핑
    	col_buyid.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("orderid"));
    	col_prodname.setCellValueFactory(new PropertyValueFactory<Prod, String>("prodname"));
    	col_farmname.setCellValueFactory(new PropertyValueFactory<Prod, String>("farmname"));
    	col_ddate.setCellValueFactory(new PropertyValueFactory<Prod, String>("ddate"));
    	col_buyweig.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("buyweig"));
    	col_buyquan.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("buyquan"));
    	col_buyprice.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("buyprice"));
    	col_buyamount.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("buyamount"));
		
		// 데이터베이스 book table정보를 가져와서 tableview에 세팅
    	ProdDao dao = new ProdDao();
		try {
			List<Prod> data = dao.selectAll();
			tableview.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
