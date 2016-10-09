package com.zxj.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * 子弹，用来射击飞机。
 * @author xj
 *
 */
public class Ball {
	private double size;//子弹的大小
	private Image image;//子弹图片
	private Random rand = new Random();
//	小球的运行速度
	private double Speed;
//	返回一个[0 2*pi]的角度
	private double degree ;
//	ballX和ballY代表小球的坐标
	private double ballX ;
	private double ballY ;
//	子弹是否消除
	private boolean disappear;
//  子弹是否越界
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
	 * 控制小球的移动，同时确认是否游戏失败
	 * @param p
	 * @param way 表示用哪种方法，系统子弹还是敌机子弹
	 * @return
	 */
	public boolean moving(Plane p,int way)
	{
//		如果小球碰到左右边边框
		if(ballX <= 0 || ballX >= Final.mjWidth - Final.ballSize)
		{
			if(way == 1)
				degree = Math.PI - degree;
			if(way == 2)
				outBound = true;
		}
//		如果小球碰到上下边框
		if(ballY >= Final.mjHeight - Final.ballSize || ballY <= 0)
		{
			if(way == 1)
				degree = -degree;
			if(way == 2)
				outBound = true;
		}
		//子弹与飞机碰撞检测，子弹矩阵是否与飞机矩形相交
		else if(getRect().intersects(p.getRect()))
		{	
			return true;
		}
//		小球坐标增加
		ballX += Speed*Math.cos(degree);
		ballY += Speed*Math.sin(degree);
		return false;

	}
	

	/**
	 * 我方子弹的移动控制,并检测是否与敌方子弹相互碰撞
	 */
	public boolean moving3(ArrayList<Ball> ab) {
		for (Ball b : ab) {
			// 如果小球碰到左右边边框
			if (ballX <= 0 || ballX >= Final.mjWidth - Final.ballSize) {
				outBound = true;
			}
			// 如果小球碰到上下边框
			if (ballY >= Final.mjHeight - Final.ballSize || ballY <= 0) {
				outBound = true;
			}
			//两球碰撞检测
			else if (getRect().intersects(b.getRect())) {
				setDisappear(true);
				b.setDisappear(true);
			}
			ballY -= Speed;
		}
		return false;
	}
	/**
	 * 获取小球的矩形
	 */
	public Rectangle getRect()
	{
		return new Rectangle((int)ballX,(int)ballY,Final.ballSize,Final.ballSize);
	}
}
