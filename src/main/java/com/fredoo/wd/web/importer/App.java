package com.fredoo.wd.web.importer;

public class App {

  public void start() throws Exception {
    UserImorter user = new UserImorter();
    EventImporter event = new EventImporter();

    event.delete();
    user.delete();

    user.importer();
    event.importer();
  }

  public static void main(String[] args) throws Exception {
    new App().start();
  }
}
