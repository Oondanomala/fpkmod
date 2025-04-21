package me.oondanomala.fpkmod.util;

/**
 * Math utility methods to deal with negative zero.
 */
public final class MathUtil {
    private static final long NEGATIVE_ZERO_BITS = Double.doubleToRawLongBits(-0.0);

    private MathUtil() {
    }

    /**
     * Checks if the provided double is negative zero.
     *
     * @param number The double to check
     * @return <tt>true</tt> if the double is negative zero, <tt>false</tt> otherwise
     */
    public static boolean isNegativeZero(double number) {
        return Double.doubleToLongBits(number) == NEGATIVE_ZERO_BITS;
    }

    /**
     * Checks whether the two provided doubles have the same sign,
     * taking into account negative zero.
     * <p>
     * If either value is {@code NaN}, returns <tt>false</tt>.
     *
     * @param number1 The first double
     * @param number2 The second double
     * @return <tt>true</tt> if both doubles have the same sign, <tt>false</tt> otherwise
     */
    public static boolean compareSign(double number1, double number2) {
        if (Double.isNaN(number1) || Double.isNaN(number2)) return false;
        return isPositive(number1) == isPositive(number2);
    }

    private static boolean isPositive(double number) {
        return Double.doubleToRawLongBits(number) >= 0;
    }
}
