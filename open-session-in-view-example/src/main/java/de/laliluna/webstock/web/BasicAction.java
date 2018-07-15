package de.laliluna.webstock.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hennebrueder
 *
 */
public interface BasicAction {
	public String  execute(HttpServletRequest request, HttpServletResponse response);
}
