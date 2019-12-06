package org.csource;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestClient {

    //上传文件
    @Test
    public void testUpload() {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);
            //创建客户端
            TrackerClient tracker = new TrackerClient();
            //连接tracker Server
            TrackerServer trackerServer = tracker.getConnection();
            if (trackerServer == null) {
                System.out.println("getConnection return null");
                return;
            }
            //获取一个storage server
            StorageServer storageServer = tracker.getStoreStorage(trackerServer);
            if (storageServer == null) {
                System.out.println("getStoreStorage return null");
            }
            //创建一个storage存储客户端
            StorageClient1 sc1 = new StorageClient1(trackerServer, storageServer);
            NameValuePair[] meta_list = null; //new NameValuePair[0];
            String item = "C:\\Users\\tianx\\Desktop\\1.jpg";
            String fileid;
            fileid = sc1.upload_file1(item, "png", meta_list);
            System.out.println("Upload local file " + item + " ok, fileid=" + fileid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //查询文件
    @Test
    public void testQueryFile() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        FileInfo fileInfo = storageClient.query_file_info("group1",
                "M00/00/00/rBNqfF3o4lmAJFmtAAJHPZ4PS4A950.png");
        System.out.println(fileInfo);
    }

    //下载文件
    @Test
    public void testDownloadFile() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
        byte[] result = storageClient1.download_file1("group1/M00/00/00/rBNqfF3o4lmAJFmtAAJHPZ4PS4A950.png");
        File file = new File("C:/Users/tianx/Desktop/2.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(result);
        fileOutputStream.close();
    }

    //删除文件
    @Test
    public void testDelectFile() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
        int result = storageClient1.delete_file("group1",
                "M00/00/00/rBNqfF3o4lmAJFmtAAJHPZ4PS4A950.png");
        if(result>0){
            System.out.println("删除成功");
        }
    }
}
