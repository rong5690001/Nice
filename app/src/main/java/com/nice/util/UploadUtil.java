package com.nice.util;

import android.content.Context;
import android.util.Log;

import com.alexbbb.uploadservice.BinaryUploadRequest;
import com.alexbbb.uploadservice.MultipartUploadRequest;
import com.alexbbb.uploadservice.UploadNotificationConfig;

import java.util.UUID;

/**
 * 上传文件工具类
 * Created by chen on 2016/1/11.
 */
public class UploadUtil {


    /**
     * "/absolute/path/to/your/file"
     * @param context
     */
    public void uploadMultipart(final Context context, String fileUrl) {

        final String uploadID = UUID.randomUUID().toString();
        try {
            new MultipartUploadRequest(context, uploadID, NiceApiUtil.IMG_SERVER_URL)
                    .addFileToUpload(fileUrl, "your-param-name")
                    .addHeader("your-custom-header-name", "your-custom-value")//TODO
                    .addParameter("your-param-name", "your-param-value")//TODO
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }

    /**
     * 二进制上传
     * @param context
     */
    public void uploadBinary(final Context context, String fileUrl) {

        final String uploadID = UUID.randomUUID().toString();

        try {
            new BinaryUploadRequest(context, uploadID, NiceApiUtil.IMG_SERVER_URL)
                    .addHeader("your-custom-header-name", "your-custom-value")//TODO
                    .setFileToUpload(fileUrl)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }
}
