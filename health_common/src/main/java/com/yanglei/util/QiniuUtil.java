package com.yanglei.util;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuUtil {
    //七牛给你分配账号密码
    static String accessKey = "oJyRdThYFKs4Eb6ocPZGr1M2yU4xtE4OYFHiDgF8";
    static String secretKey = "KXZLpPITxdmp49kHG9hV4Eg-k_zN22cj1sniyNvS";
    //自己创建的存储空间名称
    static String bucket = "heimahealth01";

    /**
     * 文件上传
     * @param data 文件数据
     * @param fileName 文件名称
     */
    public static void upload(byte[] data, String fileName){
        Configuration cfg = new Configuration(Region.huadong());
        UploadManager uploadManager = new UploadManager(cfg);

        //文件名(默认不指定key的情况下，以文件内容的hash值作为文件名)
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(data,fileName,upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 删除文件
     * @param fileName 要删除的文件名
     */
    public static void delete(String fileName){
        Configuration cfg = new Configuration(Region.huadong());

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response res = bucketManager.delete(bucket, fileName);
            System.out.println(JSON.toJSONString(res));
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
