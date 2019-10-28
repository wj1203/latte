package com.leap.latte.base;

import android.app.Activity;
import android.os.Bundle;
import com.leap.latte.R;

/**
 * @author: leap
 * @time: 2019/10/28
 * @classname: SchemeActivity
 * @description:
 */
public class SchemeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
    }
}
