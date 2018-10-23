public class Variable implements DoubleExpression, Expression {
    public String a;

    public Variable(String b) {
        a = b;
    }

    public double evaluate(double x) {
        return x;
    }

    public int evaluate(int x) {
        return x;
    }
}
