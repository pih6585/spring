package spring.frontcontroller.v3;

import java.util.Map;

import spring.frontcontroller.ModelView;

public interface ControllerV3 {

	ModelView process(Map<String,String> paramMap);

}
