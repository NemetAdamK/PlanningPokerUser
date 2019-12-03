package com.example.planningpokeruser

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import android.text.Editable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_add_question.view.*


class MainActivity : AppCompatActivity() {

    var isFragmentOneLoaded = true
    val manager = supportFragmentManager


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        taskname.text = "Wait for next question"
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Questions").child("Group").child(roomId).orderByKey()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val QuestionResult = ds.getValue(Question::class.java)
                    if (QuestionResult?.activit == true) {
                        val questionText = QuestionResult.question
                        val questionTime = QuestionResult.seconds

                        taskname.text = questionText
                        textViewSeconds.text = questionTime.toString()
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
        questionName = taskname.text.toString()
        textViewSeconds.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (questionName.equals("10")){
                    ShowFragmentOne()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                questionName = taskname.text.toString()
                if (textViewSeconds.equals("10")){
                    ShowFragmentOne()
                }
            }
        })

        if (!questionName.equals("0")){
            ShowFragmentOne()
        }

    }


    fun ShowFragmentOne() {
        val transaction = manager.beginTransaction()
        val fragment = FragmentOne()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = true
    }

    fun newGroupClicked(view: View) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_question, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Go to now group")
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.dialogLoginBtn.setOnClickListener {
            //dismiss dialog

            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val newRoom = mDialogView.dialogQuestion.text.toString()

            if (newRoom.isNotEmpty()){
                roomId=newRoom
                finish()
                startActivity(getIntent())

            }
            else
            {
                Toast.makeText(applicationContext,"Empty field", Toast.LENGTH_SHORT).show()
            }
            mAlertDialog.dismiss()
        }
        //cancel button click of custom layout
        mDialogView.dialogCancelBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }

    }


}
