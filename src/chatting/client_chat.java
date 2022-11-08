package chatting;

import jdk.javadoc.internal.tool.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;


public class client_chat extends JApplet implements Runnable, ActionListener {

    JTextField login, typein;
    JTextArea ta;
    canvas cvas;
    JPanel portal,chatroom;
    CardLayout cardm;
    String myname;
    DataInputStream is;
    DataOutputStream os;

    public static void main (String args[]){
        client_chat chatter = new client_chat();
        chatter.init();
        chatter.start();
        JFrame f = new JFrame("lab 4");
        f.getContentPane().add(chatter);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init(){
    setGUIcards();
    setLayout(cardm = new CardLayout());
    add(portal,"card-login");
    add(chatroom,"card-chat");
    cardm.show (this.getContentPane(),"card-login");
}
    void setGUIcards() {
        portal = new JPanel(new BorderLayout());		// card-1
        portal.add(new JLabel(new ImageIcon("/Users/guhanseo/Desktop/CafeGaggle.jpg")), "Center");  // 1-1
        JPanel logpan = new JPanel();
        login = new JTextField(20);
        login.addActionListener(this);
        logpan.add(new JLabel("이름 ")); logpan.add(login);  // 1-2.a, 1-2.b
        portal.add(logpan, "South");

        chatroom = new JPanel(new BorderLayout());  	// card-2
        typein = new JTextField();
        typein.addActionListener(this);
        chatroom.add(typein, "South");	// 2-2

        ta = new JTextArea(14, 25*1);
        ta.setBackground(new Color(220,255,255));
        ta.setEditable(false);
        ta.setLineWrap(true);
        JScrollPane spane = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatroom.add(spane, "Center");  // 2-1 (-> replace it with a dual interface)
        //- dual design here - 7+
        JPanel dualpan = new JPanel();
        dualpan.add(spane);
        JPanel cpan = new JPanel();
        cvas = new canvas(this,300,250);
        cpan.add(cvas);
        dualpan.add(cvas);
        dualpan.add(cpan);
        chatroom.add(dualpan,"Center");
    }

public void start(){
    System.out.println("*Thread starting*");
    (new Thread (this)).start();;
}

    public void run() {
        System.out.println("run: ... 카페 문 똑똑 ...");
        try {
            // 서버접속(create a Socket) & IO stream 두개 만들기
            Socket sock = new Socket("localhost",2023);
            is = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
            os = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
            execute();
        } catch (IOException e) { System.out.println("연결실패."); }
    }



    public void execute() {   // 서버에서 계속 오는 메시지를 받아서 출력한다.
        try {
            while (true) {
                // get msg from the server(thread) and display them -
                String msg = is.readUTF();
                if(parseDraw(msg)==false)
                    ta.append(msg +"\n");
            }
        } catch (IOException e) {} finally { System.out.println("stop"); }
    }



    public void actionPerformed (ActionEvent e) {
        Component c = (Component) e.getSource();

        // textfield 입력(이름, 채팅글)을 읽어서 서버로 보낸다.
        if ((JTextField) e.getSource() == login) {
            myname = login.getText();
            //System.out.println("login : " + myname + "인데요.");
            try {os.writeUTF(myname);; os.flush();}
            catch(IOException ioe) { System.out.println("Fail"); }
            cardm.show(this.getContentPane(), "card-chat");
            //ta.append(myname + " 입장!\n");
//	      *focus*
            typein.requestFocus();
        }
        else {  //- 챗글 from typein -
            //ta.append(myname + ": " + typein.getText() + "\n"); typein.setText("");
            try { os.writeUTF(typein.getText()); os.flush();}
            catch (IOException ioe) {}
            typein.setText("");
        }
    }
    public void sendpointpair(int x1,int y1, int x2, int y2) {
        try {
            os.writeUTF("<G>:"+x1+":"+y1+":"+x2+":"+y2);
            os.flush();
        } catch(IOException e) {}
    }
    boolean parseDraw(String line) {
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken(":");
        if(st.hasMoreTokens()==false)
            return false;
        String tag = st.nextToken(":");
        if (tag.equals("<G>")){
            int x1 = Integer.parseInt(st.nextToken(":"));
            int y1 = Integer.parseInt(st.nextToken(":"));
            int x2 = Integer.parseInt(st.nextToken(":"));
            int y2 = Integer.parseInt(st.nextToken(":"));
            cvas.draw(x1, y1, x2, y2);
            return true;
        }else
            return false;
    }
    }

