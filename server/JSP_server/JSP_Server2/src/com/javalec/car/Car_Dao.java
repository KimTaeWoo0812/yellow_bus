package com.javalec.car;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Car_Dao {

	DataSource dataSource;
	
	public Car_Dao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/MySQLDB");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}	
	
	//����, �߰�, ���� �������Ѵ�
	//���� ����Ʈ ������
	public ArrayList<String> CarList(String schoolName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet result = null;
		String query = "select carName from car where school like '"+schoolName+"';";
		;
		ArrayList<String> dtos = new ArrayList<String>();
		
		try {
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				String carName = result.getString(1);
				dtos.add(carName);
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
	
	//�߰�
	/**
	 * ����  true, ���� false
	 * @param schoolName
	 * @param beaconName
	 * @param identifier
	 * @return
	 */
	public boolean CarAdd(String schoolName,String carName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		int val = 0;
		String query = "";
		
		try {

			query ="select Count(carName) from car where school like '"+schoolName+"' AND carName like '"+carName+"';";
			
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
			
			query = "insert into car(`school`, `carName`) "
					+ "values('"+schoolName+"', '"+carName+"');";
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			//�����ϸ� 1 ��ȯ
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
	//����
	public boolean CarDelete(String schoolName, String[] carName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int val = 0;
		String query = "";
		
		
		try {
			for(int i=0;i<carName.length;i++){
				System.out.println(schoolName+"  "+carName[i]);
				
				query = "delete from car where school like '"+schoolName+"' AND carName like '"+carName[i]+"';";
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement(query);
				
				//�����ϸ� 1 ��ȯ
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
	//���� �̸� ����
	public boolean CarReName(String schoolName, String carName, String newCarName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int val = 0;
		String query = "update car set carName='"+newCarName+"' "
				+ "where school like '"+schoolName+"' AND carName like '"+carName+"';";
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			//�����ϸ� 1 ��ȯ
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
