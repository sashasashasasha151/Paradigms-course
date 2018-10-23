package expression;

public class Variable extends AbstractExpression implements TripleExpression {

    public char a = 'q';

    public Variable(char b) {
        a = b;
    }

    public int evaluate(int x, int y, int z) {
        if (a == 'x') {
            return x;
        } else {
            if (a == 'y') {
                return y;
            } else {
                return z;
            }
        }
    }
}