/**
 * Created by khristian on 3/26/17.
 */

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class UDPServerThread implements Runnable{
    final static int DEFAULT_PORT_NUMBER= 51919;
    final static int DEFAULT_BYTE_SIZE =1024;
    DatagramSocket serverSocket;
    byte[] sendData = new byte[DEFAULT_BYTE_SIZE];
    int serverPort = DEFAULT_PORT_NUMBER;
    HashMap<Integer, Double> PercentMemoryUsed;

    UDPServerThread(){
        DatagramSocket serverSocket = null;
        byte[] sendData =null;
        int serverPort= 0;
    }

    UDPServerThread(HashMap<Integer, Double> PercentMemoryUsed){
        this.PercentMemoryUsed = PercentMemoryUsed;

    }

    @Override
    public void run() {
        try {
            serverSocket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while(true)
        {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            int data;
            for (Double b : PercentMemoryUsed.values()) {
                sendData[] =b;
            }
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                try {
                    serverSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ;

    }
}
