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

        //This is where we supply a test URL. We actually just use a full path in RetroFit so this isnt used
        NetworkManager.initService("http://fakeurl.com")

        button_activity.setOnClickListener {
            val intent = Intent(this, DemoVideoActivity::class.java)
            ActivityCompat.startActivity(this, intent, null)
        }

        button_fragment.setOnClickListener {
            val demoVideoFragment = DemoVideoFragment()
            supportFragmentManager.beginTransaction().replace(R.id.video_frame, demoVideoFragment)
                .commit()
            supportFragmentManager.executePendingTransactions()
        }
    }
}
