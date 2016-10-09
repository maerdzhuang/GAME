package com.zxj.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Plane {
	Image image;
	double x,y;
	boolean left,right,up,down;//为了实现飞机八个方向运动算法
	public Plane()
	{
		
	}
	public Plane(String imagepath,double x,double y)
	{
		image = Final.getImage(imagepath);
		this.x = x;
		this.y = y;
		
	}
	
	public void drawPlane(Graphics g)
	{
		g.drawImage(image,(int) x, (int) y,Final.planeSize,Final.planeSize,null);
	}
	/**
	 * 增加键盘按下的八个方向，移动
	 * @param e
	 */
	public void addDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = true;
			break;

		case KeyEvent.VK_RIGHT:
			right = true;
			break;

		case KeyEvent.VK_UP:
			up = true;
			break;

		case KeyEvent.VK_DOWN:
			down = true;
			break;

		}
	}

	/**
	 * 键盘松开，方向停止
	 * @param e
	 */
	
	public void minusDirection(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			left = false;
			break;

		case KeyEvent.VK_RIGHT:
			right = false;
			break;

		case KeyEvent.VK_UP:
			up = false;
			break;

		case KeyEvent.VK_DOWN:
			down = false;
			break;
			
		}
	}
	

	public void move() {
		if (left) {
			if(x < 0)
				x = 0;
			else
				x -= Final.planeSpeed;
		}
		if (right) {
			if(x>Final.mjWidth-Final.planeSize)
				x=Final.mjWidth-Final.planeSize;
			else
				x += Final.planeSpeed;
		}
		if (up) {
			if(y < 0)
				y = 0;
			else
				y -= Final.planeSpeed;
		}
		if (down) {
			if(y>Final.mjHeight-Final.planeSize)
				y=Final.mjHeight-Final.planeSize;
			else
				y += Final.planeSpeed;
		}
	}
	
	/**
	 * 发射子弹
	 * @param pianchaX,pianchaX,表示子弹出现位置跟飞机的偏差
	 */
	public Ball shoot(int pianchaX,int pianchaY,Image image)
	{
		return new Ball(x+pianchaX,y+pianchaY,image);
	}
	
	/**
	 * 获取飞机的矩形
	 */
	public Rectangle getRect()
	{
		//加减，是为了让子弹尽可能命中飞机的中心，飞机才算被击中
		return new Rectangle((int)(x+Final.planeSize/2),(int)(y+Final.planeSize/2),Final.planeSize/4,Final.planeSize/4);
	}
}
