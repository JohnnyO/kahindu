package edu.psu.sweng.kahindu.matrix;

public class MatrixDictionary {

    public static final Matrix LOW_PASS_AVERAGE = getLowPass(1);
    public static final Matrix LOW_PASS_ONE = getLowPass(2);
    public static final Matrix LOW_PASS_TWO = getLowPass(4);
    public static final Matrix LOW_PASS_THREE = getLowPass(12);

    public static final Matrix MEAN3 = new Matrix(3, 3, 0.11111111f);
    public static final Matrix MEAN9 = new Matrix(9, 9, 1 / 81.0f);

    // we took these values directly from the original application, no
    // documentation provided on how they were calculated
    public static final Matrix GUASSIAN3 = new Matrix(3, 3, new float[] { 1, 2,
            1, 2, 4, 2, 1, 2, 1 }).normalize();
    public static final Matrix GUASSIAN7 = new Matrix(7, 7, new float[] { 1, 1,
            2, 2, 2, 1, 1, 1, 2, 2, 4, 2, 2, 1, 2, 2, 4, 8, 4, 2, 2, 2, 4, 8,
            16, 8, 4, 2, 2, 2, 4, 8, 4, 2, 2, 1, 2, 2, 4, 2, 2, 1, 1, 1, 2, 2,
            2, 1, 1 }).normalize();
    public static final Matrix GUASSIAN15 = new Matrix(15, 15, new float[] {
            1.9045144E-7f, 9.671922E-7f, 3.8253193E-6f, 1.1782813E-5f,
            2.8265502E-5f, 5.2806907E-5f, 7.6833596E-5f, 8.7063876E-5f,
            7.6833596E-5f, 5.2806907E-5f, 2.8265502E-5f, 1.1782813E-5f,
            3.8253193E-6f, 9.671922E-7f, 1.9045144E-7f, 9.671922E-7f,
            4.9118075E-6f, 1.9426576E-5f, 5.9838065E-5f, 1.4354405E-4f,
            2.681756E-4f, 3.901932E-4f, 4.4214682E-4f, 3.901932E-4f,
            2.681756E-4f, 1.4354405E-4f, 5.9838065E-5f, 1.9426576E-5f,
            4.9118075E-6f, 9.671922E-7f, 3.8253193E-6f, 1.9426576E-5f,
            7.6833596E-5f, 2.3666414E-4f, 5.677278E-4f, 0.0010606551f,
            0.001543244f, 0.0017487246f, 0.001543244f, 0.0010606551f,
            5.677278E-4f, 2.3666414E-4f, 7.6833596E-5f, 1.9426576E-5f,
            3.8253193E-6f, 1.1782813E-5f, 5.9838065E-5f, 2.3666414E-4f,
            7.2897685E-4f, 0.0017487246f, 0.0032670477f, 0.0047535263f,
            0.0053864513f, 0.0047535263f, 0.0032670477f, 0.0017487246f,
            7.2897685E-4f, 2.3666414E-4f, 5.9838065E-5f, 1.1782813E-5f,
            2.8265502E-5f, 1.4354405E-4f, 5.677278E-4f, 0.0017487246f,
            0.004194972f, 0.00783724f, 0.011403117f, 0.012921424f,
            0.011403117f, 0.00783724f, 0.004194972f, 0.0017487246f,
            5.677278E-4f, 1.4354405E-4f, 2.8265502E-5f, 5.2806907E-5f,
            2.681756E-4f, 0.0010606551f, 0.0032670477f, 0.00783724f,
            0.014641892f, 0.021303825f, 0.024140399f, 0.021303825f,
            0.014641892f, 0.00783724f, 0.0032670477f, 0.0010606551f,
            2.681756E-4f, 5.2806907E-5f, 7.6833596E-5f, 3.901932E-4f,
            0.001543244f, 0.0047535263f, 0.011403117f, 0.021303825f,
            0.030996885f, 0.03512407f, 0.030996885f, 0.021303825f,
            0.011403117f, 0.0047535263f, 0.001543244f, 3.901932E-4f,
            7.6833596E-5f, 8.7063876E-5f, 4.4214682E-4f, 0.0017487246f,
            0.0053864513f, 0.012921424f, 0.024140399f, 0.03512407f,
            0.039800785f, 0.03512407f, 0.024140399f, 0.012921424f,
            0.0053864513f, 0.0017487246f, 4.4214682E-4f, 8.7063876E-5f,
            7.6833596E-5f, 3.901932E-4f, 0.001543244f, 0.0047535263f,
            0.011403117f, 0.021303825f, 0.030996885f, 0.03512407f,
            0.030996885f, 0.021303825f, 0.011403117f, 0.0047535263f,
            0.001543244f, 3.901932E-4f, 7.6833596E-5f, 5.2806907E-5f,
            2.681756E-4f, 0.0010606551f, 0.0032670477f, 0.00783724f,
            0.014641892f, 0.021303825f, 0.024140399f, 0.021303825f,
            0.014641892f, 0.00783724f, 0.0032670477f, 0.0010606551f,
            2.681756E-4f, 5.2806907E-5f, 2.8265502E-5f, 1.4354405E-4f,
            5.677278E-4f, 0.0017487246f, 0.004194972f, 0.00783724f,
            0.011403117f, 0.012921424f, 0.011403117f, 0.00783724f,
            0.004194972f, 0.0017487246f, 5.677278E-4f, 1.4354405E-4f,
            2.8265502E-5f, 1.1782813E-5f, 5.9838065E-5f, 2.3666414E-4f,
            7.2897685E-4f, 0.0017487246f, 0.0032670477f, 0.0047535263f,
            0.0053864513f, 0.0047535263f, 0.0032670477f, 0.0017487246f,
            7.2897685E-4f, 2.3666414E-4f, 5.9838065E-5f, 1.1782813E-5f,
            3.8253193E-6f, 1.9426576E-5f, 7.6833596E-5f, 2.3666414E-4f,
            5.677278E-4f, 0.0010606551f, 0.001543244f, 0.0017487246f,
            0.001543244f, 0.0010606551f, 5.677278E-4f, 2.3666414E-4f,
            7.6833596E-5f, 1.9426576E-5f, 3.8253193E-6f, 9.671922E-7f,
            4.9118075E-6f, 1.9426576E-5f, 5.9838065E-5f, 1.4354405E-4f,
            2.681756E-4f, 3.901932E-4f, 4.4214682E-4f, 3.901932E-4f,
            2.681756E-4f, 1.4354405E-4f, 5.9838065E-5f, 1.9426576E-5f,
            4.9118075E-6f, 9.671922E-7f, 1.9045144E-7f, 9.671922E-7f,
            3.8253193E-6f, 1.1782813E-5f, 2.8265502E-5f, 5.2806907E-5f,
            7.6833596E-5f, 8.7063876E-5f, 7.6833596E-5f, 5.2806907E-5f,
            2.8265502E-5f, 1.1782813E-5f, 3.8253193E-6f, 9.671922E-7f,
            1.9045144E-7f });

