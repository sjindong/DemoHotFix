package hotfix.sjd.tinker;

import android.content.Context;

import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by SJD
 * time: 2018/12/5
 */
public class TinkerManager {

    private static boolean isInstalled = false;
    // 这里的ApplicationLike可以理解为Application的载体
    private static ApplicationLike mAppLike;
    private static CustomPatchListener mPatchListener;

    /**
     * 默认初始化Tinker
     *
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }

        TinkerInstaller.install(mAppLike);
        isInstalled = true;
    }

    /**
     * 初始化Tinker，带有自定义模块
     * <p>
     * 1、CustomPatchListener
     * 2、CustomResultService
     *
     * @param applicationLike
     * @param md5Value        服务器下发的md5
     */
    public static void installTinker(ApplicationLike applicationLike, String md5Value) {
        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }

        mPatchListener = new CustomPatchListener(getApplicationContext());
        mPatchListener.setCurrentMD5(md5Value);

        // Load补丁包时候的监听
        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());
        // 补丁包加载时候的监听
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());
        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        TinkerInstaller.install(applicationLike,
                loadReporter,
                patchReporter,
                mPatchListener,
                CustomResultService.class,
                upgradePatchProcessor);
        isInstalled = true;
    }

    /**
     * 增加补丁包
     *
     * @param path
     */
    public static void addPatch(String path) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 获取上下文
     *
     * @return
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }
}