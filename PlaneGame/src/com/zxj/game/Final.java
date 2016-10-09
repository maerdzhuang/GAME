package com.zxj.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class Final {
	//画布的宽度和高度
	public static int mjWidth = 600;
	public static int mjHeight = 600;
	//飞机的大小
	public static int planeSize = 70;
	//小球的大小
	public static int ballSize = 20;
	//开局小球的数量
	public static int ballNum = 5;
	//小球的速度
	public static double ballSpeed = 8;
	//飞机的速度
	public static double planeSpeed = 10;
	//敌机速度
	public static double enemyPlaneSpeed = 5;
	//背景滚动控制量
	public static int scroll = 5;
	
	public static Image getImage(String path)
	{
		URL u = Final.class.getClassLoader().getResource(path);
		BufferedImage img = null;
		try{
			img = ImageIO.read(u);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return img;
	}
}
