package org.nova.platform.common.core.utils;

/**
 * Description:
 *
 * @author 张岳
 * @date 2023/4/24 15:16
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by MQ
 * Date:2017/2/16
 */
public class ImageUtil {
    private Random random = SecureRandom.getInstanceStrong();
    private int w = 95;
    private int h = 40;

    public ImageUtil() throws NoSuchAlgorithmException {
    }

    public void  createImage(HttpServletRequest request,
                             HttpServletResponse response, String validateCode) throws IOException {

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        /*
         * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
         */
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        if (StringUtils.isEmpty(width) && StringUtils.isEmpty(height)) {
            w = Integer.parseInt(width);
            h = Integer.parseInt(height);
        }

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        /*
         * 生成背景
         */
        createBackground(g);

        /*
         * 生成字符
         */
        String s = createCharacter(g);
        request.getSession().setAttribute(validateCode, s);

        g.dispose();
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        out.close();
    }

    private Color getRandColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        if(f>255) {
            f=255;
        }
        if(b>255) {
            b=255;
        }
        return new Color(f+random.nextInt(b-f),f+random.nextInt(b-f),f+random.nextInt(b-f));
    }

    private void createBackground(Graphics g) {
        // 填充背景
        g.setColor(getRandColor(220,250));
        g.fillRect(0, 0, w, h);
        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            g.setColor(getRandColor(40,150));
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            g.drawLine(x, y, x1, y1);
        }
    }

    private String createCharacter(Graphics g) {
        char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
                'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
        String[] fontTypes = {"Arial","Arial Black","AvantGarde Bk BT","Calibri"};
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
            g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
            g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD,26));
            g.drawString(r, 15 * i + 5, 26 + random.nextInt(8));
//			g.drawString(r, i*w/4, h-5);
            s.append(r);
        }
        return s.toString();
    }
}
