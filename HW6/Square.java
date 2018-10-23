package expression;

public class Square extends AbstractExpression implements TripleExpression {
    public Square(TripleExpression x) {
        leftD = x;
    }

    public int evaluate(int x, int y, int z) {
        return leftD.evaluate(x, y, z) * leftD.evaluate(x, y, z);
    }
}