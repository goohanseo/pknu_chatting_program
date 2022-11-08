//import jdk.javadoc.internal.tool.Main;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.net.Socket;
//
//
//public class client_chat extends JApplet implements Runnable, ActionListener {
//
//    JTextField login, typein;
//    JTextArea ta;
//    //Cvas cvas;
//    JPanel portal,chatroom;
//    CardLayout cardm;
//    String myname;
//    DataInputStream is;
//    DataOutputStream os;
//
//    public static void main (String args[]){
//        client_chat chatter = new client_chat();
//        chatter.init();
//        chatter.start();
//        JFrame f = new JFrame("lab 4");
//        f.getContentPane().add(chatter);
//        f.pack();
//        f.setVisible(true);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//
//    public void init(){
//    setGUIcards();
//    setLayout(cardm = new CardLayout());
//    add(portal,"card-login");
//    add(chatroom,"card-chat");
//    cardm.show (this.getContentPane(),"card-login");
//}
//void setGUIcards(){
//    portal = new JPanel(new BorderLayout());
//    portal.add(new JLabel(new ImageIcon("/Users/guhanseo/Desktop/CafeGaggle.jpg")), "Center");
//    JPanel logpan = new JPanel();
//    login = new JTextField(20);
//    login.addActionListener(this);
//    logpan.add(new JLabel("name"));
//    logpan.add(login);
//    portal.add(logpan,"South");
//
//    chatroom = new JPanel(new BorderLayout());
//    typein = new JTextField();
//    typein.addActionListener(this);
//    chatroom.add(typein,"South");
//
//    ta = new JTextArea(14,25*1);
//    ta.setBackground(new Color(220,255,255));
//    ta.setEditable(false);
//    ta.setLineWrap(true);
//    JScrollPane spane = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//    chatroom.add(spane,"Center");
//}
//
//public void start(){
//    System.out.println("*Thread starting*");
//    (new Thread (this)).start();;
//}
//
//public void run() {
//    System.out.println("run:... hello...");
//    try {
//        Socket sock = new Socket("localhost", 2000);
//        is = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
//        os = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
//        execute();
//    } catch (IOException e) {
//        System.out.println("연결실패");
//    }
//
//
//}
//
//    public void execute() {
//        try{
//            while(true){
//                String msg = is.readUTF();
//                ta.append(msg + "\n");
//            }
//        }catch (IOException e){} finally {System.out.println("stop");}
//    }
//
//
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        Component c = (Component) e.getSource();
//
//        if((JTextField) e.getSource() == login) {
//            myname = login.getText();
//            try {
//                os.writeUTF(myname);
//                os.flush();
//            } catch (IOException ioe) {
//                System.out.println("Fail");
//            }
//                cardm.show(this.getContentPane(), "card-chat");
//                typein.requestFocus();
//
//        }
//        else{
//            try { os.writeUTF(typein.getText()); os.flush(); }
//            catch (IOException ioe) { }
//            typein.setText("");
//        }
//    }
//    }
//
