package com.javalec.member;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalec.beacon.Beacon_Dto;

public class Member_Dao {

	DataSource dataSource;
	
	public Member_Dao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/MySQLDB");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}	
	
	//수정, 추가, 삭제 만들어야한다
	public ArrayList<Member_Dto> MemberList(String schoolName) {
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		String query = "select userId, userName,role, parent1 from myschool where schoolName like '"+schoolName+"';";
		ArrayList<Member_Dto> dtos = new ArrayList<Member_Dto>();
		
		System.out.println("\t\t\t\t\t"+schoolName);
		
		
		try {
		
//			ArrayList<String> id = new ArrayList<>();
//			//ArrayList<String> name = new ArrayList<>();
//			
//			connection = dataSource.getConnection();
//			preparedStatement = connection.prepareStatement(query);
//			result = preparedStatement.executeQuery();
//			int j=0;
//			while (result.next()) {
//				id.add(result.getString(1));
//				//name.add(result.getString(2));
//			}
			
			
//				j++;
//				if(j>count)
//					break;
//				Member_Dto dto = new Member_Dto();
//				dto.SetId(result.getString(1));
//				dto.SetName(result.getString(2));
		
				
				
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			
			while(result.next()){
				Member_Dto dto = new Member_Dto();
				dto.SetId(result.getString(1));
				dto.SetName(result.getString(2));
				dto.SetRole(result.getString(3));
				dto.SetParent1(result.getString(4));

				//System.out.println("으악: "+id.get(j)+"  "+result2.getString(1)+"  "+result2.getString(2)+"  "+result2.getString(3));
				
				if(!result.getString(3).equals("학부모"))
					dtos.add(dto);
			}
			
		
		
////		query = "select user.id, user.name, mySchool.role, mySchool.parent1 from user, mySchool "
////				+ "where user.id=any(select userId from mySchool where schoolName like '"+schoolName+"');";
//		
//		query = "select id from user where id=any(select userId from mySchool where schoolName like '"+schoolName+"');";
//		ArrayList<Member_Dto> dtos = new ArrayList<Member_Dto>();
//		
//		try {
////			ArrayList<String> role = new ArrayList<>();
////			ArrayList<String> parent1 = new ArrayList<>();
////			
////			Connection connection2 = null;
////			PreparedStatement preparedStatement2 = null;
////			connection2 = dataSource.getConnection();
////			preparedStatement2 = connection.prepareStatement("select role parent1 from myschool where schoolName like '"+schoolName+"' AND userName like '"+result.getString(2)+"';");
////			ResultSet result2 = preparedStatement.executeQuery();
////			while(result2.next()){
////				role.add(result2.getString(1));
////				parent1.add(result2.getString(2));
////			}
//			
//			
//			
//			
//			ArrayList<String> id = new ArrayList<>();
//			//ArrayList<String> name = new ArrayList<>();
//			
//			connection = dataSource.getConnection();
//			preparedStatement = connection.prepareStatement(query);
//			result = preparedStatement.executeQuery();
//			int j=0;
//			while (result.next()) {
//				id.add(result.getString(1));
//				//name.add(result.getString(2));
//			}
//			
//			
////				j++;
////				if(j>count)
////					break;
////				Member_Dto dto = new Member_Dto();
////				dto.SetId(result.getString(1));
////				dto.SetName(result.getString(2));
//			for(j=0;j<id.size();j++){
//				Connection connection2 = null;
//				PreparedStatement preparedStatement2 = null;
//				connection2 = dataSource.getConnection();
//				System.out.println(id.get(j));
//				preparedStatement2 = connection.prepareStatement("select userName,role, parent1 from myschool where schoolName like '"+schoolName+"' AND userid like '"+id.get(j)+"';");
//				ResultSet result2 = preparedStatement2.executeQuery();
//				if(result2.next()){
//					Member_Dto dto = new Member_Dto();
//					dto.SetId(id.get(j));
//					dto.SetName(result2.getString(1));
//					dto.SetRole(result2.getString(2));
//					dto.SetParent1(result2.getString(3));
//
//					System.out.println("으악: "+id.get(j)+"  "+result2.getString(1)+"  "+result2.getString(2)+"  "+result2.getString(3));
//					
//					if(!result2.getString(2).equals("학부모"))
//						dtos.add(dto);
//				}
//			}
			
			
				
//				dto.SetRole(role.get(j));
//				dto.SetParent1(parent1.get(j));
				
				
				
				//System.out.println("count: "+result.getString(4));
				
				
			
			
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
	//여기서는 dtos를 받는다.  Dtos 에 등급도 들어가야된다.
	//웹에서 줄때 부모가 없으면 0으로 셋팅해서 보내야한다.
	public boolean MemberAdd(String id, String name, String schoolName, String parent1, String parent2, String role) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Member_Dto dto = new Member_Dto();
		String query = "";
		ResultSet result = null;
		int val;
		
		
		
		try {
			
			query = "select Count(userId) from mySchool where userId like '"+id+"' AND schoolName like '"+schoolName+"';";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			result = preparedStatement.executeQuery();
			int val2 = 0;
			while (result.next()) {
				val2 = result.getInt(1);
			}
			if(val2 != 0){
				System.out.println("test: "+ val2);
			
				query = "update myschool set userId='"+id+"', userName='"+name+"', "
						+ "parent1='"+parent1+"',parent2='"+parent2+"',role='"+role+"' "
								+ "where userId like '"+id+"' AND schoolName like '"+schoolName+"';";
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement(query);
				val = preparedStatement.executeUpdate();
			
				return true;
			}
			
			
			connection = dataSource.getConnection();

			query = "insert into mySchool(`userId`,`userName`,`schoolName`,`parent1`,`parent2`,`role`)" + " values('"
					+ id + "','" + name + "','" + schoolName + "','" + parent1 + "','" + parent2 + "','" + role
					+ "');";
			preparedStatement = connection.prepareStatement(query);
			// 성공하면 1 반환
			val = preparedStatement.executeUpdate();
			
			if(parent1.equals("")){
				System.out.println("떙떙");
			}
			if(parent1.equals(null))
				System.out.println("널");
			
		
		
		
			if(!parent1.equals("")){
				System.out.println(parent1);
				System.out.println(parent2);
				System.out.println(schoolName);
				System.out.println(role);
				Connection connection2 = null;
				PreparedStatement preparedStatement2 = null;
				query = "insert into mySchool(`userId`,`userName`,`schoolName`,`parent1`,`parent2`,`role`)" + " values('"
						+ parent1 + "','-','" + schoolName + "','-','-','학부모');";
				preparedStatement2 = connection.prepareStatement(query);
				// 성공하면 1 반환
				val = preparedStatement2.executeUpdate();
				
				
			}
			if(!parent2.equals("")){
				System.out.println(parent1);
				System.out.println(parent2);
				System.out.println(schoolName);
				System.out.println(role);
				Connection connection2 = null;
				PreparedStatement preparedStatement2 = null;
				query = "insert into mySchool(`userId`,`userName`,`schoolName`,`parent1`,`parent2`,`role`)" +
				" values('"+parent2+"','-','" + schoolName + "','-','-','학부모');";
				preparedStatement2 = connection.prepareStatement(query);
				// 성공하면 1 반환
				val = preparedStatement2.executeUpdate();
				
				
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
		return true;
		
	}
	//삭제
	public boolean MemberDelete(String[] id, String schoolName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "";
		int val;
		try {
			connection = dataSource.getConnection();

			//String query = "delete from beacon where identifier like '"+identifier+"';";
			for(int i=0;i<id.length;i++){
				query = "delete from mySchool where userId like '"+id[i]+"' AND schoolName like '"+schoolName+"';";
				preparedStatement = connection.prepareStatement(query);
				//성공하면 1 반환
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
		return true;
		
	}
	
	//비콘 이름 변경
	public boolean MemberReRole(ArrayList<Member_Dto> dtos, String schoolName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Member_Dto dto = new Member_Dto();
		String query = "";
		int val;
		try {//"update beacon set beaconName='"+beaconName+"' where identifier like '"+identifier+"';";
			connection = dataSource.getConnection();

			//String query = "delete from beacon where identifier like '"+identifier+"';";
			for(int i=0;i<dtos.size();i++){
				dto = dtos.get(i);
				query = "update mySchool set role='"+dto.GetRole()+"' where id like '"+dto.GetId()+"' AND schoolName like '"+schoolName+"';";
				preparedStatement = connection.prepareStatement(query);
				//성공하면 1 반환
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
		return true;
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////부모 추가, 삭제, 수정/////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	public boolean ParentAdd(ArrayList<Member_Dto> dtos, String schoolName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Member_Dto dto = new Member_Dto();
		String query = "";
		int val;
		try {
			connection = dataSource.getConnection();
			
			for(int i=0;i<dtos.size();i++){
				dto = dtos.get(i);
				query = "update mySchool set parent1='"+dto.GetParent1()+"', parent2='"+dto.GetParent2()+"' where id like '"+dto.GetId()+"' AND schoolName like '"+schoolName+"';";
				preparedStatement = connection.prepareStatement(query);
				//성공하면 1 반환
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
		return true;
		
	}
	
}