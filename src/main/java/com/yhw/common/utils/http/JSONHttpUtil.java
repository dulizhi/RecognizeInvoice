package com.yhw.common.utils.http;

import com.yhw.common.utils.Base64;
import com.yhw.common.utils.ByteUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhang.jiali4
 * @title JSONHttpUtil
 * @description
 * @date 2019/10/18
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class JSONHttpUtil {
    public static String PostRequest(String adress_Http, String strJson) {
        String returnLine = "";
        InputStreamReader in=null;
        BufferedReader reader=null;
        InputStream conInputStream=null;
        HttpURLConnection connection=null;
        OutputStream outputStream=null;
        DataOutputStream out = null;
        try {
            URL my_url = new URL(adress_Http);
            connection = (HttpURLConnection) my_url.openConnection();
            connection.setDoOutput(true);

            connection.setDoInput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(90000);
            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type", "application/json");

            connection.connect();
            outputStream=connection.getOutputStream();
            out = new DataOutputStream(outputStream);

            byte[] content = strJson.getBytes("utf-8");

            out.write(content, 0, content.length);
            out.flush();

            conInputStream = connection.getInputStream();
            in=new InputStreamReader(conInputStream, "utf-8");
            reader = new BufferedReader(in);

            String line = "";

            while ((line = reader.readLine()) != null) {
                returnLine += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(in!=null){
                    in.close();
                }
                if(reader!=null){
                    reader.close();
                }
                if(conInputStream!=null) {
                    conInputStream.close();
                }
                if(outputStream!=null) {
                    outputStream.close();
                }
                if(out!=null) {
                    out.close();
                }
                if(connection!=null){
                    connection.disconnect();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return returnLine;
    }

    public static String PostAuthRequest(String adress_Http,String userName,String userPass, String strJson) {
        String returnLine = "";
        InputStreamReader in=null;
        BufferedReader reader=null;
        InputStream conInputStream=null;
        HttpURLConnection connection=null;
        OutputStream outputStream=null;
        DataOutputStream out = null;

        System.out.println("strJson:" + strJson);
        try {
            URL my_url = new URL(adress_Http);
            connection = (HttpURLConnection) my_url.openConnection();
            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type", "application/json");

//            byte[] tokenByte = com.google.api.client.util.Base64.encodeBase64((userName + ":" + userPass).getBytes());
            byte[] tokenByte = Base64.encode((userName + ":" + userPass).getBytes()).getBytes();
            String tokenStr = new String(ByteUtil.subBytes(tokenByte, 0, tokenByte.length));
            String token = tokenStr;
            connection.setRequestProperty("Authorization", "Basic "+token);

            connection.connect();
            outputStream=connection.getOutputStream();
            out = new DataOutputStream(outputStream);

            byte[] content = strJson.getBytes("utf-8");

            out.write(content, 0, content.length);
            out.flush();
            conInputStream = connection.getInputStream();
            in=new InputStreamReader(conInputStream, "utf-8");
            reader = new BufferedReader(in);

            String line = "";

            while ((line = reader.readLine()) != null) {
                returnLine += line;
            }
            System.out.println("result:" + returnLine);
        } catch (Exception e) {
            //e.printStackTrace();
        }finally{
            try{
                if(in!=null){
                    in.close();
                }
                if(reader!=null){
                    reader.close();
                }
                if(conInputStream!=null) {
                    conInputStream.close();
                }
                if(outputStream!=null) {
                    outputStream.close();
                }
                if(out!=null) {
                    out.close();
                }
                if(connection!=null){
                    connection.disconnect();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return returnLine;
    }

    public static String PostCcrmRequest(String adress_Http, String strJson) {
        String returnLine = "";
        InputStreamReader in=null;
        BufferedReader reader=null;
        InputStream conInputStream=null;
        HttpURLConnection connection=null;
        OutputStream outputStream=null;
        DataOutputStream out = null;
        try {
            URL my_url = new URL(adress_Http);
            connection = (HttpURLConnection) my_url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
            connection.connect();
            outputStream=connection.getOutputStream();
            out = new DataOutputStream(outputStream);

            byte[] content = strJson.getBytes("utf-8");

            out.write(content, 0, content.length);
            out.flush();

            conInputStream = connection.getInputStream();
            in=new InputStreamReader(conInputStream, "utf-8");
            reader = new BufferedReader(in);

            String line = "";

            while ((line = reader.readLine()) != null) {
                returnLine += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(in!=null){
                    in.close();
                }
                if(reader!=null){
                    reader.close();
                }
                if(conInputStream!=null) {
                    conInputStream.close();
                }
                if(outputStream!=null) {
                    outputStream.close();
                }
                if(out!=null) {
                    out.close();
                }
                if(connection!=null){
                    connection.disconnect();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return returnLine;
    }


}