    public static final Matrix GUASSIAN31 = getGaussKernel(31, 31, 2.0);

    /**
     * High pass kernels
     */

    public static final Matrix HIGH_PASS_ONE = new Matrix(3, 3, new float[] {
            0, -1, 0, -1, 10, -1, 0, -1, 0 }).normalize();
    public static final Matrix HIGH_PASS_TWO = new Matrix(3, 3, new float[] {
            0, -1, 0, -1, 8, -1, 0, -1, 0 }).normalize();
    public static final Matrix HIGH_PASS_THREE = new Matrix(3, 3, new float[] {
            0, -1, 0, -1, 5, -1, 0, -1, 0 }).normalize();
    public static final Matrix HIGH_PASS_FOUR = new Matrix(3, 3, new float[] {
            1, -2, 1, -2, 5, -2, 1, -2, 1 }).normalize();
    public static final Matrix HIGH_PASS_FIVE = new Matrix(3, 3, new float[] {
            -1, -1, -1, -1, 9, -1, -1, -1, -1 }).normalize();
    public static final Matrix SHADOW_MASK = new Matrix(3, 3, new float[] { -2,
            -1, 0, -1, 0, 1, 0, 1, 2 }); // NOTE: don't normalize the shadow
                                         // mask

    /**
     * morph kernels
     */

    public static final Matrix KH = new Matrix(3, 3, new float[] { 0, 0, 0, 1,
            1, 1, 0, 0, 0 });
    public static final Matrix KV = new Matrix(3, 3, new float[] { 0, 1, 0, 0,
            1, 0, 0, 1, 0 });
    public static final Matrix KSQUARE = new Matrix(3, 3, 1);
    public static final Matrix KCROSS = new Matrix(3,3,new float []{0,1,0,1,1,1,0,1,0});


    private static Matrix getLowPass(int centroid) {
        Matrix m = new Matrix(3, 3);
        m.fill(1);
        m.setValueAt(1, 1, centroid);
        m.normalize();
        return m;
    }

    public static Matrix getGaussKernel(int width, int height, double sigma) {
        Matrix m = new Matrix(width, height);
        int xc = width / 2;
        int yc = height / 2;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                m.setValueAt(x, y, (float) gauss(x, y, xc, yc, sigma));
        return m;
    }

    public static double gauss(double x, double y, double xc, double yc,
            double sigma) {
        double dx = x - xc;
        double dy = y - yc;
        double dx2 = dx * dx;
        double dy2 = dy * dy;
        double sigma2 = sigma * sigma;
        double oneOnSigma2 = 1 / sigma2;
        return Math.exp(-(dx2 + dy2) * oneOnSigma2 / 2) / Math.PI * oneOnSigma2
                / 2;
    }

}
