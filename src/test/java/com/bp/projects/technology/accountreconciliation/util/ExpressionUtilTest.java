package com.bp.projects.technology.accountreconciliation.util;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import net.sourceforge.jeval.EvaluationException;

import org.junit.Test;

/**
 * Tests the evaluation of expressions
 * 
 * @author Kavitha
 *
 */
public class ExpressionUtilTest {

	@Test
	public void testBasicArithmetic() throws EvaluationException {
		String amountExpr = "20.23 + 23 * 25.5 / 30 - 5";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		BigDecimal expected = new BigDecimal("34.78");
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testBasicDivision() throws EvaluationException {
		String amountExpr = "2/7";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		BigDecimal expected = new BigDecimal("0.2857142857142857");
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testParanthesis() throws EvaluationException {
		String amountExpr = "(20.23 + 23) * 25.5";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		BigDecimal expected = new BigDecimal("1102.365");
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testFactorialFunction() throws EvaluationException {
		String amountExpr = "fact(5)";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		BigDecimal expected = new BigDecimal("120.0");
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testFunctions() throws EvaluationException {
		String amountExpr = "sqrt(log(fact(10)))";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		double expectedVal = Math.sqrt(Math.log(3628800));
		BigDecimal expected = new BigDecimal(String.valueOf(expectedVal));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testVariableExpressions() throws EvaluationException {
		String amountExpr = "v1= fact(10);v2=pow(log(v1),5);100*v2";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		double expectedVal = Math.pow(Math.log(3628800), 5) * 100;
		BigDecimal expected = new BigDecimal(String.valueOf(expectedVal));
		assertTrue(expected.equals(actual));
	}
	
	@Test(expected=EvaluationException.class)
	public void testInvalidExpression() throws EvaluationException {
		String amountExpr = "var1 = sqrt(25)";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
	}
	
	@Test
	public void testMixedExpression() throws EvaluationException {
		String amountExpr = "20.23 + 23 * sqrt(25.5)";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		double expectedVal = 20.23 + 23 * Math.sqrt(25.5);
		BigDecimal expected = new BigDecimal(String.valueOf(expectedVal));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testMoreFunctions() throws EvaluationException {
		String amountExpr = "sin(abs(min(-45.6, max(sqrtn(3,27), log2(32)))))";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		double expectedVal = Math.sin(Math.abs(Math.min(-45.6, Math.max(0.0, (Math.log(32)/Math.log(2))))));
		BigDecimal expected = new BigDecimal(String.valueOf(expectedVal));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testBuiltInConstants() throws EvaluationException {
		String amountExpr = "sin(PI * E) + log(E)";
		BigDecimal actual = ExpressionUtils.resolveExpressionValue(amountExpr);
		double expectedVal = Math.sin(Math.PI * Math.E) + 1;
		BigDecimal expected = new BigDecimal(String.valueOf(expectedVal));
		assertTrue(expected.equals(actual));
	}
}
