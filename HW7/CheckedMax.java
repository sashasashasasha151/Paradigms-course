package expression;

import expression.parser.ChangeMeException;

public class CheckedMax extends AbstractExpression implements TripleExpression {

    public CheckedMax(TripleExpression x, TripleExpression y) {
        leftD = x;
        rightD = y;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        int left = leftD.evaluate(x, y, z), right = rightD.evaluate(x, y, z);
        if (left < right) {
            return right;
        } else {
            return left;
        }
    }
}
