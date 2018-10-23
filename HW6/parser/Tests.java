package expression.parser;

public class Tests {
    public static void main(String[] args) {
        System.out.println(new ExpressionParser().parse("1").evaluate(1,1,1));
    }
}
