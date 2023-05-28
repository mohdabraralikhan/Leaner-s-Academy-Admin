package com.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.UserValidator;
import com.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public Login() {super();}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserValidator validator = new UserValidator();

		try {
			Users users = validator.checkLogin(email, password);
			String destPage = "login.jsp";

			if (users != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", users);
				destPage = "home.jsp";
			} else {
				String message = "Invalid email/password";
				request.setAttribute("message", message);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
			dispatcher.forward(request, response);

		} catch (SQLException | ClassNotFoundException ex) {
			throw new ServletException(ex);
		}
	}

}
