package com.example.picos.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import com.example.picos.R
import com.google.firebase.database.*

class CalendarFragment : Fragment() {
    private lateinit var calendarView: CalendarView
    private lateinit var editText: EditText
    private lateinit var stringDateSelected: String
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        editText = view.findViewById(R.id.editText)

        calendarView.setOnDateChangeListener { _, i, i1, i2 ->
            stringDateSelected = "$i${i1 + 1}$i2"
            calendarClicked()
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar")

        return view
    }

    private fun calendarClicked() {
        databaseReference.child(stringDateSelected).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.getValue() != null) {
                    editText.setText(snapshot.getValue().toString())
                } else {
                    editText.setText("null")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun buttonSaveEvent(view: View) {
        databaseReference.child(stringDateSelected).setValue(editText.text.toString())
    }
}