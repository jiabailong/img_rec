package com.example.testword;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.util.Log;

import com.example.charactermatch2.NativeFunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by jbl on 17-8-16.
 */

public class TestWord {
    public String textWord(Bitmap src, String targetName) {
        String tarname = targetName;
        targetName = "/sdcard/test/Target2/tg" + 1 + ".bmp";
        final float data[] = new float[2];
        data[0] = 0;
        data[1] = 1;
        NativeFunctions.CharacterCreate(data);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        src = BitmapFactory.decodeFile("/sdcard/test/t1.bmp", options);
//        src.getPixel(0,0);
        final int src_widht = src.getWidth();
        final int src_height = src.getHeight();

        final byte[] byte_src = bitmap2Array2bgr(src);
        int len = byte_src.length;
        final int CharacterRect[] = new int[4];
        final int numrect[] = new int[4];
        final StringBuffer stringBuffer = new StringBuffer();
        Bitmap target = BitmapFactory.decodeFile(targetName, options);
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
        int icon_rect[] = new int[10];
        RectF rectF = new RectF(numrect[0], numrect[1], numrect[2], numrect[3]);
//            canvas.drawRect(rectF,p);
        String s = com.example.numrec.NativeFunctions.NumberVersion();
        com.example.numrec.NativeFunctions.NumberCreate(data);
        int n = com.example.numrec.NativeFunctions.NumberRec(byte_clip, clip_width, cliip_height, icon_rect);
        stringBuffer.append("\n" + n + "=园型" + Arrays.toString(icon_rect) + "\n");
        com.example.numrec.NativeFunctions.NumberRelease();
        String res = int2String(icon_rect, n);
        Log.d(tarname, res.toString());
//            saveMyBitmap("1",clipBitmap);
        target.recycle();
        clipBitmap.recycle();
        src.recycle();
//            saveMyBitmap("框框",src);

        com.example.numrec.NativeFunctions.NumberRelease();
        return res;
    }

    public String int2String(int res[], int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (res[i] < 10) {
                buffer.append(res[i]);
            } else if (res[i] == 10) {
                buffer.append("V");
            } else if (res[i] == 11) {
                buffer.append("A");
            } else if (res[i] == 12) {
                buffer.append(".");
            }
        }
//        Log.d("===jia", buffer.toString());
        return buffer.toString();
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
