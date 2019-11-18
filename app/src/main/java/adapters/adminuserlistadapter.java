package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facilityreservationsystem.R;

import java.util.List;

import model.Reservation;
import model.SysUser;

public class adminuserlistadapter extends RecyclerView.Adapter<adminuserlistadapter.MyViewHolder> {

    private List<SysUser> userList;
    private static Context ct;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvusername,tvfname,tvlname,tvphone;

        public MyViewHolder(View view) {
            super(view);
            ct = view.getContext();
            tvusername = (TextView) view.findViewById(R.id.tvusername);
            tvfname = (TextView) view.findViewById(R.id.tvfname);
            tvlname = (TextView) view.findViewById(R.id.tvlname);
            tvphone = (TextView) view.findViewById(R.id.tvphone);
        }
    }

    public adminuserlistadapter(List<SysUser> userList){

        this.userList = userList;
    }

    @NonNull
    @Override
    public adminuserlistadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adminuseritem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adminuserlistadapter.MyViewHolder holder, int position) {
        SysUser user = userList.get(position);
        holder.tvusername.setText(user.getUsername());
        holder.tvfname.setText(user.getFname());
        holder.tvlname.setText(user.getLname());
        holder.tvphone.setText(String.valueOf(user.getPhone()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
