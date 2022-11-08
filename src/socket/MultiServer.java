package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiServer {

    public static void main(String[] args) {
        MultiServer multiServer = new MultiServer();
        multiServer.start();
    }

    public void start() {
        ServerSocket serversocket = null;
        Socket socket = null;
        try {
            serversocket = new ServerSocket(5050);
            while (true) {
                System.out.println("<클라이언트 연결대기중>");
                socket = serversocket.accept();
                //client가 접속할 때 마다 새로운 스레드를 생성하여 다중 접속을 가능케한다.
                ReceiveThread receivedThread = new ReceiveThread(socket);
                receivedThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace(); //에러를 단계별로 출력해줌.
        } finally {
            if (serversocket != null) {
                try {
                    serversocket.close();
                    System.out.println("<서버종료>");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("<서버소켓통신에러>");
                }
            }
        }
    }
}
class ReceiveThread extends Thread{ //사용자마다 받아줄 스레드 정의
    static List<PrintWriter> list =
            Collections.synchronizedList(new ArrayList<PrintWriter>());

    Socket socket = null;
    BufferedReader in = null; //버퍼에 대하여 읽어옴
    PrintWriter out = null; //문자열을 출력함

    public ReceiveThread (Socket socket){
        this.socket = socket;
        try{
            out = new PrintWriter(socket.getOutputStream()); //socket으로 들어오는 정보들에 대해 버퍼단위로 입력받고 문자열을 출력함
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            list.add(out);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
public void run(){
    String name = "";
    try { //최초 1회 client 이름 수신
    name = in.readLine();
    System.out.println("[" + name + "새연결생성]");
    sendAll("[" + name + "]님이 들어오셨습니다.");

    while (in != null){ //quit이 나와 break 될때까지 입력 받음
        String inputMsg = in.readLine();
        if("quit".equals(inputMsg)) break;
        sendAll(name + ">>" + inputMsg);
    }
    } catch (IOException e){
        System.out.println("[" + name + "접속끊김]");
    } finally {
        sendAll("[" + name + "]님이 나가셨습니다.");
        list.remove(out); //client list에서 삭제
        try {
            socket.close(); //socket을 닫음
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    System.out.println("[" + name + "연결종료].");
    }
    private  void sendAll (String s){
        for (PrintWriter out: list){
            out.println(s);
            out.flush(); //현재 버퍼에 저자오디어 있는 내용을 전송하고 버퍼를 비운다.
        }
    }
}