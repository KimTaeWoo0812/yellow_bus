<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.javalec.member.Member_Dto"%>
<html>
<head>
<META name="viewport" content="width=device-width, initial-scale=1.0">     
<META name="description" content="Free and easy to use online HTML Tables generator -- enter the table data and just copy and paste the generated table code into your website"> 
    
<META name="author" content="">     <LINK href="/static/img/favicon.png" rel="shortcut icon"> 
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <![endif]--> 
    <!-- Le styles -->     <LINK href="css/css" 
rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="tables.css" type="text/css" media="screen">     <LINK title="no title" href="css/combined.214.css" 
rel="stylesheet" type="text/css" media="all" charset="EUC-KR">                    
                 
<META name="GENERATOR" content="MSHTML 11.00.9600.17963">
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>원생 관리</title>
<script type="text/javascript" src="/JSP_Server2/jquery-1.12.2.min.js"></script>

</head>
<body>

<style>


.grad{
   position: absolute;
   top: -20px;
   left: -20px;
   right: -40px;
   bottom: -40px;
   width: auto;
   height: auto;
   background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(0,0,0,0)), color-stop(100%,rgba(0,0,0,0.65))); /* Chrome,Safari4+ */
   z-index: 1;
   opacity: 0.7;
}

.header{
   position: absolute;
   top: calc(10% - 35px);
   left: calc(10% - 35px);
   z-index: 2;
}

.header div{
   float: left;
   color: #ff0;
   font-family: 'Exo', sans-serif;
   font-size: 35px;
   font-weight: 200;
}

.header div span{
   color: #5379fa !important;
}


.login input[type=submit]{
   width: 50%;
   background: transparent;
   border: 0px solid #fff;
   cursor: pointer;
   border-radius: 2px;
   color: #a18d6c;
   font-family: 'Exo', sans-serif;
   font-size: 16px;
   font-weight: 400;
   padding: 6px;
   margin-top: 10px;
}
.login-active input[type=submit]{
   background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(0,0,0,0)), color-stop(100%,rgba(100,100,100,0.65)));
   width: 50%;
   border: 0px solid #fff;
   cursor: pointer;
   border-radius: 2px;
   color: #a18d6c;
   font-family: 'Exo', sans-serif;
   font-size: 20px;
   font-weight: bold;
   padding: 6px;
   margin-top: 10px;
}

.login input[type=submit]:hover{
   color: rgb(85, 85, 85); text-decoration: none; box-shadow: inset 0px 3px 8px rgba(0,0,0,0.125); background-color: rgb(229, 229, 229); -webkit-box-shadow: inset 0 3px 8px rgba(0, 0, 0, 0.125); -moz-box-shadow: inset 0 3px 8px rgba(0, 0, 0, 0.125);
}

.login input[type=submit]:active{
   opacity: 0.6;
}

.login input[type=submit]:focus{
   outline: none;
   border: 1px solid rgba(255,255,255,0.9);
}

.login input[type=password]:focus{
   outline: none;
   border: 1px solid rgba(255,255,255,0.9);
}

.login input[type=submit]:focus{
   outline: none;
}

::-webkit-input-placeholder{
   color: rgba(255,255,255,0.6);
}

::-moz-input-placeholder{
   color: rgba(255,255,255,0.6);
}
</style>

	<%
		ArrayList<Member_Dto> dtos = new ArrayList<Member_Dto>();
		String name = "unknown";
		dtos = (ArrayList) request.getAttribute("list");

		name = request.getParameter("schoolName");
	%>

	


 	<DIV class="container">
<DIV class="masthead">
<DIV class="top-header">
<H1 id="logo"><%=name%></H1></DIV>
<DIV class="navbar">
<DIV class="navbar-inner">
<DIV class="container">
<UL class="nav">
  <LI class="active"><div class="login">
<form action="main.do" method="post">
   <input type="hidden" name="schoolName" value="<%=name%>" />
      <input name="전송" type="submit" value="학원정보">
   </form>
   </div> </LI>
     <LI class="active"><div class="login-active">
   <form action="memberList.do" method="post">
   <input type="hidden" name="schoolName" value="<%=name%>" />
      <input type="submit" value="원생관리">
   </form>
   </div> </LI>
     <LI><div class="login">
   <form action="carList.do" method="post">
   <input type="hidden" name="schoolName" value="<%=name%>" />
      <input type="submit" value="차량관리">
   </form>

   </div> </LI>
     <LI><div class="login">
   <form action="beaconList.do" method="post">
   <input type="hidden" name="schoolName" value="<%=name%>" />
      <input type="submit" value="비콘관리" >
   </form>

   </div> </LI>
  </UL>
