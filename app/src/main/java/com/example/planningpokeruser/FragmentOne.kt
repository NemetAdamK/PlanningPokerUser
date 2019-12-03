package com.example.planningpokeruser

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_one.view.*



class FragmentOne : Fragment() {

    val TAG = "FragmentOne"
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var currentVote: String? = null


    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")

        val rootView = inflater.inflate(R.layout.fragment_one,container,false)

        mRecyclerView = rootView.findViewById(R.id.rvNumbers) as RecyclerView
        mRecyclerView!!.layoutManager = GridLayoutManager(this.context,3)

        var optionsList : ArrayList<String> = ArrayList()

        rootView.btn_vote.setOnClickListener(){
            val taskSeconds =
                getActivity()!!.findViewById<TextView>(R.id.textViewSeconds)
            if (taskSeconds.text.toString() == "0") {
                Toast.makeText(context, "Time expired", Toast.LENGTH_LONG).show()
            }else {
                addVoteData(currentVote.toString())
                val newFragment = FragmentTwo()
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.fragment_holder, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }


        }
        for(i in 1..10){
            optionsList.add(i.toString())
        }
        optionsList.add("Coffee")
        optionsList.add("Skip")

        mAdapter = Myadapter(optionsList)
        mRecyclerView!!.adapter = mAdapter

        mRecyclerView!!.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                currentVote = optionsList.get(position)



                Toast.makeText(context, "You selected:  " + currentVote, Toast.LENGTH_SHORT).show()



            }
        })


        return rootView
    }



    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)

    }

    fun addVoteData(voteString: String){
        val database = FirebaseDatabase.getInstance()
        val userReference = database.reference

        val taskSeconds =
            getActivity()!!.findViewById<TextView>(R.id.textViewSeconds)
        if (taskSeconds.text.equals("0")) {
            userReference.child("Answers").child("Group").child(roomId).push().setValue(
                VotedUsers(
                    userName,
                    "I'm an idiot and cannot vote in time",
                    roomId,
                    questionName
                )
            )
        } else {
            userReference.child("Answers").child("Group").child(roomId).push().setValue(
                VotedUsers(
                    userName,
                    voteString,
                    roomId,
                    questionName
                )
            )
        }

    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)

            }

            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG,"onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG,"onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG,"onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG,"onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG,"onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG,"onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG,"onDetach")
        super.onDetach()
    }
}
