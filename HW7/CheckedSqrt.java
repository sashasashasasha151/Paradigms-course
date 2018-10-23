package expression;

import expression.parser.ChangeMeException;

public class CheckedSqrt extends AbstractExpression implements TripleExpression {

    public CheckedSqrt(TripleExpression x) {
        leftD = x;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        int input = leftD.evaluate(x, y, z);
        int leftCur = 0, rightCur = input > 46340 ? 46340 : input + 1;

        if (input < 0) {
            throw new ChangeMeException("You can't take the root of the negative number - " + Integer.toString(input) + "\n");
        }

        while (leftCur != rightCur - 1) {
            if (((leftCur + rightCur) / 2) * ((leftCur + rightCur) / 2) > input) {
                rightCur = (leftCur + rightCur) / 2;
            } else {
                leftCur = (leftCur + rightCur) / 2;
            }
        }

        return leftCur;
    }
}