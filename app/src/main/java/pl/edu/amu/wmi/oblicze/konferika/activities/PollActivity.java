package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import pl.edu.amu.wmi.oblicze.konferika.R;

public class PollActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    private final int PERMISSION_REQUEST_CAMERA = 111;

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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        } else {

            zXingScannerView = new ZXingScannerView(getApplicationContext());
            setContentView(zXingScannerView);
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        super.navigationView.setCheckedItem(R.id.drawer_poll);
        if (zXingScannerView != null) {
//            zXingScannerView.resumeCameraPreview(this);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (zXingScannerView != null) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    zXingScannerView = new ZXingScannerView(getApplicationContext());
                    setContentView(zXingScannerView);
                    zXingScannerView.setResultHandler(this);
                    zXingScannerView.startCamera();
//                    Toast.makeText(this, "Mam pozwo", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Brak pozwolenia. Nie można użyć aparatu do zeskanowania kodu.", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}
