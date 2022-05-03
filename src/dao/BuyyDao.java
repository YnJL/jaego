package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cmn.ConnFac;
import vo.Buyy;


public class BuyyDao implements IDao<Buyy,String>{

	@Override
	public boolean insert(Buyy vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "INSERT INTO BUYY(BUYYID, PRODID, FARMID, HARVDATE,"
				+ " BUYYDATE, BUYYUNIT, BUYYQNTY, BUYYPRCE, BUYYTOTL)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, vo.getBuyyid());
		ppdstm.setString(2, vo.getProdid());
		ppdstm.setString(3, vo.getFarmid());
		ppdstm.setDate(4, vo.getHarvdate());
		ppdstm.setDate(5, vo.getBuyydate());
		ppdstm.setString(6, vo.getBuyyunit());
		ppdstm.setInt(7, vo.getBuyyqnty());
		ppdstm.setInt(8, vo.getBuyyprce());
		ppdstm.setInt(9, vo.getBuyytotl());
		
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean delete(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "DELETE FROM BUYY WHERE BUYYID = ?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		int res = ppdstm.executeUpdate();

		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;
	}

	@Override
	public boolean update(Buyy vo) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "UPDATE BUYY SET PRODID=?, FARMID=?, HARVDATE=?,"
				+ " BUYYDATE=?, BUYYUNIT=?, BUYYQNTY=?, BUYYPRCE=?, BUYYTOTL=? "
				+ "WHERE BUYYID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);

		ppdstm.setString(1, vo.getProdid());
		ppdstm.setString(2, vo.getFarmid());
		ppdstm.setDate(3, vo.getHarvdate());
		ppdstm.setDate(4, vo.getBuyydate());
		ppdstm.setString(5, vo.getBuyyunit());
		ppdstm.setInt(6, vo.getBuyyqnty());
		ppdstm.setInt(7, vo.getBuyyprce());
		ppdstm.setInt(8, vo.getBuyytotl());
		ppdstm.setString(9, vo.getBuyyid());
		int res = ppdstm.executeUpdate();		// update된 항목 수
		
		ppdstm.close();
		conn.close();
		return (res>=1)?true:false;		
	}

	@Override
	public Buyy select(String key) throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM BUYy WHERE BUYyID=?";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ppdstm.setString(1, key);
		ResultSet rs = ppdstm.executeQuery();
		
		Buyy vo = new Buyy();					// 기본생성 > set으로 완성
		rs.next();
		vo.setBuyyid(rs.getString(1));
		vo.setProdid(rs.getString(2));
		vo.setFarmid(rs.getString(3));
		vo.setHarvdate(rs.getDate(4));
		vo.setBuyydate(rs.getDate(5));
		vo.setBuyyunit(rs.getString(6));
		vo.setBuyyqnty(rs.getInt(7));
		vo.setBuyyprce(rs.getInt(8));
		
		ppdstm.close();
		conn.close();
		return vo;
	}


	@Override
	public List<Buyy> selectAll() throws SQLException {
		Connection conn = ConnFac.getConnection();
		String sql = "SELECT * FROM BUYy order by buyyid";
		PreparedStatement ppdstm = conn.prepareStatement(sql);
		ResultSet rs = ppdstm.executeQuery();
		
		List<Buyy> list = new ArrayList<>();
		while(rs.next()) {
			Buyy vo = new Buyy();
			vo.setBuyyid(rs.getString(1));
			vo.setProdid(rs.getString(2));
			vo.setFarmid(rs.getString(3));
			vo.setHarvdate(rs.getDate(4));
			vo.setBuyydate(rs.getDate(5));
			vo.setBuyyunit(rs.getString(6));
			vo.setBuyyqnty(rs.getInt(7));
			vo.setBuyyprce(rs.getInt(8));
			list.add(vo);
		}
		ppdstm.close();
		conn.close();
		return list;
	}

}
