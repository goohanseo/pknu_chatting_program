package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
    private Socket socket;

    private BufferedReader min;
    private PrintWriter mout;

    public client(String ip, int port){
        try {
            socket = new Socket(ip,port); //서버에 요청 보내기
            System.out.println(ip+"\nconnected\n");

            min = new BufferedReader( //연결하기
                    new InputStreamReader(socket.getInputStream()));
            mout = new PrintWriter(socket.getOutputStream());

            mout.println("응답해주셍!"); //메세지 전달
            mout.flush();
            //응답 출력
            System.out.println(min.readLine());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //소켓 닫고 연결 끊기
            try{
                socket.close();
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args){
        String ip = "172.30.1.99";
        int port = 5555;

        new client(ip,port);
    }
}
