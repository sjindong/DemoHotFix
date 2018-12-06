package hotfix.sjd.tinker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPath = getExternalCacheDir().getAbsolutePath() + File.separatorChar;
    }
    
    private String mPath;


    /**
     * 加载Tinker补丁
     *
     * @param view
     */
    public void Fix(View view) {
        File patchFile = new File(mPath, "patch_signed.apk");
        if (patchFile.exists()) {
            TinkerManager.addPatch(patchFile.getAbsolutePath());
            Toast.makeText(this, "File Exists,Please wait a moment ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File No Exists", Toast.LENGTH_SHORT).show();
        }
    }
}
