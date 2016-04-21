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
   
   //Image resources 
   //Cookie package
   public static final String ThinMintImg = "Cookies/ThinMint.png";
   public static final String DoSiDoImg = "Cookies/DoSiDo.png";
   public static final String BerryImg = "Cookies/Berry.png";
   public static final String TagalongImg = "Cookies/Tagalong.png";
   public static final String LemonImg = "Cookies/Lemon.png";
   public static final String SamoaImg = "Cookies/SamoaFlake.png";
   public static final String ThanksALotImg = "Cookies/ThanksALot.png";
   public static final String TrefoilImg = "Cookies/Trefoil.png";
   
   //Professor package
   public static final String KempeImg = "Professors/Kempe.png";
   public static final String CoteImg = "Professors/Cote.png";
   public static final String MillerImg = "Professors/Berry.png";
   public static final String ShindlerImg = "Professors/Shindler.png";
   public static final String RedekoppImg = "Professors/Redekopp.png";
   public static final String SukhatmeImg = "Professors/Sukhatme.png";
   public static final String RosenbloomImg = "Professors/Rosenbloom.png";
   public static final String DughmiImg = "Professors/Dughmi.png";
   
   //Trump package
   public static final String SideTrumpImg = "Trump/SideTrump.png";
   public static final String StraightTrumpImg = "Trump/StraightTrump.png";
   public static final String TrumpImg = "Trump/Trump.png";
   public static final String TrumpAlongImg = "Trump/TrumpAlong.png";
   public static final String SmirkImg = "Trump/Smirk.png";
   public static final String TrumpALotImg = "Trump/TrumpALot.png";
   public static final String TrumpMouthImg = "Trump/TrumpMouth.png";
   public static final String TrumpBerryImg = "Trump/TrumpBerry.png";
   
   public static final int DEATH_SCORE = -1; 
   
   
}
