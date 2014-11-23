<%@page import="com.losy.common.utils.SessionKeys"%>
<%@ page contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ page import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%@ page import="java.io.OutputStream"%>
<%!Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}%>
<%
	try {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		OutputStream os = response.getOutputStream();
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(240, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("ºÚÌå", Font.BOLD, 19));
		g.setColor(getRandColor(160, 200));
		String sRand = "";
		String rand = "";
		String base = "0123456789";
		for (int i = 0; i < 4; i++) {
			int start = random.nextInt(base.length());
			rand = base.substring(start, start + 1);
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		session.setAttribute(SessionKeys.LOGIN_CODE, sRand);
		g.dispose();
		ImageIO.write(image, "JPEG", os);
		os.flush();
		os.close();
		os = null;
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody();
	} catch (IllegalStateException e) {
		e.printStackTrace();
	}
%>