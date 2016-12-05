package com.softgen.gate.asynctask;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.softgen.gate.provider.OTP_Generation;
import com.softgen.gate.provider.UtilsProvider;
import com.softgen.gate.sender.GMailSender;

/**
 * Created by 9Jeevan on 05-10-2016.
 */
public class SendEmailTask extends AsyncTask<String, String, String> implements DialogInterface.OnCancelListener {
    private UtilsProvider utils;
    private Context mContext;
    private String mEmailId;
    private Callback mCallback;

    public SendEmailTask(Callback callback, Context context, String emailId) {
        mCallback = callback;
        mContext = context;
        this.mEmailId = emailId;
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        utils = new UtilsProvider(mContext);
        utils.showProgress("", "Sending OTP..");
    }

    @Override
    protected String doInBackground(String... params) {
        GMailSender mailsender = new GMailSender("gate4ru@gmail.com", "gate123$");
        String[] toArr = {mEmailId};
        String tempOtp = OTP_Generation.generateOTP(6);
        mailsender.set_to(toArr);
        mailsender.set_from("gate4ru@gmail.com");
        mailsender.set_subject("OTP Validation");
        mailsender.setBody("Hello," + "\n"
                + "\nThe Account has been sent with the OTP." + "\n"
                + "\n" + "Please check below." + "\n" + "\n"
                + "The OTP is: " + tempOtp + "\n" + "\n"
                + "Thank You For Using Gate Service");
        try {
            if (mailsender.send()) {
                Log.d("EmailSent successfully.", "Email was sent successfully.");
                return tempOtp;
            } else {
                utils.hideProgress();
                UtilsProvider.alertBox(mContext, "Error");
                Log.d("Email was not sent.", "Email was not sent.");
            }
        } catch (Exception e) {
            Log.e("MailApp", "Could not send email", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String aResult) {
        super.onPostExecute(aResult);
        utils.hideProgress();
        mCallback.onEmailSentResult(this, aResult);
    }

    public interface Callback {
        void onEmailSentResult(SendEmailTask task, String result);
    }
}
