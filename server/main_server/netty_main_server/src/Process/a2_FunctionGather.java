package Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import SubClass.SC;
import io.netty.channel.ChannelHandlerContext;

public class a2_FunctionGather extends SharingFunction{
	String strMsg = "";
	DB Db;
	public a2_FunctionGather(){
		Db = DB.shared();
		SC.Print0("DB 접속 from a2_FunctionGather");
	}
	
	//=========================================================여기서부터 쿼리 기능 함수
	public synchronized void RODING(ChannelHandlerContext ctx) {
		String strMsg = "RODING"+SC._del;
		SendMsg(ctx, strMsg);
	}
	public synchronized void JOINUS(ChannelHandlerContext ctx, String Msg[]) {
		
		int msg;
		String strMsg = "";
		
		
		msg = Db.JoinUs(Msg[1], Msg[2], Msg[3]);
		

		strMsg = "JOIN_US" + SC._del + msg + SC._del;
		SendMsg(ctx, strMsg);
	}
	public synchronized void IDCHECK(ChannelHandlerContext ctx, String Msg[])
	{
		int msg=Db.IdCheck(Msg[1]);
		
		strMsg="IDCHECK"+SC._del+msg+SC._del;
		SendMsg(ctx, strMsg);
	}
	public synchronized void LOGIN(ChannelHandlerContext ctx, String Msg[]) {
		
		int check = Db.LOGIN(Msg[1], Msg[2]);
		StringBuilder name = new StringBuilder("0");
		if(check == 1){
			SC.Print0("접속! "+Msg[1]);
			SC.clients.put(Msg[1], ctx);
			name = Db.GetName(Msg[1]);
		}
		strMsg="LOGIN"+SC._del+Integer.toString(check)+SC._del + name.toString() + SC._del;
		SendMsg(ctx, strMsg);
	}

	public synchronized void LOGOUT(ChannelHandlerContext ctx) {
		Set key = SC.clients.keySet();
		for (Iterator iterator2 = key.iterator(); iterator2.hasNext();) {
			String keyName = (String) iterator2.next();
			if (ctx.equals(SC.clients.get(keyName))) {
				String id = keyName;
				SC.clients.remove(id);
				SC.Print0("Logout id: " + id);
				
				 Db.LoginOut(id);

				ctx.close();

				return;
			}
		}
	}
	
	public synchronized void S_LIST(ChannelHandlerContext ctx, String Msg[]) {
		try{
		ArrayList<StringBuilder> val = Db.S_LIST(Msg[1]);
		SendMsg_Before_Process_List(ctx, val, "S_LIST");
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		int a = 1;
	}
	
	
	public synchronized void S_INFO(ChannelHandlerContext ctx, String Msg[]) {
		String msg = Db.S_INFO(Msg[1]);
		
		SendMsg(ctx,"S_INFO"+SC._del+msg);
	}
	
	public synchronized void S_CAR(ChannelHandlerContext ctx, String Msg[]) {
		ArrayList<StringBuilder> val = Db.S_CAR(Msg[1]);
		SendMsg_Before_Process_List(ctx, val, "S_CAR");
	}
	
	public synchronized void S_BEACON(ChannelHandlerContext ctx, String Msg[]) {
		ArrayList<StringBuilder> val = Db.S_BEACON(Msg[1]);
		SendMsg_Before_Process_List(ctx, val, "S_BEACON");
	}
	//========================================== 로그
	
	public synchronized void LOG(ChannelHandlerContext ctx, String Msg[]) {
		Db.LOG(Msg[1], Msg[2], Msg[3], Msg[4]);
	}
	
	//학생한테 로그 리스트를 준다
	public synchronized void L_LIST_TO_S(ChannelHandlerContext ctx, String Msg[]) {
		ArrayList<StringBuilder> val = Db.L_LIST_TO_S(Msg[1]);
		SendMsg_Before_Process_List22(ctx, val, "L_LIST_TO_S");
		
		val = Db.L_LIST_TO_S2(Msg[1]);//이전에꺼까지 보내기위해 만듬. 이런식으로함으로써 속도 2배걸림
		SendMsg_Before_Process_List2(ctx, val);
	}
	
	// 부모한테 학생의 로그 리스트를 준다
	public synchronized void L_LIST_TO_P(ChannelHandlerContext ctx, String Msg[]) {
		ArrayList<StringBuilder> val = Db.L_LIST_TO_P(Msg[1]);
		SendMsg_Before_Process_List22(ctx, val, "L_LIST_TO_P");
		
		val = Db.L_LIST_TO_P2(Msg[1]);//이전에꺼까지 보내기위해 만듬. 이런식으로함으로써 속도 2배걸림
		SendMsg_Before_Process_List2(ctx, val);
	}
	
	//=========================================== 로그 끝

	//========================================== 관리자 기능
	public synchronized void M_NOTICE(ChannelHandlerContext ctx, String Msg[]) {
		int val = Db.M_NOTICE(Msg[1], Msg[2]);
		SendMsg(ctx,"M_NOTICE"+SC._del+val);
	}
	
	public synchronized void M_VIEW_MEMBER(ChannelHandlerContext ctx, String Msg[]) {
		ArrayList<StringBuilder> val = Db.M_VIEW_MEMBER(Msg[1]);
		SendMsg_Before_Process_List(ctx, val, "M_VIEW_MEMBER");
	}
	
	
	
	//========================================== 관리자 기능 끝
}
