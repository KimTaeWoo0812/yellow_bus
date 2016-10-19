package com.javalec.beacon;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Beacon_Dao {

	DataSource dataSource;
	
	public Beacon_Dao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/MySQLDB");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}	
	static String getDate() {
		long time = System.currentTimeMillis(); 
        SimpleDateFormat f = new SimpleDateFormat("yyyy년MM월dd일hh시mm분");
        return f.format(new Date(time));
	}
	
	//수정, 추가, 삭제 만들어야한다
	//비콘 리스트 보내기
	public ArrayList<Beacon_Dto> BeaconList(String schoolName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet result = null;
		String query = "select schoolName, beaconName, identifier, isCar, makeDate from beacon where schoolName like '"+schoolName+"';";
		
		
		ArrayList<Beacon_Dto> dtos = new ArrayList<Beacon_Dto>();
		
		try {
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				Beacon_Dto dto = new Beacon_Dto();
				dto.SetSchoolName(result.getString(1));
				dto.SetBeaconName(result.getString(2));
				dto.SetIdentifier(result.getString(3));
				dto.SetIdCar(result.getString(4));
				dto.SetMakeDate(result.getString(5));
				dtos.add(dto);
				System.out.println("DB_1");
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
		return dtos;
	}
	
	//추가
	/**
	 * 성공  true, 실패 false
	 * @param schoolName
	 * @param beaconName
	 * @param identifier
	 * @return
	 */
	public boolean BeaconAdd(String schoolName,String beaconName, String identifier, String isCar) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		int val = 0;
		String query = "select Count(identifier) from beacon where identifier like '"+identifier+"';";
		
		try {
			
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			int check = 0;
			
			while (result.next()) {
				check = result.getInt(1);
			}

			if(check != 0)
				return false;
			
			connection = null;
			preparedStatement = null;
			
			query = "insert into beacon(`schoolName`, `beaconName`, `identifier`, `isCar`,`makeDate`) "
					+ "values('"+schoolName+"', '"+beaconName+"', '"+identifier+"', '"+isCar+"', '"+getDate()+"');";
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			//성공하면 1 반환
			val = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(val == 1)
			return true;
		else
			return false;
		
	}
	//삭제
	public boolean BeaconDelete(String[] identifier) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int val = 0;
		String query = "";
		
		try {
			
			for(int i=0;i<identifier.length;i++){
				String msg = identifier[i];
				query = "delete from beacon where identifier like '"+msg+"';";
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement(query);
				val = preparedStatement.executeUpdate();
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(val == 1)
			return true;
		else
			return false;
		
	}
	//비콘 이름 변경
	public boolean BeaconReName(String beaconName, String identifier) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int val = 0;
		String query = "update beacon set beaconName='"+beaconName+"' where identifier like '"+identifier+"';";
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			//성공하면 1 반환
			val = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(val == 1)
			return true;
		else
			return false;
		
	}
	
}
