package abc.netio;

import abc.bos.*;
import abc.dbio.OuterDbMgr;
import abc.json.*;

import java.io.*;
import java.net.*;


public class ServerThread extends Thread {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Socket  sock ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public ServerThread( Socket  socket ) {
    
    sock = socket;
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  run()                                                                  //
  //-------------------------------------------------------------------------//
  @Override
  public void run() {
    
    try {
      InputStream        inS = sock.getInputStream();
      ObjectInputStream  ois = new ObjectInputStream( inS );
      Object             obj = ois.readObject();
      
      if (obj instanceof String) {
        String           str      = (String) obj;
        ConnTypeRequest  req      = JsonConnTypeUtil.jsonToRequest( str );
        String           chatUser = req.chatUser;
        String           theType  = req.connectionType;
        
        if (null != theType) {
          if (ConnectionTypes.PASSIVE_CLIENT.equals( theType )) {
            PassiveSet.add( chatUser, sock );
            send_Ack_Response( sock );
            //(We now exit this thread.)
          }
          else if (ConnectionTypes.ACTIVE_CLIENT.equals( theType )) {
            send_Ack_Response( sock );
            process_Incoming_Messages( chatUser, sock );
          } //if
        } //if
      } //if
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //run()
  
  
  //-------------------------------------------------------------------------//
  //  send_Ack_Response()                                                    //
  //-------------------------------------------------------------------------//
  private void send_Ack_Response( Socket  socket ) {
    
    ConnTypeResponse resp = new ConnTypeResponse();
      resp.theResponse    = ResponseCodes.OK;
    
    try {
      OutputStream        outS = socket.getOutputStream();
      ObjectOutputStream  oos  = new ObjectOutputStream( outS );
      String              json = JsonConnTypeUtil.responseToJson( resp );
      
      oos.writeObject( json );
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //send_Ack_Response()
  
  
  //-------------------------------------------------------------------------//
  //  process_Incoming_Messages()                                            //
  //-------------------------------------------------------------------------//
  private void process_Incoming_Messages( String  chatUser,
                                          Socket  socket ) {
    while (true) {
      try {
        InputStream        inS = socket.getInputStream();
        ObjectInputStream  ois = new ObjectInputStream( inS );
        Object             obj = ois.readObject();
        
        if (obj instanceof String) {
          String       str = (String) obj;
          ChatMessage  msg = JsonMessageUtil.jsonToMsg( str );
          
          if (null != msg) {
            System.out.println("New chat message received.");
            
            //- - - - - - -
            
            msg = OuterDbMgr.storeMessage( msg );    //Also assigns the "timeStamp" value.
            
            System.out.println("New chat message saved in db.");
            
            //- - - - - - -
            
            PostMan.deliverMessageToInterestedParties( msg );
            
            //- - - - - - -
            
            String json = JsonMessageUtil.msgToJson( msg );
            
            //- - - - - - -
            
            OutputStream        outS = socket.getOutputStream();
            ObjectOutputStream  oos  = new ObjectOutputStream( outS );
            
            oos.writeObject( json );
          } //if
        } //if
      }
      catch (Exception ex) {
        ex.printStackTrace( System.err );
      } //try
    } //while
    
  } //process_Incoming_Messages()
  
  
} //class
