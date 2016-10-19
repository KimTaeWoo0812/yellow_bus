package Process;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import SubClass.LogManager;
import SubClass.SC;
import SubClass.ServerManage;


public class DB {
	static Connection con = null;
	static Statement stmt;
	
	static ServerManage Manage;
	
	public static String _del="";//데이터 구분자
	public static String _endDel="";//어레이리스트 종료 구분자
	public static String _endSendDel="";//데이터 끝 구분자
	
	public static DB _shared = null;//싱글톤
	public static DB shared() {
		if (_shared == null) {
			_shared = new DB();
		}
		return _shared;
	}
	
	public DB() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Manage = new ServerManage(this);
		Thread t = new Thread(Manage);
		t.start();
		
		_del+=(char)200;
		_endDel+=(char)201;
		_endSendDel+=(char)202;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Class.forName("org.gjt.mm.mysql.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "ekdrms52");
			System.out.println("DB접속 성공!");

			stmt = con.createStatement();
			
//			//유저
//			stmt.executeUpdate("CREATE TABLE user ("
//					+ "num int NOT NULL auto_increment, "
//					+ "id varchar(40) NOT NULL, " 
//					+ "pw varchar(40) NOT NULL, " 
//					+ "name varchar(40) NOT NULL, "
//					+ "isLogin int NOT NULL, " 
//					+ "temp varchar(20) NOT NULL, "
//					+ "PRIMARY KEY(num) " 
//					+");");
//			//학원 계정
//			stmt.executeUpdate("CREATE TABLE manager_account ("
//					+ "num int NOT NULL, "
//					+ "id varchar(40) NOT NULL, " 
//					+ "pw varchar(40) NOT NULL" 
//					+");");
//			
//			//내 학원 목록
//			stmt.executeUpdate("CREATE TABLE mySchool ("
//					+ "userId varchar(40) NOT NULL, " 
//					+ "userName varchar(40) NOT NULL, " 
//					+ "schoolName varchar(40) NOT NULL, "
//					+ "parent1 varchar(40) NOT NULL, "
//					+ "parent2 varchar(40) NOT NULL, "
//					+ "role varchar(20) NOT NULL "   //역할
//					+");");
//					//+ "foreign key(userId) references user(num) "
//					//+ "on delete cascade"
//					//1 = 학생,  2 = 학부모, 3 = 1등급 관리자, 4 = 2등급 관리자, 5 = 3등급 관리자
//			
//			//활동 로그
//			stmt.executeUpdate("CREATE TABLE move_log ("
//					+ "studentId  varchar(40) NOT NULL, " 
//					+ "studentName  varchar(40) NOT NULL, " 
//					+ "content  varchar(60) NOT NULL, " //내용
//					+ "time  varchar(40) NOT NULL " //data는 19글자로 나온다
//					+");");
//			
//			//학원
//			stmt.executeUpdate("CREATE TABLE school ("
//					+ "num int NOT NULL auto_increment, "
//					+ "name varchar(40) NOT NULL, "
//					+ "location varchar(40) NOT NULL, "
//					+ "lat varchar(40) NOT NULL, "
//					+ "lon varchar(40) NOT NULL, "
//					+ "manager varchar(40) NOT NULL, "
//					+ "isNotice varchar(10) NOT NULL, "
//					+ "notice TEXT(1024) NOT NULL, "//1020byte 사용
//					+ "PRIMARY KEY(num) " 
//					+");");
//			//비콘
//			stmt.executeUpdate("CREATE TABLE beacon ("
//					+ "schoolName  varchar(40) NOT NULL, " 
//					+ "beaconName varchar(40) NOT NULL, "
//					+ "identifier varchar(60) NOT NULL, "
//					+ "isCar varchar(10) NOT NULL, "
//					+ "makeDate varchar(60) NOT NULL "
//					+");");
//			
//			
//			//차
//			stmt.executeUpdate("CREATE TABLE car ("
//					+ "school  varchar(40) NOT NULL, " 
//					+ "carName varchar(40) NOT NULL "
//					+");");
		
					
			ResultSet result = stmt.executeQuery("select * from user;");
			System.out.println("user:");
			while (result.next()) {
				System.out.print(result.getString(1) + "\t");
				System.out.print(result.getString(2) + "\t");
				System.out.print(result.getString(3) + "\t");
				System.out.print(result.getString(4) + "\t");
				System.out.print(result.getString(5) + "\t");
				System.out.print(result.getString(6) + "\n");
			}
			result = stmt.executeQuery("select * from mySchool;");
			System.out.println("mySchool:");
			while (result.next()) {
				System.out.print(result.getString(1) + "\t");
				System.out.print(result.getString(2) + "\t");
				System.out.print(result.getString(3) + "\t");
				System.out.print(result.getString(4) + "\n");
			}

			
//			result = stmt.executeQuery("select * from move_log;");
//			System.out.println("move_log:");
//			while (result.next()) {
//				System.out.print(result.getString(1) + "\t");
//				System.out.print(result.getString(2) + "\t");
//				System.out.print(result.getString(3) + "\t");
//				System.out.print(result.getString(4) + "\n");
//			}
			result = stmt.executeQuery("select * from school;");
			System.out.println("school:");
			while (result.next()) {
				System.out.print(result.getString(1) + "\t");
				System.out.print(result.getString(2) + "\t");
				System.out.print(result.getString(3) + "\t");
				System.out.print(result.getString(4) + "\t");
				System.out.print(result.getString(5) + "\t");
				System.out.print(result.getString(6) + "\t");
				System.out.print(result.getString(7) + "\t");
				System.out.print(result.getString(8) + "\n");
			}
			result = stmt.executeQuery("select * from beacon;");
			System.out.println("beacon:");
			while (result.next()) {
				System.out.print(result.getString(1) + "\t");
				System.out.print(result.getString(2) + "\n");
			}
			result = stmt.executeQuery("select * from car;");
			System.out.println("car:");
			while (result.next()) {
				System.out.print(result.getString(1) + "\t");
				System.out.print(result.getString(2) + "\n");
			}
			// con.close();//DB 占쏙옙占쏙옙
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL err:" + e.toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		

		LogManager.SetLog();
	}
	public static ArrayList<String> CheckTables(){
		ArrayList<String> val = new ArrayList<String>();
		try {
			ResultSet result = stmt.executeQuery("show tables;");
			while (result.next())
				val.add(result.getString(1));

			result.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return val;
	}
	
	public static int Make_Log_Table(String newName){
		
		try {
			stmt.executeUpdate("CREATE TABLE "+newName+" ("
					+ "studentId  varchar(40) NOT NULL, " 
					+ "studentName  varchar(40) NOT NULL, " 
					+ "content  varchar(80) NOT NULL, " //내용
					+ "time  varchar(40) NOT NULL " //data는 19글자로 나온다
					+");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return 0;
			
		}
		
		return 1;
		
	}
	
	//활동 로그
//	stmt.executeUpdate("CREATE TABLE move_log ("
//			+ "studentId  varchar(40) NOT NULL, " 
//			+ "studentName  varchar(40) NOT NULL, " 
//			+ "content  varchar(60) NOT NULL, " //내용
//			+ "time  varchar(40) NOT NULL " //data는 19글자로 나온다
//			+");");
	
	
	
	
	static String getDate() {
		long time = System.currentTimeMillis(); 
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
        return f.format(new Date(time));
	}
	
	public synchronized ArrayList<StringBuilder> Process_List(String query, int size){
		ArrayList<StringBuilder> val = new ArrayList<StringBuilder>();
		StringBuilder temp = new StringBuilder();
		ResultSet result;
		int j=0, i=0;
		try {
			
			result = stmt.executeQuery(query);
			while (result.next()){
				for(i=1;i<size;i++){
					temp.append(result.getString(i)+_del);
				}
				temp.append(result.getString(i)+_del+_endDel);

				j++;
				if(j>60){
					temp.append(SC._endSendDel);
					//SC.Print0(temp.toString());
					val.add(temp);
					temp.setLength(0);
					j=0;
				}
			}
			temp.append("0"+_del+"0"+_del+_endDel);
			val.add(temp);
			result.close();
			return val;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 회원가입
	 * return 1 = 가입 성공, 2 = 실패, 3 = 이미 있는 회원
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public synchronized int JoinUs(String id, String pw, String name) {
		ResultSet result;
		try {
			//먼저 중복확인
			result = stmt
					.executeQuery("select Count(id) from user where id like '"
							+ id + "' ;");
			String strTemp = "";
			while (result.next())
				strTemp = result.getString(1);

			char cTemp = strTemp.charAt(0);

			if (cTemp == '0') {
				//여기서부터 가입 시작

				String message = "insert into user(`num`, `id`, `pw`, `name`, `isLogin`, `temp`) "
						+ "values(NULL,'"
						+ id
						+ "', '"
						+ pw
						+ "', '"
						+ name
						+ "', '"
						+ 0
						+ "', '" + 0 + "');";
				
				//비번 확인!!
				
				
				// String message = "insert into USER values('" + temp + "','" +id+ "','" + pw + "','0');";
				stmt.executeUpdate(message);
				result = stmt
						.executeQuery("select Count(id) from user where id like '"
								+ id + "' ;");
				int temp = 0;
				while (result.next())
					temp = result.getInt(1);

				result.close();
				if (temp == 1){
					SC.Print0(id + " 가입 성공 from DB");
					return 1;
				}
				else
					return 0;
			} else
				return 3;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 성공: 1, 실패: 0
	 * @param id
	 * @return
	 */
	public synchronized int IdCheck(String id) {
		ResultSet result;
		try {
			result = stmt
					.executeQuery("select Count(id) from user where id like '"
							+ id + "' ;");
			String strTemp = "";
			while (result.next())
				strTemp = result.getString(1);

			char cTemp = strTemp.charAt(0);
			result.close();
			if (cTemp == '0')
				return 1;
			else
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * return 1 = 성공, 2 = 없는 id, 3 = 이미 로그인됨, 3 = 틀린 비밀번호, 0 = 실패
	 * @param id
	 * @return
	 */
	public synchronized int LOGIN(String id, String pw) {
		ResultSet result;
		try {
			result = stmt
					.executeQuery("select Count(id),pw,isLogin from user where id like '"+ id + "' ;");
			String strTemp = "";
			String pwCheck = "";
			int nTemp = 0;
			while (result.next()){
				strTemp = result.getString(1);
				pwCheck = result.getString(2);
				nTemp = result.getInt(3);
				
			}
			result.close();
			char cTemp = strTemp.charAt(0);
			if (cTemp == '0')//없는 id
				return 2;
			if (nTemp == 1)
				return 3;// 이미 로그인됨
			if(!pwCheck.equals(pw))
				return 4;//틀린 비밀번호
			
			else{
//				String m = "update user set islogin='1' where id like '" + id
//						+ "';";
//				stmt.executeUpdate(m);
				
				SC.Print0(id + " 로그인 from DB");
				return 1; // 로그인 성공
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;// 실패
		}

	}
	public synchronized void LoginOut(String id) {
		try {
			stmt.executeUpdate("update user set islogin='0' where id like '"
					+ id + "';");// 占쏙옙占쏙옙
			SC.Print0(id + " 로그아웃 from DB");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized StringBuilder GetName(String id){
		ResultSet result;
		StringBuilder strTemp = null;
		try {
			result = stmt.executeQuery("select name from user where id like '"+ id + "' ;");
			
			while (result.next())
				strTemp = new StringBuilder(result.getString(1));
			
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strTemp;
	}
	public synchronized ArrayList<StringBuilder> S_LIST(String id) {
		ArrayList<StringBuilder> val = Process_List("SELECT schoolName,role from mySchool where userId like '"+id+"';", 2);
		return val;
	}
	
	public synchronized String S_INFO(String schoolName) {
		ResultSet result;
		String strTemp="";
		int i=0;
		try {
			
			result = stmt.executeQuery("SELECT num,name,location,lat,lon,manager,isNotice,notice from school "
					+ "where name like '"+schoolName+"';");
			while (result.next()){
				strTemp += result.getString(1)+_del;
				strTemp += result.getString(2)+_del;
				strTemp += result.getString(3)+_del;
				strTemp += result.getString(4)+_del;
				strTemp += result.getString(5)+_del;
				strTemp += result.getString(6)+_del;
				strTemp += result.getString(7)+_del;
				strTemp += result.getString(8)+_del;

				break;
			}
			result.close();
			return strTemp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "0";
		
		
	}
	
	public synchronized ArrayList<StringBuilder> S_CAR(String schoolName) {
		ArrayList<StringBuilder> val = Process_List("SELECT carName from car where school like '"+schoolName+"';", 1);
		return val;
	}
	public synchronized ArrayList<StringBuilder> S_BEACON(String schoolName) {
		ArrayList<StringBuilder> val = Process_List("SELECT identifier,isCar from beacon where schoolName like '"+schoolName+"';", 2);
		return val;
	}
	public synchronized void LOG(String id, String name, String content, String time) {
		try {
			String message = "insert into "+LogManager.GetRearLogTableName()+"(`studentId`, `studentName`, `content`, `time`) "
					+ "values('"+id+"','"+name+"','"+content+"','"+time+"');";
			stmt.executeUpdate(message);
			SC.Print0("from DB: LOG 삽입");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 학생한테 로그 리스트를 준다
	public synchronized ArrayList<StringBuilder> L_LIST_TO_S(String studentId) {
		//System.out.println("asdasdasd: "+LogManager.GetRearLogTableName());
		ArrayList<StringBuilder> val = Process_List("SELECT content,time from " + LogManager.GetRearLogTableName()
				+ " where studentId like '" + studentId + "';", 2);
		return val;
	}

	// 학생한테 로그 리스트를 준다2
	public synchronized ArrayList<StringBuilder> L_LIST_TO_S2(String studentId) {
		ArrayList<StringBuilder> val = Process_List("SELECT content,time from " + LogManager.GetR_RearLogTableName()
				+ " where studentId like '" + studentId + "';", 2);
		return val;
	}

	// 부모한테 학생의 로그 리스트를 준다
	public synchronized ArrayList<StringBuilder> L_LIST_TO_P(String parent) {
		ArrayList<StringBuilder> query = new ArrayList<StringBuilder>();
		StringBuilder temp = new StringBuilder();
		ResultSet result;
		String strTemp = "";
		parent = "SELECT userId from mySchool where parent1 like '"+parent+"' OR parent2 like '"+parent+"';";
		
		int j=0;
		try {
			
			result = stmt.executeQuery(parent);
			while (result.next())
				parent = result.getString(1);
				
			result.close();
			//System.out.println(parent);
			ArrayList<StringBuilder> val = Process_List("SELECT content,time from "+LogManager.GetRearLogTableName()
														+" where studentId like '"+parent+"';", 2);
			return val;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 부모한테 학생의 로그 리스트를 준다2
	public synchronized ArrayList<StringBuilder> L_LIST_TO_P2(String parent) {
		ArrayList<StringBuilder> query = new ArrayList<StringBuilder>();
		StringBuilder temp = new StringBuilder();
		ResultSet result;
		String strTemp = "";
		parent = "SELECT userId from mySchool where parent1 like '"+parent+"' OR parent2 like '"+parent+"';";
		int j = 0;
		try {

			result = stmt.executeQuery(parent);
			while (result.next())
				parent = result.getString(1);

			//System.out.println(parent);
			ArrayList<StringBuilder> val = Process_List("SELECT content,time from " + LogManager.GetR_RearLogTableName()
														+ " where studentId like '" + parent + "';", 2);
			result.close();
			return val;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 1: 성공, 2: 실패
	 * 
	 * @param schoolName
	 * @param text
	 * @return
	 */
	public synchronized int M_NOTICE(String schoolName, String text) {
		int val = 0;
		String m = "update school set isNotice='1', notice='"+text+"' where name like '" + schoolName+ "';";
		try {
			stmt.executeUpdate(m);
			val = 1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}
	
	public synchronized ArrayList<StringBuilder> M_VIEW_MEMBER(String schoolName) {
		ArrayList<StringBuilder> val = Process_List("SELECT userId,userName,role from mySchool where schoolName like '"+schoolName+"';", 3);
		return val;
	}
}
