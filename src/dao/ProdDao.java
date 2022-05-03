package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cmn.ConnFac;
import vo.Prod;

public class ProdDao implements IDao<Prod,String>{

	@Override
	public synchronized boolean insert(Prod vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "INSERT INTO PROD(PRODID, PRODNAME, STOCUNIT, STOCQNTY, STOCPRCE) "
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, vo.getProdid());
		ppdstm.setString(2, vo.getProdname());
		ppdstm.setString(3, vo.getStocunit());
		ppdstm.setInt	(4, vo.getStocqnty());
		ppdstm.setInt	(5, vo.getStocprce());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean delete(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "DELETE FROM PROD WHERE PRODID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		int res = ppdstm.executeUpdate();

		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean update(Prod vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "UPDATE PROD SET PRODNAME = ?, STOCUNIT = ?,"
				+ " STOCQNTY = ?, STOCPRCE = ?"
				+ " WHERE PRODID = ?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);

		ppdstm.setString(1, vo.getProdname());
		ppdstm.setString(2, vo.getStocunit());
		ppdstm.setInt	(3, vo.getStocqnty());
		ppdstm.setInt	(4, vo.getStocprce());
		ppdstm.setString(5, vo.getProdid());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;		
	}

	@Override
	public synchronized Prod select(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM PROD WHERE PRODID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		ResultSet rs = ppdstm.executeQuery();
		
		Prod vo = new Prod();					// 기본생성 > set으로 완성
		if(rs.next()) {
		vo.setProdid(rs.getString(1));
		vo.setProdname(rs.getString(2));
		vo.setStocunit(rs.getString(3));
		vo.setStocqnty(rs.getInt(4));
		vo.setStocprce(rs.getInt(5));
		}
		ppdstm.close();
		conn.close();
		return vo;
	}

	@Override
	public List<Prod> selectAll() throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM PROD order by prodid";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ResultSet rs = ppdstm.executeQuery();
		
		List<Prod> list = new ArrayList<>();
		while(rs.next()) {
			Prod vo = new Prod();
			vo.setProdid(rs.getString(1));
			vo.setProdname(rs.getString(2));
			vo.setStocunit(rs.getString(3));
			vo.setStocqnty(rs.getInt(4));
			vo.setStocprce(rs.getInt(5));
			list.add(vo);
		}
		ppdstm.close();
		conn.close();
		return list;
	}


}
