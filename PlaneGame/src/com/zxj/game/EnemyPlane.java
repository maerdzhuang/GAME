package com.zxj.game;

import java.awt.Graphics;

public class EnemyPlane extends Plane {
	double speed;
	public EnemyPlane() {}
	public EnemyPlane(String imagepath,double x,double y)
	{
		super(imagepath,x,y);
	}
	public EnemyPlane(String imagepath,double speed)
	{
		image = Final.getImage(imagepath);
		super.x = 6;
		super.y = Final.planeSize;
		this.speed = speed;
	}
	public void drawEnemyPlane(Graphics g)
	{
		moving();
		super.drawPlane(g);
	}
	
	/**
	 * µÐ»úÔË¶¯¹ì¼£
	 */
	public void moving()
	{
		if(super.x<=0 || super.x>=Final.mjWidth-Final.planeSize)
		{
//			speed += 2;
			speed = -speed;
		}
		super.x += speed;
	}
}
