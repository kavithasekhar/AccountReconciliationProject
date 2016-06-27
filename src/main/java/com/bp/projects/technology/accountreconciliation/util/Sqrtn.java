package com.bp.projects.technology.accountreconciliation.util;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

/**
 * Custom function to find the nth root of a given number (sqrtn(3,27) = 3 or
 * pow(27,1/3), sqrtn(3,-27) results in imaginary values). This function just returns as
 * 0.0 as per requirement
 * 
 * @author Kavitha
 *
 */
public class Sqrtn implements Function {

	@Override
	public String getName() {
		return "sqrtn";
	}

	@Override
	public FunctionResult execute(Evaluator evaluator, String arguments)
			throws FunctionException {
		Double result = new Double("0.0");

		return new FunctionResult(result.toString(),
				FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
	}

}
