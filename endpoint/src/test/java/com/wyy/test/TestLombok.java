package com.wyy.test;

import com.wyy.endpoint.vo.Member;

public class TestLombok {
    public static void main(String[] args) {
        Member member = new Member();
        member.setMid("yootk");
        System.out.println(member.getMid());
        System.out.println("YOOTK-4".compareTo("YOOTK-5"));
    }
}
