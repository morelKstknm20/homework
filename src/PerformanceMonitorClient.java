/*
 * CSCI320 - Networking and Distributed Computing - Spring 2017
 * Instructor: Thyago Mota
 * Description: Prg02 - Performance Monitor Client
 * Your name: Khristian Morel
 */
import com.sun.jmx.snmp.InetAddressAcl;

import java.io.RandomAccessFile;
import java.net.*;
import java.io.*;

class PerformanceMonitorClient implements Runnable {
    private static DatagramSocket clientSocket;
    final static int DEFAULT_BYTE_SIZE=1024;
    private static InetAddress serverAddress;
    private static int serverPort;
    private byte[] receiveData = new byte[DEFAULT_BYTE_SIZE];
    private byte[] sendData = new byte[DEFAULT_BYTE_SIZE];

    PerformanceMonitorClient() {
        this.serverAddress = null;
        this.serverPort = 0;
    }

    PerformanceMonitorClient(String host) throws SocketException, UnknownHostException {
        DatagramSocket clientSocket = new DatagramSocket();
        serverAddress = InetAddress.getByName(host);
    }

    @Override
    public void run() {
        PerformanceMonitorClientGUI gui = new PerformanceMonitorClientGUI();
    }

        public void send(String memUsed) throws IOException {
            sendData = memUsed.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);
        }

        public String receive() throws IOException {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            return modifiedSentence;
        }

    public void main(String[] args) throws SocketException, UnknownHostException, IOException {

            PerformanceMonitorClient performanceMonitorClient = new PerformanceMonitorClient();
            new Thread(performanceMonitorClient).start();
        }
}
