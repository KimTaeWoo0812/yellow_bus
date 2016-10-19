package com.javalec.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class School_Dao {

	DataSource dataSource;
	
	public School_Dao() {
		try {
			Context context = new InitialContext();
			Context envCtx = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource)envCtx.lookup("jdbc/MySQLDB");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
//	public int insertMember(Account_Dto dto) {
//		int ri = 0;
//		
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		String query = "insert into members values (?,?,?,?,?,?)";
//		
//		try {
//			connection = getConnection();
//			pstmt = connection.prepareStatement(query);
//			pstmt.setString(1, dto.getId());
//			pstmt.setString(2, dto.getPw());
//			pstmt.setString(3, dto.getName());
//			pstmt.setString(4, dto.geteMail());
//			pstmt.setTimestamp(5, dto.getrDate());
//			pstmt.setString(6, dto.getAddress());
//			pstmt.executeUpdate();
//			ri = 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close();
//				if(connection != null) connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		return ri;
//	}
	public School_Dto DB_Maie(String schoolName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet result = null;
		String query = "select * from school where name like '"+schoolName+"';";
		String num = "";
		School_Dto dto = new School_Dto();
		try {
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
		
			if(result.next()) {
				dto.SetNum(result.getString(1));
				dto.SetName(result.getString(2));
				dto.SetLocation(result.getString(3));
				dto.SetLat(result.getString(4));
				dto.SetLon(result.getString(5));
				dto.SetManager(result.getString(6));
				dto.SetIsNotice(result.getString(7));
				dto.SetNotice(result.getString(8));
				
			}
			else {
				dto.SetName("0");
				System.out.println("DB_3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(result != null) result.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		return dto;
		
	}
	public School_Dto DB_Login(String id, String pw) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet result = null;
		String query = "select * from school where num like"
				+ " (select num from manager_account where id like '"+id+"' AND pw like '"+pw+"');";
		String num = "";
		School_Dto dto = new School_Dto();
		try {
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			if(result.next()) {
				dto.SetNum(result.getString(1));
				dto.SetName(result.getString(2));
				dto.SetLocation(result.getString(3));
				dto.SetLat(result.getString(4));
				dto.SetLon(result.getString(5));
				dto.SetManager(result.getString(6));
				dto.SetIsNotice(result.getString(7));
				dto.SetNotice(result.getString(8));
				
//				dto = new School_Dto(result.getString(1), result.getString(2), result.getString(3),
//						result.getString(4), result.getString(5), result.getString(6),
//						result.getString(7), result.getString(8));
				System.out.println("DB_2");
			}
			else {
				dto.SetName("0");
				System.out.println("DB_3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(result != null) result.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		return dto;
		
	}
	public School_Dto ReNotice(String schoolName, String notice) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int val = 0;
		ResultSet result = null;
		String query = "update school set notice='"+notice+"', isNotice='1' where name like '"+schoolName+"';";
		 
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			//성공하면 1 반환
			val = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		query = "select * from school where name like '"+schoolName+"';";
		String num = "";
		School_Dto dto = new School_Dto();
		try {
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
		
			if(result.next()) {
				dto.SetNum(result.getString(1));
				dto.SetName(result.getString(2));
				dto.SetLocation(result.getString(3));
				dto.SetLat(result.getString(4));
				dto.SetLon(result.getString(5));
				dto.SetManager(result.getString(6));
				dto.SetIsNotice(result.getString(7));
				dto.SetNotice(result.getString(8));
				
//				dto = new School_Dto(result.getString(1), result.getString(2), result.getString(3),
//						result.getString(4), result.getString(5), result.getString(6),
//						result.getString(7), result.getString(8));
				System.out.println("DB_2");
			}
			else {
				System.out.println("DB_3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(result != null) result.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		return dto;
		
	}
	
	public School_Dto DeleteNotice(String schoolName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int val = 0;
		ResultSet result = null;
		String query = "update school set isNotice='0' where name like '"+schoolName+"';";
		 
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			//성공하면 1 반환
			val = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		query = "select * from school where name like '"+schoolName+"';";
		String num = "";
		School_Dto dto = new School_Dto();
		try {
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
		
			if(result.next()) {
				dto.SetNum(result.getString(1));
				dto.SetName(result.getString(2));
				dto.SetLocation(result.getString(3));
				dto.SetLat(result.getString(4));
				dto.SetLon(result.getString(5));
				dto.SetManager(result.getString(6));
				dto.SetIsNotice(result.getString(7));
				dto.SetNotice(result.getString(8));
				
//				dto = new School_Dto(result.getString(1), result.getString(2), result.getString(3),
//						result.getString(4), result.getString(5), result.getString(6),
//						result.getString(7), result.getString(8));
				System.out.println("DB_2");
			}
			else {
				System.out.println("DB_3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(result != null) result.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		return dto;
		
	}
		
//	public ArrayList<School_Dto> membersAll() {
//		
//		ArrayList<School_Dto> dtos = new ArrayList<School_Dto>();
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String query = "select * from members";
//		
//		try {
//			connection = getConnection();
//			pstmt = connection.prepareStatement(query);
//			rs = pstmt.executeQuery();
//			
//			System.out.println("============");
//			while (rs.next()) {
//				School_Dto dto = new School_Dto();
//				dto.setId(rs.getString("id"));
//				dto.setPw(rs.getString("pw"));
//				dto.setName(rs.getString("name"));
//				dto.seteMail(rs.getString("eMail"));
//				dto.setrDate(rs.getTimestamp("rDate"));
//				dto.setAddress(rs.getString("address"));
//				dtos.add(dto);
//			}
//			System.out.println("--------------------------");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				pstmt.close();
//				connection.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		return dtos;
//		
//	}
//	
//	private Connection getConnection() {
//		
//		Context context = null;
//		DataSource dataSource = null;
//		Connection connection = null;
//		try {
//			context = new InitialContext();
////			Context envCon = (Context) con.lookup("java:comp/env");
////			DataSource ds = (DataSource) envCon.lookup("jdbc/MySQLDB");
//			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/MySQLDB");
//			connection = dataSource.getConnection();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return connection;
//	}
	
}
