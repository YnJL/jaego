package dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<V,K> {	// 일반적 인터페이스 용도 : 메소드 명 통일 - 추상메소드 활용
	public boolean insert(V vo) throws SQLException; 	// C
	public boolean delete(K key) throws SQLException;	// D
	public boolean update(V vo) throws SQLException;	// U
	public V select(K key) throws SQLException;		// R
	public List<V> selectAll() throws SQLException;	// R
}
