package com.github.wp17.lina.util;

import java.math.BigDecimal;

public abstract class NumberUtil {

	/**
	 * 将两个int类型的值组合成一个long
	 * @param hi
	 * @param lo
	 * @return
	 */
	public static long mergeLong(int hi,int lo){
		long redoLong = 0L;
		long a1 = ((long)hi << 32) & 0xFFFFFFFF00000000L;
		long b1 = lo & 0x00000000FFFFFFFFL;
		redoLong = a1 | b1;
		return redoLong;
	}

	/**
	 * 将一个字符串转换成一个int（效率比Integer.parseInt更高，但只支持10进制）。
	 * @param s
	 * @return
	 */
	public static int parseInt(final String s) {
	    if ( s == null )
	        throw new NumberFormatException( "Null string" );

	    int num  = 0;
	    int sign = -1;
	    final int len  = s.length( );
	    char ch  = s.charAt( 0 );
	    if ( ch == '-' )
	    {
	        if ( len == 1 )
	            throw new NumberFormatException( "Missing digits:  " + s );
	        sign = 1;
	    }
	    else
	    {
	        final int d = ch - '0';
	        if ( d < 0 || d > 9 )
	            throw new NumberFormatException( "Malformed:  " + s );
	        num = -d;
	    }

	    final int max = (sign == -1) ?
	        -Integer.MAX_VALUE : Integer.MIN_VALUE;
	    final int multmax = max / 10;
	    int i = 1;
	    while ( i < len )
	    {
	    	ch = s.charAt(i++);
	    	if(ch == '.')
	    		break;
	        int d = ch - '0';
	        if ( d < 0 || d > 9 )
	            throw new NumberFormatException( "Malformed:  " + s );
	        if ( num < multmax )
	            throw new NumberFormatException( "Over/underflow:  " + s );
	        num *= 10;
	        if ( num < (max+d) )
	            throw new NumberFormatException( "Over/underflow:  " + s );
	        num -= d;
	    }

	    return sign * num;
	}

	/**
	 * 快速将一个字符串转换成int类型的数，务必确定这个字符串的格式完全正确
	 * @param s
	 * @return
	 */
	public static int parseValidInt(final String s){
	    int num  = 0;
	    int sign = -1;
	    final int len  = s.length( );
	    final char ch  = s.charAt( 0 );
	    if ( ch == '-' )
	        sign = 1;
	    else
	        num = '0' - ch;

	    int i = 1;
	    while ( i < len )
	        num = num*10 + '0' - s.charAt( i++ );

	    return sign * num;

	}

    /**
     * 指定精确位将两个double类型的数据四舍五入的相加
     */
    public static float sumDouble(double number1, double number2, int exact){
    	double sum = number1 + number2;
    	BigDecimal b = new BigDecimal(sum);
    	return b.setScale(exact, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 返回两个double类型的数据四舍五入的相乘的整数值
     */
    public static int multiplyDouble(double number1, double number2){
		BigDecimal b1 = new BigDecimal(number1);
		BigDecimal b2 = new BigDecimal(number2);
		return b1.multiply(b2).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * (x1,y1)，到(x2,y2)两点间距离的平方
     */
	public static int distanceSquare(int x1,int y1,int x2,int y2){
		int xDiff = x1 - x2;
		int yDiff = y1 - y2;
		return xDiff*xDiff + yDiff*yDiff;
	}

	/**
	 * 解析一个long数值。
	 * @param s
	 * @return
	 */
	public static long parseLong(final String s) {
		if (s == null)
			throw new NumberFormatException("Null string");
		long num = 0;
		int sign = -1;
		final int len = s.length();
		char ch = s.charAt(0);
		if (ch == '-') {
			if (len == 1)
				throw new NumberFormatException("Missing digits:  " + s);
			sign = 1;
		} else {
			final int d = ch - '0';
			if (d < 0 || d > 9)
				throw new NumberFormatException("Malformed:  " + s);
			num = -d;
		}
		final long max = (sign == -1) ? -Long.MAX_VALUE : Long.MIN_VALUE;
		final long multmax = max / 10;
		int i = 1;
		while (i < len) {
			ch = s.charAt(i++);
			if (ch == '.')
				break;
			int d = ch - '0';
			if (d < 0 || d > 9)
				throw new NumberFormatException("Malformed:  " + s);
			if (num < multmax)
				throw new NumberFormatException("Over/underflow:  " + s);
			num *= 10;
			if (num < (max + d))
				throw new NumberFormatException("Over/underflow:  " + s);
			num -= d;
		}
		return sign * num;
	}
}
