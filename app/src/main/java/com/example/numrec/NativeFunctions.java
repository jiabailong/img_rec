package com.example.numrec;

public class NativeFunctions {
	static{
        System.loadLibrary("numrec");
	}
	public static native String NumberVersion();
	public static native int NumberCreate(float[] Para);
	public native static int NumberRec(byte[] Image,int imgWidth, int imgHeight, int[]Number);
	public native static int NumberRelease();
}
