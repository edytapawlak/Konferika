package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import pl.edu.amu.wmi.oblicze.konferika.R;

public class PollActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_poll);

        mActivity = this;
        super.navigationView.setCheckedItem(R.id.drawer_poll);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_poll, null, false);
        super.drawerLayout.addView(contentView, 0);
        initToolbar();
    }

    public void scan(View view) {
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }

    @Override
    protected void onResume() {
        super.onResume();
        super.navigationView.setCheckedItem(R.id.drawer_poll);
        if(zXingScannerView!= null) {
//            zXingScannerView.resumeCameraPreview(this);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(zXingScannerView != null) {
            zXingScannerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        String url = result.getText();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        onBackPressed();
//        zXingScannerView.resumeCameraPreview(this);
//        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
    }
}