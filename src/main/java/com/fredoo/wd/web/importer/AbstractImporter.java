package com.fredoo.wd.web.importer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AbstractImporter {

  protected Connection fredooConn;
  protected Connection oxwaConn;

  public AbstractImporter() throws SQLException {
    if (fredooConn == null) {
      fredooConn = getConnection("fredoo");
    }
    if (oxwaConn == null) {
      oxwaConn = getConnection("oxwa123");
    }
  }

  private Connection getConnection(String database) throws SQLException {

    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", "fredoo");
    connectionProps.put("password", "ai198052");

    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database
        + "?useUnicode=true&characterEncoding=UTF-8", connectionProps);
    System.out.println("Connected to database : " + database);
    return conn;
  }
}
