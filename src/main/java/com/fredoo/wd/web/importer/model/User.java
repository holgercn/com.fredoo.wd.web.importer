package com.fredoo.wd.web.importer.model;

import java.util.Date;

public class User {
  private String id;
  private String email;
  private String password;
  private String username;
  private Date joinStamp;
  private String activityStamp;
  private String accountType;
  private String emailVerify;
  private String joinIp;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String isEmailVerify() {
    return emailVerify;
  }

  public void setEmailVerify(String emailVerify) {
    this.emailVerify = emailVerify;
  }

  public Date getJoinStamp() {
    return joinStamp;
  }

  public void setJoinStamp(Date joinStamp) {
    this.joinStamp = joinStamp;
  }

  public String getActivityStamp() {
    return activityStamp;
  }

  public void setActivityStamp(String activityStamp) {
    this.activityStamp = activityStamp;
  }

  public String getJoinIp() {
    return joinIp;
  }

  public void setJoinIp(String joinIp) {
    this.joinIp = joinIp;
  }

  public String getEmailVerify() {
    return emailVerify;
  }

}
