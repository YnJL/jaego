package ctrl;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import cmn.TableViewFactory;
import dao.BuyyDao;
import dao.CustDao;
import dao.FarmDao;
import dao.ProdDao;
import dao.SellDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vo.Buyy;
import vo.Cust;
import vo.Farm;
import vo.Prod;
import vo.Sell;

public class RawCtrl implements Initializable {

    @FXML
    private Button btn_cust;

    @FXML
    private Button btn_stoc;

    @FXML
    private Button btn_buyy;

    @FXML
    private Button btn_farm;

    @FXML
    private Button btn_sell;
    
    @FXML
    private BorderPane bp_raw;
    

    @SuppressWarnings("unchecked")
	@FXML
    void open_stoc(ActionEvent event) {
       	Share.tableview = TableViewFactory.getTable(Prod.class);
       	bp_raw.setCenter(Share.tableview);
    	ProdDao dao = new ProdDao();
		try {
			List<Prod> data = dao.selectAll();
			Share.tableview.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Share.tab = "stoc";
    }

    @SuppressWarnings("unchecked")
	@FXML
    void open_buyy(ActionEvent event) {
    	Share.tableview = TableViewFactory.getTable(Buyy.class);
    	bp_raw.setCenter(Share.tableview);
    	BuyyDao dao = new BuyyDao();
		try {
			List<Buyy> data = dao.selectAll();
			Share.tableview.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Share.tab = "buyy";

    }

    @SuppressWarnings("unchecked")
	@FXML
    void open_sell(ActionEvent event) {
    	Share.tableview = TableViewFactory.getTable(Sell.class);
       	bp_raw.setCenter(Share.tableview);
    	SellDao dao = new SellDao();
		try {
			List<Sell> data = dao.selectAll();
			Share.tableview.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Share.tab = "sell";
    }

    @SuppressWarnings("unchecked")
	@FXML
    void open_farm(ActionEvent event) {
    	Share.tableview = TableViewFactory.getTable(Farm.class);
       	bp_raw.setCenter(Share.tableview);
    	FarmDao dao = new FarmDao();
		try {
			List<Farm> data = dao.selectAll();
			Share.tableview.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Share.tab = "farm";
    }

    @SuppressWarnings("unchecked")
	@FXML
    void open_cust(ActionEvent event) {
    	Share.tableview = TableViewFactory.getTable(Cust.class);
       	bp_raw.setCenter(Share.tableview);
    	CustDao dao = new CustDao();
		try {
			List<Cust> data = dao.selectAll();
			Share.tableview.getItems().addAll(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Share.tab = "cust";
    }

    @FXML
    void sel_mod(ActionEvent event) {
    	Share.tv_idx = Share.tableview.getSelectionModel().getSelectedIndex();
    	switch(Share.tab) {
			case "stoc" : {
				try {
					BorderPane root = FXMLLoader.load(getClass().getResource("../fxml/mod_stoc.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("../cmn/application.css").toExternalForm());
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("재고정보변경");
					stage.show();
				} catch(Exception e) {
					e.printStackTrace();
				}
    			break;
			}
		
			case "buyy" : {
				try {
					BorderPane root = FXMLLoader.load(getClass().getResource("../fxml/mod_buyy.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("../cmn/application.css").toExternalForm());
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("구매정보변경");
					stage.show();
				} catch(Exception e) {
					e.printStackTrace();
				}
    			break;
			}
			
		case "sell" : {
			try {
				BorderPane root = FXMLLoader.load(getClass().getResource("../fxml/mod_sell.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("../cmn/application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("판매정보변경");
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
		case "farm" : {
			try {
				BorderPane root = FXMLLoader.load(getClass().getResource("../fxml/mod_farm.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("../cmn/application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("농장정보변경");
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
		case "cust" : {
			try {
				BorderPane root = FXMLLoader.load(getClass().getResource("../fxml/mod_cust.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("../cmn/application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("고객정보변경");
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
		default:;
    	}
    }

    @FXML
    void sel_del(ActionEvent event) {
    	switch(Share.tab) {
    		case "stoc" : {
    			Prod x = (Prod) Share.tableview.getSelectionModel().getSelectedItem();
    			ProdDao dao = new ProdDao();
    			
    			try {
					dao.delete(x.getProdid());
					Share.tableview.getItems().remove(x);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			break;
    		}
    		case "buyy" : {
    			Buyy x = (Buyy) Share.tableview.getSelectionModel().getSelectedItem();
    			BuyyDao dao = new BuyyDao();
    			
    			try {
					dao.delete(x.getBuyyid());
					Share.tableview.getItems().remove(x);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			break;
    		}
    		case "sell" : {
    			Sell x = (Sell) Share.tableview.getSelectionModel().getSelectedItem();
    			SellDao dao = new SellDao();
    			
    			try {
					dao.delete(x.getSellid());
					Share.tableview.getItems().remove(x);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			break;
    		}
    		case "farm" : {
    			Farm x = (Farm) Share.tableview.getSelectionModel().getSelectedItem();
    			FarmDao dao = new FarmDao();
    			
    			try {
					dao.delete(x.getFarmid());
					Share.tableview.getItems().remove(x);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			break;
    		}
    		case "cust" : {
    			Cust x = (Cust) Share.tableview.getSelectionModel().getSelectedItem();
    			CustDao dao = new CustDao();
    			
    			try {
					dao.delete(x.getCustid());
					Share.tableview.getItems().remove(x);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			break;
    		}
    		default:;
    	}
    }
    	
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		// tableview 클릭시 정보 읽기
//		tableview.setOnMouseClicked(
//				new EventHandler<MouseEvent>() {
//					@Override
//					public void handle(MouseEvent event) {
//						// 선택된 index값 / 선택된 객체
//						int idx = tableview.getSelectionModel().getSelectedIndex();
//						switch(tab) {
//						Orders vo = tableview.getSelectionModel().getSelectedItem();
//						textarea.appendText("선택된 행 : "+idx+"\n");
//						textarea.appendText("선택된 객체 : "+vo.toString()+"\n");
//						
//					}}
//				);
//		
	}
}
