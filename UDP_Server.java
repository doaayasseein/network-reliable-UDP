package udp_server;
import java.io.*;
import java.net.*;
public class UDP_Server {
   public static void main(String args[]) throws Exception
      { byte[] receiveData = new byte[16];
        byte[] sendData ;        
        DatagramSocket serverSocket = new DatagramSocket(9876);
        InetAddress IPAddress=InetAddress.getByName("localhost");
            while(true)
           { DatagramPacket receivenumber = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivenumber );
                  int port = receivenumber.getPort();
                  String temp = new String((receivenumber .getData()));
                 int packetsNumber = Integer.parseUnsignedInt(temp.substring(0, 1));
                System.out.println("number= " +packetsNumber);   
                int sequenceNumber=1;
                  String sentence="";
                 for(int i=0;i<packetsNumber;i++){
             DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  serverSocket.send(receivePacket);
                  String packet = new String( receivePacket.getData());
                   System.out.println(packet);
                  String recievedData=packet.substring(1, 16);
                  sentence += recievedData;
                  IPAddress = receivePacket.getAddress();
                  port = receivePacket.getPort();} 
                  System.out.println("RECEIVED: " + sentence );
                    String capital = sentence.toUpperCase();
                   System.out.println(capital);
                     for(int i=0;i<capital.length();i+=15){
                   String part=capital.substring(i, i+15);
                   part= sequenceNumber+part;
                   sendData=part.getBytes();
                   DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);
                  sequenceNumber++;}}}}
              
                
            
