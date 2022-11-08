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
            socket = new Socket("localhost", 5050);
            System.out.println("[서버와 연결되었습니다.]");

            String name = "user" + (int) (Math.random() * 10);
            Thread sendThread = new SendThread(socket, name);
            sendThread.start();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (in != null) {
                String inputMsg = in.readLine();
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

class SendThread2 extends Thread{
    Socket socket = null;
    String name;

    Scanner scanner = new Scanner(System.in);

    public SendThread2(Socket socket, String name){
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
                String outputMsg = scanner.nextLine();
                out.println(outputMsg);
                out.flush();
                if ("quit".equals(outputMsg)) break;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}