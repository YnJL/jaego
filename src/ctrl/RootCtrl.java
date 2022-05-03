package ctrl;

import java.io.IOException;
import java.sql.Connection;
import cmn.ConnFac;
import cmn.TableViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RootCtrl {

//    @FXML
//    private Button btn_show_farm;
//
//    @FXML
//    private Button btn_show_cust;

    @FXML
    private Button btn_show_default;

    @FXML
    private Button btn_show_stock;

    @FXML
    private Button btn_show_deal;
    
    @FXML
    private BorderPane bp_main;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tableview;
    
    @FXML
    void show_default(ActionEvent event) {
    	try {
			BorderPane bp = FXMLLoader.load(getClass().getResource("../fxml/View_Raw.fxml"));
			bp_main.setCenter(bp);
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
     
	@FXML
    void show_stock(ActionEvent event) {
    	Connection conn = ConnFac.getConnection();
   		tableview = TableViewFactory.getTable("select * from View_Prodlist", conn);
   		bp_main.setCenter(tableview);
    }

	@FXML
    void show_deal(ActionEvent event) {
    	Connection conn = ConnFac.getConnection();
		tableview = TableViewFactory.getTable("select * from View_Deallist", conn);
		bp_main.setCenter(tableview);
    }

    @FXML
    void addnew(ActionEvent event) {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("../fxml/addnew.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../cmn/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("신규정보등록");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

    }

//    @FXML
//    void mod(ActionEvent event) {
//
//    }
//
//    @FXML
//    void del(ActionEvent event) {
//
//    }

}
