package spring.frontcontroller.v3.controller;

import java.util.Map;

import spring.domain.member.Member;
import spring.domain.member.MemberRepository;
import spring.frontcontroller.ModelView;
import spring.frontcontroller.v3.ControllerV3;

public class MemberSaveControllerV3 implements ControllerV3 {

	private MemberRepository memberRepository = MemberRepository.getInstance();
	@Override
	public ModelView process(Map<String, String> paramMap) {
		String username = paramMap.get("username");
		int age = Integer.parseInt(paramMap.get("age"));

		Member member = new Member(username,age);
		memberRepository.save(member);

		ModelView mv = new ModelView("save-result");
		mv.getModel().put("member",member);
		return mv;
	}
}
