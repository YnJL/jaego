package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmn.ConnFac;

public class ASAMPLELISTDAO implements IDao<Map<String,String>,Integer> {


	@Override
	public Map<String, String> select(Integer key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM VIEW_SALELIST WHERE ORDERID = ?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setInt(1, key);
		ResultSet rs = ppdstm.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();

		Map<String, String> map = new HashMap<>();
		while(rs.next()) {
			for(int i=1;i<=rsmd.getColumnCount();i++) {
			map.put(rsmd.getColumnName(i), rs.getString(i));
			}			
		}
		return map;
	}

	@Override
	public List<Map<String, String>> selectAll() throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM VIEW_SALELIST";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ResultSet rs = ppdstm.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		List<Map<String,String>> list = new ArrayList<>();
		while(rs.next()) {
			Map<String, String> map = new HashMap<>();
			for(int i=1;i<=rsmd.getColumnCount();i++) {
			map.put(rsmd.getColumnName(i), rs.getString(i));
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map<String, String>> selectByCondition(String condition) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM VIEW_SALELIST WHERE " + condition;
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ResultSet rs = ppdstm.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		List<Map<String,String>> list = new ArrayList<>();
		while(rs.next()) {
			Map<String, String> map = new HashMap<>();
			for(int i=1;i<=rsmd.getColumnCount();i++) {
			map.put(rsmd.getColumnName(i), rs.getString(i));
			}
			list.add(map);
		}
		return list;
	}

/////////////////////// 미사용 //////////////////////	
	
	@Override
	public boolean insert(Map<String, String> vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Map<String, String> vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
