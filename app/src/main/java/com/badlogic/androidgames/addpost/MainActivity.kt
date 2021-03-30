package com.badlogic.androidgames.addpost

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_context.*

class MainActivity : AppCompatActivity() {

    var pass: TextView? = null
    var user: TextView? = null
    private var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bu kısımda yukarıda tanımladığımız bileşenlerle xml olarak hazırladığımız bileşenleri birbirlerine bağlıyoruz.
      //  user = findViewById<View>(R.id.tvMail) as TextView
       // pass = findViewById<View>(R.id.tvPass) as TextView

      /*  fun btnLogin(view: View?) {
            val kullanici = user!!.text.toString()
            val parola = pass!!.text.toString()
        } */

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_add_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_menu) {
            val action = ListFragmentDirections.actionListFragmentToContextFragment("bilgidenGeldim",0)
            Navigation.findNavController(this, R.id.fragment).navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }
}