<DIV id="main-header">
<H2><EM>원생</EM> 관리 </H2>
</DIV>
</DIV>



<%
int j = 2;
%>
 		
<script language='javascript'">
//window.history.forward(1); //back버튼 방지스크립트 

function FSetId(ID){
	//document.all.ID.value = ID;
	$('#ID').val(ID);
}
function FSetName(NAME){
	$('#NAME').val(NAME);
}
function FSetRole(ROLE){
	$('#ROLE').val(ROLE);
}
function FSetParent1(PARENT1){
	$('#PARENT1').val(PARENT1);
}
function InPutCheck(){
	if(document.frm1.ID.value==''){
		alert("아이디를 입력하세요!");
		document.frm1.id.focus();
	}
	else if(document.frm1.NAME.value==''){
		alert("이름을 입력하세요!");
		document.frm1.pw.focus();
	}
	else{
		document.frm1.submit(); 
    	return true;
	}
}
</script>

<form name=form method="post" action="memberDelete.do">
	<table id="beacon-table" class="hover table dataTable" aria-describedby="beacon-table_info">
  <thead>
    <tr role="row">
      <th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" aria-label="" style="width: 13px;"> <label>
        
        <span class="lbl"></span> </label>
      </th>
      <th class="sorting" role="columnheader" tabindex="0" aria-controls="beacon-table" rowspan="1" colspan="1" aria-label="No.: activate to sort column ascending" style="width: 19px;">No.</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">학생ID</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">이름</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">등급</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">학부모ID</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">수정</th>
      </tr>
  </thead>
  <tbody role="alert" aria-live="polite" aria-relevant="all">
  
  
  
		<%
 	Member_Dto dto = new Member_Dto();
 	for (int i = 0; i < dtos.size(); i++) {
 		dto = dtos.get(i);
 		
 		%>
 		<tr style="text-align: center;" class="even">
      <td class="center"><label>
        <input type="checkbox" class="beacon-checkbox" name=check value="<%=dto.GetId()%>">
        <span class="lbl"></span> </label></td>
 		<td data-title="No." class=" "><%=i+1%></td>
      <td data-title="학원 명" class=""><%=dto.GetId()%></td>
      <td data-title="비콘 이름" class=""><%=dto.GetName()%></td>
      <td data-title="비콘 고유 번호" class=""><%=dto.GetRole()%></td>
      <td data-title="용도" class=""><%=dto.GetParent1()%></td>
      <td>
      <input type="button" value="수정" 
 		onClick="FSetId('<%=dto.GetId()%>'), 
 		FSetName('<%=dto.GetName()%>'),
 		FSetRole('<%=dto.GetRole()%>'),
 		FSetParent1('<%=dto.GetParent1()%>')";/>
      </td>
    </tr>
         
    <%
 	}
	%>
 
	</table>
	
	
		<DIV class="container">
<DIV class="masthead">
<DIV class="top-header">
<H1 id="logo"></H1></DIV>
<DIV class="navbar">
<DIV class="navbar-inner">
<DIV class="container">
<UL class="nav">
  <LI class="active"><div class="login-active">
	
	
<input type="submit" value ="선택값 삭제">
<input type="hidden" name="schoolName" value="<%=name%>" />
   </form>
	
	
	<form name="frm1" action="memberAdd.do" method="post"
	onSubmit="InPutCheck();return false">
	<input type="hidden" name="schoolName" value="<%=name%>" />
		학생 ID : <input type="text" name="id" id="ID"> <br />
		학생 이름 : <input type="text" name="name" id="NAME"> <br />
		학부모1 : <input type="text" name="parent1" id="PARENT1"><br />
		학부모2 : <input type="text" name="parent2" id="PARENT2"><br />
		등급 :
		<from>
		<select name="role" id="ROLE">
		<option value="학생" selected>학생</option>
		<option value="학부모" >학부모</option>
		<option value="매니저" >매니저</option>
		</select> 
		</from>
		
		<br />
		<input type="submit" value="학생 추가">
	</form>
	
	   </div> </LI>
  </UL>
<DIV id="main-header">
</DIV>
</DIV>


</body>
</html>