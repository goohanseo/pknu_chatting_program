package chatting;

import java.awt.*;
import java.awt.event.*;

class canvas extends Canvas { //그림판으로 활용될 캔버스 클래스
    client_chat cchat;
    Point p1 = new Point(0,0), p2 = new Point(0,0); //Point p1과 p2를 이용하여 선을 그린다
    canvas(client_chat cc, int w, int h) {
        cchat = cc;
        setSize(w,h);
        setBackground(new Color(100,100,100));
        addMouseMotionListener(new DrawDragger());
        addMouseListener(new DrawMouser());
    }

    public void paint(Graphics g) { g.drawString("그림판", 30,20); }

    void draw(int x1, int y1, int x2, int y2) {
        Graphics g = getGraphics();
        g.drawLine(x1,y1,x2,y2);
    }

    class DrawMouser extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            p1.x = e.getX(); p1.y = e.getY();
            p2.setLocation(p1);
        }
        public void mouseReleased(MouseEvent e) {
            p2.x = e.getX(); p2.y = e.getY();
            //draw(p1.x,p1.y,p2.x,p2.y);
            cchat.sendpointpair(p1.x, p1.y, p2.x, p2.y);
        }
    }

    class DrawDragger extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            p2.x = e.getX(); p2.y = e.getY();
            //draw(p1.x,p1.y,p2.x,p2.y);
            cchat.sendpointpair(p1.x, p1.y, p2.x, p2.y);
            p1.setLocation(p2);
        }
    }
}
