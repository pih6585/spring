package spring.frontcontroller.v4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spring.frontcontroller.ModelView;
import spring.frontcontroller.MyView;
import spring.frontcontroller.v3.ControllerV3;
import spring.frontcontroller.v4.controller.MemberFormControllerV4;
import spring.frontcontroller.v4.controller.MemberListControllerV4;
import spring.frontcontroller.v4.controller.MemberSaveControllerV4;

@WebServlet(name = "FrontControllerV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerV4 extends HttpServlet {

	private Map<String, ControllerV4> controllerV4Map = new HashMap<>();

	public FrontControllerV4() {
		controllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
		controllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
		controllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		String requestURI = request.getRequestURI();
		ControllerV4 controller = controllerV4Map.get(requestURI);

		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new HashMap<>();

		String viewName = controller.process(paramMap, model);
		MyView view = viewResolver(viewName);

		view.render(model, request, response);
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
			.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
}
