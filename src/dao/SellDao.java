package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cmn.ConnFac;
import vo.Sell;


public class SellDao implements IDao<Sell,String>{

	@Override
	public boolean insert(Sell vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "INSERT INTO SELL(SELLID, PRODID, CUSTID,"
				+ " SELLDATE, SELLUNIT, SELLQNTY, SELLPRCE, SELLTOTL)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, vo.getSellid());
		ppdstm.setString(2, vo.getProdid());
		ppdstm.setString(3, vo.getCustid());
		ppdstm.setDate(4, vo.getSelldate());
		ppdstm.setString(5, vo.getSellunit());
		ppdstm.setInt(6, vo.getSellqnty());
		ppdstm.setInt(7, vo.getSellprce());
		ppdstm.setInt(8, vo.getSelltotl());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean delete(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "DELETE FROM SELL WHERE SELLID = ?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		int res = ppdstm.executeUpdate();

		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean update(Sell vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "UPDATE SELL SET PRODID=?, CUSTID=?, SellDATE=?,"
				+ " SELLUNIT=?, SELLQNTY=?, SELLPRCE=?, SELLTOTL=? "
				+ "WHERE SELLID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);

		ppdstm.setString(1, vo.getProdid());
		ppdstm.setString(2, vo.getCustid());
		ppdstm.setDate(3, vo.getSelldate());
		ppdstm.setString(4, vo.getSellunit());
		ppdstm.setInt(5, vo.getSellqnty());
		ppdstm.setInt(6, vo.getSellprce());
		ppdstm.setInt(7, vo.getSelltotl());
		ppdstm.setString(8, vo.getSellid());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;		
	}

	@Override
	public Sell select(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM SELL WHERE SELLID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		ResultSet rs = ppdstm.executeQuery();
		
		Sell vo = new Sell();					// 기본생성 > set으로 완성
		rs.next();
		vo.setSellid(rs.getString(1));
		vo.setProdid(rs.getString(2));
		vo.setCustid(rs.getString(3));
		vo.setSelldate(rs.getDate(4));
		vo.setSellunit(rs.getString(5));
		vo.setSellqnty(rs.getInt(6));
		vo.setSellprce(rs.getInt(7));
		
		ppdstm.close();
		conn.close();
		return vo;
	}

	@Override
	public List<Sell> selectAll() throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM SELL order by sellid";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ResultSet rs = ppdstm.executeQuery();
		
		List<Sell> list = new ArrayList<>();
		while(rs.next()) {
			Sell vo = new Sell();
			vo.setSellid(rs.getString(1));
			vo.setProdid(rs.getString(2));
			vo.setCustid(rs.getString(3));
			vo.setSelldate(rs.getDate(4));
			vo.setSellunit(rs.getString(5));
			vo.setSellqnty(rs.getInt(6));
			vo.setSellprce(rs.getInt(7));
			list.add(vo);
		}
		ppdstm.close();
		conn.close();
		return list;
	}

}
