<%@ page language="java" contentType="text/css; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ page import="hr.fer.oprpp2.hw04.Kljucevi"%>
body {
  background-color: <%=session.getAttribute(Kljucevi.KEY_USER_COLOR)== null ? Kljucevi.DEFAULT_COLOR : session.getAttribute(Kljucevi.KEY_USER_COLOR)%>;
}