package com.zxj.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PlaneGame {
	JFrame jf = new JFrame("�ɻ���Ϸ");
	MyJPanel mj = new MyJPanel();
	Plane p1 = new Plane("ico/plane.jpg",Final.mjWidth/2,Final.mjHeight-Final.planeSize);
	Image background;
	Image boon;
	Image ball1;
	Image ball2;
	Image ball3;
	
	//ϵͳ�ӵ��������Ա�����
	ArrayList<Ball> balls =  new ArrayList<>();
	//�Ƿ�ֹͣ�ӵ��ļ�����,��Ӧ�����
	byte isStop = 0;
	//�ж���Ϸ�Ƿ�ʧ��
	boolean isLose = false;
	//����ʱ��
	long survivalTime = 0;
	//ʵʱ��¼ʱ�䡮
	long currentTime = 0;
	//����
	double grade = 0;
	//��ʼʱ��ͽ���ʱ��
	Date startTime;
	Date endTime;
	//�л��Ƿ����
	boolean isAppear;
	//�л��ӵ�����,���Ա��ҷ��ӵ�����
	ArrayList<Ball> ebb = new ArrayList<>();
	//�ҷ��ӵ����飬���Ժ͵з��ӵ����黥��
	ArrayList<Ball> pbb = new ArrayList<>();
	EnemyPlane ep = new EnemyPlane("ico/enemyPlane.png",Final.enemyPlaneSpeed);
	//Timer��ִ�д���
	int count;
	//ϵͳ���ɵ��ӵ���
	int num;

	/**
	 * ����ͼƬ
	 */
	public void loadImage()
	{
			background = Final.getImage("ico/bg.jpg");
			boon = Final.getImage("ico/boon.jpg");
			ball1 = Final.getImage("ico/04.gif");
			ball2 = Final.getImage("ico/01.gif");
			ball3 = Final.getImage("ico/03.gif");

	}
	
	/**
	 * ���̲���
	 */
	public void keyOperation()
	{
//		������̼�����
		KeyAdapter keyProcessor = new KeyAdapter()
				{
					public void keyPressed(KeyEvent ke)
					{
						p1.addDirection(ke);
						p1.move();
						//�ҷ��ɻ������ӵ�
						if(ke.getKeyCode()==KeyEvent.VK_SPACE && currentTime > 3)
							pbb.add(p1.shoot(Final.planeSize/2,-Final.planeSize/2,ball1));
					}
					public void keyReleased(KeyEvent e)
					{
						p1.minusDirection(e);
						p1.move();
					}
				};
//		Ϊ���ں�mj�ֱ���Ӽ��̼�����,һ��������Ҫ���
		mj.addKeyListener(keyProcessor);
		jf.addKeyListener(keyProcessor);
	}
	
	/**
	 * ������
	 */
	public void mouseOperation(Timer timer)
	{
		mj.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				isStop ++;
				if(isStop % 2 == 1)
					timer.stop();
				else
					timer.start();
			}
		});
	}
	
	/**
	 * ��Ϸ��������ӡ
	 */
	public void print()
	{
		endTime = new Date();
		survivalTime = (endTime.getTime() - startTime.getTime())/1000; 
		mj.repaint();
		JOptionPane.showMessageDialog(jf, " ��Ϸ���� \t\n" +" ����ʱ��: \t" + survivalTime +"s\n"+" �ܷ�: \t" + grade + "��");
		
		System.exit(0);
	}
	/**
	 * �����з��ӵ�����,way��ʾ�������ƶ�����
	 */
	public void traverArray(ArrayList<Ball> ab,int way)
	{
		for(Ball b : ab)
		{
			if(!isLose)
			{
				isLose = b.moving(p1,way);
			}
			else
			{
				print();
			}
		}
	}
	
	/**
	 * ϵͳ����num�������ӵ�
	 * @param num
	 */
	public void addBalls(int number)
	{
		for(int i=0;i<number;i++)
		{
			balls.add(new Ball(Final.mjWidth/2,Final.mjHeight/2,Final.ballSize,Final.ballSpeed,ball2));
		}
	}
	
	public void init()
	{
		num = Final.ballNum;
		startTime = new Date();
		loadImage();
		keyOperation();
		addBalls(num);
//		����ÿ0.1��ִ��һ�ε��¼�������
		ActionListener taskPerformer = evt -> 
		{	
			count++;
			endTime = new Date();
			currentTime = (endTime.getTime() - startTime.getTime())/1000;
			grade += 2;
			if(currentTime > 3)
			{
				isAppear = true;
				//��ʾ1�뷢��һ���ӵ�
				if(count%5 == 0)
				{
					ebb.add(ep.shoot(Final.planeSize/2,Final.planeSize/2,ball3));
				}
				if(count%100 == 0)
				{
					num += 5;
					addBalls(num);
				}
			}
			traverArray(balls,2);
			traverArray(ebb,2);
			for(Ball b3 : pbb)
			{
				b3.moving3(ebb);
			}
			
			mj.repaint();
			
		};
		Timer timer = new Timer(100,taskPerformer);
		timer.start();
		if(isLose)
			timer.stop();
		mouseOperation(timer);
		mj.setPreferredSize(new Dimension(Final.mjWidth,Final.mjHeight));
		jf.add(mj);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	/**
	 * ����ͼƬ
	 * @param path
	 */
	public void loadImage(BufferedImage image,String path)
	{
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new PlaneGame().init();
	}
	
	class MyJPanel extends JPanel
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
		public void paint(Graphics g) {
			Color c = g.getColor();
			//������
			g.drawImage(background, 0,0,null);
			//�ж��ҷ��Ƿ��ӵ����У���ʧ��
			if(isLose) 
				g.drawImage(boon, (int)p1.x, (int)p1.y,Final.planeSize,Final.planeSize,null);
			else	
				p1.drawPlane(g);
			//�жϵл��Ƿ����
			if(isAppear)
			{
				ep.drawEnemyPlane(g);
			}
//			//����ϵͳ�ӵ�����
//			for(Ball b : balls)
//			{
//				b.drawBall(g,Color.MAGENTA,"oval");
//			}
			//����ϵͳ�Լ����ɵ��ӵ�
			tra(balls,g,Color.magenta);
			//�����л��ӵ�����
			tra(ebb, g, Color.green);
			//�����ҷ��ӵ�����
			tra(pbb,g,Color.red);
			//���Ը���ʱ��ͷ���
			g.setColor(Color.green);
			g.drawString(" ����ʱ�䣺" + currentTime, 10, 10);
			g.drawString(" �ܷ֣�" + grade, 10, 25);
			g.setColor(c);
		}
		/**
		 * �����ӵ�����,�����ӵ�����
		 */
		public void tra(ArrayList<Ball> ab,Graphics g,Color c)
		{
			Iterator<Ball> ib = ab.iterator();
			while(ib.hasNext())
			{
				Ball b2 = ib.next();
				b2.drawBall(g);
//				��Vector��ArrayList�ڵ�����ʱ�����ͬʱ��������޸�
//				�ͻ��׳�java.util.ConcurrentModificationException�쳣��
//				if(b2.isOutBound())
//					ebb.remove(b2);
//				������Ҫ�������������޸�	
				if(b2.isOutBound() || b2.isDisappear())
					ib.remove();
			}
		}
		
	}
}
