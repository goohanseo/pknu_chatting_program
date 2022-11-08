//import java.io.*;
//import java.net.Socket;
//import java.util.Enumeration;
//
//public class chatHandler extends Thread {
//    protected server_chat server;
//    protected Socket sock;
//    protected DataInputStream is;
//    protected DataOutputStream os;
//    private boolean stop;
//
//    chatHandler(server_chat server, Socket sock) throws IOException {
//        this.server = server;
//        this.sock = sock;
//        is = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
//        os = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
//    }
//
//    public void run() {
//        String name = "";
//        try {
//            name = is.readUTF();
//            if (name.equals("")) name = "나몰라";
//            broadcast(name + "entrance!");
//            while (!stop) {
//                broadcast(name + ":" + is.readUTF());
//            }
//        } catch (IOException e) {
//            System.out.println("...???");
//        } finally {
//            System.out.println(name + "exit");
//            server.vhandler.removeElement(this);
//        }
//    }
//
//
//    protected void broadcast(String message){
//        synchronized (server.vhandler){
//            Enumeration en = server.vhandler.elements();
//            while (en.hasMoreElements()){
//                chatHandler c = (chatHandler) en.nextElement();
//                try{
//                    c.os.writeUTF(message);
//                    c.os.flush();
//                } catch(IOException e){}
//            }
//        }
//    }
//}
