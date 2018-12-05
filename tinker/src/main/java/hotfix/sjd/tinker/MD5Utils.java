package hotfix.sjd.tinker;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Lulei on 2017/1/20.
 * time : 16:32
 * date : 2017/1/20
 * mail to lulei4461@gmail.com
 *
 * @author lulei
 */

public class MD5Utils {
    private static String TAG = "MD5";

    public static boolean isFileMD5Matched(String path, String currentMD5) {
        String r = getFileMd5(new File(path));
        return TextUtils.equals(r, currentMD5);
    }

    /**
     * FileInputStream字节流方式
     *
     * @param file 文件
     * @return 文件MD5
     */
    public static String getFileMd5(File file) {
        MessageDigest messageDigest;
        FileInputStream fis = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            int len = 0;
            fis = new FileInputStream(file);
            //普通流读取方式
            byte[] buffer = new byte[1024 * 1024 * 10];
            while ((len = fis.read(buffer)) > 0) {
                //该对象通过使用 update（）方法处理数据
                messageDigest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * RandomAccessFile 方式
     *
     * @param file 文件
     * @return
     */
    public static String getFileMd53(File file) {
        MessageDigest messageDigest;
        RandomAccessFile randomAccessFile = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[1024 * 1024 * 10];
            int len = 0;
            while ((len = randomAccessFile.read(bytes)) != -1) {
                messageDigest.update(bytes, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    randomAccessFile = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}
