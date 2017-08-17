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






}
