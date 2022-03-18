package abc.dbio;

import abc.bos.*;
import abc.util.*;

import java.sql.*;
import java.util.*;


public class ServerDbMgr {
  
  //-------------------------------------------------------------------------//
  //  getAllMessages_for_User_since_Timestamp()                              //
  //-------------------------------------------------------------------------//
  public static List<ChatMessage> getAllMessages_for_User_since_Timestamp( String          chatUser ,
                                                                           java.util.Date  dtCutOff ,
                                                                           Connection      con )
                                                                    throws Exception {
    
    List<ChatMessage> arrMsg = new ArrayList<>();
    
    String sUser   = "'" + chatUser + "'";
    String sCutOff = DbDateUtil.formatTimeStamp( dtCutOff );
    
    String sql = "select "
               + " TimeStamp, "
               + " Id, "
               + " Sender, "
               + " Destination, "
               + " Body, "
               + " Shown "
               + " from   ChatMessage "
               + " where  (Sender = " + sUser + " or Destination = " + sUser + ")"
               + " and    TimeStamp >= " + sCutOff
               + " order  by TimeStamp ";
    
    Statement stmt = con.createStatement();
    ResultSet rs   = stmt.executeQuery( sql );
    
    while (rs.next()) {
      ChatMessage msg = new ChatMessage();
      
      msg.timeStamp   = rs.getTimestamp("TimeStamp");
      msg.id          = rs.getString("Id");
      msg.sender      = rs.getString("Sender");
      msg.destination = rs.getString("Destination");
      msg.body        = rs.getString("Body");
      msg.shown       = rs.getBoolean("Shown");
      
      arrMsg.add( msg );
    } //while
    
    rs.close();
    stmt.close();
    
    return arrMsg;
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  update_Shown_Status()                                                  //
  //-------------------------------------------------------------------------//
  public static void update_Shown_Status( String      id ,
                                          Connection  con ) throws Exception {
    
    String sId = "'" + id + "'";
    
    String sql = "update ChatMessage "
               + " set   Shown = true "
               + " where Id = " + sId;
    
    Statement stmt = con.createStatement();
    stmt.executeUpdate( sql );
    
    stmt.close();
  } //(m)
  
  
} //class
