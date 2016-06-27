package com.bp.projects.technology.accountreconciliation.util;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

/**
 * Custom function to find the factorial of a given number
 * 
 * @author Kavitha
 *
 */
public class Factorial implements Function {

	@Override
	public String getName() {
		return "fact";
	}

	@Override
	public FunctionResult execute(Evaluator evaluator, String arguments)
			throws FunctionException {
		Double result = null;
		int number = 0;
		try {
			Double argument = new Double(arguments);
			number = argument.intValue();
		} catch (Exception e) {
			throw new FunctionException("Invalid argument.", e);
		}
		result = new Double(String.valueOf(fact(number)));

		return new FunctionResult(result.toString(),
				FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
	}

	private int fact(int number) {
		if(number < 0) {
			return 0;
		}
		if(number == 0 || number ==1) {
			return 1;
		}
		return number * fact(number-1);
	}
}
