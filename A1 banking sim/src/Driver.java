import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
public class Driver {

    /** 
     * main class
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	 /*******************************************************************************************************************************************
    	  * TODO : implement all the operations of main class   																					*
    	  ******************************************************************************************************************************************/
        
    	
    	Network objNetwork = new Network("network");            /* Activate the network */
        objNetwork.start();
        Server objServer = new Server();
        objServer.start();
        Client objSendingClient = new Client("sending");	
        objSendingClient.start();	/* Activate the client (send) */
//        Client objReceivingClient = new Client("receiving");	/* Activate the client (receive) */
//        objReceivingClient.start();
        
        
        
        /* Complete here the code for the main method ...*/
    }
}
