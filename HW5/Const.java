public class Const implements DoubleExpression, Expression {
    public final double k;

    public Const(double x) {
        k = x;
    }

    public double evaluate(double x) {
        return k;
    }

    public int evaluate(int x) {
        return (int) k;
    }
}