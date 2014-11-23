<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.Transparency"%>
<%@page import="java.awt.AlphaComposite"%>
<%@page import="java.awt.Font"%>
<%@page import="java.io.OutputStream"%>
<%@page import="com.sun.image.codec.jpeg.JPEGImageEncoder"%>
<%@page import="com.sun.image.codec.jpeg.JPEGCodec"%>
<%@page import="java.awt.Graphics2D"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="com.losy.common.utils.SessionKeys"%>
<%@page import="java.awt.Color"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%!
	public static final char chars [] =  new char[]{'2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','K','M','N','P','Q','R','S','T','W','Y'};
	
	public static Random random = new Random();
	public static Color getRandomColor() {
		return new Color(random.nextInt(255) & 0x00ffffff ,random.nextInt(255),random.nextInt(255));
	}

	public static Color getReverseColor(Color c) {
		return new Color(255- c.getRed(),255-c.getBlue(),255-c.getGreen());
	}
	
	public static final int [] colorRgbs = new int[] {0x00123456,0x00654321,0x00383838,0x000000ff,0x00ff0000,0x00000000,0x00ff8800,0x00c000c0,0x00808080,0x006b51c7};
	
	public static String getRandomString(Graphics2D g){
		StringBuffer sb = new StringBuffer();
		int j = 0;
		
		for(int i = 0; i < 4 ; i++) {
			char c  = chars[random.nextInt(chars.length)];
			String s = new String(new char[]{c});
			if(random.nextInt(20) % 2 == 0) s = s.toLowerCase();
			sb.append(s);
			g.setColor(new Color(colorRgbs[random.nextInt(colorRgbs.length)]));
			g.drawString(s,(5 + j),15 + random.nextInt(5));
			j += 12;
		}
		return sb.toString();
	}
 %>

<%
	
	response.setContentType("image/jpeg");
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires",0);
	
	
	OutputStream output = response.getOutputStream();
	Color c = getRandomColor();
	int width = 60, height = 20;
	BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	for(int i = 0; i < width; ++i) {
       for(int j = 0; j < height; ++j) {           
             bi.setRGB(i, j,0x00ffffff);
       }
    }
	Graphics2D g = bi.createGraphics();
	g.setFont(new Font("Times new Roman",Font.PLAIN,20));
	g.setColor(c);
	for(int i=0;i<30;i++) g.drawRect(random.nextInt(width), random.nextInt(height),1,1);
	String code = getRandomString(g);
	session.setAttribute(SessionKeys.LOGIN_CODE, code);
	g.dispose();
	ImageIO.write(bi, "JPEG", output);
	output.flush();
	output.close();
	output = null;
	response.flushBuffer();
	out.clear();
	out = pageContext.pushBody();
%>
