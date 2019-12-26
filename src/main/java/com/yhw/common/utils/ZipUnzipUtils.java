package com.yhw.common.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author zhang.jiali4
 * @title ZipUnzipUtils
 * @description
 * @date 2019/11/11
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class ZipUnzipUtils {
    /**
     * 一个压缩包中包含多个文件，
     * @return 文件的byte[]
     */
    public static List<byte[]> decompressFiles(InputStream in) {
        List<byte[]> results = new ArrayList<>();
        ZipInputStream Zin= null;
        BufferedInputStream Bin= null;
        ByteArrayOutputStream bos = null;
        try {
            Zin=new ZipInputStream(in);
            Bin=new BufferedInputStream(Zin);
            ZipEntry entry;
            while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){
                bos = new ByteArrayOutputStream(0xffff);
                int b;
                while((b=Bin.read())!=-1){
                    bos.write(b);
                }
                if(bos != null) {
                    results.add(bos.toByteArray());
                }
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != Bin){
                try {
                    Bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != Zin){
                try {
                    Zin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

    public static void addInputstreamToZip(ZipOutputStream zipOutputStream, InputStream is, String fileName){
        try {
            zipOutputStream.putNextEntry(new ZipEntry(fileName));
            byte[] bytes = new byte[1024];
            int n = 0;
            while ((n = is.read(bytes)) != -1) {
                zipOutputStream.write(bytes, 0, n);
                zipOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
