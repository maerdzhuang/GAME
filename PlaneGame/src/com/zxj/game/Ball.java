package com.zxj.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * �ӵ�����������ɻ���
 * @author xj
 *
 */
public class Ball {
	private double size;//�ӵ��Ĵ�С
	private Image image;//�ӵ�ͼƬ
	private Random rand = new Random();
//	С��������ٶ�
	private double Speed;
//	����һ��[0 2*pi]�ĽǶ�
	private double degree ;
//	ballX��ballY����С�������
	private double ballX ;
	private double ballY ;
//	�ӵ��Ƿ�����
	private boolean disappear;
//  �ӵ��Ƿ�Խ��
	private boolean outBound;

	public boolean isDisappear() {
		return disappear;
	}
	public void setDisappear(boolean disappear) {
		this.disappear = disappear;
	}
	public double getBallX() {
		return ballX;
	}
	public void setBallX(double ballX) {
		this.ballX = ballX;
	}
	public double getBallY() {
		return ballY;
	}
	public void setBallY(double ballY) {
		this.ballY = ballY;
	}
	public boolean isOutBound() {
		return outBound;
	}
	public void setOutBound(boolean outBound) {
		this.outBound = outBound;
	}
	
	public double getDegree() {
		return degree;
	}
	public void setDegree(double degree) {
		this.degree = degree;
	}
	
	public Ball() 
	{
	}
	public Ball(double ballX,double ballY,double size,double Speed,Image image) 
	{
		this(ballX,ballY,image);
		this.size = size;
		this.Speed = Speed;
		this.degree = rand.nextDouble()*Math.PI*2;
	}
	public Ball(double ballX,double ballY,Image image)
	{
		this.ballX = ballX;
		this.ballY = ballY; 
		this.size = Final.ballSize;
		this.Speed = Final.ballSpeed;
		this.degree = Math.PI/2;
		this.image = image;
	}
	
	
	public void drawBall(Graphics g)
	{
		g.drawImage(image, (int)ballX, (int)ballY, (int)size, (int)size, null);
	}
	/**
	 * ����С����ƶ���ͬʱȷ���Ƿ���Ϸʧ��
	 * @param p
	 * @param way ��ʾ�����ַ�����ϵͳ�ӵ����ǵл��ӵ�
	 * @return
	 */
	public boolean moving(Plane p,int way)
	{
//		���С���������ұ߱߿�
		if(ballX <= 0 || ballX >= Final.mjWidth - Final.ballSize)
		{
			if(way == 1)
				degree = Math.PI - degree;
			if(way == 2)
				outBound = true;
		}
//		���С���������±߿�
		if(ballY >= Final.mjHeight - Final.ballSize || ballY <= 0)
		{
			if(way == 1)
				degree = -degree;
			if(way == 2)
				outBound = true;
		}
		//�ӵ���ɻ���ײ��⣬�ӵ������Ƿ���ɻ������ཻ
		else if(getRect().intersects(p.getRect()))
		{	
			return true;
		}
//		С����������
		ballX += Speed*Math.cos(degree);
		ballY += Speed*Math.sin(degree);
		return false;

	}
	

	/**
	 * �ҷ��ӵ����ƶ�����,������Ƿ���з��ӵ��໥��ײ
	 */
	public boolean moving3(ArrayList<Ball> ab) {
		for (Ball b : ab) {
			// ���С���������ұ߱߿�
			if (ballX <= 0 || ballX >= Final.mjWidth - Final.ballSize) {
				outBound = true;
			}
			// ���С���������±߿�
			if (ballY >= Final.mjHeight - Final.ballSize || ballY <= 0) {
				outBound = true;
			}
			//������ײ���
			else if (getRect().intersects(b.getRect())) {
				setDisappear(true);
				b.setDisappear(true);
			}
			ballY -= Speed;
		}
		return false;
	}
	/**
	 * ��ȡС��ľ���
	 */
	public Rectangle getRect()
	{
		return new Rectangle((int)ballX,(int)ballY,Final.ballSize,Final.ballSize);
	}
}
