package abc.dbio;

import abc.bos.*;
import java.sql.*;


public class InnerDbMgr {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Connection  dbCon ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public InnerDbMgr() {
    
    try {
      Class.forName("org.hsqldb.jdbc.JDBCDriver" );
      dbCon = DriverManager.getConnection("jdbc:hsqldb:file:datadir/chatdb", "SA", "");
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  storeMessage()                                                         //
  //-------------------------------------------------------------------------//
  public ChatMessage storeMessage( ChatMessage  msg ) throws Exception {
    
    msg.timeStamp = new java.util.Date();
    
    CommonDbMgr.storeMessage( msg, dbCon );
    
    return msg;
  } //storeMessage()
  
  
} //class
