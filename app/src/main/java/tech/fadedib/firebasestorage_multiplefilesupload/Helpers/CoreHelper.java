package tech.fadedib.firebasestorage_multiplefilesupload.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/*This is a universal class to write function which can be used anywhere in activities of the whole app*/

/*It is not necessary to make this class. You can simply write functions in your activity.java class if your
function is for a specific activity only.*/

public class CoreHelper {
    Context context;

    public CoreHelper(Context context) {
        this.context = context;
    }

    public void createSnackBar(View view, String message, String actionText, View.OnClickListener actionClickListener, int time){
        Snackbar.make(view, message, time).setAction(actionText, actionClickListener).show();
    }

    public void createAlert(String alertTitle, String alertMessage, String positiveButtonText,
                            String negativeButtonText, DialogInterface.OnClickListener positiveButtonListener,
                            DialogInterface.OnClickListener negativeButtonListener,
                            DialogInterface.OnDismissListener dismissListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(alertTitle)
                .setMessage(alertMessage)
                .setPositiveButton(positiveButtonText, positiveButtonListener)
                .setNegativeButton(negativeButtonText, negativeButtonListener)
                .setOnDismissListener(dismissListener)
                .create().show();
    }

    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
