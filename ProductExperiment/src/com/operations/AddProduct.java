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
 * Servlet implementation class AddProduct
 */
public class AddProduct extends HttpServlet {
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
		super.destroy();
		try {
			ps.close();
			ps=null;
			con.close();
			con=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String idS=request.getParameter("id");
		String name=request.getParameter("name");
		String priceS=request.getParameter("price");
		int id=Integer.parseInt(idS);
		int price=Integer.parseInt(priceS);
		
		try(PreparedStatement ps=con.prepareStatement("insert into products(productId, name, price) values(?,?,?);")){
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setInt(3, price);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
