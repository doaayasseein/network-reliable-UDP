package udp_client;
  import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit ;
public class UDP_client {
   public static void main(String args[]) throws Exception
   { BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[16];
      byte[] recieve = new byte[16];
      byte[] receiveData =new byte [2048];
      String sentence = inFromUser.readLine();
      //calculating number of packets and sending it
      int packetsNumber=0;      int sequenceNumber=1;
      if((sentence.length()%15) ==0) { packetsNumber=sentence.length()/15;}
      else{ 
     packetsNumber=(sentence.length()/15)+1;
     for(int i=0;i<(sentence.length()%15);i++){
     sentence=  sentence.concat(" ");}}
     sendData= (Integer.toString(packetsNumber)).getBytes();
     DatagramPacket sendnumber = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
     clientSocket.send(sendnumber);
      
      for(int i=0;i<sentence.length();i+=15){
      String subsentence=sentence.substring(i, i+15);
      subsentence=sequenceNumber+ subsentence ;
      sendData=subsentence.getBytes();
      
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);
       // checking if the message was acknowledged or not 
      DatagramPacket receiveAcknolodgement  = new DatagramPacket(recieve, recieve.length);
      clientSocket.receive(receiveAcknolodgement);
      String acknowledgement = new String((receiveAcknolodgement.getData()));
       if (acknowledgement.equals( subsentence))
         { System.err.println(" packet "+sequenceNumber +" is sent and acknoledged ");}
     else { System.err.println(" connection lost, lost packet will be resent in 3 seconds  ");
           clientSocket.send(sendPacket);
         TimeUnit.MINUTES.sleep(3);}
         sequenceNumber++;
                         } 
      String totalsentence="";
     for(int j=0;j<packetsNumber;j++){
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String packet = new String( receivePacket.getData());
     // System.out.println(packet);
      String recievedpart=packet.substring(1, 16);
      totalsentence += recievedpart;
      }
      System.out.println("FROM SERVER:" + totalsentence);
      clientSocket.close();
    }}
