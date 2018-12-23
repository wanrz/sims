package com.sims.common.base.controller;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.imageio.ImageIO;

public class PictureCheckCodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PictureCheckCodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void init() throws ServletException {
		super.init();
	}
	/*该方法主要作用是获得随机生成的颜色*/ 
	public Color getRandColor(int s,int e){
		Random random=new Random ();
		if(s>255) s=255;
		if(e>255) e=255;
		int r,g,b;
		r=s+random.nextInt(e-s);	//随机生成RGB颜色中的r值
		g=s+random.nextInt(e-s);	//随机生成RGB颜色中的g值
		b=s+random.nextInt(e-s);	//随机生成RGB颜色中的b值
		return new Color(r,g,b);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置不缓存图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		//指定生成的响应图片,一定不能缺少这句话,否则错误.
		response.setContentType("image/jpeg");
		int width=86,height=25;		//指定生成验证码的宽度和高度
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);	//创建BufferedImage对象,其作用相当于一图片
		Graphics g=image.getGraphics();		//创建Graphics对象,其作用相当于画笔
		Graphics2D g2d=(Graphics2D)g;		//创建Grapchics2D对象
		Random random=new Random();
		Font mfont=new Font("宋体",Font.BOLD,18);	//定义字体样式
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);	//绘制背景
		g.setFont(mfont);					//设置字体
		g.setColor(getRandColor(180,200));
		
		//绘制100条颜色和位置全部为随机产生的线条,该线条为2f
		for(int i=0;i<50;i++){
			int x=random.nextInt(width-1);
			int y=random.nextInt(height-1);
			int x1=random.nextInt(6)+1;
			int y1=random.nextInt(12)+1;
			BasicStroke bs=new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);	//定制线条样式
			Line2D line=new Line2D.Double(x,y,x+x1,y+y1);
			g2d.setStroke(bs);
			g2d.draw(line);		//绘制直线
		}
		//输出由英文，数字，和中文随机组成的验证文字，具体的组合方式根据生成随机数确定。
		String sRand="";
		String ctmp="";
		int itmp=0;
		//制定输出的验证码为四位
		for(int i=0;i<4;i++){
			switch(random.nextInt(2)){
				case 1:		//生成A-Z的字母
				     itmp=random.nextInt(26)+65;
				     itmp = (itmp == 79) ? 80 :itmp;
				     ctmp=String.valueOf((char)itmp);
				     break;				
				default:
					 itmp=random.nextInt(10)+48;
					 itmp = (itmp == 48) ? 50 :itmp;
					 ctmp=String.valueOf((char)itmp);
					 break;
			}
			sRand+=ctmp;
			Color color=new Color(20+random.nextInt(110),20+random.nextInt(110),random.nextInt(110));
			g.setColor(color);
			g.drawString(ctmp, 15*i+18, 18);
		}
		HttpSession session=request.getSession();
		session.setAttribute("randCheckCode", sRand);
		g.dispose();	//释放g所占用的系统资源
		ImageIO.write(image,"JPEG",response.getOutputStream());	//输出图片
	}
}