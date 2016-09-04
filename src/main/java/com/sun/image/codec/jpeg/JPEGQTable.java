/*     */ package com.sun.image.codec.jpeg;
/*     */ 
/*     */ public class JPEGQTable
/*     */ {
/*     */   private int[] quantval;
/*     */   private static final byte QTABLESIZE = 64;
/*  45 */   public static final JPEGQTable StdLuminance = new JPEGQTable();
/*     */   public static final JPEGQTable StdChrominance;
/*     */ 
/*     */   private JPEGQTable()
/*     */   {
/*  88 */     this.quantval = new int[64];
/*     */   }
/*     */ 
/*     */   public JPEGQTable(int[] paramArrayOfInt)
/*     */   {
/*  98 */     if (paramArrayOfInt.length != 64) {
/*  99 */       throw new IllegalArgumentException("Quantization table is the wrong size.");
/*     */     }
/*     */ 
/* 102 */     this.quantval = new int[64];
/* 103 */     System.arraycopy(paramArrayOfInt, 0, this.quantval, 0, 64);
/*     */   }
/*     */ 
/*     */   public int[] getTable()
/*     */   {
/* 114 */     int[] arrayOfInt = new int[64];
/* 115 */     System.arraycopy(this.quantval, 0, arrayOfInt, 0, 64);
/* 116 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public JPEGQTable getScaledInstance(float paramFloat, boolean paramBoolean)
/*     */   {
/* 135 */     long l1 = paramBoolean ? 255L : 32767L;
/* 136 */     int[] arrayOfInt = new int[64];
/*     */ 
/* 138 */     for (int i = 0; i < 64; i++) {
/* 139 */       long l2 = (long)(this.quantval[i] * paramFloat + 0.5D);
/*     */ 
/* 142 */       if (l2 <= 0L) l2 = 1L;
/*     */ 
/* 145 */       if (l2 > l1) l2 = l1;
/*     */ 
/* 147 */       arrayOfInt[i] = ((int)l2);
/*     */     }
/* 149 */     return new JPEGQTable(arrayOfInt);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  47 */     int[] arrayOfInt = { 16, 11, 12, 14, 12, 10, 16, 14, 13, 14, 18, 17, 16, 19, 24, 40, 26, 24, 22, 22, 24, 49, 35, 37, 29, 40, 58, 51, 61, 60, 57, 51, 56, 55, 64, 72, 92, 78, 64, 68, 87, 69, 55, 56, 80, 109, 81, 87, 95, 98, 103, 104, 103, 62, 77, 113, 121, 112, 100, 120, 92, 101, 103, 99 };
/*     */ 
/*  58 */     StdLuminance.quantval = arrayOfInt;
/*     */ 
/*  67 */     StdChrominance = new JPEGQTable();
/*     */ 
/*  69 */     arrayOfInt = new int[] { 17, 18, 18, 24, 21, 24, 47, 26, 26, 47, 99, 66, 56, 66, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99 };
/*     */ 
/*  79 */     StdChrominance.quantval = arrayOfInt;
/*     */   }
/*     */ }

/* Location:           D:\software\pro\Java\jdk1.7.0_67\jre\lib\rt.jar
 * Qualified Name:     com.sun.image.codec.jpeg.JPEGQTable
 * JD-Core Version:    0.6.2
 */