<%@page %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action='addproduct' method='get'>
		<table>
			<tr>
				<td>Id</td>
				<td><input name="id"></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input name='name' /></td>
			</tr>
			<tr>
				<td>Price</td>
				<td><input name='price' /></td>
			</tr>
			<tr>
				<td colspan='2' align="center"><input type='submit'
					value='Add Product' /></td>
			</tr>
		</table>
	</form>
</body>
</html>