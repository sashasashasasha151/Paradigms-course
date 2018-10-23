package expression;

import expression.parser.ChangeMeException;

public class Variable extends AbstractExpression implements TripleExpression {

    public String a;

    public Variable(String b) {
        a = b;
    }

    public int evaluate(int x, int y, int z) {
        if (a.equals("x")) {
            return x;
        } else {
            if (a.equals("y")) {
                return y;
            } else {
                return z;
            }
        }
    }
}