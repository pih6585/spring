package spring.frontcontroller.v3.controller;

import java.util.List;
import java.util.Map;

import spring.domain.member.Member;
import spring.domain.member.MemberRepository;
import spring.frontcontroller.ModelView;
import spring.frontcontroller.v3.ControllerV3;

public class MemberListControllerV3 implements ControllerV3 {
	private MemberRepository memberRepository = MemberRepository.getInstance();
	@Override
	public ModelView process(Map<String, String> paramMap) {
		List<Member> members = memberRepository.findAll();
		ModelView mv = new ModelView("members");
		mv.getModel().put("member",members);
		return mv;
	}
}
