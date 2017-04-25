package com.app.android.konferika.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.app.android.konferika.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PollActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_poll);

        mActivity = this;
        super.navigationView.setCheckedItem(R.id.drawer_poll);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_poll, null, false);
        super.drawerLayout.addView(contentView, 0);
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
        String url = "https://rszczers.students.wmi.amu.edu.pl/oblicze_poll/" + result.getText();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        onBackPressed();
//        zXingScannerView.resumeCameraPreview(this);
//        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
    }
}
