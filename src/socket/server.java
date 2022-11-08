package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private ServerSocket serversocket;
    private Socket socket;

    private BufferedReader min; //들어옴
    private PrintWriter mout; //나감

    public server(){
        try {
            serversocket = new ServerSocket(5555);
            System.out.println("server start");
            //스레드 멈춰있음

            //연결 요청 들어오면 연결
            socket = serversocket.accept();
            System.out.println("connected");

            min = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            mout = new PrintWriter(socket.getOutputStream());

            //클라이언트에서 보낸 문자열 출력
            System.out.println(min.readLine());

            //클라이언트에 전송완료 출력
            mout.println("success");
            mout.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            // 소켓 닫기 (연결 끊기)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                serversocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  static  void main(String[] args){
        server server = new server();
    }
}
