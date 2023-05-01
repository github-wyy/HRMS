package com.wyy.test;

import com.wyy.jwt.StartJWTConfiguration;
import com.wyy.jwt.util.JWTMemberDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = StartJWTConfiguration.class)
public class TestJWTMemberDataService {
    private String token = "eyJhdXRob3IiOiLmnY7lhbTljY4iLCJtb2R1bGUiOiJKV1QtVEVTVCIsImFsZyI6IkhTMjU2In0.eyJtc2ciOiLkuJbnlYzkuIrniIblj6_niLHnmoTogIHluIgg4oCU4oCUIOeIhuWPr-eIseeahOWwj-adjuiAgeW4iCIsInN1YiI6IntcImZsYWdcIjpcIjFcIixcInJvbGVzXCI6W1widXNlclwiLFwiZW1wXCIsXCJyYXRpbmdcIixcImRlcHRcIl0sXCJuYW1lXCI6XCLmspDoqIDnp5HmioAg4oCU4oCUIOadjuWFtOWNjlwiLFwibWlkXCI6XCJtdXlhblwiLFwiYWN0aW9uc1wiOltcImRlcHQ6bGlzdFwiLFwidXNlcjphZGRcIixcImVtcDphZGRcIixcInJhdGluZzpsaXN0XCJdfSIsInNpdGUiOiJ3d3cueW9vdGsuY29tL3Jlc291cmNlcyIsImlzcyI6Ik11eWFuWW9vdGsiLCJleHAiOjE2NDc1MTg4NTQsImlhdCI6MTY0NzQxODg1NCwibmljZSI6Ikdvb2QgR29vZCBHb29kIiwianRpIjoieW9vdGstNDdmZWJlYTItNWEwMi00YzE2LThlZWMtYWVjMmU1ZWFkMjMwIn0.kk508jT6JaYtndT-uiIUUyQnsFmJ7KMkIeB4mFeL1vI";
    @Autowired
    private JWTMemberDataService memberDataService;
    @Test
    public void testParseContent() {
        System.out.println(this.memberDataService.actions(token));
        System.out.println(this.memberDataService.roles(token));
        System.out.println(this.memberDataService.id(token));
        System.out.println(this.memberDataService.adminFlag(token));
        System.out.println(this.memberDataService.mid(token));
        System.out.println(this.memberDataService.name(token));
    }
}
