package abc.netio;

import abc.bos.*;
import abc.dbio.*;
import abc.json.*;

import java.io.*;
import java.net.*;


public class ServerThread extends Thread {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private String  chatUser ;
  private Socket  sock     ;
  
  
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
      
      Object  obj = ois.readObject();
      String  str = (String) obj;
      
      ConnTypeRequest  req = JsonConnTypeUtil.jsonToRequest( str );
      
      chatUser = req.chatUser;
      
      String  conType = req.connectionType;
      
      OutputStream        outS = sock.getOutputStream();
      ObjectOutputStream  oos  = new ObjectOutputStream( outS );
      
      if (ConnectionTypes.PASSIVE_CLIENT.equals( conType )) {
        PassiveLookup.add( chatUser, sock, oos );
        send_Ack_Response( oos );
        //(We now exit this thread, for this type of connection.)
      }
      else if (ConnectionTypes.ACTIVE_CLIENT.equals( conType )) {
        send_Ack_Response( oos );
        process_Incoming_Requests();
      } //if
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //run()
  
  
  //-------------------------------------------------------------------------//
  //  send_Ack_Response()                                                    //
  //-------------------------------------------------------------------------//
  private void send_Ack_Response( ObjectOutputStream  oos ) {
    
    ConnTypeResponse  resp = new ConnTypeResponse();
      resp.theResponse = ResponseCodes.OK;
    
    try {
      String  json = JsonConnTypeUtil.responseToJson( resp );
      oos.writeObject( json );
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //send_Ack_Response()
  
  
  //-------------------------------------------------------------------------//
  //  process_Incoming_Requests()                                            //
  //-------------------------------------------------------------------------//
  private void process_Incoming_Requests() {
    
    while (true) {
      try {
        InputStream        inS = sock.getInputStream();
        ObjectInputStream  ois = new ObjectInputStream( inS );
        
        Object  requestType  = ois.readObject();
        Object  theRequest   = ois.readObject();
        
        String  sRequestType = (String) requestType;
        
        if (RequestTypes.GET_MSGS_SINCE_CUTOFF.equals( sRequestType )) {
          doWork_Get_Msgs_Since_Cutoff( theRequest );
        }
        else if (RequestTypes.PROCESS_ONE_NEW_MSG.equals( sRequestType )) {
          doWork_Process_One_New_Msg( theRequest );
        }
        else if (RequestTypes.UPDATE_SHOWN_STATUS.equals( sRequestType )) {
          doWork_Update_Shown_Status( theRequest );
        } //if
      }
      catch (java.io.EOFException ex) {
        break;    //Exit the while loop, and also exit this thread.
      }
      catch (Exception ex) {
        ex.printStackTrace( System.err );
      } //try
    } //while
    
  } //process_Incoming_Requests()
  
  
  //-------------------------------------------------------------------------//
  //  doWork_Get_Msgs_Since_Cutoff()                                         //
  //-------------------------------------------------------------------------//
  private void doWork_Get_Msgs_Since_Cutoff( Object  requestObj ) throws Exception {
    
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  doWork_Process_One_New_Msg()                                           //
  //-------------------------------------------------------------------------//
  private void doWork_Process_One_New_Msg( Object  requestObj ) throws Exception {
    
    String       str = (String) requestObj;
    ChatMessage  msg = JsonMessageUtil.jsonToMsg( str );
    
    System.out.println("Server received new chat message.");
    
    //- - Store msg in server db - -:
    
    msg = OuterDbMgr.storeMessage( msg );    //Also assigns the "timeStamp" value.
    
    System.out.println("New chat message saved in server db.");
    
    //- - Deliver message to recipients - -:
    
    PostMan.deliverMessageToInterestedParties( msg );
    
    //- - Send msg (with timestamp) back to the original client - -:
    
    String json = JsonMessageUtil.msgToJson( msg );
    
    OutputStream        outS = sock.getOutputStream();
    ObjectOutputStream  oos  = new ObjectOutputStream( outS );
    
    oos.writeObject( json );
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  doWork_Update_Shown_Status()                                           //
  //-------------------------------------------------------------------------//
  private void doWork_Update_Shown_Status( Object  requestObj ) throws Exception {
    
  } //(m)
  
  
} //class
