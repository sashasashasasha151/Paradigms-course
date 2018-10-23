package expression;

import expression.parser.ChangeMeException;

public class CheckedAdd extends AbstractExpression implements TripleExpression {

    public CheckedAdd(TripleExpression x, TripleExpression y) {
        leftD = x;
        rightD = y;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        int left = leftD.evaluate(x, y, z), right = rightD.evaluate(x, y, z);
        if ((left >= 0 && right >= 0 && left + right < 0) || (left < 0 && right < 0 && left + right >= 0)) {
            throw new ChangeMeException(Integer.toString(left) + " + " + Integer.toString(right) + " - Overflow exception\n");
        } else {
            return left + right;
        }
    }
}
