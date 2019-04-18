package com.github.welshk.eirene.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.welshk.eirene.demo.networking.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This is where we supply a test URL
        NetworkManager.Instance.initService("http://d1s3yn3kxq96sy.cloudfront.net/")

        button.setOnClickListener {
            val intent = Intent(this, DemoVideoActivity::class.java)
            ActivityCompat.startActivity(this, intent, null)
        }
    }
}
