package com.mdsd.library.manage.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class UploadFileManage {
    /**
     * 上传图片
     * @param uploadFile
     * @param serverUrl
     * @param hemoId
     * @return
     * @throws Exception
     */
    public static int UploadFile(File uploadFile, String serverUrl, String hemoId) throws Exception {
        int result=0;
        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识，随机生成

        String fileName = uploadFile.getName();
        StringBuilder sb = new StringBuilder();
        //带上参数,可添加多个参数
        sb.append("--" + BOUNDARY + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"hemoId\"" + "\r\n");
        sb.append("\r\n");
        sb.append(hemoId + "\r\n");

        sb.append("--" + BOUNDARY + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"" + fileName + "\"; filename=\"" + fileName + "\"" + "\r\n");
        sb.append("Content-Type: image/jpeg" + "\r\n");
        sb.append("\r\n");

        byte[] headerInfo = sb.toString().getBytes("UTF-8");
        byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
        System.out.println(sb.toString());
        URL url = new URL(serverUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        conn.setRequestProperty("Content-Length", String.valueOf(headerInfo.length + uploadFile.length() + endInfo.length));
        conn.setDoOutput(true);

        OutputStream out = conn.getOutputStream();
        InputStream in = new FileInputStream(uploadFile);
        out.write(headerInfo);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1)
            out.write(buf, 0, len);
        out.write(endInfo);
        in.close();
        out.close();
        if (conn.getResponseCode() == 200) {
            result=1;
        }
        return result;
    }
}
