package com.hotpot.service;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hotpot.entity.QueueUp;

import java.io.IOException;
import java.net.Inet4Address;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zoupeng on 15/12/30.
 */

public final class QRCode {
    private static final String IMAGE_PATH = "qrcode/";

    public static String encode(QueueUp person){
        String format = "png";

        JSONObject json = new JSONObject();
        try{
            json.put("url", Inet4Address.getLocalHost().toString());
            json.put("position",person);
        }catch (Exception e){
            e.printStackTrace();
        }
        String content = json.toJSONString();
        int width = 200;
        int height = 200;
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,width,height,hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        String filename = person.getPhone() + "." + format;
        Path path = FileSystems.getDefault().getPath(IMAGE_PATH, filename);
        try {
            MatrixToImageWriter.writeToPath(bitMatrix,format,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IMAGE_PATH+filename;
    }

    public static void main(String[] args) {
        int width = 200;
        int height = 200;
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode("http://www.baidu.com", BarcodeFormat.QR_CODE,width,height,hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        String filename = "test.png";
        Path path = FileSystems.getDefault().getPath(IMAGE_PATH, filename);
        try {
            MatrixToImageWriter.writeToPath(bitMatrix,"png",path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
