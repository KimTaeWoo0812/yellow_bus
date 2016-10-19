package com.javalec.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	abstract int execute(HttpServletRequest request, HttpServletResponse response, int CASE);
	
}
