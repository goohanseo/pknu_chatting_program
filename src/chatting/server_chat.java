package chatting;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

public class server_chat {
    protected Vector vhandler; //사용자 등록 장부
    int id = 0;
    server_chat(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        vhandler = new Vector(2,5);
        System.out.println("chatting" + port + "ready");
        while(true){
            Socket csock = server.accept();
            chatHandler c = new chatHandler(this,csock);
            vhandler.addElement(c); //유저등
            System.out.println(++id+"받아라"+ vhandler.size()+"명");
            c.start();
        }
    }
    public int numChatters(){ return vhandler.size();}
    public static void main (String args[])throws IOException{
        if(args.length != 1)
            throw new RuntimeException("Syntax: KChatServer port");
        new server_chat(Integer.parseInt(args[0]));
    }
}
class  chatHandler extends Thread {
    protected server_chat server;
    protected Socket sock;
    protected DataInputStream is;
    protected DataOutputStream os;
    private boolean stop;

    chatHandler(server_chat server, Socket sock) throws IOException {
        this.server = server;
        this.sock = sock;
        is = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
        os = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
    }

    public void run() {
        String name = "";
        try {
            name = is.readUTF();
            if (name.equals("")) name = "나몰라";
            broadcast(name + "entrance!");
            while (!stop) {
                broadcast(name + ":" + is.readUTF());
            }
        } catch (IOException e) {
            System.out.println("...???");
        } finally {
            System.out.println(name + "exit");
            server.vhandler.removeElement(this);
        }
    }


    protected void broadcast(String message){
        synchronized (server.vhandler){
            Enumeration en = server.vhandler.elements();
            while (en.hasMoreElements()){
                chatHandler c = (chatHandler) en.nextElement();
                try{
                    c.os.writeUTF(message);
                    c.os.flush();
                } catch(IOException e){}
            }
        }
    }
}
