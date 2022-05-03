package com.hang.config.utils;

import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zzh-
 * @DATE: 2022/5/3
 * @TIME: 17:20
 */
public class FastDFSUtils {
    private static Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            logger.error("FastDFS Client Init Fail! ",e);
        }
    }


    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getTrackerServer();

    }

    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

    public static String[] upload(MultipartFile file){
        String filename = file.getOriginalFilename();
        logger.info("File Name :" + filename);
        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        StorageClient storageClient = null;
        //获取storage客户端
        try {
            storageClient = getStorageClient();
            //上传
            try {
                uploadResults = storageClient.upload_file(file.getBytes(),filename.substring(filename.lastIndexOf(".")+1),null);
            } catch (IOException e) {
                logger.error("IO Exception when uploadind the file:" + filename, e);
            }
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file:" + filename, e);
        }
        logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");
        //验证上传结果
        if (uploadResults == null && storageClient != null){
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        //上传成功返回groupName
        logger.info("upload file successfully!!!" + "group_name:" + uploadResults[0] + ", remoteFileName:" + " " + uploadResults[1]);
        return uploadResults;
    }

    public static FileInfo getFileInfo(String groupName, String remoteFileName) {

        try {
            StorageClient storageClient = getStorageClient();
            return storageClient.get_file_info(groupName,remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        }catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        }catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static void deleteFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            storageClient.download_file(groupName, remoteFileName);
        } catch (Exception e) {
            logger.error("文件删除失败!"+e.getMessage());
        }
    }

    public static String getTrackerUrl() {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
            storageServer = trackerClient.getStoreStorage(trackerServer);
        } catch (Exception e) {
            logger.error("获取文件路径失败!" + e.getMessage());
        }
//        return "http://"+storageServer.getInetSocketAddress().getHostString() + "/";
        return "http://192.168.88.152/";
    }


}


