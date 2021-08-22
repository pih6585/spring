package spring.frontcontroller.v4.controller;

import java.util.List;
import java.util.Map;

import spring.domain.member.Member;
import spring.domain.member.MemberRepository;
import spring.frontcontroller.v4.ControllerV4;

public class MemberListControllerV4 implements ControllerV4 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public String process(Map<String, String> paramMap, Map<String, Object> model) {
		model.put("members", memberRepository.findAll());
		return "members";
	}
}
