package com.xadmin.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.usermanagement.bean.UserBean;
import com.xadmin.usermanagement.dao.UserDao;
import javax.servlet.annotation.WebServlet;

@WebServlet("/")

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao user;
   

	
	public void init(ServletConfig config) throws ServletException {
		
		user = new UserDao();
		
	}
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		switch(action) {
		
		case "/new":
			showNewForm(request,response);
			break;
			
		case "/insert":
			try {
				insertUser(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/delete":
			
			deleteUser(request,response);
			break;
			
		case "/edit":
			showEditForm(request,response);
			
			break;
			
		case "/update":
			try {
				updateUser(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default:
			try {
				listUser(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		
	}

	

	


	//new user reg form

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//to go to particular page we use RequestDispatcher
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		
		dispatcher.forward(request, response);
	}
	
	//insert user
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException {
		
		
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String country = request.getParameter("country");
			UserBean newUser = new UserBean(name,email,country);
			
			user.insertUser(newUser);
			response.sendRedirect("list");
			
			
			
		}
	
	//delete user
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		
		
		try {
			
			user.deleteUser(id);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	//edit user method
	

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		UserBean existingUser;
		
		try {
			
			existingUser = user.selectUser(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			
			request.setAttribute("user", existingUser);
			
			dispatcher.forward(request, response);
		}catch(Exception e) {
			
		}
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		UserBean userBean = new UserBean(id, name, email, country);
		
		user.updateUser(userBean);
		response.sendRedirect("list");
		
		
	}
	

	private void listUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException,ServletException {
		
		
		try {
			
			List<UserBean> listUser = user.selectAllUsers();
			
			request.setAttribute("listUser", listUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);

			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			
		}
		
		
	}


	
	

}
