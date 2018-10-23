package expression;

import expression.parser.ChangeMeException;

public class CheckedMultiply extends AbstractExpression implements TripleExpression {

    public CheckedMultiply(TripleExpression x, TripleExpression y) {
        leftD = x;
        rightD = y;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        int left = leftD.evaluate(x, y, z), right = rightD.evaluate(x, y, z);
        if (left >= 1 && right >= 1 && 2147483647 / left < right) {
            throw new ChangeMeException(Integer.toString(left) + " * " + Integer.toString(right) + " - Overflow exception\n");
        }
        if (left < 0 && right < 0 && 2147483647 / left > right) {
            throw new ChangeMeException(Integer.toString(left) + " * " + Integer.toString(right) + " - Overflow exception\n");
        }
        if (left < -1 && right >= 0 && -2147483648 / left < right) {
            throw new ChangeMeException(Integer.toString(left) + " * " + Integer.toString(right) + " - Overflow exception\n");
        }
        if (left >= 0 && right < -1 && -2147483648 / right < left) {
            throw new ChangeMeException(Integer.toString(left) + " * " + Integer.toString(right) + " - Overflow exception\n");
        }
        return left * right;
    }
}