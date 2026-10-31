package com.odoo.brightloop.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.odoo.brightloop.database.DBConnection;
import com.odoo.brightloop.model.UserModel;

@Repository 
public class UserRepository {
    
    public UserModel findUserByEmail(
        String email
    ) {
        String query = "select * from users where email = ?;";
        try(
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ){
            ps.setString(1,email);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    UserModel user = new UserModel();

                    user.setUserId(rs.getInt("user_id"));
                    user.setEmailId(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));

                    return user;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
