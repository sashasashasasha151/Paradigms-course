package expression;

import expression.parser.ChangeMeException;

public class CheckedDivide extends AbstractExpression implements TripleExpression {

    public CheckedDivide(TripleExpression x, TripleExpression y) {
        leftD = x;
        rightD = y;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        int left = leftD.evaluate(x, y, z), right = rightD.evaluate(x, y, z);
        if (left == -2147483648 && right == -1) {
            throw new ChangeMeException(Integer.toString(left) + " / " + Integer.toString(right) + " - Overflow exception\n");
        }
        if (right == 0) {
            throw new ChangeMeException(Integer.toString(left) + " / " + Integer.toString(right) + " - Division by zero exception\n");
        }
        return left / right;
    }
}