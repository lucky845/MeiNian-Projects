package com.atguigu.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/* 七牛云文件上传测试类
 * Zone.zone0:华东
 * Zone.zone1:华北
 * Zone.zone2:华南
 * 自动识别上传区域
 * Zone.autoZone
 */
public class TestQiNiu {

    /**
     * 上传本地文件
     */
    //@Test
    public void uploadFile() {

        // 构建一个带有指定Zone对象的配制类
        Configuration cfg = new Configuration(Zone.zone2()); // 华南
        //
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证
        String accessKey = "18WlNj3Lpm7mJ9H1xTuYj0WcUlRoLpOgBktiZmKc";
        String secretKey = "9FYwPkid-cI1XQNKnG82g3HT1aro-sW96PgJU-KS";
        String bucket = "lucky845";
        // 设置需要上传的文件的路径
        String localFilePath = "D:\\Administrator\\Pictures\\Saved Pictures\\QQ图片.gif";
        // 默认不指定key的情况下,以文件的内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            // 解析上传的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key); // Fmw9iUWZL0fN32E3z0ZtAxzY6eBZ
            System.out.println(putRet.hash); // Fmw9iUWZL0fN32E3z0ZtAxzY6eBZ
        } catch (QiniuException e) {
            Response r = e.response;
            System.out.println("r = " + r);
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException e2) {
                e2.printStackTrace();
            }
        }

    }

    // 删除空间中的文件
    //@Test
    public void deleteFile() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "18WlNj3Lpm7mJ9H1xTuYj0WcUlRoLpOgBktiZmKc";
        String secretKey = "9FYwPkid-cI1XQNKnG82g3HT1aro-sW96PgJU-KS";
        String bucket = "lucky845"; //空间名称
        String key = "Fmw9iUWZL0fN32E3z0ZtAxzY6eBZ"; //文件名称
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
