package com.example.dabassa.solarify;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;


import java.io.FileOutputStream;
import java.io.IOException;

import static android.widget.Toast.LENGTH_SHORT;

public class SavingsActivity extends AppCompatActivity {

    //Defining all elements of savings.xml
    Button back;
    TextView sys_cap;
    TextView space_req;
    TextView cost_final;
    TextView irr;
    TextView year_units;
    TextView avg_savings;
    TextView payback_period;
    TextView co2;
    TextView trees;
    EditText EMAIL;
    EditText UNAME;
    EditText PHONE;




    protected void onCreate(Bundle savedInstanceState)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.symbol);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savings);
        //Prevent keyboard opening by default
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);




        //Declaring all elements of savins.xml
        back = (Button) findViewById(R.id.back);
        sys_cap = (TextView) findViewById(R.id.sys_cap);
        space_req = (TextView) findViewById(R.id.space_req);
        cost_final = (TextView) findViewById(R.id.cost_final);
        year_units = (TextView) findViewById(R.id.year_units);
        avg_savings = (TextView) findViewById(R.id.avg_savings);
        payback_period = (TextView) findViewById(R.id.payback_period);
        irr = findViewById(R.id.IRR);
        co2 = findViewById(R.id.co2);
        trees = findViewById(R.id.trees);
        EMAIL = (EditText) findViewById(R.id.umail);
        UNAME = (EditText) findViewById(R.id.uname);
        PHONE = (EditText) findViewById(R.id.uphone);


        //Reading values from MainActvity.java
        final String[] passedArg= getIntent().getStringArrayExtra("arg");
        Integer N = passedArg[4].length();
        String sys = "  "; String space = "  "; String cost = "   "; String year = "   "; String avg = " ";
        String payback = "          "; String co = ""; String tree = "          "; String ir = "        ";
        for(int i=0; i<N-passedArg[0].length(); i++)
            sys += " ";

        for(int i=0; i<N-passedArg[1].length(); i++)
            space += " ";

        for(int i=0; i<N-passedArg[2].length(); i++)
            cost += " ";

        for(int i=0; i<N-passedArg[4].length(); i++)
            year += " ";

        for(int i=0; i<N-passedArg[5].length(); i++)
            avg += " ";

        for(int i=0; i<N-passedArg[6].length(); i++)
            payback += " ";

        for(int i=0; i<passedArg[7].length()-passedArg[8].length(); i++)
            tree += " ";

        for(int i=0; i<N-passedArg[9].length(); i++)
            ir += " ";

        sys_cap.setText(passedArg[0]+sys);
        space_req.setText(passedArg[1]+space);
        cost_final.setText(passedArg[2]+cost);
        year_units.setText(passedArg[4]+year);
        avg_savings.setText(passedArg[5]+avg);
        payback_period.setText(passedArg[6]+payback);
        co2.setText(passedArg[7]+co);
        trees.setText(passedArg[8]+tree);
        irr.setText(passedArg[9]+ir);

        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(720, 1024, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

                    /*Bitmap bitmap = Bitmap.createBitmap(
                            600, // Width
                            300, // Height
                            Bitmap.Config.ARGB_8888 // Config
                    );*/

        // draw something on the page
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.rgb(255,140,0));
        //paint.setTypeface(Typeface.create())
        paint.setTextSize(12);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sybol);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.address);
        canvas.drawBitmap(bmp, null, new Rect(30,20,700,60), paint);
        canvas.drawText("www.solarify.in", 600,20,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("askus@solarify.in",600,35,paint);
        canvas.drawText("+91-9742454443",600,50,paint);
        //canvas.drawBitmap(bmp2,null, new Rect(600,20,700,60), paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText(UNAME.getText().toString(),30,100,paint);
        canvas.drawText(passedArg[10],30,120,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Congratulations! You have taken the first step towards using a cleaner form of electricity. " +
                "We have used your inputs on our",30,160,paint);




        //OVERVIEW
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("Solar Savings Calculator",30,180,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(", to determine your requirements and potential impact. ",164,180,paint);
        paint.setColor(Color.rgb(255,140,0));
        paint.setTextSize(14);
        canvas.drawText("OVERVIEW",300, 220,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Break-even period                    Yearly Savings with Solar                    Internal Rate of Return", 90, 260, paint);
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.circle2);
        canvas.drawBitmap(bmp,null,new Rect(85,290,195,390),paint);
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.circlebig2);
        canvas.drawBitmap(bmp,null,new Rect(265,280,405,410), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.circle2);
        canvas.drawBitmap(bmp,null, new Rect(490,290,600,390), paint);
        canvas.drawText(passedArg[6],112,345,paint);
        canvas.drawText(passedArg[5], 300, 350, paint);
        canvas.drawText(passedArg[9], 522, 345, paint);

        //Detailed report
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("DETAILED SOLAR SAVINGS REPORT", 220, 450, paint);

        //ICONS
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.size);
        canvas.drawBitmap(bmp, null, new Rect(90,480,105,495), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.space);
        canvas.drawBitmap(bmp, null, new Rect(90,510,105, 525), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.power);
        canvas.drawBitmap(bmp,null,new Rect(88, 540, 105, 560), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cost);
        canvas.drawBitmap(bmp, null, new Rect(90,590,105, 605), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.year);
        canvas.drawBitmap(bmp, null, new Rect(90,620,105,635), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.payback);
        canvas.drawBitmap(bmp, null, new Rect(88,650,105,675), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.irr);
        canvas.drawBitmap(bmp, null, new Rect(90,680,105,695), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.co2);
        canvas.drawBitmap(bmp, null, new Rect(88,730,105, 750), paint);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
        canvas.drawBitmap(bmp, null, new Rect(88,760,105, 778), paint);


        //Attribute Name, Semicolon and Attribute values
        paint.setColor(Color.BLACK);
        canvas.drawText("System size", 120, 493, paint); canvas.drawText(":", 330, 493, paint); canvas.drawText(passedArg[0], 450, 493, paint);
        canvas.drawText("Space required", 120, 523, paint); canvas.drawText(":", 330, 523, paint); canvas.drawText(passedArg[1], 450, 523, paint);
        canvas.drawText("Power generated", 120, 553, paint); canvas.drawText(":", 330, 553, paint); canvas.drawText(passedArg[4], 450, 553, paint);
        canvas.drawText("Cost of the system", 120, 603, paint); canvas.drawText(":", 330, 603, paint); canvas.drawText(passedArg[2], 450, 603, paint);
        canvas.drawText("Yearly savings", 120, 633, paint); canvas.drawText(":", 330, 633, paint); canvas.drawText(passedArg[5], 450, 633, paint);
        canvas.drawText("Break-even period", 120, 663, paint); canvas.drawText(":", 330, 663, paint); canvas.drawText(passedArg[6], 450, 663, paint);
        canvas.drawText("Internal rate of return (*IRR)", 120, 693, paint); canvas.drawText(":", 330, 693, paint); canvas.drawText(passedArg[9], 450, 693, paint);
        canvas.drawText("Carbon emissions avoided", 120, 743, paint); canvas.drawText(":", 330, 743, paint); canvas.drawText(passedArg[7], 450, 743, paint);
        canvas.drawText("Trees saved", 120, 773, paint); canvas.drawText(":", 330, 773, paint); canvas.drawText(passedArg[8], 450, 773, paint);


        //TEXT
        paint.setTextSize(12);
        canvas.drawText("Today, investing in solar power is better " +
                "than putting your money in a bank’s fixed deposit (*see IRR value). You can now", 30, 830, paint);

        canvas.drawText("save money while making the world a " +
                "better place. Move forward in your solar journey by talking to an expert from our", 30, 850, paint);

        canvas.drawText("team.",30,870,paint);

        //FOOTER
        paint.setColor(Color.rgb(128,128,128));
        canvas.drawText("© 2019 Azotea Energy Private Limited.", 30, 980, paint); canvas.drawText("Office: #475, Floor 1, 33rd Cross, 7th Main,", 475, 980, paint);
        canvas.drawText("All Rights Reserved.", 30, 1000, paint); canvas.drawText("Jayanagar 4th Block, Bangalore – 560011", 475, 1000, paint);

        document.finishPage(page);

        //NEW PAGE
        pageInfo = new PdfDocument.PageInfo.Builder(720, 1024, 2).create();

        // start a page
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint.setTextSize(12);

        //HEADER
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sybol);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.address);
        canvas.drawBitmap(bmp, null, new Rect(30,20,700,60), paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("www.solarify.in", 600,20,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("askus@solarify.in",600,35,paint);
        canvas.drawText("+91-9742454443",600,50,paint);

        //TEXT
        paint.setTextSize(18);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("SCHEDULE A FREE CONSULT WITH US", 200, 120, paint);
        paint.setTextSize(12);
        paint.setColor(Color.BLACK);
        canvas.drawText("You can simply drop a", 150, 140, paint);
        paint.setColor(Color.rgb(0,255,0));
        canvas.drawText("Whatsapp message", 278, 140, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("of this page on 9742454443.", 390, 140, paint);

        canvas.drawText("We are a young team of eco-engineers who are transiting homes, communities, businesses, and institutions to sustainable,", 30, 180, paint);
        canvas.drawText("energy-efficient, and solar-powered buildings.", 30, 200, paint);

        //IMAGE
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.save);
        canvas.drawBitmap(bmp, null, new Rect(70, 250, 640, 620), paint);

        //TEXT
        canvas.drawText("Investing in solar energy gives you",30, 650, paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("80%", 217, 650, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("more financial returns than putting your money in the bank.", 242, 650,paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("For this reason, going solar today is practical and profitable.", 30, 670, paint);

        //WHAT DO WE OFFER
        paint.setColor(Color.BLACK);
        paint.setTextSize(14);
        canvas.drawText("WHAT DO WE OFFER ?", 280, 720, paint);
        paint.setTextSize(12);
        canvas.drawText("1. ROOFTOP SOLAR: ON-GRID, OFF-GRID, HYBRID PV SYSTEMS", 50, 760, paint);
        canvas.drawText("2. BUILDING INTEGRATED PV SYSTEM", 50, 790, paint);
        canvas.drawText("3. LITHIUM-ION BATTERY STORAGE", 50, 820, paint);
        canvas.drawText("4. END-TO-END EXECUTION OF PROJECTS ", 50, 850, paint);

        //FOOTER
        paint.setTextSize(12);
        paint.setColor(Color.rgb(128,128,128));
        canvas.drawText("© 2019 Azotea Energy Private Limited.", 30, 980, paint); canvas.drawText("Office: #475, Floor 1, 33rd Cross, 7th Main,", 475, 980, paint);
        canvas.drawText("All Rights Reserved.", 30, 1000, paint); canvas.drawText("Jayanagar 4th Block, Bangalore – 560011", 475, 1000, paint);


        document.finishPage(page);

        //NEW PAGE
        pageInfo = new PdfDocument.PageInfo.Builder(720, 1024, 3).create();

        // start a page
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint.setTextSize(12);

        //HEADER
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sybol);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.address);
        canvas.drawBitmap(bmp, null, new Rect(30,20,700,60), paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("www.solarify.in", 600,20,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("askus@solarify.in",600,35,paint);
        canvas.drawText("+91-9742454443",600,50,paint);

        //TEXT - WHAT WE HAVE DONE
        paint.setTextSize(14);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        canvas.drawText("WHAT HAVE WE DONE",280,100, paint);
        paint.setTextSize(12);
        canvas.drawBitmap(bmp, null, new Rect(50, 130, 60, 140), paint);
        canvas.drawText("More than 400 kilowatt of solar projects installed", 70, 139, paint);

        canvas.drawBitmap(bmp, null, new Rect(50, 165, 60, 175), paint);
        canvas.drawText("Consulted with 100s of homeowners, administrators, communities and businesses", 70, 174, paint);

        paint.setTextSize(14);
        canvas.drawText("OUR 2018 HIGHLIGHTS", 275, 225, paint);
        paint.setTextSize(12);

        canvas.drawBitmap(bmp, null, new Rect(50, 255, 60, 265), paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("Nagarbhavi", 70, 264, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(": Bangalore’s first Lithium-ion powered home",136, 264, paint);

        canvas.drawBitmap(bmp, null, new Rect(50, 285, 60, 295), paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("Brigade Petunia", 70, 294, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(": Bangalore’s first apartment building to use solar for 100% of their community needs",158, 294, paint);

        canvas.drawBitmap(bmp, null, new Rect(50, 315, 60, 325), paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("Basavanagudi", 70, 324, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(": Bangalore’s first solar powered public park",150, 324, paint);

        canvas.drawBitmap(bmp, null, new Rect(50, 345, 60, 355), paint);
        paint.setColor(Color.rgb(255,140,0));
        canvas.drawText("Mittal Auriga", 70, 354, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(": India’s first carbon neutral apartment",142, 354, paint);


        //FOOTER
        paint.setTextSize(12);
        paint.setColor(Color.rgb(128,128,128));
        canvas.drawText("© 2019 Azotea Energy Private Limited.", 30, 980, paint); canvas.drawText("Office: #475, Floor 1, 33rd Cross, 7th Main,", 475, 980, paint);
        canvas.drawText("All Rights Reserved.", 30, 1000, paint); canvas.drawText("Jayanagar 4th Block, Bangalore – 560011", 475, 1000, paint);



        document.finishPage(page);


                        /*canvas.drawText("System Capacity: " + passedArg[0], 0, 20, paint);
                        canvas.drawText("Space Required: " + passedArg[1], 0, 40, paint);
                        canvas.drawText("Cost of system: " + passedArg[2], 0, 60, paint);
                        canvas.drawText("Monthy units generated: " + passedArg[3], 0, 80, paint);
                        canvas.drawText("Yearly units generated: " + passedArg[4], 0, 100, paint);
                        canvas.drawText("Average yearly savings: " + passedArg[5], 0, 120, paint);
                        canvas.drawText("Payback period: " + passedArg[6], 0, 140, paint);*/

        final String outpath = Environment.getExternalStorageDirectory() + "/mypdf.pdf";
        try {
            document.writeTo(new FileOutputStream(outpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();

        /*print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavingsActivity.this, Print.class);
                String[] arr = new String[1];
                arr[0] = outpath;
                intent.putExtra("print", arr);
                startActivity(intent);
            }
        });*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname_value = UNAME.getText().toString();
                String email_value = EMAIL.getText().toString();
                String phone_value = PHONE.getText().toString();


                if(!email_value.isEmpty()) {

                    if (Patterns.EMAIL_ADDRESS.matcher(email_value).matches()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Email sent! Please check your mail id for the detailed report", LENGTH_SHORT);
                        toast.show();


                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute(email_value, "solarify_details");

                    }

                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Email is not valid, try again", LENGTH_SHORT);
                        toast.show();
                    }
                }

                if (uname_value.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter your Name", LENGTH_SHORT);
                    toast.show();
                } else if(email_value.isEmpty() && phone_value.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter your Email or phone", LENGTH_SHORT);
                    toast.show();
                }

                else{
                    if(!phone_value.isEmpty() && phone_value.length() != 10)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a 10-digit mobile no", LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute("info@solarify.in", "user_details");
                        Intent intent = new Intent(SavingsActivity.this, detailsSubmitted.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }

            }
        });


    }


    public class AsyncTaskRunner extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            final String username = "info@solarify.in";
            final String password = "solarifyindia";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.zoho.com"); //SMTP Host
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
            props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
            props.put("mail.smtp.port", "465"); //SMTP Port



            if(strings[1].equals("solarify_details")) {

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("info@solarify.in"));
                    message.setRecipients(MimeMessage.RecipientType.TO,
                            InternetAddress.parse(strings[0]));
                    message.setSubject("Detailed report from Solarify");
                    message.setText("Dear Mail Crawler,"
                            + "\n\n No spam to my email, please!");

                    String body = "Hello " + UNAME.getText().toString() + "\n\n" +
                                  "Greetings from Solarify!\n\n" +
                                  "We appreciate your interest towards sustainability. A heartiest congratulation from team Solarify from being just a step closer to becoming an independent power producer and reducing your bills to zero.\n\n" +
                                  "Solarify is a platform to accelerate the deployment of rooftop solar in India by making it easier for rooftop owners to understand their solar energy requirements and providing access to technical/policy expertise, best market prices & project finance.\n\n" +
                                  "We have attached a detailed analysis of your requirement below.\n\n"+
                                  "Thank You,\n" +
                                  "Team Solarify\n" +
                                  "+91-9742454443";

                    DataHandler dh = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));

                    MimeBodyPart messageBodyPart = new MimeBodyPart();

                    Multipart multipart = new MimeMultipart();

                    messageBodyPart = new MimeBodyPart();
                    MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                    String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mypdf.pdf";
                    String fileName = "SolarifyDetails.pdf";
                    FileDataSource source = new FileDataSource(file);
                    messageBodyPart.setDataHandler(new DataHandler((javax.activation.DataSource) source));
                    messageBodyPart.setFileName(fileName);
                    messageBodyPart2.setDataHandler(dh);

                    multipart.addBodyPart(messageBodyPart);
                    multipart.addBodyPart(messageBodyPart2);


                    message.setContent(multipart);

                    Transport.send(message);

                    System.out.println("Done");

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                return null;

            }

            else{

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("info@solarify.in"));
                    message.setRecipients(MimeMessage.RecipientType.TO,
                            InternetAddress.parse(strings[0]));
                    message.setSubject("User_logged");
                    message.setText("Dear Mail Crawler,"
                            + "\n\n No spam to my email, please!");

                    String body = "Username: " + UNAME.getText().toString() + "\n" +
                                  "Email: " + EMAIL.getText().toString() + "\n" +
                                  "Phone: " + PHONE.getText().toString() +"\n";
                    DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
                    message.setDataHandler(handler);
                    Transport.send(message);

                    System.out.println("Done");

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }

                return null;
            }
        }
    }


}
