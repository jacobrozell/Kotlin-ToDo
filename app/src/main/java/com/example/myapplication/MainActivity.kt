package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dialog.view.*
import kotlinx.android.synthetic.main.list_item.view.*


class MainActivity : AppCompatActivity() {

    var items = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ToDoAdapter(items, applicationContext)
        title = "ToDo"

        items.add("Test1")
        items.add("Test2")
        items.add("Test3")

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add -> {
                val dialog = Dialog(this, R.style.Dialog)
                val view = LayoutInflater.from(this).inflate(R.layout.add_dialog, null)
                dialog.setContentView(view)

                val text = view.add_text
                val add = view.add_dialog_button

                add.setOnClickListener {
                    val adapter = recyclerView.adapter as ToDoAdapter
                    adapter.items.add(text.text.toString())
                    adapter.notifyItemInserted(adapter.itemCount-1)
                    dialog.dismiss()
                }
                dialog.show()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    class ToDoAdapter (val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.item_text.text = items[position]

            holder.itemView.item_button.setOnClickListener {
                items.removeAt(position)
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }
        }
    }
}
