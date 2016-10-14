package com.operations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Delete
 */
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con = null;

	@Override
	public void init() throws ServletException {
		ServletContext context = getServletContext();
		super.init();
		try {
			Class.forName(context.getInitParameter("driver"));
			con = DriverManager.getConnection(context.getInitParameter("url"),
					context.getInitParameter("user"),
					context.getInitParameter("pass"));
			if (con == null)
				throw new SQLException("Connection not established");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idS = request.getParameter("id");
		int id=Integer.parseInt(idS);
		try(PreparedStatement ps=con.prepareStatement("delete from products where productId=?;")){
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
