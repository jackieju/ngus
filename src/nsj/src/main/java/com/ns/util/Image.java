package com.ns.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.ns.log.Log;
import com.sun.mail.iap.ByteArray;

public class Image {

	public static void main(String v[]) throws Exception {
		genThumbnail(new URL("http://www.google.cn/intl/zh-CN/images/logo_cn.gif"),
				new File("d:\\a1.jpg"), 100, 100, "jpeg");
		System.out.print("aa");

	}

	public static void genThumbnail(File fi, File fo, int width, int height,
			String outFormat) throws Exception{
		//try {
			BufferedImage bis = ImageIO.read(fi);
			genThumbnail(bis, fo, width, height, outFormat);
		//} catch (Exception e) {
			//e.printStackTrace();
			//Log.error(e);
			//throw e;
		//}
	}

	public static void genThumbnail(BufferedImage bis, File fo, int width,
			int height, String outFormat) throws Exception{
		try {

			AffineTransform transform = new AffineTransform();

			int w = bis.getWidth();
			int h = bis.getHeight();
			// double scale = (double)h/w;

			System.out.println("old: "+w + ", "+h);
			int nw = 0;
			int nh = 0;
			if (w >= h) {
				nw = width;
				nh = (nw * h) / w;
			} else {
				nh = height;
				nw = (nh * w) / h;
			}

			double sx = (double) nw / w;
			double sy = (double) nh / h;
			System.out.println("new: "+nw + ", "+nh);
			transform.setToScale(sx, sy);

			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_INT_RGB);
			//ato.filter(bis, bid);
			bid.getGraphics().drawImage(bis,0,0,nw, nh,null);

			ImageIO.write(bid, outFormat, fo);
			//System.out.print("aa");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e);
			throw e;
		}
	}

	public static ByteArrayOutputStream resize(InputStream in, int width,
			int height, String outFormat) throws Exception{
		BufferedImage bis = ImageIO.read(in);
		try {

			AffineTransform transform = new AffineTransform();

			int w = bis.getWidth();
			int h = bis.getHeight();
			// double scale = (double)h/w;

			System.out.println("old: "+w + ", "+h);
			int nw = 0;
			int nh = 0;
			if (w >= h) {
				nw = width;
				nh = (nw * h) / w;
			} else {
				nh = height;
				nw = (nh * w) / h;
			}

			double sx = (double) nw / w;
			double sy = (double) nh / h;
			System.out.println("new: "+nw + ", "+nh);
			transform.setToScale(sx, sy);

			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_INT_RGB);
			//ato.filter(bis, bid);
			bid.getGraphics().drawImage(bis,0,0,nw, nh,null);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			ImageIO.write(bid, outFormat, out);
			
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e);
			throw e;
		}
		
		
	}
	
	public static void genThumbnail(URL url, File fo, int width, int height,
			String outFormat) throws Exception {
		try {

//			AffineTransform transform = new AffineTransform();
			BufferedImage bis = ImageIO.read(url);
			if (bis==null)
				throw new Exception("cannot get image from " + url.toString());
			genThumbnail(bis, fo, width, height, outFormat);
//			int w = bis.getWidth();
//			int h = bis.getHeight();
//			double scale = (double) w / h;
//
//			int nw = width;
//			int nh = (nw * h) / w;
//			if (nh > height) {
//				nh = 120;
//				nw = (nh * w) / h;
//			}
//
//			double sx = (double) nw / w;
//			double sy = (double) nh / h;
//
//			transform.setToScale(sx, sy);
//
//			AffineTransformOp ato = new AffineTransformOp(transform, null);
//			BufferedImage bid = new BufferedImage(nw, nh,
//					BufferedImage.TYPE_3BYTE_BGR);
//			ato.filter(bis, bid);
//			ImageIO.write(bid, outFormat, fo);
		} catch (Exception e) {
			Log.error(e);
			throw e;
		}
	}
}
