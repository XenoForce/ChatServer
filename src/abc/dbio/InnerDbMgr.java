package abc.dbio;

import abc.bos.*;

import java.sql.*;
import java.util.*;


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
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  getAllMessages_for_User_since_Timestamp()                              //
  //-------------------------------------------------------------------------//
  public List<ChatMessage> getAllMessages_for_User_since_Timestamp( String          chatUser ,
                                                                    java.util.Date  dtCutOff )
                                                             throws Exception {
    
    List<ChatMessage> retVal = ServerDbMgr.getAllMessages_for_User_since_Timestamp( chatUser ,
                                                                                    dtCutOff ,
                                                                                    dbCon );
    return retVal;
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  update_Shown_Status()                                                  //
  //-------------------------------------------------------------------------//
  public void update_Shown_Status( List<String>  arrId ) throws Exception {
    
    for (String id : arrId) {
      ServerDbMgr.update_Shown_Status( id, dbCon );
    } //for
    
  } //(m)
  
  
} //class
