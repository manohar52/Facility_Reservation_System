package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertBox {
    public int okay;
    public void showOKDialog(Context context, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you Sure ?");
        builder.setPositiveButton(android.R.string.ok,listener);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }
}

//Usage
//
//        AlertBox ab = new AlertBox();
//        ab.showOKDialog(.this, new DialogInterface.OnClickListener(){
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//        });