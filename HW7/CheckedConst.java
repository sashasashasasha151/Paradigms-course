package expression;

import expression.parser.ChangeMeException;

public class CheckedConst extends AbstractExpression implements TripleExpression {

    private final int k;

    public CheckedConst(int x) {
        k = x;
    }

    public int evaluate(int x, int y, int z) throws ChangeMeException {
        return k;
    }
}