package com.zxj.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Plane {
	Image image;
	double x,y;
	boolean left,right,up,down;//Ϊ��ʵ�ַɻ��˸������˶��㷨
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
	 * ���Ӽ��̰��µİ˸������ƶ�
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
	 * �����ɿ�������ֹͣ
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
	 * �����ӵ�
	 * @param pianchaX,pianchaX,��ʾ�ӵ�����λ�ø��ɻ���ƫ��
	 */
	public Ball shoot(int pianchaX,int pianchaY,Image image)
	{
		return new Ball(x+pianchaX,y+pianchaY,image);
	}
	
	/**
	 * ��ȡ�ɻ��ľ���
	 */
	public Rectangle getRect()
	{
		//�Ӽ�����Ϊ�����ӵ����������зɻ������ģ��ɻ����㱻����
		return new Rectangle((int)(x+Final.planeSize/2),(int)(y+Final.planeSize/2),Final.planeSize/4,Final.planeSize/4);
	}
}
