package pl.edu.amu.wmi.oblicze.konferika.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pl.edu.amu.wmi.oblicze.konferika.R.layout.activity_stop);

        final int infoId = getIntent().getIntExtra("info", -1);
        TextView infoTv = (TextView) findViewById(pl.edu.amu.wmi.oblicze.konferika.R.id.stop_info_tv);

        switch (infoId) {
            case 0:
                infoTv.setText("Dziękujemy za uczestnictwo." +
                        "\nDo zobaczenia za rok.");
                break;
            case 2:
                infoTv.setText("Przerwa techniczna. \nZapraszamy za chwilę");
        }
    }
}
