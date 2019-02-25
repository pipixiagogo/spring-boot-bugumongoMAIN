package com.zh.mongodb.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CCodeUtil {
    private static Random random = new Random();
    /**
    * 生成随机验证码
    * @param length   长度
    * @param exChars  排除的字符
    * @return
    */
    public static String getRandomCode(int length,String exChars) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < length) {
            int t = random.nextInt(123);
            if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                sb.append((char) t);
                i++;
            }
        }
        return sb.toString();
    }

    public static BufferedImage getImageFromCode(String textCode,int width,int height,
                                                 Color backColor,int interLine,Color lineColor,
                                                 boolean randomLocation,Color foreColor){
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random r=new Random();
        g.setColor(backColor==null?getRandomColor():backColor);
        g.fillRect(0,0,width,height);
        //画线
        if(interLine>0){
            int x=r.nextInt(4),y=0;
            int x1=width-r.nextInt(4),y1=0;
            for(int i=0;i<interLine;i++){
                g.setColor(lineColor==null?getRandomColor():lineColor);
                y=r.nextInt(height-r.nextInt(4));
                y1=r.nextInt(height-r.nextInt(4));
                g.drawLine(x,y,x1,y1);
            }
        }
        //写验证码
        int fsize=(int)(height*0.8);//字体大小为图片高度的80%
        int fx=0;
        int fy=fsize;
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,fsize));
        for(int i=0;i<textCode.length();i++){
            fy = randomLocation?(int)((Math.random()*0.3+0.6)*height) : fy;//每个字符高低是否随机
            g.setColor(foreColor==null ? getRandomColor() : foreColor);
            g.drawString(textCode.charAt(i)+"",fx,fy);
            fx+=(width / textCode.length()) * (Math.random() * 0.3 + 0.8); //依据宽度浮动
        }
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);//噪点数量
            for (int i = 0; i < area; i++) {
            int xxx = r.nextInt(width);
            int yyy = r.nextInt(height);
            int rgb = getRandomColor().getRGB();
            image.setRGB(xxx, yyy, rgb);
        }
        g.dispose();
        return image;
    }

    private static Color getRandomColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r,g,b);
    }

}
