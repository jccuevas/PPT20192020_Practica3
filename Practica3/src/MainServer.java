
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author usuario
 */
public class MainServer {

    static String host = "simu18";
    static ServerSocket server=null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            InetAddress serveraddr = InetAddress.getLocalHost();
            server = new ServerSocket(80,5,serveraddr);
            System.out.println("Server waiting...");
            while(true){
                Socket s = server.accept();
                HttpConnection conn = new HttpConnection(s);
                new Thread(conn).start();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
}
