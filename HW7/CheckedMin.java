package expression;

import expression.parser.ChangeMeException;

public class CheckedMin extends AbstractExpression implements TripleExpression {

    public CheckedMin(TripleExpression x, TripleExpression y) {
        leftD = x;
        rightD = y;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        int left = leftD.evaluate(x, y, z), right = rightD.evaluate(x, y, z);
        if (left < right) {
            return left;
        } else {
            return right;
        }
    }
}