<!DOCTYPE html>
<%@page import="com.beans.Products"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
.wide {
	width: 100%;
}
</style>
</head>
<body class='wide'>
	<%!Connection con;
	PreparedStatement ps;
	ResultSet rs;
	int operation;%>
	<%
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/advancedjava", "root", "root");
	%>
	<table class='wide'>
		<tr>
			<td>
				<iframe name='main' style='width:100%;position:relative;'></iframe>
			</td>
		</tr>
		<tr>
			<td>
				<%
					ArrayList<Products> products = new ArrayList<Products>();
					Products tempProducts;
					ps = con.prepareStatement("select * from products;");
					rs = ps.executeQuery();
					while (rs.next()) {
						tempProducts = new Products();
						tempProducts.setId(rs.getInt("productId"));
						tempProducts.setName(rs.getString("name"));
						tempProducts.setPrice(rs.getInt("price"));
						products.add(tempProducts);
					}
					out.print("<table>");
					out.print("<tr>");
					int i=0;
					for ( i = 0; i < products.size(); i++) {
						if (i % 3 == 0)
							out.print("</tr><tr>");
						out.print("<td><a target='main' href='display?id=?"
								+ products.get(i).getId()
								+ "'>"
								+ products.get(i).getName() + "</a></td>");
					}
					if(i%3==0)
						out.print("</tr><tr>");
					out.print("<td align='center'><a href='getdetails.html' target='main'>Add a product</a></td>");
					out.print("</tr>");
					out.print("</table>");
					//For beauty purpose use colspan 3-i  for add product if needed.
				%>
			</td>
		</tr>
	</table>
</body>
</html>