package expression;

public class Divide extends AbstractExpression implements TripleExpression {

    public Divide(TripleExpression x, TripleExpression y) {
        leftD = x;
        rightD = y;
    }

    public int evaluate(int x, int y, int z) {
        return leftD.evaluate(x,y,z) / rightD.evaluate(x,y,z);
    }
}