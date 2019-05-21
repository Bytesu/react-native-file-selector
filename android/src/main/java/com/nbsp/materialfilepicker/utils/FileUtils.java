package com.nbsp.materialfilepicker.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class FileUtils {
    static Map<File, String> m=new HashMap<>();
    static List<String> usefulPaths=new ArrayList<>();
    public static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static List<File> getFileListByDirPath(String path, FileFilter filter) {
        File directory = new File(path);
        File[] files = directory.listFiles(filter);

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());
        return result;
    }

    public static String getFileName(File f){
        if(m.containsKey(f)){
            return m.get(f);
        }else{
            return f.getName();
        }
    }

    public static List<File> getUsefulPath() {
        String[] paths={"tencent/MicroMsg/Download","tencent/QQfile_recv","Download","QQBrowser","UCDownloads","nuBrowserDownload"};
        String[] names={"微信","QQ","默认浏览器","QQ 浏览器","UC 浏览器","nubia 浏览器"};
        List<File> result = new ArrayList<>();
        m.clear();
        usefulPaths.clear();
        for(int i=0;i<paths.length;++i){
            String path=basePath+"/"+paths[i];
            File directory = new File(path);
            if(directory.exists()){
                result.add(directory);
                m.put(directory,names[i]);
                usefulPaths.add(directory.getAbsolutePath());
            }
        }
        return result;
    }

    public static String cutLastSegmentOfPath(String path) {
        for(int i=0;i<usefulPaths.size();++i){
            if(usefulPaths.get(i).equals(path)){
                return basePath;
            }
        }
        if (path.length() - path.replace("/", "").length() <= 1)
            return "/";
        String newPath = path.substring(0, path.lastIndexOf("/"));
        // We don't need to list the content of /storage/emulated
        if (newPath.equals("/storage/emulated"))
            newPath = "/storage";
        return newPath;
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
