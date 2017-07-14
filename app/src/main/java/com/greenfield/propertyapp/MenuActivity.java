package com.greenfield.propertyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnPropertyRegistration)
    Button btnPropertyRegistration;
    @BindView(R.id.btnOwnPaymentPayment)
    Button btnOwnPaymentPayment;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @OnClick({R.id.btnPropertyRegistration, R.id.btnOwnPaymentPayment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPropertyRegistration:
                startActivity(new Intent(MenuActivity.this, PropertyFormActivity.class));
                break;
            case R.id.btnOwnPaymentPayment:
                startActivity(new Intent(MenuActivity.this,OwnerFormActivity.class));
                break;
        }
    }
}
