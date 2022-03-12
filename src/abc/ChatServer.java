package abc;

import abc.netio.*;
import abc.util.*;

import java.io.*;
import java.util.*;


public class ChatServer {
  
  //-------------------------------------------------------------------------//
  //  main()                                                                 //
  //-------------------------------------------------------------------------//
  public static void main( String[]  args ) {
    
    try {
      var app = new ChatServer();
      app.run();
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //main()
  
  
  //-------------------------------------------------------------------------//
  //  run()                                                                  //
  //-------------------------------------------------------------------------//
  private void run() throws Exception {
    
    Properties props = readConfigFile();
    
    String serverPort = props.getProperty("ServerPort");
    
    //start_DB_Engine();
    
    start_Network_Server( serverPort );
    
  } //run()
  
  
  //-------------------------------------------------------------------------//
  //  start_Network_Server()                                                 //
  //-------------------------------------------------------------------------//
  private void start_Network_Server( String  sPortNo ) {
    
    int iPortNo = Server_PortNo_Util.getServerPortNo( sPortNo );
    
    var netSvr = new NetworkServer();
    netSvr.start( iPortNo );
    
    
  } //start_Network_Server()
  
  
  //-------------------------------------------------------------------------//
  //  readConfigFile()                                                       //
  //-------------------------------------------------------------------------//
  private Properties readConfigFile() throws Exception {
    
    File file = new File("config.txt");
    FileInputStream fis = new FileInputStream( file );
    
    Properties props = new Properties();
    props.load( fis );
    
    fis.close();
    
    return props;
  } //readConfigFile()
  
  
} //class
