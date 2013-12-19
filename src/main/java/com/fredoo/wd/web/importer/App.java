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

    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, connectionProps);
    System.out.println("Connected to database : " + database);
    return conn;
  }

  public static void main(String[] args) throws Exception {
    App app = new App();
    Connection fredoo = app.getConnection("fredoo");
    String insert = "insert into USER(id,email,nick_name,password,created,enabled) values (?,?,?,?,?,?)";
    PreparedStatement statement_fredoo = (PreparedStatement) fredoo.prepareStatement(insert);

    Connection oxwa123 = app.getConnection("oxwa123");
    Statement statement = oxwa123.createStatement();
    ResultSet rs = statement.executeQuery("select * from ow_base_user");
    while (rs.next()) {
      System.out.println(rs.getInt("id") + " " + rs.getString("email"));
      statement_fredoo.setInt(1, rs.getInt("id"));
      statement_fredoo.setString(2, rs.getString("email"));
      statement_fredoo.setString(3, rs.getString("username"));
      statement_fredoo.setString(4, rs.getString("password"));
      statement_fredoo.setDate(5, new Date(Long.parseLong(rs.getString("joinStamp") + "000")));
      statement_fredoo.setBoolean(6, true);
      statement_fredoo.execute();
    }
  }
}
