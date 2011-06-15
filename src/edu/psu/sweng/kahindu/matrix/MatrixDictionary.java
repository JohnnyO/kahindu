package edu.psu.sweng.kahindu.matrix;

public class MatrixDictionary {

    public static final Matrix LOW_PASS_AVERAGE = getLowPass(1);
    public static final Matrix LOW_PASS_ONE = getLowPass(2);
    public static final Matrix LOW_PASS_TWO = getLowPass(4);
    public static final Matrix LOW_PASS_THREE = getLowPass(12);

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
            -1, 0, -1, 0, 1, 0, 1, 2 });  //NOTE:  don't normalize the shadow mask

    private static Matrix getLowPass(int centroid) {
        Matrix m = new Matrix(3, 3);
        m.fill(1);
        m.setValueAt(1, 1, centroid);
        m.normalize();
        return m;
    }

}
