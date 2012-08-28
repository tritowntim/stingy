package com.dussabi.stingy;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Recent
 */
public class Recent extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String getRecentEntries() {

		String entries = "";
		try {
			// line required, otherwise ERROR=No suitable driver found for jdbc:postgresql://localhost:5432/stingydb
			Class.forName("org.postgresql.Driver").newInstance();
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/stingydb", "tritowntim",
					"antimatter");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, entry_date, description, amount from entry");
			while (rs.next()) {
				entries += (rs.getInt(1) + " " + rs.getDate(2) + " "
						+ rs.getString(3) + " " + String
							.valueOf(rs.getFloat(4))) + "<br/>";
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("ERROR=" + e.getLocalizedMessage());
		} finally {
			return entries;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder page = new StringBuilder();
		page.append("<html>");
		page.append("<head>");
		page.append("</head>");
		page.append("<body>");
		page.append("<p>stingy</p>");
		page.append("<p>recent transactions:</p>");
		page.append("<p>" + getRecentEntries() + "</p>");
		page.append("</body>");
		page.append("</html>");
		PrintWriter out = response.getWriter();
		out.print(page.toString());
	}

}
