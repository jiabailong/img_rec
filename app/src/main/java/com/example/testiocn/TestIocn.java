package com.example.testiocn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.charactermatch.NativeFunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by jbl on 17-8-11.
 */

public class TestIocn {
    public String test(Bitmap src, String targetName) {
        String tarname = targetName;
        tarname = "/sdcard/test/Target3/tg" + 1 + ".bmp";
        final float data[] = new float[2];
        data[0] = 0;
        data[1] = 1;
        NativeFunctions.CharacterCreate(data);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        src = BitmapFactory.decodeFile("/sdcard/test/t3.bmp", options);
        final int src_widht = src.getWidth();
        final int src_height = src.getHeight();

        final byte[] byte_src = bitmap2Array2bgr(src);
        int len = byte_src.length;


        int CharacterRect[] = new int[4];
        int numrect[] = new int[4];
        StringBuffer stringBuffer = new StringBuffer();
        Bitmap target = BitmapFactory.decodeFile(tarname, options);
        int t_widht = target.getWidth();
        int t_height = target.getHeight();
        byte[] byte_target = bitmap2Array2bgr(target);
        int m = NativeFunctions.CharacterMatch(byte_src, src_widht, src_height,
                byte_target, t_widht, t_height, CharacterRect, numrect);
        stringBuffer.append("===" + m + "=定位字符串" +
                Arrays.toString(CharacterRect) + "=\n待识别" + Arrays.toString(numrect));
        Bitmap clipBitmap = Bitmap.createBitmap(src, numrect[0], numrect[1],
                numrect[2], numrect[3]);
        byte[] byte_clip = bitmap2Array2bgr(clipBitmap);
        int clip_width = clipBitmap.getWidth();
        int cliip_height = clipBitmap.getHeight();
        int icon_rect[] = new int[9];
        com.example.icondetection.NativeFunctions.IconCreate(data);
        int n = com.example.icondetection.NativeFunctions.IconDetection(byte_clip, clip_width, cliip_height, icon_rect);
        stringBuffer.append("\n" + n + "=园型" + Arrays.toString(icon_rect) + "\n");
        com.example.icondetection.NativeFunctions.IconRelease();
        Log.d("===jia", stringBuffer.toString());
        target.recycle();
        clipBitmap.recycle();
        String s = res2Word(n);
        Log.d("jia====", s);
        return  s;
    }

    public String res2Word(int res) {
        String s;
        if (res == 1) {
            s = "运行正常";
        } else {
            s = "运行故障";
        }
        return s;
    }

    public byte[] bitmap2Array2bgr(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] temp = byteBuffer.array();

//        byte[] pixels = new byte[(temp.length / 4) * 3]; // Allocate for 3 byte BGR
//
//        // Copy pixels into place
//        for (int i = 0; i < (temp.length / 4); i++) {
//            pixels[i * 3] = temp[i * 4 + 3];     // B
//            pixels[i * 3 + 1] = temp[i * 4 + 2]; // G
//            pixels[i * 3 + 2] = temp[i * 4 + 1]; // R
//
//            // Alpha is discarded
//        }
//
//        return pixels;
        return temp;
    }

    public void saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
