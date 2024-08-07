package com.example.assignment2;

//******************************************//
//      By: Hashmeet Singh Saini            //
//      August 1st 2024                     //
//******************************************//

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//Contact Adaptor
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    //class memebers
    Context context;
    List<Contact> contacts;

    //Constructor
    public ContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    //This function is used to inflate the view with our list item and sends the inflated view to
    //the view holder
    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ContactAdapter.ContactViewHolder(view);
    }

    //This function binds a list item to the data from the list. Its job is to populate the list item
    //with the relevant information.
//    @Override
//    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
//        holder.ivPhoto.setImageResource(contacts.get(position).getImageId());
//        holder.tvName.setText(contacts.get(position).getName());
//        holder.tvEmail.setText(contacts.get(position).getEmail());
//        holder.tvPhone.setText(contacts.get(position).getPhone());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, EditContactActivity.class);
//                intent.putExtra("isEdit",true);
//                intent.putExtra("contact", contacts.get(position));
//                intent.putExtra("position", holder.getAdapterPosition());
//                context.startActivity(intent);
//            }
//        });
//    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        holder.tvName.setText(contacts.get(position).getName());
        holder.tvEmail.setText(contacts.get(position).getEmail());
        holder.tvPhone.setText(contacts.get(position).getPhone());

        holder.itemView.setOnClickListener(v -> {
            ((MainActivity) context).onContactClick(contacts.get(position), position);
        });
    }

    //defines the number of items in the recycler view. (length of the list)
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    //Constituent class that holds the view. and stores the information of all views in itself.
    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        //class members
        TextView tvName, tvEmail, tvPhone;

        //constructor
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.tvName);
            tvEmail= itemView.findViewById(R.id.tvEmail);
            tvPhone= itemView.findViewById(R.id.tvPhone);

        }
    }
}
