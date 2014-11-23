package com.losy.codegen.main;

import com.losy.codegen.utils.TemplateUtils;

import freemarker.template.Template;

public class Test {
	public static void main(String[] args) {
		TemplateUtils.initTempConfig("com\\losy\\codegen\\template");
		Template t = TemplateUtils.getTemplate("controller.ftl");
		System.out.println(t);
	}
}
