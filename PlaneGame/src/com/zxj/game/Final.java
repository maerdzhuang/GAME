package com.zxj.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class Final {
	//�����Ŀ�Ⱥ͸߶�
	public static int mjWidth = 600;
	public static int mjHeight = 600;
	//�ɻ��Ĵ�С
	public static int planeSize = 70;
	//С��Ĵ�С
	public static int ballSize = 20;
	//����С�������
	public static int ballNum = 5;
	//С����ٶ�
	public static double ballSpeed = 8;
	//�ɻ����ٶ�
	public static double planeSpeed = 10;
	//�л��ٶ�
	public static double enemyPlaneSpeed = 5;
	//��������������
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
