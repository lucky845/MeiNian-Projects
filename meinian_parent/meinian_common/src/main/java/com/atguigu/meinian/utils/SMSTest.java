package com.atguigu.meinian.utils;

import org.junit.Test;

public class SMSTest {

    @Test
    public void test() throws Exception {
        SMSUtils.sendShortMessage("17683805053",ValidateCodeUtils.generateValidateCode4String(6));
    }

}
