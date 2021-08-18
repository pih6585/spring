package spring.frontcontroller.v2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spring.frontcontroller.MyView;

public interface ControllerV2 {

	MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
