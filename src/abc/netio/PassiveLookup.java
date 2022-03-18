package abc.netio;

import java.io.*;
import java.net.*;
import java.util.*;


public class PassiveLookup {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private static final Map<String, List<PassiveObj>>  dataStruct = new HashMap<>();
  
  
  //-------------------------------------------------------------------------//
  //  add()                                                                  //
  //-------------------------------------------------------------------------//
  public static void add( String  chatUser,
                          Socket  sock,
                          ObjectOutputStream  oos ) {
    
    PassiveObj pObj = new PassiveObj();
      pObj.chatUser = chatUser;
      pObj.socket   = sock;
      pObj.oos      = oos;
    
    synchronized( dataStruct ) {
      List<PassiveObj> list = dataStruct.get( chatUser );
      
      if (null == list) {
        list = new ArrayList<>();
        dataStruct.put( chatUser, list );
      } //if
      
      list.add( pObj );
    } //sync
    
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  remove()                                                               //
  //-------------------------------------------------------------------------//
  public static void remove( String      chatUser,
                             PassiveObj  pObj ) {
    
    synchronized( dataStruct ) {
      List<PassiveObj> list = dataStruct.get( chatUser );
      
      if (null != list) {
        if (list.contains( pObj )) {
          list.remove( pObj );
        } //if
      } //if
    } //sync
    
  } //(m)
  
  
  //-------------------------------------------------------------------------//
  //  getAll_for_Recipient()                                                 //
  //-------------------------------------------------------------------------//
  public static List<PassiveObj> getAll_for_Recipient( String  chatUser ) {
    
    List<PassiveObj> retVal = new ArrayList<>();
    
    synchronized( dataStruct ) {
      List<PassiveObj> list = dataStruct.get( chatUser );
      
      if (null != list) {
        list.forEach( x -> retVal.add( x ));
      } //if
    } //sync
    
    return retVal;
  } //(m)
  
  
} //class
