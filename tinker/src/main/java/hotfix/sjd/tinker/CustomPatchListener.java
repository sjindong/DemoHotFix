package hotfix.sjd.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.loader.shareutil.ShareConstants;

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
        if (!MD5Utils.isFileMD5Matched(path, currentMD5)) {
            return ShareConstants.ERROR_PATCH_DISABLE;
        }
        return super.patchCheck(path, patchMd5);
    }
}
