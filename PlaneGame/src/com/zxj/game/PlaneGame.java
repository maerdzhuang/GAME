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
	JFrame jf = new JFrame("飞机游戏");
	MyJPanel mj = new MyJPanel();
	Plane p1 = new Plane("ico/plane.jpg",Final.mjWidth/2,Final.mjHeight-Final.planeSize);
	Image background;
	Image boon;
	Image ball1;
	Image ball2;
	Image ball3;
	
	//系统子弹，不可以被消灭
	ArrayList<Ball> balls =  new ArrayList<>();
	//是否停止子弹的计数器,响应鼠标点击
	byte isStop = 0;
	//判断游戏是否失败
	boolean isLose = false;
	//生存时间
	long survivalTime = 0;
	//实时记录时间‘
	long currentTime = 0;
	//分数
	double grade = 0;
	//开始时间和结束时间
	Date startTime;
	Date endTime;
	//敌机是否出现
	boolean isAppear;
	//敌机子弹数组,可以被我方子弹消除
	ArrayList<Ball> ebb = new ArrayList<>();
	//我方子弹数组，可以和敌方子弹数组互消
	ArrayList<Ball> pbb = new ArrayList<>();
	EnemyPlane ep = new EnemyPlane("ico/enemyPlane.png",Final.enemyPlaneSpeed);
	//Timer的执行次数
	int count;
	//系统生成的子弹数
	int num;

	/**
	 * 加载图片
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
	 * 键盘操作
	 */
	public void keyOperation()
	{
//		定义键盘监听器
		KeyAdapter keyProcessor = new KeyAdapter()
				{
					public void keyPressed(KeyEvent ke)
					{
						p1.addDirection(ke);
						p1.move();
						//我方飞机发射子弹
						if(ke.getKeyCode()==KeyEvent.VK_SPACE && currentTime > 3)
							pbb.add(p1.shoot(Final.planeSize/2,-Final.planeSize/2,ball1));
					}
					public void keyReleased(KeyEvent e)
					{
						p1.minusDirection(e);
						p1.move();
					}
				};
//		为窗口和mj分别添加键盘监听器,一定两个都要添加
		mj.addKeyListener(keyProcessor);
		jf.addKeyListener(keyProcessor);
	}
	
	/**
	 * 鼠标操作
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
	 * 游戏结束，打印
	 */
	public void print()
	{
		endTime = new Date();
		survivalTime = (endTime.getTime() - startTime.getTime())/1000; 
		mj.repaint();
		JOptionPane.showMessageDialog(jf, " 游戏结束 \t\n" +" 生存时间: \t" + survivalTime +"s\n"+" 总分: \t" + grade + "分");
		
		System.exit(0);
	}
	/**
	 * 遍历敌方子弹数组,way表示用哪种移动方法
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
	 * 系统生成num数量的子弹
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
//		定义每0.1秒执行一次的事件监听器
		ActionListener taskPerformer = evt -> 
		{	
			count++;
			endTime = new Date();
			currentTime = (endTime.getTime() - startTime.getTime())/1000;
			grade += 2;
			if(currentTime > 3)
			{
				isAppear = true;
				//表示1秒发射一个子弹
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
	 * 加载图片
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
			//画背景
			g.drawImage(background, 0,0,null);
			//判断我方是否被子弹击中，而失败
			if(isLose) 
				g.drawImage(boon, (int)p1.x, (int)p1.y,Final.planeSize,Final.planeSize,null);
			else	
				p1.drawPlane(g);
			//判断敌机是否出现
			if(isAppear)
			{
				ep.drawEnemyPlane(g);
			}
//			//遍历系统子弹数组
//			for(Ball b : balls)
//			{
//				b.drawBall(g,Color.MAGENTA,"oval");
//			}
			//遍历系统自己生成的子弹
			tra(balls,g,Color.magenta);
			//遍历敌机子弹数组
			tra(ebb, g, Color.green);
			//遍历我方子弹数组
			tra(pbb,g,Color.red);
			//试试更新时间和分数
			g.setColor(Color.green);
			g.drawString(" 生存时间：" + currentTime, 10, 10);
			g.drawString(" 总分：" + grade, 10, 25);
			g.setColor(c);
		}
		/**
		 * 遍历子弹数组,画出子弹数组
		 */
		public void tra(ArrayList<Ball> ab,Graphics g,Color c)
		{
			Iterator<Ball> ib = ab.iterator();
			while(ib.hasNext())
			{
				Ball b2 = ib.next();
				b2.drawBall(g);
//				对Vector、ArrayList在迭代的时候如果同时对其进行修改
//				就会抛出java.util.ConcurrentModificationException异常。
//				if(b2.isOutBound())
//					ebb.remove(b2);
//				所以需要遍历器来遍历修改	
				if(b2.isOutBound() || b2.isDisappear())
					ib.remove();
			}
		}
		
	}
}
