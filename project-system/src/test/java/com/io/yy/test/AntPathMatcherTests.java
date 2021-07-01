package com.io.yy.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author kris
 * @ClassName AntPathMatcherTests
 * @Description TODO
 * @date 10/21
 */
public class AntPathMatcherTests {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();


    @Test
    void match() {
        // test exact matching
        assertThat(pathMatcher.match("test", "test")).isTrue();

//        assertThat(pathMatcher.match("*/*", "test/test/test")).isTrue();
        System.out.println(pathMatcher.match("/**/test", "/bla/bla/test"));
        System.out.println(pathMatcher.match("**/get**", "prod-api/homework/getHwSubjectTypeList"));
        System.out.println(pathMatcher.match("**/get**", "prod-api/homework/555/getHwSubjectTypeList"));
        System.out.println(pathMatcher.match("**/check**", "prod-api/wx/checkLoginQr/IM9708J7T5"));
        System.out.println(pathMatcher.match("/verificationCode/getBase64Image", "/verificationCode/getBase64Image"));




        assertThat(pathMatcher.match("/**/test", "/bla/bla/test")).isTrue();

        assertThat(pathMatcher.match("/**/get**", "/homework/getHwSubjectTypeList")).isTrue();

        assertThat(pathMatcher.match("/**/get**", "/homework/555/getHwSubjectTypeList")).isTrue();

        assertThat(pathMatcher.match("/**/find**", "/sysMsg/findMsgRecordCount")).isTrue();

        assertThat(pathMatcher.match("/**/get**/**", "/hwStuHomework/getTobeStuHomeworkListOne/1317739513367351298")).isTrue();



    }
}
