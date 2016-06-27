package com.bp.projects.technology.accountreconciliation.util;

import static com.bp.projects.technology.accountreconciliation.util.ExpressionConstants.ASSIGNMENT;
import static com.bp.projects.technology.accountreconciliation.util.ExpressionConstants.BEGIN_INDEX;
import static com.bp.projects.technology.accountreconciliation.util.ExpressionConstants.DELIMITER;
import static com.bp.projects.technology.accountreconciliation.util.ExpressionConstants.E;
import static com.bp.projects.technology.accountreconciliation.util.ExpressionConstants.PI;
import static net.sourceforge.jeval.EvaluationConstants.CLOSED_VARIABLE;
import static net.sourceforge.jeval.EvaluationConstants.OPEN_VARIABLE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 * Util class to help evalute the given expressions. This uses the jeval
 * Evaluator library
 * 
 * @author Kavitha
 * @see net.sourceforge.jeval.Evaluator
 *
 */
public class ExpressionUtils {

	/**
	 * Evaluates the given expression and returns a decimal value
	 * 
	 * @param amountExpr
	 * @return
	 * @throws EvaluationException
	 */
	public static BigDecimal resolveExpressionValue(String amountExpr)
			throws EvaluationException {
		Evaluator eval = new Evaluator();
		eval.putFunction(new BinaryLog());
		eval.putFunction(new Factorial());
		eval.putFunction(new Sqrtn());

		String result = null;

		Objects.requireNonNull(amountExpr);
		if (amountExpr.contains(PI) || amountExpr.contains(E)) {
			amountExpr = amountExpr.replaceAll(PI, OPEN_VARIABLE + PI
					+ CLOSED_VARIABLE);
			amountExpr = amountExpr.replaceAll(E, OPEN_VARIABLE + E
					+ CLOSED_VARIABLE);
		}
		if (amountExpr.contains(DELIMITER)) {
			String[] arr = amountExpr.split(DELIMITER);
			if (arr != null && arr.length > 0) {
				List<String> variableNames = new ArrayList<String>();
				for (String string : arr) {

					for (String variableName : variableNames) {
						string = string.replaceAll(variableName, OPEN_VARIABLE
								+ variableName + CLOSED_VARIABLE);
					}

					if (string.contains(ASSIGNMENT)) {
						int index = string.indexOf(ASSIGNMENT);
						String variableName = string.substring(BEGIN_INDEX,
								index).trim();
						String variableValueExpr = string.substring(index + 1)
								.trim();
						variableNames.add(variableName);
						final String variableValue = eval
								.evaluate(variableValueExpr);
						eval.putVariable(variableName, variableValue);
					} else {
						result = eval.evaluate(string);
					}
				}
				eval.clearVariables();
			}
		} else {
			result = eval.evaluate(amountExpr);
		}

		BigDecimal finalResult = new BigDecimal(result);
		return finalResult;
	}
}
