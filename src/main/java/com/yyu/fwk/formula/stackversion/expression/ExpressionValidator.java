package com.yyu.fwk.formula.stackversion.expression;

public class ExpressionValidator {

	/*
	 * 1. 所有字面量必须使用单引号包裹，包括字符串和数字
	 * 2. 字面量中目前不支持有单引号的值，如果有程序会报错
	 * 3. 表达式中支持任意空格
	 * 4. 表达式不支持空括号: ()
	 * 5. 不支持(abc)
	 * 6. 不支持(*abc)
	 * 7. 不支持(abc*)
	 * 8. 不支持负数，负数需要 a * '-1'
	 */
}
