/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
(function ($) {
	$.extend($.validator.messages, {
		required: "必填",
		remote: "请修正该字段",
		email: "格式错误",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "必须为正整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: $.validator.format("最多 {0}个字符"),
		minlength: $.validator.format("至少{0}个字符"),
		rangelength: $.validator.format("最少 {0}字符，最多 {1}字符"),
		range: $.validator.format("请输入一个介于 {0}和 {1}之间的值"),
		max: $.validator.format("请输入一个最大为 {0}的值"),
		min: $.validator.format("请输入一个最小为 {0}的值")
	});
}(jQuery));