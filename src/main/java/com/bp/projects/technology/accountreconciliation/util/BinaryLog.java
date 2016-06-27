package com.bp.projects.technology.accountreconciliation.util;

import java.math.BigDecimal;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

/**
 * Custom function to find the binary logarithm of a given number
 * 
 * @author Kavitha
 *
 */
public class BinaryLog implements Function {

	@Override
	public String getName() {
		return "log2";
	}

	@Override
	public FunctionResult execute(Evaluator evaluator, String arguments)
			throws FunctionException {
		Double result = null;
		Double number = null;

		try {
			number = new Double(arguments);
		} catch (Exception e) {
			throw new FunctionException("Invalid argument.", e);
		}

		double numeratorVal = Math.log(number.doubleValue());
		BigDecimal numerator = new BigDecimal(String.valueOf(numeratorVal));
		double denominatorVal = Math.log(2);
		BigDecimal denominator = new BigDecimal(String.valueOf(denominatorVal));
		
		result = new Double(numerator.divide(denominator).doubleValue());

		return new FunctionResult(result.toString(), 
				FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
	}

}
