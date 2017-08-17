package com.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.charactermatch2.NativeFunctions;
import com.example.testiocn.TestIocn;
import com.example.testword.TestWord;
import com.exampleimg_rec.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.tt);
//        NativeFunctions nativeFunctions=new NativeFunctions();
        textView.setText(NativeFunctions.CharacterVersion());
        File file=new File("/sdcard/model");
        if(!file.exists()){
            CopyAsset. copyAssetFolder(getAssets(), "model",
                    "/sdcard/model");
        }else{
            long l=CopyAsset.getFolderSize(file);
            if(l/1000<862){
                CopyAsset. copyAssetFolder(getAssets(), "model",
                        "/sdcard/model");
            }
            Log.d("jia====",l+"");
        }

        new TestWord().textWord(null,null);
        new TestIocn().test(null,null);
//                NativeFunctions.CharacterRelease();
//                com.example.icondetection.NativeFunctions.IconRelease();

            }



//textView.setText(stringBuffer.toString());


//        saveMyBitmap("==",clipBitmap);
    public byte[] bitmap2Array2bgr(Bitmap bitmap){
      int  width = bitmap.getWidth();
      int  height = bitmap.getHeight();

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
        return  temp;
    }
    public void saveMyBitmap(String bitName,Bitmap mBitmap){
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
