package com.example.baserecycleradaptertest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baserecycleradaptertest.databinding.ActivityMainBinding
import com.example.baserecycleradaptertest.databinding.RecyclerItemBinding

class MainActivity : AppCompatActivity(){

    var itemlist : ArrayList<ReviewDTO> = arrayListOf()
    lateinit var binding: ActivityMainBinding

    init {
        itemlist.add(ReviewDTO("이름1","아이디1","리뷰내용1",this))
        itemlist.add(ReviewDTO("이름2","아이디2","리뷰내용2",this))
        itemlist.add(ReviewDTO("이름3","아이디3","리뷰내용3",this))
        itemlist.add(ReviewDTO("이름4","아이디4","리뷰내용4",this))
        itemlist.add(ReviewDTO("이름5","아이디5","리뷰내용5",this))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,  R.layout.activity_main)
        binding.activityreview = this

        binding.recyclerView.adapter = BaseRecyclerAdapter<ReviewDTO,RecyclerItemBinding>(R.layout.recycler_item,itemlist)
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}



class ReviewDTO(
        //닉네임
        var nickName : String ? = null,
        //Firebase Uid
        var id : String ? = null,
        //리뷰 포스트 자체의 id
        var reviewId : String ? = null,
        var context : Context
){
    fun click(view : View){
        Toast.makeText(context,"nickname : " + nickName + " id : " + id +" reviewId : " + reviewId  ,Toast.LENGTH_SHORT).show();
    }

}




open class BaseRecyclerAdapter<item,viewBinding : ViewDataBinding>(@LayoutRes val layoutId: Int,itemlist : ArrayList<item>) : RecyclerView.Adapter<BaseRecyclerAdapter<item,viewBinding>.CustomViewHolder>() {

    //각뷰에 결합될 variable name 은 item 이여야함

    var itemList : ArrayList<item> = arrayListOf()

    init {
        itemList = itemlist;
    }


    //베이스가 될 커스텀 홀더
    inner class CustomViewHolder(val binding: viewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item : item){
            binding.setVariable(BR.item,item)
        }
    }

    fun addItem(item : item){
        itemList.add(item);
    }


    //여기서 바인딩을 할당
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding : viewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),layoutId,parent,false)
        return CustomViewHolder(binding)

    }


    override fun getItemCount(): Int {
        return itemList.count()
    }

    //뷰홀더에 데이터 할당
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }
}