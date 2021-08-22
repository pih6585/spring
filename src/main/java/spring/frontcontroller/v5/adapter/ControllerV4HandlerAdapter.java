package spring.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spring.frontcontroller.ModelView;
import spring.frontcontroller.MyView;
import spring.frontcontroller.v4.ControllerV4;
import spring.frontcontroller.v5.MyHandleAdapter;

public class ControllerV4HandlerAdapter implements MyHandleAdapter {
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof ControllerV4);
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		ServletException,
		IOException {
		ControllerV4 controller = (ControllerV4)handler;

		Map<String, String> paramMap = createParamMap(request);
		HashMap<String, Object> model = new HashMap<>();
		String viewName = controller.process(paramMap, model);
		ModelView mv = new ModelView(viewName);
		mv.setModel(model);
		return mv;
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
			.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
}
