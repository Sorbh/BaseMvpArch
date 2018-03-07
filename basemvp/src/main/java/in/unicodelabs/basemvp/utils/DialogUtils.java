package in.unicodelabs.basemvp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

public class DialogUtils {
    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.show();
//        if (progressDialog.getWindow() != null) {
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }
//        progressDialog.setContentView(R.layout.progress_dialog);
//        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
    public static ProgressDialog showLoadingDialog(Context context, String message) {
        if(TextUtils.isEmpty(message)){
            return showLoadingDialog(context);
        }

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
//        if (progressDialog.getWindow() != null) {
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }
//        progressDialog.setContentView(R.layout.progress_dialog);
//        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static void infoPopup(Context context, String message, String buttonName) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
//        dialog.setCancelable(true);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.CENTER);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        TextView btnTxt = (TextView) dialog.findViewById(R.id.txtOK);
//        TextView txt = (TextView) dialog.findViewById(R.id.txt);
//
//        txt.setText(message);
//        btnTxt.setText(buttonName);
//        btnTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public interface SelectListner {
        public void onSelect(int pos, Object value);
    }
}
