package com.example.adiblar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adiblar.R
import com.example.adiblar.databinding.ItemAdibBinding
import com.example.adiblar.models.AdiblarModel
import com.example.adiblar.util.MySharedPrefarance
import com.squareup.picasso.Picasso

class AdibAdapter(val context: Context?, var list:ArrayList<AdiblarModel>,var rvItemClick: RvItemClick, var saqNoSaq:Int=0) :RecyclerView.Adapter<AdibAdapter.MyViewHolder>(){
    inner class MyViewHolder(var itemAdibBinding: ItemAdibBinding):RecyclerView.ViewHolder(itemAdibBinding.root){
        fun onBind(adiblarModel: AdiblarModel){
            Picasso.get().load(adiblarModel.imgUri).into(itemAdibBinding.imageRvAdib)
            itemAdibBinding.root.setOnClickListener {
                rvItemClick.onMyItemClick(adiblarModel)
            }
            MySharedPrefarance.init(context)
            var mList = MySharedPrefarance.obektString
            var index = -1
            for (n in mList.indices) {
                println("mList[n] = ${mList[n].imgUri} == ${adiblarModel.imgUri}")
                if (mList[n].imgUri == adiblarModel.imgUri) {
                    index = n
                    break
                }
            }
            itemAdibBinding.tvNameAdibRv.text = adiblarModel.fio
            itemAdibBinding.tvDateAdibRv.text = "(${adiblarModel.birthday}-${adiblarModel.diedYear})"
            if (index!=-1){
                itemAdibBinding.imageSelection.setImageResource(R.drawable.ic_saqlangan_2)
            }else{
                itemAdibBinding.imageSelection.setImageResource(R.drawable.ic_saqlangan_1)
            }
            itemAdibBinding.imageSelection.setOnClickListener {

                if (index != -1) {
                    val l = MySharedPrefarance.obektString
                    println(""+l.removeAt(index)+"removed ")
                    MySharedPrefarance.obektString = l
                } else {
                    val l = MySharedPrefarance.obektString
                    l.add(adiblarModel)
                    MySharedPrefarance.obektString = l
                }
                if (saqNoSaq == 1){
                    list.remove(adiblarModel)
                    notifyItemRemoved(position)
                    notifyItemRangeRemoved(position, mList.size)
                }else{
                    notifyItemChanged(position)
                }
            }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemAdibBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface RvItemClick{
        fun onMyItemClick(adiblarModel: AdiblarModel)
    }
}
