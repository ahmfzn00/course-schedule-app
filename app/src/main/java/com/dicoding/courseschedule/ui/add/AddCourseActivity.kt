package com.dicoding.courseschedule.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.ui.detail.DetailViewModelFactory
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.ui.list.ListViewModel
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    var startTime: String = ""
    var endTime: String = ""
    var day: Int = 0

    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        val factory = AddViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        initComponent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database
                insertAction()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertAction() {
        val courseName = findViewById<EditText>(R.id.add_ed_courseName).text.toString()
        val lecturer = findViewById<EditText>(R.id.add_ed_lecturer).text.toString()
        val note = findViewById<EditText>(R.id.add_ed_note).text.toString()
        if (courseName.isNotEmpty()
            && day.toString().isNotEmpty()
            && startTime.isNotEmpty()
            && endTime.isNotEmpty()
            && lecturer.isNotEmpty()
            && note.isNotEmpty()){
            addCourseViewModel.insertCourse(
                courseName,
                day,
                startTime,
                endTime,
                lecturer,
                note
            )
            Toast.makeText(this, "Berhasil menambahkan course", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun initComponent() {
        // access the spinner
        val spinner = findViewById<Spinner>(R.id.add_spin_day)
        val dayData = resources.getStringArray(R.array.day)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, dayData)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    day = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
        }

    }

    fun showTimePickerForStartTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "startDialog")
    }

    fun showTimePickerForEndTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "endDialog")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val value = "${hour}:${minute}"
        if (tag == "startDialog") {
            val startTimeEd = findViewById<TextView>(R.id.add_tv_startTime)
            startTime = value
            startTimeEd.text = value
        } else {
            val endTimeEd = findViewById<TextView>(R.id.add_tv_endTime)
            endTime = value
            endTimeEd.text = value
        }
    }
}