<html>
<head>	<script type="text/javascript">
		function ajaxQuery(){
			$.ajax({
				   type: 'POST',
			  	   url: 'user/mongo.do',
			  	   dataType:'json',
			  	   success: function(data){
			  		  alert(data.rspcod);
			  		  alert(data.rspmsg);
			  	   },
			  	   error: function(data){
			  		   alert("error" + data.responseText);
			  	   }
			 });
		}
	</script>
</head>
<body>
	<h2>Hello World!</h2>
	<form action="http://localhost:8080/tddef-authWeb/authUsr/selectAll.do" method="post">
		<table>
			<tr>
				<td>姓名：</td>
				<td><input type="text" name="name" value="kk"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="query" /></td>
				<td><input type="reset" value="reset" /></td>
			</tr>
		</table>
		<input type="button" value="query" onclick="ajaxQuery()"/>
	</form>
</body>
</html>
