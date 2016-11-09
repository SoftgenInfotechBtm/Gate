package com.softgen.gate.asynctask;

/**
 * Created by 9Jeevan on 05-10-2016.
 */
public class SendEmail  {
//TODO: Mahesh Coding With constructor and dynamic input to know everywhere.
//    @Override
//    public void onCancel(DialogInterface dialog) {
//
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        utils.showProgress("", "Sending OTP..");
//    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        super.onPostExecute(result);
//        utils.hideProgress();
//        register.setVisibility(View.VISIBLE);
//        checkTermsConditions.setVisibility(View.VISIBLE);
//        inputLayoutEnterOtp.setVisibility(View.VISIBLE);
//        resend.setVisibility(View.VISIBLE);
//        otp.setVisibility(View.GONE);
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//        mailsender = new GMailSender("gate4ru@gmail.com", "gate4ruc$123");
//        String[] toArr = {emailid};
//        tempOtp = OTP_Generation.generateOTP(6);
//        mailsender.set_to(toArr);
//        mailsender.set_from("gate4ru@gmail.com");
//        mailsender.set_subject("OTP Validation");
//        mailsender.setBody("Hello," + "\n"
//                + "\nThe Account has been sent with the OTP." + "\n"
//                + "\n" + "Please check below." + "\n" + "\n"
//                + "The OTP is: " + tempOtp + "\n" + "\n"
//                + "Thank You For Using Gate Service");
//        try {
//            if (mailsender.send()) {
//                Log.d("Emailsent successfully.",
//                        "Email was sent successfully.");
//            } else {
//                utils.hideProgress();
//                UtilsProvider.alertBox(mActivity, "Error");
//                Log.d("Email was not sent.", "Email was not sent.");
//            }
//        } catch (Exception e) {
//            Log.e("MailApp", "Could not send email", e);
//        }
//        Log.e("after execute method ", "Coming here");
//        Log.e(">>>>>>>>>> tempOtp", tempOtp + "coming here");
//        OTPMaster master1 = new OTPMaster();
//        master1.setUserID(userid);
//        master1.setOtp(tempOtp);
//        master1.setEmail(email1);
//        master1.setMobile(mobileno);
//        master1.setCreatedAt(new Date());
//        Log.e("values", master1 + "");
//        db.createOTPUser(master1);
////            db.updateOtpUser(master1);
////            finish();
//        Log.e("OTPMaster object", master1.toString());
//        return null;
//    }
}
