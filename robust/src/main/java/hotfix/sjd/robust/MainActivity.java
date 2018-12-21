package hotfix.sjd.robust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.meituan.robust.Patch;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.RobustCallBack;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button btn1;
    protected Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn1) {
            new PatchExecutor(getApplicationContext(), new PatchManipulateImp(), new callBack()).start();
        } else if (view.getId() == R.id.btn2) {
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(MainActivity.this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(MainActivity.this);
    }

    private class callBack implements RobustCallBack {
        @Override
        public void onPatchListFetched(boolean result, boolean isNet, List<Patch> patches) {

        }

        @Override
        public void onPatchFetched(boolean result, boolean isNet, Patch patch) {

        }

        @Override
        public void onPatchApplied(boolean result, Patch patch) {

        }

        @Override
        public void logNotify(String log, String where) {

        }

        @Override
        public void exceptionNotify(Throwable throwable, String where) {

        }
    }
}

