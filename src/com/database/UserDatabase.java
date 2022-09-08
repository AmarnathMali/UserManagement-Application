package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.User;

public class UserDatabase {
	private String JdbcUrl = "jdbc:mysql://localhost:3306/usermanagementportal?useSSL=false";
	private String userName="root";
	private String password="root";
	
	private static final String Insert_User = "insert into user_list"+"(name,email,country) values"+"(?,?,?);";
	private static final String Select_UserBy_Id="select id,name,email,country from user_list where id=?;";
	private static final String Select_AllUser="select * from user_list";
	private static final String Delete_User="delete from user_list where id=?";
	private static final String Update_User="update user_list set name=?,email=?,country=? where id=?";
	
	protected Connection getConnection(){
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(JdbcUrl,userName,password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
		
	}
	//insert user method
	public void insertUser(User user) {
		Connection con = getConnection();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(Insert_User);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getCountry());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//udate user method
	public boolean updateUser(User user){
		Connection con = getConnection();
		boolean rowUpdate=false;
		try {
			PreparedStatement ps = con.prepareStatement(Update_User);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getCountry());
			ps.setInt(4, user.getId());
			ps.executeUpdate();
			rowUpdate = ps.executeUpdate()>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowUpdate;
	}

	//select user by id
	public User selectUserById(int Id){
		Connection con = getConnection();
		User user=null;
		try {
			PreparedStatement ps = con.prepareStatement(Select_UserBy_Id);
			ps.setInt(1, Id);
            System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(Id,name,email,country);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
		
	}
	//select all user
	public List<User> selectAllUser(){
		Connection con = getConnection();
		List<User> user = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(Select_AllUser);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user.add(new User(id,name,email,country));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
		
	}
	//delete user
	public boolean deleteUser(int id){
		Connection con = getConnection();
		boolean deleteRow = false;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(Delete_User);
			ps.setInt(1, id);
			deleteRow = ps.executeUpdate()>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return deleteRow;
		
	}
	
	
}
