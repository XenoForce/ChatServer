package abc.dbio;

import abc.bos.*;
import java.util.*;


public class OuterDbMgr {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private static InnerDbMgr  innerMgr = new InnerDbMgr();
  
  
  //-------------------------------------------------------------------------//
  //  storeMessage()                                                         //
  //-------------------------------------------------------------------------//
  public static ChatMessage storeMessage( ChatMessage  msg ) throws Exception {
    
    synchronized( innerMgr ) {
      msg = innerMgr.storeMessage( msg );
    } //sync
    
    return msg;
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  getAllMessages_for_User_since_Timestamp()                              //
  //-------------------------------------------------------------------------//
  public static List<ChatMessage> getAllMessages_for_User_since_Timestamp( String          chatUser ,
                                                                           java.util.Date  dtCutOff )
                                                                    throws Exception {
    List<ChatMessage> retVal;
    
    synchronized( innerMgr ) {
      retVal = innerMgr.getAllMessages_for_User_since_Timestamp( chatUser, dtCutOff );
    } //sync
    
    return retVal;
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  update_Shown_Status()                                                  //
  //-------------------------------------------------------------------------//
  public static void update_Shown_Status( List<String>  arrId ) throws Exception {
    
    synchronized( innerMgr ) {
      innerMgr.update_Shown_Status( arrId );
    } //sync
    
  } //(m)
  
  
} //class
