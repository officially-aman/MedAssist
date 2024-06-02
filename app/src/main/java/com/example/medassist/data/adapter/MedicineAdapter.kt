package com.example.medassist.data.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medassist.R
import com.example.medassist.data.Medicine

class MedicineAdapter(
    private val medicineList: List<Medicine>,
    private val listener: OnItemClickListener?
) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.diseases_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val medicine = medicineList[position]
        Log.e("medicineList ", medicine.medicines!!)
        holder.symptomsTextView.text = medicine.symptoms
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(medicineList[position])
            }
        }
        //        holder.medicinesTextView.setText(medicine.getMedicines());
//        holder.adviceTextView.setText(medicine.getAdvice());
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symptomsTextView: TextView = itemView.findViewById(R.id.symptomsTextView)

        //        private TextView medicinesTextView;
        //        private TextView adviceTextView;
        init {
            //            medicinesTextView = itemView.findViewById(R.id.medicinesTextView);
//            adviceTextView = itemView.findViewById(R.id.adviceTextView);
        }
    }

    interface OnItemClickListener {
        fun onItemClick(medicine: Medicine?)
    }
}
