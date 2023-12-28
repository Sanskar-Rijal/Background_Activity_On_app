package com.example.background_acticity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var progressdialog:Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnExecute:Button= findViewById<Button>(R.id.btn1)
        btnExecute.setOnClickListener {
            showprogressbar()
            lifecycleScope.launch {//in order to make this run we have to say which scope we want to run
                execute("task executed sucessfully")
            }
            //couritines push the task in background so that userinterface is not frozen
        }
    }
    private suspend fun execute(result:String) //to make it couriteines we use suspend
    {
        withContext(Dispatchers.IO)
        {
        for(i in 1..100000)
        {
            Log.e("delay :" , "" +i)
        }
       runOnUiThread {
           cancelprogressdialogue()
           Toast.makeText(this@MainActivity,result,Toast.LENGTH_SHORT).show()
       }//this should runon ui so app may crash
    }
    }
    private fun cancelprogressdialogue()
    {
        if(progressdialog !=null)
        {
            progressdialog?.dismiss()
            progressdialog=null
        }
    }
    private fun showprogressbar()
    {
        progressdialog=Dialog(this@MainActivity)
        /**
         * set the screen content from a layout resource
         * The resource will be inflated ,adding all top-level views to the screen
         */
        progressdialog?.setContentView(R.layout.progressbar)

        //now to start the dialogue we use .show()
        progressdialog?.show()
    }
}