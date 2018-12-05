package hotfix.sjd.tinker;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;

import java.io.File;

/**
 * Created by SJD
 * time: 2018/12/5
 */
public class CustomResultService extends DefaultTinkerResultService {

    private static final String TAG = "Tinker.SampleResultService";

    /**
     * patch文件的最终安装结果，默认是安装完成后杀掉自己进程
     * 此段代码主要是复制DefaultTinkerResultService的代码逻辑
     */
    @Override
    public void onPatchResult(PatchResult result) {
        if (result == null) {
            TinkerLog.e(TAG, "DefaultTinkerResultService received null result!!!!");
            return;
        }
        TinkerLog.i(TAG, "DefaultTinkerResultService received a result:%s ", result.toString());

        //first, we want to kill the recover process
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());

        // if success and newPatch, it is nice to delete the raw file, and restart at once
        // only main process can load an upgrade patch!
        if (result.isSuccess) {
            //删除patch包
            deleteRawPatchFile(new File(result.rawPatchFilePath));
            //杀掉自己进程，如果不需要则可以注释，在这里做自己的逻辑
            if (checkIfNeedKill(result)) {
                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                TinkerLog.i(TAG, "I have already install the newly patch version!");
            }
        }
    }
}
