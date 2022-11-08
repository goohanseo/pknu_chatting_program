package socket;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient2 {
    public static void main(String[] args) {
        MultiClient2 multiclient = new MultiClient2();
        multiclient.start();
    }

    private void start() {
        Socket socket = null;
        BufferedReader in = null;
        try {
            socket = new Socket("localhost", 5050); //서버 소켓 연결
            System.out.println("[서버와 연결되었습니다.]");

            String name = "user" + (int) (Math.random() * 10); //클라이언트 이름 생성
            Thread sendThread = new SendThread(socket, name); //sendthread에 소켓과 이름 전달
            sendThread.start(); //sendthread 실행

            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //입력값을 버퍼단위로 읽어옴
            while (in != null) {
                String inputMsg = in.readLine(); //sendthread.start내의 quit에 의해 종료됨
                if (("[" + name + "]님이 나가셨습니다.").equals(inputMsg)) break;
                System.out.println("From:" + inputMsg);
            }
        } catch (IOException e) {
            System.out.println("[서버 접속끊김]");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[서버 연결종료]");
    }
}

class SendThread2 extends Thread{ //sendthread class 생성
    Socket socket = null;
    String name;

    Scanner scanner = new Scanner(System.in);

    public SendThread2(Socket socket, String name){ //소켓과 자신의 이름 형태로 전송
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run(){
        try{
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(name);
            out.flush();

            while (true) {
                String outputMsg = scanner.nextLine(); //quit할 때까지 입력을 받아서 전송함
                out.println(outputMsg);
                out.flush();
                if ("quit".equals(outputMsg)) break;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}