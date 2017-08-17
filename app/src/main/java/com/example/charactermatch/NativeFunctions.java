package com.example.charactermatch;

public class NativeFunctions {
	static{
		System.loadLibrary("charactermatch");
	}

	//Version
	public static native String CharacterVersion();
	//01 init 
	public static native int CharacterCreate(float[] Para);
	//02 detect
	public native static int CharacterMatch(byte[] srcImage,int srcWidth, int srcHeight, byte[] targetImage,int targetWidth, int targetHeight, int[] CharacterRect, int[] TargetRect);
	//03 release��
	public native static int CharacterRelease();
	public native static int IconDetection(byte[] Image,int imgWidth, int imgHeight, int[] iconRect);

}
