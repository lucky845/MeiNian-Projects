package com.atguigu.test;

import com.atguigu.meinian.utils.QiniuUtils;

public class QiNiuUtilsTest {

    /**
     * 测试上传功能
     */
    //@Test
    public void test1() {
        QiniuUtils.upload2Qiniu("D:\\Administrator\\Pictures\\Saved Pictures\\QQ图片.gif", "gif.gif");
    }

    /**
     * 测试删除功能
     */
    //@Test
    public void test2() {
        QiniuUtils.deleteFileFromQiniu("gif.gif");
    }

}
