package com.maxaer.constants;

public class GameConstants
{

   public static final float PIXEL_TO_METERS = 100f;
   public static final short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
   public static final short CATEGORY_BLOCK = 0x0002; // 0000000000000010 in binary
   public static final short CATEGORY_PLATFORM = 0x0004;
   public static final short MASK_PLAYER = CATEGORY_PLATFORM; //A player should be able to make contact with a block 
   public static final short MASK_BLOCK = CATEGORY_PLAYER | CATEGORY_BLOCK | CATEGORY_PLATFORM; // A block should only make contact with a block
   public static final short MASK_PLATFORM = CATEGORY_BLOCK | CATEGORY_PLAYER;
}
