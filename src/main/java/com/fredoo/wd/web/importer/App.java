package com.fredoo.wd.web.importer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.PreparedStatement;

public class App {

  public Connection getConnection(String database) throws SQLException {

    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", "fredoo");
    connectionProps.put("password", "ai198052");

    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database
        + "?useUnicode=true&characterEncoding=UTF-8", connectionProps);
    System.out.println("Connected to database : " + database);
    return conn;
  }

  public static void main(String[] args) throws Exception {
    App app = new App();
    Connection fredooConn = app.getConnection("fredoo");
    Connection oxwa123Conn = app.getConnection("oxwa123");

    // handle user
    Statement delete = fredooConn.createStatement();
    delete.execute("delete from EVENT");
    delete.execute("delete from USER");
    
    delete.close();

    Statement oxwa123 = oxwa123Conn.createStatement();
    ResultSet rs = oxwa123.executeQuery("select * from ow_base_user");
    String insert = "insert into USER(id,email,nick_name,password,created,enabled) values (?,?,?,?,?,?)";
    PreparedStatement fredoo = (PreparedStatement) fredooConn.prepareStatement(insert);
    while (rs.next()) {
      System.out.println("User : " + rs.getInt("id") + " " + rs.getString("email"));
      fredoo.setInt(1, rs.getInt("id"));
      fredoo.setString(2, rs.getString("email"));
      fredoo.setString(3, rs.getString("username"));
      fredoo.setString(4, rs.getString("password"));
      fredoo.setDate(5, new Date(Long.parseLong(rs.getString("joinStamp") + "000")));
      fredoo.setBoolean(6, true);
      fredoo.execute();
    }
    rs.close();
    oxwa123.close();
    fredoo.close();

    // handle event
    oxwa123 = oxwa123Conn.createStatement();
    rs = oxwa123.executeQuery("select * from ow_event_item");
    insert = "insert into EVENT(id,created,description,end,image,location,start,status,title,type,owner_id) values (?,?,?,?,?,?,?,?,?,?,?)";
    fredoo = (PreparedStatement) fredooConn.prepareStatement(insert);
    while (rs.next()) {
      System.out.println("Event : " + rs.getInt("id") + " " + rs.getString("title"));
      fredoo.setInt(1, rs.getInt("id"));
      fredoo.setDate(2, new Date(Long.parseLong(rs.getString("createTimeStamp") + "000")));
      fredoo.setBlob(3, rs.getBlob("description"));
      fredoo.setDate(4, new Date(Long.parseLong(rs.getString("endTimeStamp") + "000")));
      fredoo.setString(5, rs.getString("image"));
      fredoo.setString(6, rs.getString("location"));
      fredoo.setDate(7, new Date(Long.parseLong(rs.getString("startTimeStamp") + "000")));
      fredoo.setInt(8, rs.getInt("status"));
      fredoo.setString(9, rs.getString("title"));
      fredoo.setInt(10, 0);
      fredoo.setInt(11, rs.getInt("userId"));
      fredoo.execute();
    }
    rs.close();
    oxwa123.close();
    fredoo.close();
  }
}
