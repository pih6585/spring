package spring.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spring.frontcontroller.ModelView;
import spring.frontcontroller.MyView;
import spring.frontcontroller.v3.controller.MemberFormControllerV3;
import spring.frontcontroller.v3.controller.MemberListControllerV3;
import spring.frontcontroller.v3.controller.MemberSaveControllerV3;
import spring.frontcontroller.v4.controller.MemberFormControllerV4;
import spring.frontcontroller.v4.controller.MemberListControllerV4;
import spring.frontcontroller.v4.controller.MemberSaveControllerV4;
import spring.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import spring.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

@WebServlet(name = "FrontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	private final Map<String, Object> handlerMappingMap = new HashMap<>();
	private final List<MyHandleAdapter> handlerAdapters = new ArrayList<>();

	public FrontControllerServletV5() {
		initHandlerMappingMap();
		initHandlerAdapters();
	}

	private void initHandlerMappingMap() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

		handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
	}

	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {

		Object handler = getHandler(request);

		if (handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MyHandleAdapter adapter = getHandlerAdapter(handler);

		ModelView mv = adapter.handle(request, response, handler);

		String viewName = mv.getViewName();
		MyView view = viewResolver(viewName);

		view.render(mv.getModel(), request, response);
	}

	private Object getHandler(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return handlerMappingMap.get(requestURI);
	}

	private MyHandleAdapter getHandlerAdapter(Object handler) {
		for (MyHandleAdapter handlerAdapter : handlerAdapters) {
			if (handlerAdapter.supports(handler)) {
				return handlerAdapter;
			}
		}
		throw new IllegalArgumentException("handler adapter 를 찾을 수 없습니다.");
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
