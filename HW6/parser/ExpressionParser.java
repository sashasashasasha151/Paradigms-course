package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {

    private int index = 0;
    private String input;

    public TripleExpression parse(String expression) {
        input = expression;
        index = 0;
        return makeResult(Parse());
    }

    private TripleExpression makeResult(Result currentExpression) {
        switch (currentExpression.args.size()) {
            case 2: {
                TripleExpression a = makeResult(currentExpression.args.get(0));
                TripleExpression b = makeResult(currentExpression.args.get(1));
                if (currentExpression.expression == "+") return new Add(a, b);
                if (currentExpression.expression == "-") return new Subtract(a, b);
                if (currentExpression.expression == "*") return new Multiply(a, b);
                if (currentExpression.expression == "/") return new Divide(a, b);
                if (currentExpression.expression == "<<") return new ShiftLeft(a, b);
                if (currentExpression.expression == ">>") return new ShiftRight(a, b);
            }
            case 1: {
                TripleExpression a = makeResult(currentExpression.args.get(0));
                if (currentExpression.expression == "-") return new uMin(a);
                if (currentExpression.expression == "abs") return new Abs(a);
                if (currentExpression.expression == "square") return new Square(a);
            }
            case 0: {
                if (Character.isDigit(currentExpression.expression.charAt(0))) {
                    if(currentExpression.expression.equals("2147483648")) {
                        return new Const(Integer.parseInt("-2147483648"));
                    } else {
                        return new Const(Integer.parseInt(currentExpression.expression));
                    }
                } else {
                    return new Variable(currentExpression.expression.charAt(0));
                }
            }
        }
        return new Const(Integer.parseInt(currentExpression.expression));
    }

    private Result Parse() {
        return binaryParser(0);
    }

    private Result binaryParser(int currentPriority) {
        Result left = expressionsParser();

        while (true) {
            String op = charParser();
            int priority = get_priority(op);
            if (priority <= currentPriority) {
                index -= op.length();
                return left;
            }

            Result right = binaryParser(priority);
            left = new Result(op, left, right);
        }
    }

    private Result expressionsParser() {
        String currentChar = charParser();

        if (currentChar == "(") {
            Result result = Parse();
            index++;
            return result;
        }

        if (Character.isDigit(currentChar.charAt(0)) || currentChar.charAt(0) == 'x' ||
                currentChar.charAt(0) == 'y' || currentChar.charAt(0) == 'z')
            return new Result(currentChar);

        return new Result(currentChar, expressionsParser());
    }

    private String charParser() {
        while (index<input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }

        if (index == input.length()) return "";

        if (Character.isDigit(input.charAt(index))) {
            String number = "";
            while (index < input.length() && Character.isDigit(input.charAt(index)))
                number = number + input.charAt(index++);
            return number;
        }

        String[] operation = {"+", "-", "*", "/", "(", ")", "x", "y", "z", ">>", "<<", "abs", "square"};
        for (int j = 0; j < 13; j++) {
            if (operation[j].charAt(0) == input.charAt(index)) {
                index += operation[j].length();
                return operation[j];
            }
        }
        return "";
    }


    private int get_priority(String operation) {
        String tmp = "<<";
        if (operation == tmp) return 1;
        if (operation == ">>") return 1;
        if (operation == "+") return 2;
        if (operation == "-") return 2;
        if (operation == "*") return 3;
        if (operation == "/") return 3;
        return 0;
    }
}
