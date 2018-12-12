package hotfix.sjd.tinker;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by SJD
 * time: 2018/12/5
 */
public class CustomPatchListener extends DefaultPatchListener {

    private String currentMD5;

    public void setCurrentMD5(String md5Value) {
        this.currentMD5 = md5Value;
    }

    public CustomPatchListener(Context context) {
        super(context);
    }

    /**
     * patch的检测
     *
     * @param path
     * @return
     */
    @Override
    protected int patchCheck(String path, String patchMd5) {
        //MD5校验的工具可以网上查找
        //这里要求我们在初始化Tinker的时候加上MD5的参数
        //增加patch文件的md5较验
        if (!isFileMD5Matched(path, currentMD5)) {
            return ShareConstants.ERROR_PATCH_DISABLE;
        }
        return super.patchCheck(path, patchMd5);
    }


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

}
