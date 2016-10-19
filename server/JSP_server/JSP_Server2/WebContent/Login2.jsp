<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
<title>로그인</title>
    
    
    
    
        <style>
      /* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
      @import url(http://fonts.googleapis.com/css?family=Exo:100,200,400);
@import url(http://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300);

body{
	margin: 0;
	padding: 0;
	background: #fff;

	color: #fff;
	font-family: Arial;
	font-size: 12px;
}

.body{
	position: absolute;
	top: -20px;
	left: -20px;
	right: -40px;
	bottom: -40px;
	width: auto;
	height: auto;
	background-image: url(https://i.ytimg.com/vi/DiHoDgrafrs/maxresdefault.jpg);
	background-size: cover;
	-webkit-filter: blur(5px);
	z-index: 0;
}

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

.login{
	position: absolute;
	top: 20%;
	height: 150px;
	width: 350px;
	padding: 10px;
	z-index: 2;
	left: 10%;
}

.login input[type=text]{
	width: 250px;
	height: 30px;
	background: transparent;
	border: 1px solid rgba(255,255,255,0.6);
	border-radius: 2px;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
}

.login input[type=password]{
	width: 250px;
	height: 30px;
	background: transparent;
	border: 1px solid rgba(255,255,255,0.6);
	border-radius: 2px;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
	margin-top: 10px;
}

.login input[type=submit]{
	width: 260px;
	height: 35px;
	background: #fff;
	border: 1px solid #fff;
	cursor: pointer;
	border-radius: 2px;
	color: #a18d6c;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 6px;
	margin-top: 10px;
}

.login input[type=button]:hover{
	opacity: 0.8;
}

.login input[type=button]:active{
	opacity: 0.6;
}

.login input[type=text]:focus{
	outline: none;
	border: 1px solid rgba(255,255,255,0.9);
}

.login input[type=password]:focus{
	outline: none;
	border: 1px solid rgba(255,255,255,0.9);
}

.login input[type=button]:focus{
	outline: none;
}

::-webkit-input-placeholder{
   color: rgba(255,255,255,0.6);
}

::-moz-input-placeholder{
   color: rgba(255,255,255,0.6);
}
        </style>

    
        <script src="js/prefixfree.min.js"></script>

    
</head>

  <body>

<script language='javascript'>

//window.history.forward(0); //backÃ«Â²ÂÃ­ÂÂ¼ Ã«Â°Â©Ã¬Â§ÂÃ¬ÂÂ¤Ã­ÂÂ¬Ã«Â¦Â½Ã­ÂÂ¸ 

function loginCK(){
	if(document.frm1.id.value==''){
		alert("아이디를 입력하세요!");
		document.frm1.id.focus();
	}
	else if(document.frm1.pw.value==''){
		alert("비밀번호를 입력하세요!");
		document.frm1.pw.focus();
	}
	else{
		document.frm1.submit(); 
    	return true;
	}
}


</script>

    <div class="body"></div>
		<div class="grad"></div>
		<div class="header">
			<div>Yellowbus<span>Authentication System</span></div>
		</div>
		<br>
		<div class="login">
        <form name="frm1" action="Login.do" method="post"
	onSubmit="loginCK();return false">
				<input type="text" placeholder="username" name="id"><br>
				<input type="password" placeholder="password" name="pw"><br>
				<input type="submit" value="Login">
		</div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    
  </body>
</html>
