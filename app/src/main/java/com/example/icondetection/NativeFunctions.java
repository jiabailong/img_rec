package com.example.icondetection;

public class NativeFunctions {
	static{
        System.loadLibrary("icondetection");
	}
	public static native int IconCreate(float[] Para);
	public native static int IconDetection(byte[] Image,int imgWidth, int imgHeight, int[] iconRect);
	public native static int IconRelease();
}
