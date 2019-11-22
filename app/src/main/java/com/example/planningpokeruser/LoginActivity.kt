package com.example.planningpokeruser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean


class LoginActivity : AppCompatActivity() {

    var btn_Login : Button? = null
    val database = FirebaseDatabase.getInstance()
    var room:String? = null
    var ok_room = false
    var roomNumberList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadRoomNumbers(roomNumberList)

        btn_Login = this.btn_login




        btn_Login!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View){
                if (!edt_username.text.isEmpty()){
                    var editText = edt_roomid.text.toString()
                    if (roomNumberList.contains(editText)){
                            roomId=editText
                            val userReference = database.getReference()
                            userName=edt_username.text.toString()
                            userReference.child("Users").push().setValue(edt_username.text.toString())
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext,"Incorrect room number", Toast.LENGTH_LONG).show()
                        }
                } else {
                    Toast.makeText(applicationContext,"Username is missing", Toast.LENGTH_LONG).show()
                }
            }
        })



    }

    fun loadRoomNumbers(roomNumbers: ArrayList<String>) {
        val myRef1 = database.reference
        myRef1.child("Questions").child("Group").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                roomNumbers.add(dataSnapshot.value.toString())
                for (ds in dataSnapshot.children)
                        roomNumbers.add(ds.key.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

    }



}
