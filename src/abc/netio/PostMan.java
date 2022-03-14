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
    
    List<Socket> list = PassiveSet.getSockets_for_Recipient( chatUser );
    
    for (Socket sock : list) {
      if (sock.isClosed() || sock.isInputShutdown() || sock.isOutputShutdown()) {
        PassiveSet.removeSocket( chatUser, sock );
        continue;
      } //if
      
      try {
        sendChatMessage( msg, sock );
      }
      catch (Exception ex) {
        PassiveSet.removeSocket( chatUser, sock );
      } //try
    } //for
    
  } //deliverMessageToInterestedParties()
  
  
  //-------------------------------------------------------------------------//
  //  sendChatMessage()                                                      //
  //-------------------------------------------------------------------------//
  public static void sendChatMessage( ChatMessage  msg ,
                                      Socket       sock ) throws Exception {
    
    String json = JsonMessageUtil.msgToJson( msg );
    
    //- - - - - - -
    
    OutputStream        outS = sock.getOutputStream();
    ObjectOutputStream  oos  = new ObjectOutputStream( outS );
    
    oos.writeObject( json );
    
    System.out.println("PostMan sent a chat message to a client program.");
    
  } //sendChatMessage()
  
  
} //class
