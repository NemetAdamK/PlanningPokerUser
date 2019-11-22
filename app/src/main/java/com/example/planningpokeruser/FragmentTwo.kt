package com.example.planningpokeruser

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*






class FragmentTwo : Fragment() {

    val TAG = "FragmentTwo"
    var textView: TextView? = null


    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    fun loadWithData() : ArrayList<VotedUsers>{
        val users = ArrayList<VotedUsers>()
        val database = FirebaseDatabase.getInstance()
        mAdapter = SummaryAdapter(users)

        val myRef = database.reference.child("Answers").child("Group").child(roomId).orderByKey()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                users.clear()
                for (ds in dataSnapshot.children) {

                    val QuestionResult = ds.getValue(VotedUsers::class.java)
                    if (questionName == QuestionResult!!.questionString) {
                        val nameVoter = QuestionResult.name
                        val voteName = QuestionResult.vote
                        users.add(VotedUsers(nameVoter, voteName, roomId, questionName))


                        mAdapter = SummaryAdapter(users)
                        mAdapter?.notifyDataSetChanged()
                        mRecyclerView!!.adapter = mAdapter
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Action canceled",Toast.LENGTH_SHORT).show()
            }
        })
        return users
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        //return inflater!!.inflate(R.layout.fragment_two,container,false)

        val rootView = inflater.inflate(R.layout.fragment_two,container,false)

        mRecyclerView = rootView.findViewById(R.id.rvItems) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        loadWithData()

        seeData()
        return rootView
    }



    private fun seeData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Questions").child("Group").child(roomId).orderByKey()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val QuestionResult = ds.getValue(Question::class.java)
                    if (QuestionResult?.activit == false && QuestionResult.question== questionName) {
                        val timer = object: CountDownTimer(2000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {

                            }

                            override fun onFinish() {
                                val textview =
                                    activity!!.findViewById(R.id.taskname) as TextView
                                textview.text = "Wait for next question"
                                fragmentManager?.popBackStackImmediate()

                            }
                        }
                        timer.start()
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }


}
