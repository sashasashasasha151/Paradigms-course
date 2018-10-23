package expression.exceptions;

import expression.TripleExpression;
import expression.parser.ChangeMeException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws ChangeMeException;
}