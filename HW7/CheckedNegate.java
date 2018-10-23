package expression;

import expression.parser.ChangeMeException;

public class CheckedNegate extends AbstractExpression implements TripleExpression {
    public CheckedNegate(TripleExpression x) {
        leftD = x;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        int left = leftD.evaluate(x, y, z);
        if (left == -2147483648) {
            throw new ChangeMeException("-1 * " + Integer.toString(left) + " - Overflow exception\n");
        }
        return left * -1;
    }
}
