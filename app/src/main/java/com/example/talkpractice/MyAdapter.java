package com.example.talkpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Chat> mDataset;
    String stMyEmail="";
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public TextView nickname;
        public MyViewHolder(View v){
            super(v);
            textView =v.findViewById(R.id.tvChat);
            nickname =v.findViewById(R.id.nickname);
        }
    }

    @Override
    public int getItemViewType(int position) {
 //       return super.getItemViewType(position)
        if(mDataset.get(position).email.equals(stMyEmail))
            return 1;
        else
            if(position != 0 && mDataset.get(position-1).email.equals(mDataset.get(position).email))
                return 3;
            else
                return 2;
    }

    public MyAdapter(ArrayList<Chat> myDataset,String stEmail){
        mDataset=myDataset;
        this.stMyEmail=stEmail;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v=(View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view,parent,false);
        if(viewType == 3)
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_text_view2,parent,false);
        else if(viewType==1){
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.right_text_view,parent,false);
        }

        MyViewHolder vh=new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.textView.setText(mDataset.get(position).getText());
        if(getItemViewType(position) == 2){
            String nick = mDataset.get(position).getEmail();
            String[] mobNum = nick.split("@");
            nick = mobNum[0];

            holder.nickname.setText(nick);
        }
    }
    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}
