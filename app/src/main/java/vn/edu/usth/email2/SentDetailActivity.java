package vn.edu.usth.email2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SentDetailActivity extends AppCompatActivity {

    TextView icon;
    TextView receiver;
    TextView title;
    TextView detail;
    ImageView star;
    int color2 = Color.argb(255,241,181,3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        receiver = findViewById(R.id.sd_receiver);
        title = findViewById(R.id.sd_title);
        detail = findViewById(R.id.sd_detail);
        star = findViewById(R.id.starro);
        icon = findViewById(R.id.ic_receiver);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            icon.setText(mBundle.getString("icon"));
            ((GradientDrawable) icon.getBackground()).setColor(mBundle.getInt("colorIcon"));
            receiver.setText(mBundle.getString("receiver"));
            title.setText(mBundle.getString("title"));
            detail.setText(mBundle.getString("detail"));
        }
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (star.getColorFilter() != null) {
                    star.clearColorFilter();
                } else {
                    star.setColorFilter(color2);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ib_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.del) {

        }
        return super.onOptionsItemSelected(item);
    }
}