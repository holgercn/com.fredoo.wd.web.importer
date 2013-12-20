package com.fredoo.wd.web.importer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class EventImporter extends AbstractImporter implements Importer {

  public EventImporter() throws SQLException {
    super();
  }

  public void delete() throws Exception {
    Statement delete = fredooConn.createStatement();
    delete.execute("delete from EVENT_MEMBER");
    delete.execute("delete from EVENT");
  }

  public void importer() throws Exception {
    Statement oxwa123 = oxwaConn.createStatement();
    ResultSet rs = oxwa123.executeQuery("select * from ow_event_item");
    String insert = "insert into EVENT(id,created,description,end,image,location,start,status,title,type,owner_id) values (?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement fredoo = (PreparedStatement) fredooConn.prepareStatement(insert);
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

    importerMember();
  }

  private void importerMember() throws Exception {
    Statement oxwa123 = oxwaConn.createStatement();
    ResultSet rs = oxwa123.executeQuery("select * from ow_event_user");

    String insert = "insert into EVENT_MEMBER (event_id, member_id) values (?,?)";
    PreparedStatement fredoo = (PreparedStatement) fredooConn.prepareStatement(insert);
    while (rs.next()) {
      System.out.println("event_member : " +rs.getInt("eventId") + " " + rs.getInt("userId"));
      if (rs.getInt("status") == 1) {
        fredoo.setInt(1, rs.getInt("eventId"));
        fredoo.setInt(2, rs.getInt("userId"));
        fredoo.execute();
      }
    }
  }

}
