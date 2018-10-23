package expression.parser;

import expression.*;
import expression.exceptions.Parser;

public class ExpressionParser implements Parser {

    private int index = 0;
    private String input;

    public TripleExpression parse(String expression) throws ChangeMeException {
        input = expression;
        index = 0;

        int back = expression.length() - 1;
        while (Character.isWhitespace(expression.charAt(back)) && back >= 0) {
            back--;
        }
        if (expression.charAt(back) != 'x' && expression.charAt(back) != 'z' && expression.charAt(back) != 'y' &&
                expression.charAt(back) != ')' && !Character.isDigit(expression.charAt(back))) {
            throw new ChangeMeException(input + " >No argument here or wrong symbol(" + expression.charAt(back) + ")<\n");
        }

        int depth = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                depth++;
            } else {
                if (expression.charAt(i) == ')') {
                    depth--;
                }
            }
            if (depth < 0) {
                StringBuilder answer = new StringBuilder(5);
                for (int w = 0; w < i + 1; w++) {
                    answer.append(input.charAt(w));
                }
                answer.append("<-No opening parenthesis for this closing parenthesis\n");
                throw new ChangeMeException(answer.toString());
            }
        }
        if (depth != 0) {
            throw new ChangeMeException(input + " - Not enough closing parenthesis for this expression\n");
        }

        if (expression.charAt(index) == '*' || expression.charAt(index) == '/' || expression.charAt(index) == '+' || expression.charAt(index) == 'm') {
            StringBuilder answer = new StringBuilder(5);
            for (int w = 0; w < index; w++) {
                answer.append(input.charAt(w));
            }
            answer.append(" >No argument here< ");
            for (int w = index; w < input.length(); w++) {
                answer.append(input.charAt(w));
            }
            answer.append("\n");
            throw new ChangeMeException(answer.toString());
        }
        return makeResult(Parse());
    }

    private TripleExpression makeResult(Result currentExpression) throws ChangeMeException {
        switch (currentExpression.args.size()) {
            case 2: {
                TripleExpression a = makeResult(currentExpression.args.get(0));
                TripleExpression b = makeResult(currentExpression.args.get(1));
                if (currentExpression.expression.equals("min")) return new CheckedMin(a, b);
                if (currentExpression.expression.equals("max")) return new CheckedMax(a, b);
                if (currentExpression.expression.equals("+")) return new CheckedAdd(a, b);
                if (currentExpression.expression.equals("-")) return new CheckedSubtract(a, b);
                if (currentExpression.expression.equals("*")) return new CheckedMultiply(a, b);
                if (currentExpression.expression.equals("/")) return new CheckedDivide(a, b);
            }
            case 1: {
                if (currentExpression.args.get(0).expression.equals("2147483648") && currentExpression.expression.equals("-")) {
                    return new CheckedConst(Integer.parseInt("-2147483648"));
                }
                TripleExpression a = makeResult(currentExpression.args.get(0));
                if (currentExpression.expression.equals("-")) return new CheckedNegate(a);
                if (currentExpression.expression.equals("abs")) return new CheckedAbs(a);
                if (currentExpression.expression.equals("sqrt")) return new CheckedSqrt(a);
            }
            case 0: {
                String over = "2147483647";
                if (Character.isDigit(currentExpression.expression.charAt(0))) {
                    if (currentExpression.expression.length() > 10) {
                        throw new ChangeMeException("Overflow exception\n");
                    } else {
                        if (currentExpression.expression.length() == 10) {
                            for (int e = 0; e < 10; e++) {
                                if (Character.getNumericValue(currentExpression.expression.charAt(e)) < Character.getNumericValue(over.charAt(e))) {
                                    break;
                                } else {
                                    if (Character.getNumericValue(currentExpression.expression.charAt(e)) > Character.getNumericValue(over.charAt(e))) {
                                        throw new ChangeMeException("Overflow exception for input - " + currentExpression.expression + "\n");
                                    }
                                }
                            }
                        } else {
                            return new CheckedConst(Integer.parseInt(currentExpression.expression));
                        }
                    }
                } else {
                    return new Variable(currentExpression.expression);
                }
            }
        }
        return new CheckedConst(Integer.parseInt(currentExpression.expression));
    }

    private Result Parse() throws ChangeMeException {
        return binaryParser(0);
    }

    private Result binaryParser(int currentPriority) throws ChangeMeException {
        Result left = expressionsParser();

        while (true) {
            String op = charParser();
            int priority = getPriority(op);
            if (priority <= currentPriority) {
                index -= op.length();
                return left;
            }
            Result right = binaryParser(priority);
            left = new Result(op, left, right);
        }
    }

    private Result expressionsParser() throws ChangeMeException {
        String currentChar = charParser();

        if (currentChar.equals("(")) {
            Result result = Parse();
            index++;
            return result;
        }

        if (Character.isDigit(currentChar.charAt(0)) || currentChar.charAt(0) == 'x' ||
                currentChar.charAt(0) == 'y' || currentChar.charAt(0) == 'z')
            return new Result(currentChar);

        return new Result(currentChar, expressionsParser());
    }

    private String charParser() throws ChangeMeException {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }

        if (index == input.length()) return "";

        if (Character.isDigit(input.charAt(index))) {
            String number = "";
            while (index < input.length() && Character.isDigit(input.charAt(index)))
                number = number + input.charAt(index++);
            return number;
        }

        String[] operation = {"+", "/", "*", "abs", "sqrt", ")", "(", "-", "y", "z", "x", "min", "max"};
        int cur = index;
        for (int j = 0; j < operation.length; j++) {
            int operationLength = 0, currentIndex = index;
            for (int n = 0; n < operation[j].length(); n++) {
                if (operation[j].charAt(n) == input.charAt(currentIndex)) {
                    operationLength++;
                    currentIndex++;
                }
            }
            cur = operationLength;

            if (input.charAt(index) == 'a' && (index + 3) >= input.length()) {
                throw new ChangeMeException(input + " >No argument here< \n");
            }

            if (input.charAt(index) == 'a' && (input.charAt(index + 3) == 'x' || input.charAt(index + 3) == 'y' ||
                    input.charAt(index + 3) == 'z' || Character.isDigit(input.charAt(index + 3)))) {
                StringBuilder answer = new StringBuilder(5);
                for (int w = 0; w < index + 3; w++) {
                    answer.append(input.charAt(w));
                }
                answer.append(" >No space between operation and argument here< ");
                for (int w = index + 3; w < input.length(); w++) {
                    answer.append(input.charAt(w));
                }
                answer.append("\n");
                throw new ChangeMeException(answer.toString());
            }

            if (input.charAt(index) == 'm' && (index + 3) >= input.length()) {
                throw new ChangeMeException(input + " >No argument here< \n");
            }

            if (input.charAt(index) == 'm' && (index + 3) < input.length() && (input.charAt(index + 3) == 'x' || input.charAt(index + 3) == 'y' ||
                    input.charAt(index + 3) == 'z' || Character.isDigit(input.charAt(index + 3)))) {
                StringBuilder answer = new StringBuilder(5);
                for (int w = 0; w < index + 3; w++) {
                    answer.append(input.charAt(w));
                }
                answer.append(" >No space between operation and argument< ");
                for (int w = index + 3; w < input.length(); w++) {
                    answer.append(input.charAt(w));
                }
                throw new ChangeMeException(answer.toString()+"\n");
            }

            if (operation[j].length() == operationLength) {
                int checkingIndex = index + operation[j].length();
                while (checkingIndex < input.length()) {
                    if (Character.isWhitespace(input.charAt(checkingIndex))) {
                        checkingIndex++;
                        continue;
                    }
                    if ((input.charAt(index) == 'x' || input.charAt(index) == 'z' || input.charAt(index) == 'y') &&
                            ((input.charAt(checkingIndex) == '(' || input.charAt(checkingIndex) == 'a' || input.charAt(checkingIndex) == 's' || input.charAt(checkingIndex) == 'x' ||
                                    input.charAt(checkingIndex) == 'z' || input.charAt(checkingIndex) == 'y' || Character.isDigit(input.charAt(checkingIndex))))) {
                        StringBuilder answer = new StringBuilder(5);
                        for (int w = 0; w < checkingIndex; w++) {
                            answer.append(input.charAt(w));
                        }
                        answer.append(" >No operation here< ");
                        for (int w = checkingIndex; w < input.length(); w++) {
                            answer.append(input.charAt(w));
                        }
                        throw new ChangeMeException(answer.toString()+"\n");
                    }
                    if ((input.charAt(index) == 's' || input.charAt(index) == 'a' || input.charAt(index) == '*' || input.charAt(index) == '+' || input.charAt(index) == '-' ||
                            input.charAt(index) == '/' || input.charAt(index) == 'm') && (input.charAt(checkingIndex) == '*' || input.charAt(checkingIndex) == '+' ||
                            input.charAt(checkingIndex) == '/' || input.charAt(checkingIndex) == ')' || input.charAt(checkingIndex) == 'm' || input.charAt(checkingIndex) == '/')) {
                        StringBuilder answer = new StringBuilder(5);
                        for (int w = 0; w < checkingIndex; w++) {
                            answer.append(input.charAt(w));
                        }
                        answer.append(" >No argument here< ");
                        for (int w = checkingIndex; w < input.length(); w++) {
                            answer.append(input.charAt(w));
                        }
                        throw new ChangeMeException(answer.toString()+"\n");
                    }
                    if (input.charAt(index) == ')' && (input.charAt(checkingIndex) == '(' || input.charAt(checkingIndex) == 'x' ||
                            input.charAt(checkingIndex) == 'y' || input.charAt(checkingIndex) == 'z' || Character.isDigit(input.charAt(checkingIndex)))) {
                        StringBuilder answer = new StringBuilder(5);
                        for (int w = 0; w < checkingIndex; w++) {
                            answer.append(input.charAt(w));
                        }
                        answer.append(" >No argument here< ");
                        for (int w = checkingIndex; w < input.length(); w++) {
                            answer.append(input.charAt(w));
                        }
                        throw new ChangeMeException(answer.toString()+"\n");
                    }
                    if (input.charAt(index) == '(' && (input.charAt(checkingIndex) == ')' || input.charAt(checkingIndex) == '+' ||
                            input.charAt(checkingIndex) == '*' || input.charAt(checkingIndex) == '/' || input.charAt(checkingIndex) == 'm')) {
                        StringBuilder answer = new StringBuilder(5);
                        for (int w = 0; w < checkingIndex; w++) {
                            answer.append(input.charAt(w));
                        }
                        answer.append(" >No argument here< ");
                        for (int w = checkingIndex; w < input.length(); w++) {
                            answer.append(input.charAt(w));
                        }
                        throw new ChangeMeException(answer.toString()+"\n");
                    }
                    break;
                }
                index += operation[j].length();
                return operation[j];
            }
        }
        StringBuilder answer = new StringBuilder("Wrong symbol(");
        for (int w = index + cur; w < index + cur + 1; w++) {
            answer.append(input.charAt(w));
        }
        throw new ChangeMeException(answer.toString() + ") or wrong operation in expression - " + input + "\n");
    }


    private int getPriority(String operation) {
        if (operation.equals("min")) return 1;
        if (operation.equals("max")) return 1;
        if (operation.equals("+")) return 2;
        if (operation.equals("-")) return 2;
        if (operation.equals("*")) return 3;
        if (operation.equals("/")) return 3;
        return 0;
    }
}
