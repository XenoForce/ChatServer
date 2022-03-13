package abc.dbio;

import abc.bos.*;


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
    } //synchronized()
    
    return msg;
  } //storeMessage()
  
  
} //class
