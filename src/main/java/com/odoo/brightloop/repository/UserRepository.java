package com.odoo.brightloop.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.odoo.brightloop.database.DBConnection;
import com.odoo.brightloop.model.UserModel;

public class UserRepository {
    
    public UserModel findUserByEmail(
        String email
    ) {
        String query = "select * from users where emailid = ?;";
        try(
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ){
            ps.setString(1,email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("userid"));
                user.setEmailId(rs.getString("emailid"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));

                return user;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
