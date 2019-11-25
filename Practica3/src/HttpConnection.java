
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
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
public class HttpConnection implements Runnable{

    Socket socket = null;
    
    public HttpConnection (Socket s){
        socket=s;
    }
    
    @Override
    public void run() {
        DataOutputStream dos = null;
        try {
            System.out.println("Starting new HTTP connection with "+socket.getInetAddress().toString());
            dos = new DataOutputStream(socket.getOutputStream());
                      
            
            BufferedReader bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = bis.readLine();
            String []partes = line.split(" ");
            if(partes!=null && partes.length==3){
                do{
                    line= bis.readLine();
                    System.out.println("HTTP HEADER: "+line);

                }while(line!=null && line.length()>0);
                
                if(partes[0].equalsIgnoreCase("get")){
                
                    dos.write(("HTTP/1.1 200 OK\r\ncontent-type:text/html\r\n\r\n<html><body>Hola</body></html>").getBytes());
                    dos.flush();
                }else{
                    dos.write(("HTTP/1.1 405 Method Not Allowed\r\n\r\n").getBytes());
                    dos.flush();
                }
                
            }else{
                do{
                    line= bis.readLine();
                    System.out.println("HTTP HEADER: "+line);

                }while(line!=null && line.length()>0);
                
                dos.write(("HTTP/1.1 400 Bad Request\r\n\r\n").getBytes());
                dos.flush();
            }
            
            
            dos.close();
            bis.close();
            socket.close();          
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dos.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
