package com.operations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Display
 */
public class Display extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection con = null;
	PreparedStatement ps = null;
	
	
	@Override
	public void init() throws ServletException {
		ServletContext context = getServletContext();
		super.init();
		try {
			Class.forName(context.getInitParameter("driver"));
			con = DriverManager.getConnection(context.getInitParameter("url"), context.getInitParameter("user"), context.getInitParameter("pass"));
			if (con == null)
				throw new SQLException("Connection not established");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		try {
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs = null;
		PrintWriter out = response.getWriter();
		
		String idS= request.getParameter("id");
		int id=Integer.parseInt(idS);
		try(PreparedStatement ps=con.prepareStatement("select * from products where productId=?")){
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				String name=rs.getString("name");
				String priceS=rs.getString("price");
				int price=Integer.parseInt(priceS);
				out.print(
						  "<table>"
						+ "<form action='update' method='post'>"
						+ "<tr>"
						+ "<td>Id</td><td>"+id+"<input type='hidden' name='id' value='"+id+"'	/></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>Name</td><td>	<input name='name' 	value='"+name+"'/></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>Price</td><td>	<input name='price' value='"+price+"'/></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td colspan='2'>     <input type='submit' value='Update'	/></td>"
						+ "</tr>"
						+ "</form>"
						+ "<form action='delete' method='get'>"
						+ "<tr>"
						+ "<td><input type='hidden' name='id' value='"+id+"' /></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td><input type='submit' value='Delete Product' /></td>"
						+ "</tr>"
						+ "</form>"
						+ "</table>"
						);
			} else {
				out.print(" No Product by that name found . ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.print("<h3>Hello</h3>");
	}

}