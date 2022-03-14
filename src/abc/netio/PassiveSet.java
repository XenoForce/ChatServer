package abc.netio;

import java.net.*;
import java.util.*;


public class PassiveSet {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private static final Map<String, List<Socket>>  dataStruct = new Hashtable<>();
  
  
  //-------------------------------------------------------------------------//
  //  add()                                                                  //
  //-------------------------------------------------------------------------//
  public static void add( String  chatUser,
                          Socket  sock ) {
    
    synchronized( dataStruct ) {
      List<Socket> list = dataStruct.get( chatUser );
      
      if (null == list) {
        list = new ArrayList<>();
        dataStruct.put( chatUser, list );
      } //if
      
      list.add( sock );
    } //synchronized()
    
  } //add()
  
  
  //-------------------------------------------------------------------------//
  //  removeSocket()                                                         //
  //-------------------------------------------------------------------------//
  public static void removeSocket( String  chatUser,
                                   Socket  sock ) {
    
    synchronized( dataStruct ) {
      List<Socket> list = dataStruct.get( chatUser );
      
      if (null != list) {
        if (list.contains( sock )) {
          list.remove( sock );
        } //if
      } //if
    } //synchronized()
    
  } //removeSocket()
  
  
  //-------------------------------------------------------------------------//
  //  getSockets_for_Recipient()                                             //
  //-------------------------------------------------------------------------//
  public static List<Socket> getSockets_for_Recipient( String  chatUser ) {
    
    List<Socket> retVal = new ArrayList<>();
    
    synchronized( dataStruct ) {
      List<Socket> list = dataStruct.get( chatUser );
      
      if (null != list) {
        list.forEach( x -> retVal.add( x ));
      } //if
    } //synchronized()
    
    return retVal;
  } //getSockets_for_Recipient()
  
  
} //class
