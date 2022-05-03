package ctrl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;

public class AddNewCtrl implements Initializable {

	@FXML
	private Button add;
	@FXML
	private Button close;
	@FXML
	private ChoiceBox<String> new_cmb;
    @FXML
    private BorderPane bp_add;

	@FXML
	void add(ActionEvent event) {

	}

	@FXML
	void close(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new_cmb.getItems().add("신규구매등록");
		new_cmb.getItems().add("신규판매등록");
		new_cmb.getItems().add("신규농장등록");
		new_cmb.getItems().add("신규고객등록");

		new_cmb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				switch (newValue) {
				case "신규구매등록":
					try {
						BorderPane bp = FXMLLoader.load(getClass().getResource("../fxml/add_buyy.fxml"));
						bp_add.setCenter(bp);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				case "신규판매등록":
					try {
						BorderPane bp = FXMLLoader.load(getClass().getResource("../fxml/add_sell.fxml"));
						bp_add.setCenter(bp);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				case "신규농장등록":
					try {
						BorderPane bp = FXMLLoader.load(getClass().getResource("../fxml/add_farm.fxml"));
						bp_add.setCenter(bp);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "신규고객등록":
					try {
						BorderPane bp = FXMLLoader.load(getClass().getResource("../fxml/add_cust.fxml"));
						bp_add.setCenter(bp);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					;
				}
			}
		});

	}

}
