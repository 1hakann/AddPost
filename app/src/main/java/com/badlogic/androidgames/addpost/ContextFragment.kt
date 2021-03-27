package com.badlogic.androidgames.addpost

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_context.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.jar.Manifest


class ContextFragment : Fragment() {

    var secilenGorsel: Uri? = null
    var secilenBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_context, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnKaydet.setOnClickListener {
            Kaydet(it)
        }

        ivImg.setOnClickListener {
            ResimSec(it)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1) {
            // İzni Aldık
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galeryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeryIntent,2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            secilenGorsel = data.data

            try {
                context?.let {
                    if(secilenGorsel != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(it.contentResolver, secilenGorsel!!)
                            secilenBitmap = ImageDecoder.decodeBitmap(source)
                            ivImg.setImageBitmap(secilenBitmap)
                        } else {
                            secilenBitmap = MediaStore.Images.Media.getBitmap(it.contentResolver, secilenGorsel)
                            ivImg.setImageBitmap(secilenBitmap)
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun Kaydet(view:View) {
        //kotlin android extension kullandığımız için bu şkeilde alabiliyoruz.
        val postBaslik = tvHeader.text.toString()
        val postContext = tvPost.text.toString()
        if(secilenBitmap != null) {
            val kucukBitmap = bitmapKucult(secilenBitmap!!, 300) //ideal değeri deneyerek bul.
            val outputStream = ByteArrayOutputStream()
            kucukBitmap.compress(Bitmap.CompressFormat.PNG, 70, outputStream)
            val byteDizisi = outputStream.toByteArray()

            // SQL e Kaydetme İşlemi
            try {
                context?.let {
                    val db = it.openOrCreateDatabase("posts", Context.MODE_PRIVATE, null)
                    db.execSQL("CREATE TABLE IF NOT EXISTS posts (id INTEGER PRIMARY KEY, postbaslik VARCHAR, posticerik VARCHAR, gorsel BLOB)")
                    val sqlString = "INSERT INTO posts (postbaslik, posticerik, gorsel) VALUES (?,?,?)"
                    val statement = db.compileStatement(sqlString)
                    statement.bindString(1,postBaslik)
                    statement.bindString(2,postContext)
                    statement.bindBlob(3,byteDizisi)
                    statement.execute()

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            // listfragmana geri dönüldüğü anı da kodlamalıyız
            val action = ContextFragmentDirections.actionContextFragmentToListFragment()
            Navigation.findNavController(view).navigate(action)
        }

    }

    fun ResimSec(view:View) {

        activity?.let {
            if(ContextCompat.checkSelfPermission(it.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)

            } else {
                val galeryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeryIntent,2)
            }
        }
    }

    fun bitmapKucult(userSelectBitmap: Bitmap, maxSize: Int) : Bitmap {
        var width = userSelectBitmap.width
        var height = userSelectBitmap.height
        var bitmapOrani : Double = width.toDouble() / height.toDouble()
        if(bitmapOrani > 1) {
            //gorsel yatay
            width = maxSize
            var kisaltilmisHeight = width / bitmapOrani
            height = kisaltilmisHeight.toInt()
        } else {
            // gorsel dikey
            height = maxSize
            var kisaltilmisWidth = height * bitmapOrani
            width = kisaltilmisWidth.toInt()
        }

        return Bitmap.createScaledBitmap(userSelectBitmap, width/2, height/2, true)
    }
}