package com.fredoo.wd.web.importer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.PreparedStatement;

public class UserImorter extends AbstractImporter implements Importer {

  public UserImorter() throws SQLException {
    super();
  }

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public void importer() throws Exception {
    Statement oxwa123 = oxwaConn.createStatement();

    ResultSet rs = oxwa123.executeQuery("select * from ow_base_user left JOIN ow_base_avatar ON userId = ow_base_user.id");
    String insert = "insert into USER(id,email,nick_name,password,created,enabled,avatar,birthday,first_name,last_name,gender) values (?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement fredoo = (PreparedStatement) fredooConn.prepareStatement(insert);
    while (rs.next()) {
      int id = rs.getInt("id");
      System.out.println("User : " + id + " " + rs.getString("email"));
      fredoo.setInt(1, id);
      fredoo.setString(2, rs.getString("email"));
      fredoo.setString(3, rs.getString("username"));
      fredoo.setString(4, rs.getString("password"));
      fredoo.setDate(5, new Date(Long.parseLong(rs.getString("joinStamp") + "000")));
      fredoo.setBoolean(6, true);
      fredoo.setString(7, rs.getString("hash"));

      Statement oxwa1232 = oxwaConn.createStatement();
      ResultSet reSet = oxwa1232.executeQuery("select * from ow_base_question_data where userId = " + id);
      Date birth = null;
      int sex = 0;
      String first_name = null;
      String last_name = "";
      while (reSet.next()) {
        String question = reSet.getString("questionName");
        if (question.equals("sex")) {
          sex = reSet.getInt("intValue");
        }

        if (question.equals("birthdate")) {
          java.util.Date d = sdf.parse(reSet.getString("dateValue"));
          birth = new Date(d.getTime());
        }

        if (question.equals("realname")) {
          first_name = reSet.getString("textValue");
        }
      }

      reSet.close();
      oxwa1232.close();

      fredoo.setDate(8, birth);
      fredoo.setString(9, first_name);
      fredoo.setString(10, last_name);
      fredoo.setString(11, "" + sex);
      fredoo.execute();

    }

    ResultSet reSet = oxwa123.executeQuery("select * from ow_friends_friendship");
    Statement fstatement = fredooConn.createStatement();
    while (reSet.next()) {
      System.out.println("Friend : " + reSet.getInt("userId") + " : " + reSet.getInt("friendId"));
      if (reSet.getString("status").equals("active")) {
        fstatement.execute("insert into USER_FRIEND (user_id, friend_id) values (" + reSet.getInt("userId") + ","
            + reSet.getInt("friendId") + ")");
      }
    }
    fstatement.close();

    rs.close();
    oxwa123.close();
    fredoo.close();

  }

  public void delete() throws Exception {
    Statement delete = fredooConn.createStatement();
    delete.execute("delete from USER_FRIEND");
    delete.execute("delete from USER");
  }

}
