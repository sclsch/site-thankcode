/**
 * All rights Reserved, Designed By ysusolt.
 *
 * @author: jiangqian
 * @date: 2019/10/31 0031
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * io流操作工具类
 *
 * @author: jiangqian
 * @date: 2019/10/31 0031 17:42
 * @version: V1.0
 * @review: jiangqian/2019/10/31 0031 17:42
 */
public class IOutils {

    private static final Logger logger = LoggerFactory.getLogger(IOutils.class);

    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            logger.error("获取字符串失败", e);
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    /**
     * 输入流复制到输出流
     *
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error("复制流失败  异常:{}", e);
        }
    }


    /**
     * 根据文件路径  创建文件流
     */

    public static File createFile(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();//创建目录
            }
        } catch (Exception e) {
            logger.error("create file failure", e);
            return file;
        }
        return file;
    }

    /**
     * 读写到硬盘
     *
     * @param path
     * @param inputStream
     */
    public static void writeFile(String path, InputStream inputStream) {
        try {
            File file = new File(path);
            if(file.isDirectory()){
                throw new RuntimeException("路径不能是文件夹");
            }
            OutputStream out = new FileOutputStream(path);
            byte[] buff = new byte[1024];
            int b;
            while ((b = inputStream.read(buff)) != -1) {
                out.write(buff, 0, b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 两个文件夹之间的复制
     *
     * @throws IOException
     */
    public static void copyDir(String targetPath, String sourcePath) {
        if (targetPath.equals(sourcePath)) {
            return;
        }
        File targetFile = new File(targetPath);
        File sourceFile = new File(sourcePath);
        copyDirAndFile(sourceFile, targetFile);
    }

    public static void copyDirAndFile(File targetFile, File sourceFile) {
        if (targetFile.isFile()) {
            fileCopy(targetFile, sourceFile);
        } else if (sourceFile.getAbsolutePath().contains(sourceFile.getAbsolutePath())) {
            logger.info("父目录不能拷贝到子目录中");
            return;
        } else {
            if (!sourceFile.exists()) {
                sourceFile.mkdirs();
            }
            File[] fileList = targetFile.listFiles();
            for (File f : fileList) {
                if (f.isDirectory()) {
                    copyDirAndFile(f, new File(targetFile, f.getName()));
                } else {
                    fileCopy(f, new File(targetFile, f.getName()));
                }
            }
        }
    }

    public static void fileCopy(File sourceFile, File targetFile) {
        if (sourceFile.isDirectory() || null == sourceFile) {
            logger.info("拷贝非文件,或文件为空");
            return;
        } else if (targetFile.isDirectory()) {
            logger.info("拷贝文件，但目标路径为文件夹无法拷贝");
            return;
        } else {
            InputStream is = null;
            OutputStream os=null;
            try {
                is = new BufferedInputStream(new FileInputStream(sourceFile));
                os = new BufferedOutputStream(new FileOutputStream(targetFile, true));
                byte[] car = new byte[1024];
                int len = 0;
                while (-1 != (len = is.read(car))) {
                    os.write(car, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 文件变成输入流
     * @param file
     * @return
     */
    public static InputStream fileToInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.info("文件变成输入流 失败：{}",e);
            return null;
        }
    }

    public static void main(String[] args) {
        File targetFile = new File("D:\\截图1.png");

        writeFile("F:\\test111",fileToInputStream(targetFile));

    }
}

