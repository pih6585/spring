package spring.frontcontroller.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spring.frontcontroller.MyView;
import spring.frontcontroller.v2.controller.MemberFormControllerV2;
import spring.frontcontroller.v2.controller.MemberListControllerV2;
import spring.frontcontroller.v2.controller.MemberSaveControllerV2;

@WebServlet(name = "FrontControllerV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerV2 extends HttpServlet {

	private Map<String, ControllerV2> controllerV2Map = new HashMap<>();

	public FrontControllerV2() {
		controllerV2Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
		controllerV2Map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
		controllerV2Map.put("/front-controller/v2/members", new MemberListControllerV2());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		String requestURI = request.getRequestURI();
		ControllerV2 controllerV2 = controllerV2Map.get(requestURI);

		if(controllerV2 == null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		MyView view = controllerV2.process(request,response);
		view.render(request,response);

	}
}
