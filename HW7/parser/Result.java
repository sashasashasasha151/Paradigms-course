package expression.parser;

import java.util.Vector;

public class Result {

    public Result(String expression) {
        this.expression = expression;
    }

    public Result(String expression, Result a) {
        this.expression = expression;
        args.addElement(a);
    }

    public Result(String expression, Result a, Result b) {
        this.expression = expression;
        args.addElement(a);
        args.addElement(b);
    }

    public String expression;
    Vector<Result> args = new Vector();
}