package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cmn.ConnFac;
import vo.Farm;


public class FarmDao implements IDao<Farm,String>{

	@Override
	public boolean insert(Farm vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "INSERT INTO FARM(FARMID, FARMNAME, ADDR, Cont) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, vo.getFarmid());
		ppdstm.setString(2, vo.getFarmname());
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
		String sql = "DELETE FROM FARM WHERE FARMID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		int res = ppdstm.executeUpdate();

		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean update(Farm vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "UPDATE FARM SET FARMNAME=?, ADDR=?, Cont=?"
				+ " WHERE FARMID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);

		ppdstm.setString(1, vo.getFarmname());
		ppdstm.setString(2, vo.getAddr());
		ppdstm.setString(3, vo.getCont());
		ppdstm.setString(4, vo.getFarmid());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;	
	}

	@Override
	public Farm select(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM FARM WHERE FARMID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		ResultSet rs = ppdstm.executeQuery();
		
		Farm vo = new Farm();					// 기본생성 > set으로 완성
		rs.next();
		vo.setFarmid(rs.getString(1));
		vo.setFarmname(rs.getString(2));
		vo.setAddr(rs.getString(3));
		vo.setCont(rs.getString(4));
		
		ppdstm.close();
		conn.close();
		return vo;
	}

	@Override
	public List<Farm> selectAll() throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM FARM order by farmid";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ResultSet rs = ppdstm.executeQuery();
		
		List<Farm> list = new ArrayList<>();
		while(rs.next()) {
			Farm vo = new Farm();
			vo.setFarmid(rs.getString(1));
			vo.setFarmname(rs.getString(2));
			vo.setAddr(rs.getString(3));
			vo.setCont(rs.getString(4));
			list.add(vo);
		}
		ppdstm.close();
		conn.close();
		return list;
	}

}
