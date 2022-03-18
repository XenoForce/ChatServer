package abc.netio;

import abc.bos.*;
import abc.json.*;

import java.io.*;
import java.net.*;
import java.util.*;


public class PostMan {
  
  //-------------------------------------------------------------------------//
  //  deliverMessageToInterestedParties()                                    //
  //-------------------------------------------------------------------------//
  public static void deliverMessageToInterestedParties( ChatMessage  msg ) {
    
    String chatUser = msg.destination;
    
    List<PassiveObj> list = PassiveLookup.getAll_for_Recipient( chatUser );
    
    for (PassiveObj pObj : list) {
      Socket              sock = pObj.socket;
      ObjectOutputStream  oos  = pObj.oos;
      
      if (sock.isClosed()
      ||  sock.isInputShutdown()
      ||  sock.isOutputShutdown() ) {
        PassiveLookup.remove( chatUser, pObj );
        continue;
      } //if
      
      try {
        sendChatMessage( msg, oos );
      }
      catch (Exception ex) {
        PassiveLookup.remove( chatUser, pObj );
      } //try
    } //for
    
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  sendChatMessage()                                                      //
  //-------------------------------------------------------------------------//
  public static void sendChatMessage( ChatMessage         msg ,
                                      ObjectOutputStream  oos ) throws Exception {
    
    String json = JsonMessageUtil.msgToJson( msg );
    
    oos.writeObject( json );
    
    System.out.println("PostMan sent a chat message to a client program.");
    
  } //(m)
  
  
} //class
