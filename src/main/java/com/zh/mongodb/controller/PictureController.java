package com.zh.mongodb.controller;

import com.zh.mongodb.util.CCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class PictureController {

    @RequestMapping(value = "/ccode")
    public void ccode(HttpServletResponse response, HttpSession session) throws IOException {
        // 生成验证码
        String text = CCodeUtil.getRandomCode(4,null);
        session.setAttribute("ccode",text);
        response.setContentType("image/jpeg");
        BufferedImage image = CCodeUtil.getImageFromCode(text,47,18,
                Color.WHITE,1,null,
                false,Color.black);
        ImageIO.write( image,"JPEG", response.getOutputStream());
    }

    @RequestMapping(value = "/response")
    public void doResponse(HttpServletResponse response)throws IOException{
        response.setStatus(200);
        //response.setHeader("123","321");
        response.setHeader("Content-type", "text/html;charset=utf-8");
        long l = System.currentTimeMillis();
        response.setDateHeader("xxx",l);
        response.getOutputStream().write("中国牛逼".getBytes());
    }
}
