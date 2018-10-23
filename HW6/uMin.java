package expression;

public class uMin extends AbstractExpression implements TripleExpression {
    public uMin(TripleExpression x) {
        leftD = x;
    }

    public int evaluate(int x, int y, int z) {
        return leftD.evaluate(x, y, z) * -1;
    }
}