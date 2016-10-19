<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.javalec.beacon.Beacon_Dto"%>
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
<title>���� ����</title>
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



<script language="javascript"> 
//window.history.forward(1); //back��ư ������ũ��Ʈ 
</script> 



		<%
 	ArrayList<Beacon_Dto> dtos = new ArrayList<Beacon_Dto>();
 	String name = "unknown";
 	dtos = (ArrayList)request.getAttribute("list");
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
      <input name="����" type="submit" value="�п�����">
   </form>
   </div> </LI>
     <LI class="active"><div class="login">
   <form action="memberList.do" method="post">
   <input type="hidden" name="schoolName" value="<%=name%>" />
      <input type="submit" value="��������">
   </form>
   </div> </LI>
     <LI><div class="login">
   <form action="carList.do" method="post">
   <input type="hidden" name="schoolName" value="<%=name%>" />
      <input type="submit" value="��������">
   </form>

   </div> </LI>
     <LI><div class="login-active">
   <form action="beaconList.do" method="post">
   <input type="hidden" name="schoolName" value="<%=name%>" />
      <input type="submit" value="���ܰ���" >
   </form>

   </div> </LI>
  </UL>
<DIV id="main-header">
<H2><EM>����</EM> ���� </H2>
</DIV>
</DIV>





	
	<form name=form method="post" action="beaconDelete.do">
	
	<table id="beacon-table" class="hover table dataTable" aria-describedby="beacon-table_info">
  <thead>
    <tr role="row">
      <th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" aria-label="" style="width: 13px;"> <label>
        
        <span class="lbl"></span> </label>
      </th>
      <th class="sorting" role="columnheader" tabindex="0" aria-controls="beacon-table" rowspan="1" colspan="1" aria-label="No.: activate to sort column ascending" style="width: 19px;">No.</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">�п� ��</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">���� �̸�</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">���� ���� ��ȣ</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">�뵵</th>
      <th class="col-md-2 col-xs-2 sorting" tabindex="0" rowspan="1" colspan="1" role="columnheader" aria-controls="beacon-table" aria-label="Serial Number: activate to sort column ascending" style="width: 141px;">��� ��¥</th>
      </tr>
  </thead>
  <tbody role="alert" aria-live="polite" aria-relevant="all">
   
    <%
 	Beacon_Dto dto = new Beacon_Dto();
 	for (int i = 0; i < dtos.size(); i++) {
 		dto = dtos.get(i);
 		%>
    	<tr style="text-align: center;" class="even">
      <td class="center"><label>
        <input type="checkbox" class="beacon-checkbox" name=check value="<%=dto.GetIdentifier()%>">
        <span class="lbl"></span> </label></td>
      <td data-title="No." class=" "><%=i+1%></td>
      <td data-title="�п� ��" class=""><%=dto.GetSchoolName()%></td>
      <td data-title="���� �̸�" class=""><%=dto.GetBeaconName()%></td>
      <td data-title="���� ���� ��ȣ" class=""><%=dto.GetIdentifier()%></td>
      <td data-title="�뵵" class=""><%=dto.GetIsCar()%></td>
      <td data-title="��� ��¥" class=""><%=dto.GetMakeDate()%></td>
    </tr>
    <%
    }
    name = request.getParameter("schoolName");
    %>
  </tbody>
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
  
  
<input type="submit" value ="���ð� ����">
<input type="hidden" name="schoolName" value="<%=name%>" />
   </form>
	
	
	<form action="beaconAdd.do" method="post">
	<input type="hidden" name="schoolName" value="<%=name%>" />
		���� �̸� : <input type="text" name="beaconName"> <br />
		������ȣ : <input type="text" name="identifier"><br />
		<from>
		<select name="isCar">
		<option value="������" selected>������</option>
		<option value="�п���" >�п���</option>
		</select> 
		</from>
		
		
		<input type="submit" value="���� �߰�">
	</form>

   </div> </LI>
  </UL>
<DIV id="main-header">
</DIV>
</DIV>

</body>
</html>