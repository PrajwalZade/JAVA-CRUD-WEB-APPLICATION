/**
 * 
 */
	package com.xadmin.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xadmin.usermanagement.bean.UserBean;

/**
	 * 
	 * JDBC DATABASE CONNECTION CLASS
	 * @author prajwal
	 *
	 */
		public class UserDao {
			
			
		private String jdbcURL = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
		
		private String jdbcUserName = "root";
		private String jdbcpassword = "2580";
		private String jdbcDriver = "com.mysql.jdbc.Driver";
		
		
		// DATABASE QUERIRS
		
		private static final String INSERT_USER_SQL = "INSERT INTO users"+ "(name, email, country) VALUES"+ "(?,?,?);";
		
		private static final String SELECT_USER_BY_ID = "select id.name,email,country from users where id = ?;";
		
		private static final String SELECT_ALL_USERS = "select *from users;";
		private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
		
		private static final String UPDATE_USERS_SQL = "update users set name = ?, email =?m country=? where id =?;";

		/**
		 * 
		 */
		public UserDao() {
			
		
		}
		
		//DATABASE CONNECTION METHOD
		
		protected Connection getConnection() {
			
			Connection con = null;
			
			try {
				Class.forName(jdbcDriver);
				
				try {
					con = DriverManager.getConnection(jdbcURL,jdbcUserName,jdbcpassword);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			return con;
			
		}
		
		
		//INSERT USER METHOD
		
		public void insertUser(UserBean user) throws SQLException {
			
			System.out.println(INSERT_USER_SQL);
			
			
			try {
				
				Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(INSERT_USER_SQL);
				
				ps.setString(1, user.getName());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getCountry());
				System.out.println(ps);
				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//SELECT THE USER BY ID
		
		public UserBean selectUser(int id) {
			
			UserBean user = null;
			
			
			try(
					//step1: Established a connection
					
					Connection con = getConnection();
					
					//step2: Create the statement using connection object
					PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID);
					) {
				
				ps.setInt(1, id);
				System.out.println(ps);
				
				//step3: Execute the query or update query
				
				ResultSet rs = ps.executeQuery();
				
				//setp4: Process the result object..
				
				while(rs.next()) {
					
					String name = rs.getString("name");
					String email = rs.getString("email");
					String country = rs.getString("country");
					
					user = new UserBean(id,name, email,country);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return user;
			
		}
		
		
		//SELECT ALL USERS
		
		public List<UserBean> selectAllUsers(){
			
			List<UserBean> users  = new ArrayList<>();
			
			
			
			try(
					//step1: Establishing the connection
				
				Connection con = getConnection();
				
				//step2: create a statement using connection obj
				PreparedStatement ps = con.prepareStatement(SELECT_ALL_USERS);) {
				
				System.out.println(ps);
				
				ResultSet rs =  ps.executeQuery();
				//step3: process the ResultSet object
				while(rs.next()) {
					
					int id = rs.getInt("id"); 
					String name = rs.getString("name");
					String email = rs.getString("email");
					String country = rs.getString("country");
					
					users.add(new UserBean(id,name, email,country));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return users;
		}
		
		//UPDATE USER METHOD
		
		
		public boolean updateUser(UserBean user) {
			
			boolean rowUpdated = false;
			
				
				
				try(//step1: Establishing the connection
						
						Connection con = getConnection();
						//step2: create a statement using connection obj
						PreparedStatement ps = con.prepareStatement(UPDATE_USERS_SQL);
						) 
				{
					
					System.out.println("updated user:" + ps);
					ps.setString(1, user.getName());
					ps.setString(2, user.getEmail());
					ps.setString(3, user.getCountry());
					
					rowUpdated = ps.executeUpdate() > 0;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return rowUpdated;		
		}
		//DELETE USER FROM DB METHOD 
		
		public boolean deleteUser(int id) {
			
			boolean rowDeleted = false;
			
			try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(DELETE_USERS_SQL);) {
			ps.setInt(1,id);
			rowDeleted = ps.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			return rowDeleted;
			
			
		}
				
	}
