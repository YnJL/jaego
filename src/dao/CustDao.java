package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cmn.ConnFac;
import vo.Cust;


public class CustDao implements IDao<Cust,String>{

	@Override
	public boolean insert(Cust vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "INSERT INTO CUST(CUSTID, CUSTNAME, ADDR, CONT) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, vo.getCustid());
		ppdstm.setString(2, vo.getCustname());
		ppdstm.setString(3, vo.getAddr());
		ppdstm.setString(4, vo.getCont());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean delete(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "DELETE FROM CUST WHERE CUSTID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		int res = ppdstm.executeUpdate();

		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean update(Cust vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "UPDATE CUST SET CUSTNAME=?, ADDR=?, Cont=?"
				+ " WHERE CUSTID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);

		ppdstm.setString(1, vo.getCustname());
		ppdstm.setString(2, vo.getAddr());
		ppdstm.setString(3, vo.getCont());
		ppdstm.setString(4, vo.getCustid());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;	
	}

	@Override
	public Cust select(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM CUST WHERE CUSTID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		ResultSet rs = ppdstm.executeQuery();
		
		Cust vo = new Cust();					// 기본생성 > set으로 완성
		rs.next();
		vo.setCustid(rs.getString(1));
		vo.setCustname(rs.getString(2));
		vo.setAddr(rs.getString(3));
		vo.setCont(rs.getString(4));
		
		ppdstm.close();
		conn.close();
		return vo;
	}

	@Override
	public List<Cust> selectAll() throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM CUST order by custid";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ResultSet rs = ppdstm.executeQuery();
		
		List<Cust> list = new ArrayList<>();
		while(rs.next()) {
			Cust vo = new Cust();
			vo.setCustid(rs.getString(1));
			vo.setCustname(rs.getString(2));
			vo.setAddr(rs.getString(3));
			vo.setCont(rs.getString(4));
			list.add(vo);
		}
		ppdstm.close();
		conn.close();
		return list;
	}

}
