package cmn;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class MyTableView<T> extends TableView<T> {
// 금액셀의 정렬에 사용할 상수
	public final static String LEFT = "LEFT";
	public final static String RIGHT = "RIGHT";
	public final static String CENTER = "CENTER";
	
// vo클래스의 필드수 만큼의 컬럼을 배열로
	@SuppressWarnings("rawtypes")
	public static TableColumn c[];
// vo의 필드 정보
	public static Field fields[];

/**
 *	@param data  - 테이블에 반영시킬 데이타
 *	@param voclass - vo 클래스 지정
 **/

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MyTableView(ObservableList<T> data, Class voclass) {
// 테이블에 메뉴버튼 표시하기
		this.setTableMenuButtonVisible(true);
// 데이타를 테이블의 아이템으로 셋팅
		this.setItems(data);
// vo에 선언된 필드를 배열로 담는다.
		fields = voclass.getDeclaredFields();
// vo의 필드 수만큼 배열을 선언
		c = new TableColumn[fields.length];
// 향상된 for문을 쓰기 위해 count변수를 for문 바깥에 선언
		int cnt = 0;
		for (Field f : fields) {
// 테이블 컬럼 만들기
			c[cnt] = new TableColumn<Object, Object>(f.getName().toUpperCase());
// CellFactory지정 - 해당필드(f.getName())의 값(new PropertyValueFactory)으로 렌더링 한다. 
			c[cnt].setCellValueFactory(new PropertyValueFactory(f.getName()));
// 컬럼의 기본 너비를 100으로 
			c[cnt].setMinWidth(100);
// 모든 컬럼이 일단 정렬할 수 있도록 지정
			c[cnt].setSortable(true);
// 일단 에디트할 수 있는 가능성을 지정- 추후 다른 절차가 필요함
			c[cnt].setEditable(true);
			cnt++;
		}
//테이블에 만든 컬럼들을 추가 한다.
		this.getColumns().addAll(c);
	}

	/**
	 * 
	 * @param table - fxml을 이용하여 table view를 만든경우 컬럼은 새로이 만들지 않고 만들어진 디자인된 컬럼에
	 *              CellValueFactory만 지정
	 * @param voclass - 사용할 vo 클래스 지정(확장자포함)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setTableView(TableView table, Class voclass) {
		fields = voclass.getDeclaredFields();
		int cnt = 0;
		for (Field f : fields) {
// CellFactory지정
			((TableColumn) table.getColumns().get(cnt)).setCellValueFactory(new PropertyValueFactory(f.getName()));
// 모든 컬럼이 일단 정렬할 수 있도록 지정
			((TableColumn) table.getColumns().get(cnt)).setSortable(true);
// 일단 에디트할 수 있는 가능성을 지정- 추후 다른 절차가 필요함
			((TableColumn) table.getColumns().get(cnt)).setEditable(true);
			cnt++;
		}
	}

	/**
	 * 
	 * @param 숫자를 의미하는 문자열
	 * @return 컴마가 붙은 문자열을 리턴
	 */
	public static String changeCommerNumeric(String x) {
		double num = Double.parseDouble(x);
		DecimalFormat df = new DecimalFormat("#,##0");
		return df.format(num);
	}

	/**
	 * 
	 * @param col - 숫자를 의미하는 컬럼지정
	 * @param pos - 정렬 위치(오른쪽,왼쪽,가운데)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> void setAlignment(TableColumn col, String pos) {
		col.setCellFactory(new Callback<TableColumn, TableCell>() {
			public TableCell call(TableColumn p) {
				TableCell cell = new TableCell<T, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}

					private String getString() {
						String ret = "";
						if (getItem() != null) {
							String gi = getItem().toString();
							NumberFormat df = DecimalFormat.getInstance();
							df.setMinimumFractionDigits(0);// 소숫점아래자리수
							df.setRoundingMode(RoundingMode.DOWN);

							ret = df.format(Double.parseDouble(gi.replaceAll("\\,", "")));
						} else {
							ret = "0";
						}
						return ret;
					}
				};

				switch (pos) {
				case "right":
				case "RIGHT":
					cell.setStyle("-fx-alignment: center-right;");
					break;
				case "left":
				case "LEFT":
					cell.setStyle("-fx-alignment: center-left;");
					break;
				case "center":
				case "CENTER":
					cell.setStyle("-fx-alignment: center;");
					break;
				}

				return cell;
			}
		});

	}
}