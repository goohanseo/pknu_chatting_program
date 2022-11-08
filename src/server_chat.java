//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Vector;
//
//public class server_chat {
//    protected Vector vhandler; //사용자 등록 장부
//    int id = 0;
//    server_chat(int port) throws IOException {
//        ServerSocket server = new ServerSocket(port);
//        vhandler = new Vector(2,5);
//        System.out.println("chatting" + port + "ready");
//        while(true){
//            Socket csock = server.accept();
//            chatHandler c = new chatHandler(this,csock);
//            vhandler.addElement(c); //유저등
//            System.out.println(++id+"받아라"+ vhandler.size()+"명");
//            c.start();
//        }
//    }
//    public int numChatters(){ return vhandler.size();}
//    public static void main (String args[])throws IOException{
//        if(args.length != 1)throw new RuntimeException();
//        new server_chat(Integer.parseInt(args[0]));
//    }
//}
