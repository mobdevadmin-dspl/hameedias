package com.datamation.hmdsfa.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.VanStockController;
import com.datamation.hmdsfa.fragment.FragmentTools;
import com.datamation.hmdsfa.model.VanStock;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ReportActivity {

    Context context;
    String APP_NAME = "HameediaSFA";
    String FOLDER_NAME = "VAN STOCK REPORTS";
    File vanStockfile;
    int pagewidth = 595;
    int pageheight = 842;
    Date currentTime = Calendar.getInstance().getTime();

    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String DATE = dateFormat.format(now);
    double totAmt = 0.00;
    double totQty = 0;

    ArrayList<VanStock> list_1 ;
    ArrayList<VanStock> list_2;
    ArrayList<VanStock> list_3 ;
    ArrayList<VanStock> list_4 ;
    ArrayList<VanStock> list_5 ;
    ArrayList<VanStock> list_6 ;
    ArrayList<VanStock> list_7 ;
    ArrayList<VanStock> list_8 ;
    ArrayList<VanStock> list_9 ;
    ArrayList<VanStock> list_10 ;
    ArrayList<VanStock> list_11 ;
    ArrayList<VanStock> list_12 ;


    ArrayList<VanStock> qtylist_1 ;
    ArrayList<VanStock> qtylist_2;
    ArrayList<VanStock> qtylist_3 ;
    ArrayList<VanStock> qtylist_4 ;
    ArrayList<VanStock> qtylist_5 ;
    ArrayList<VanStock> qtylist_6 ;

    public ReportActivity(Context context) {
        this.context = context;
    }


    public void reportDialog() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                .content("Are you sure, Do you want to create report?")
                .positiveColor(ContextCompat.getColor(context, R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(context, R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        printVanStockReport();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }


    private void printVanStockReport() {

        PdfDocument vanStockReportPDF = new PdfDocument();
        Paint vanStockReportPaint = new Paint();

        PdfDocument.PageInfo vanStockReportPageInfo1 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page vanStockReportPage1 = vanStockReportPDF.startPage(vanStockReportPageInfo1);
        Canvas canvasStockM1 = vanStockReportPage1.getCanvas();


        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTextSize(24);
        vanStockReportPaint.setColor(Color.BLACK);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvasStockM1.drawText("H.S Marketing Private Limited", 20, 40, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTextSize(16);
        vanStockReportPaint.setColor(Color.BLACK);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvasStockM1.drawText("22/2, Rawathawatta Rd,Rawathawatta, Moratuwa. Colombo", 20, 60, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTextSize(24);
        vanStockReportPaint.setColor(Color.BLACK);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvasStockM1.drawText("Posted Van Sales Out", 20, 100, vanStockReportPaint);

        final SimpleDateFormat dateFormatT = new SimpleDateFormat("HH:mm aaa");
        String time = dateFormatT.format(new Date());

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Report Printed On :   " + DATE + "    " + time, 20, 140, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Document No.        :   HSM/VSOUT/21/00242 ", 20, 160, vanStockReportPaint);

        String locCode = new SalRepController(context).getCurrentLoccode();
        String repCode = new SalRepController(context).getCurrentRepCode();
        String repName = new SalRepController(context).getRepName(repCode);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Location From          :    " + "MAINSTORES", 340, 160, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Location To                :   " + locCode, 340, 180, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Date                            :   " + DATE, 20, 200, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Sales Person Code  :   " + repName, 340, 200, vanStockReportPaint);

        /***********************************************************************************************************/

        //table - set header titles
        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText(" # ", 15, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Item No. ", 35, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Barcode", 90, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Variant", 150, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Description", 245, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Article No.", 350, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Quantity", 430, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Price", 490, 240, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(12);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM1.drawText("Amount", 530, 240, vanStockReportPaint);

        canvasStockM1.drawLine(20, 255, pagewidth - 20, 255, vanStockReportPaint);

        // -------------------------------  Add data to table  ---------------------------------------------------------------------

        int record = 1;
        int y1 = 270;
        int maxLineLength = 11;
        // Add data to table
        ArrayList<VanStock> stockList = new VanStockController(context).getStockDetailList(locCode);
        list_1 = new ArrayList<VanStock>(stockList.subList(0,28));
        list_2 = new ArrayList<VanStock>(stockList.subList(29, 69));
        list_3 = new ArrayList<VanStock>(stockList.subList(70, 110));
        list_4 = new ArrayList<VanStock>(stockList.subList(111, 151));
        list_5 = new ArrayList<VanStock>(stockList.subList(152,192));
        list_6 = new ArrayList<VanStock>(stockList.subList(193, 233));
        list_7 = new ArrayList<VanStock>(stockList.subList(234,274));
        list_8 = new ArrayList<VanStock>(stockList.subList(275,315));
        list_9 = new ArrayList<VanStock>(stockList.subList(316,356));
        list_9 = new ArrayList<VanStock>(stockList.subList(357,397));
        list_10 = new ArrayList<VanStock>(stockList.subList(398,438));
        list_11 = new ArrayList<VanStock>(stockList.subList(439,479));
        list_12 = new ArrayList<VanStock>(stockList.subList(480,stockList.size()));

        for (VanStock vanStock : list_1) {

                vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
                vanStockReportPaint.setTextSize(8);
                vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                canvasStockM1.drawText("" + record, 15, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getItem_No(), 35, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getBarcode(), 85, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getVariant_Code(), 150, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getDescription(), 205, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getArticleNo(), 350, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getQuantity_Issued(), 440, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getUnitPrice(), 490, y1, vanStockReportPaint);
                canvasStockM1.drawText(vanStock.getAmount(), 535, y1, vanStockReportPaint);

                double amt = Double.parseDouble(vanStock.getAmount());
                double qty = Double.parseDouble(vanStock.getQuantity_Issued());

                totAmt = totAmt + amt ;
                totQty = totQty + qty;

                record++;
                y1 = y1 + 20;
        }

        vanStockReportPDF.finishPage(vanStockReportPage1);

        //*****************************************************************************************************************************************

        //Start Second Page
        PdfDocument.PageInfo vanStockReportPageInfo2 = new PdfDocument.PageInfo.Builder(595, 842, 2).create();
        PdfDocument.Page vanStockReportPage2 = vanStockReportPDF.startPage(vanStockReportPageInfo2);
        Canvas canvasStockM2 = vanStockReportPage2.getCanvas();

        int y2 = 30;

        for (VanStock vanStock : list_2) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM2.drawText("" + record, 15, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getItem_No(), 35, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getBarcode(), 85, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getVariant_Code(), 150, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getDescription(), 205, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getArticleNo(), 350, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getQuantity_Issued(), 440, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getUnitPrice(), 490, y2, vanStockReportPaint);
            canvasStockM2.drawText(vanStock.getAmount(), 535, y2, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y2 = y2 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage2);

        //page 3
        PdfDocument.PageInfo vanStockReportPageInfo3 = new PdfDocument.PageInfo.Builder(595, 842, 3).create();
        PdfDocument.Page vanStockReportPage3 = vanStockReportPDF.startPage(vanStockReportPageInfo3);
        Canvas canvasStockM3 = vanStockReportPage3.getCanvas();

        int y3 = 30;

        for (VanStock vanStock : list_3) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM3.drawText("" + record, 15, y3, vanStockReportPaint);
            canvasStockM3.drawText(vanStock.getItem_No(), 35, y3, vanStockReportPaint);
            canvasStockM3.drawText(vanStock.getBarcode(), 85, y3, vanStockReportPaint);
            canvasStockM3.drawText(vanStock.getVariant_Code(), 150, y3, vanStockReportPaint);
            canvasStockM3.drawText(vanStock.getDescription(), 205, y3, vanStockReportPaint);
           canvasStockM3.drawText(vanStock.getArticleNo(), 350, y3, vanStockReportPaint);
           canvasStockM3.drawText(vanStock.getQuantity_Issued(), 440, y3, vanStockReportPaint);
           canvasStockM3.drawText(vanStock.getUnitPrice(), 490, y3, vanStockReportPaint);
           canvasStockM3.drawText(vanStock.getAmount(), 535, y3, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y3 = y3 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage3);

        //page 4
        PdfDocument.PageInfo vanStockReportPageInfo4 = new PdfDocument.PageInfo.Builder(595, 842, 4).create();
        PdfDocument.Page vanStockReportPage4 = vanStockReportPDF.startPage(vanStockReportPageInfo4);
        Canvas canvasStockM4 = vanStockReportPage4.getCanvas();

        int y4 = 30;

        for (VanStock vanStock : list_4) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM4.drawText("" + record, 15, y4, vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getItem_No(), 35, y4, vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getBarcode(), 85, y4, vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getVariant_Code(), 150, y4, vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getDescription(), 205, y4, vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getArticleNo(), 350, y4 ,vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getQuantity_Issued(), 440, y4, vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getUnitPrice(), 490, y4, vanStockReportPaint);
            canvasStockM4.drawText(vanStock.getAmount(), 535, y4, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y4 = y4 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage4);

        //page 5
        PdfDocument.PageInfo vanStockReportPageInfo5 = new PdfDocument.PageInfo.Builder(595, 842, 5).create();
        PdfDocument.Page vanStockReportPage5 = vanStockReportPDF.startPage(vanStockReportPageInfo5);
        Canvas canvasStockM5 = vanStockReportPage5.getCanvas();

        int y5 = 30;

        for (VanStock vanStock : list_5) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM5.drawText("" + record, 15, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getItem_No(), 35, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getBarcode(), 85, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getVariant_Code(), 150, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getDescription(), 205, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getArticleNo(), 350, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getQuantity_Issued(), 440, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getUnitPrice(), 490, y5, vanStockReportPaint);
            canvasStockM5.drawText(vanStock.getAmount(), 535, y5, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y5 = y5 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage5);

        //page 6
        PdfDocument.PageInfo vanStockReportPageInfo6 = new PdfDocument.PageInfo.Builder(595, 842, 6).create();
        PdfDocument.Page vanStockReportPage6 = vanStockReportPDF.startPage(vanStockReportPageInfo6);
        Canvas canvasStockM6 = vanStockReportPage6.getCanvas();

        int y6 = 30;

        for (VanStock vanStock : list_6) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM6.drawText("" + record, 15, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getItem_No(), 35, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getBarcode(), 85, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getVariant_Code(), 150, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getDescription(), 205, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getArticleNo(), 350, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getQuantity_Issued(), 440, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getUnitPrice(), 490, y6, vanStockReportPaint);
            canvasStockM6.drawText(vanStock.getAmount(), 535, y6, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y6 = y6 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage6);

        //page 7
        PdfDocument.PageInfo vanStockReportPageInfo7 = new PdfDocument.PageInfo.Builder(595, 842, 7).create();
        PdfDocument.Page vanStockReportPage7 = vanStockReportPDF.startPage(vanStockReportPageInfo7);
        Canvas canvasStockM7 = vanStockReportPage7.getCanvas();

        int y7 = 30;

        for (VanStock vanStock : list_7) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM7.drawText("" + record, 15, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getItem_No(), 35, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getBarcode(), 85, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getVariant_Code(), 150, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getDescription(), 205, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getArticleNo(), 350, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getQuantity_Issued(), 440, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getUnitPrice(), 490, y7, vanStockReportPaint);
            canvasStockM7.drawText(vanStock.getAmount(), 535, y7, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y7 = y7 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage7);

        //page 8
        PdfDocument.PageInfo vanStockReportPageInfo8 = new PdfDocument.PageInfo.Builder(595, 842, 8).create();
        PdfDocument.Page vanStockReportPage8 = vanStockReportPDF.startPage(vanStockReportPageInfo8);
        Canvas canvasStockM8 = vanStockReportPage8.getCanvas();

        int y8 = 30;

        for (VanStock vanStock : list_8) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM8.drawText("" + record, 15, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getItem_No(), 35, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getBarcode(), 85, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getVariant_Code(), 150, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getDescription(), 205, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getArticleNo(), 350, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getQuantity_Issued(), 440, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getUnitPrice(), 490, y8, vanStockReportPaint);
            canvasStockM8.drawText(vanStock.getAmount(), 535, y8, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y8 = y8 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage8);

        //page 9
        PdfDocument.PageInfo vanStockReportPageInfo9 = new PdfDocument.PageInfo.Builder(595, 842, 9).create();
        PdfDocument.Page vanStockReportPage9 = vanStockReportPDF.startPage(vanStockReportPageInfo9);
        Canvas canvasStockM9 = vanStockReportPage9.getCanvas();

        int y9 = 30;

        for (VanStock vanStock : list_9) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM9.drawText("" + record, 15, y9, vanStockReportPaint);
            canvasStockM9.drawText(vanStock.getItem_No(), 35, y9, vanStockReportPaint);
            canvasStockM9.drawText(vanStock.getBarcode(), 85, y9, vanStockReportPaint);
            canvasStockM9.drawText(vanStock.getVariant_Code(), 150, y9, vanStockReportPaint);
            canvasStockM9.drawText(vanStock.getDescription(), 205, y9, vanStockReportPaint);
           canvasStockM9.drawText(vanStock.getArticleNo(), 350, y9, vanStockReportPaint);
           canvasStockM9.drawText(vanStock.getQuantity_Issued(), 440, y9, vanStockReportPaint);
           canvasStockM9.drawText(vanStock.getUnitPrice(), 490, y9, vanStockReportPaint);
           canvasStockM9.drawText(vanStock.getAmount(), 535, y9, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y9 = y9 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage9);

        //page 10
        PdfDocument.PageInfo vanStockReportPageInfo10 = new PdfDocument.PageInfo.Builder(595, 842, 10).create();
        PdfDocument.Page vanStockReportPage10 = vanStockReportPDF.startPage(vanStockReportPageInfo10);
        Canvas canvasStockM10 = vanStockReportPage10.getCanvas();

        int y10 = 30;

        for (VanStock vanStock : list_10) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM10.drawText("" + record, 15, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getItem_No(), 35, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getBarcode(), 85, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getVariant_Code(), 150, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getDescription(), 205, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getArticleNo(), 350, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getQuantity_Issued(), 440, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getUnitPrice(), 490, y10, vanStockReportPaint);
            canvasStockM10.drawText(vanStock.getAmount(), 535, y10, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y10 = y10 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage10);

        //page 11
        PdfDocument.PageInfo vanStockReportPageInfo11 = new PdfDocument.PageInfo.Builder(595, 842, 11).create();
        PdfDocument.Page vanStockReportPage11 = vanStockReportPDF.startPage(vanStockReportPageInfo11);
        Canvas canvasStockM11 = vanStockReportPage11.getCanvas();

        int y11 = 30;

        for (VanStock vanStock : list_11) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM11.drawText("" + record, 15, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getItem_No(), 35, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getBarcode(), 85, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getVariant_Code(), 150, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getDescription(), 205, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getArticleNo(), 350, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getQuantity_Issued(), 440, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getUnitPrice(), 490, y11, vanStockReportPaint);
            canvasStockM11.drawText(vanStock.getAmount(), 535, y11, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y11 = y11 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage11);

        //page 12
        PdfDocument.PageInfo vanStockReportPageInfo12 = new PdfDocument.PageInfo.Builder(595, 842, 12).create();
        PdfDocument.Page vanStockReportPage12 = vanStockReportPDF.startPage(vanStockReportPageInfo12);
        Canvas canvasStockM12 = vanStockReportPage12.getCanvas();

        int y12 = 30;

        for (VanStock vanStock : list_12) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
           canvasStockM12.drawText("" + record, 15, y12, vanStockReportPaint);
           canvasStockM12.drawText(vanStock.getItem_No(), 35, y12, vanStockReportPaint);
           canvasStockM12.drawText(vanStock.getBarcode(), 85, y12, vanStockReportPaint);
           canvasStockM12.drawText(vanStock.getVariant_Code(), 150, y12, vanStockReportPaint);
           canvasStockM12.drawText(vanStock.getDescription(), 205, y12, vanStockReportPaint);
            canvasStockM12.drawText(vanStock.getArticleNo(), 350, y12, vanStockReportPaint);
            canvasStockM12.drawText(vanStock.getQuantity_Issued(), 440, y12, vanStockReportPaint);
            canvasStockM12.drawText(vanStock.getUnitPrice(), 490, y12, vanStockReportPaint);
            canvasStockM12.drawText(vanStock.getAmount(), 535, y12, vanStockReportPaint);

            double amt = Double.parseDouble(vanStock.getAmount());
            double qty = Double.parseDouble(vanStock.getQuantity_Issued());

            totAmt = totAmt + amt ;
            totQty = totQty + qty;

            record++;
            y12 = y12 + 20;

        }

        vanStockReportPDF.finishPage(vanStockReportPage12);


        // page 13

        PdfDocument.PageInfo vanStockReportPageInfo13 = new PdfDocument.PageInfo.Builder(595, 842, 13).create();
        PdfDocument.Page vanStockReportPage13 = vanStockReportPDF.startPage(vanStockReportPageInfo13);
        Canvas canvasStockM13 = vanStockReportPage13.getCanvas();

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM13.drawText("Summary", 20, 20, vanStockReportPaint);
        canvasStockM13.drawLine(20, 22,80 , 22, vanStockReportPaint);

        // Add data to table
       ArrayList<VanStock> qtyList = new VanStockController(context).getQtyByArticleNo(locCode);

        qtylist_1 = new ArrayList<VanStock>(qtyList.subList(0, 38));
        qtylist_2 = new ArrayList<VanStock>(qtyList.subList(39, 79));
        qtylist_3 = new ArrayList<VanStock>(qtyList.subList(80, 120));
        qtylist_4 = new ArrayList<VanStock>(qtyList.subList(121, 161));
        qtylist_5 = new ArrayList<VanStock>(qtyList.subList(162, 202));
        qtylist_6 = new ArrayList<VanStock>(qtyList.subList(203, qtyList.size()));

        int m = 50;

        for (VanStock vanStock : qtylist_1) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM13.drawText(vanStock.getDescription(), 20, m, vanStockReportPaint);
            canvasStockM13.drawText(vanStock.getTotQty(), 260, m, vanStockReportPaint);

            m = m + 20;
        }

        vanStockReportPDF.finishPage(vanStockReportPage13);

        // page 14
        PdfDocument.PageInfo vanStockReportPageInfo14 = new PdfDocument.PageInfo.Builder(595, 842, 14).create();
        PdfDocument.Page vanStockReportPage14= vanStockReportPDF.startPage(vanStockReportPageInfo14);
        Canvas canvasStockM14 = vanStockReportPage14.getCanvas();

        int n = 20;

        for (VanStock vanStock : qtylist_2) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM14.drawText(vanStock.getDescription(), 20, n, vanStockReportPaint);
            canvasStockM14.drawText(vanStock.getTotQty(), 260, n, vanStockReportPaint);

            n = n +  20;
        }


        vanStockReportPDF.finishPage(vanStockReportPage14);

        // page 15
        PdfDocument.PageInfo vanStockReportPageInfo15 = new PdfDocument.PageInfo.Builder(595, 842, 15).create();
        PdfDocument.Page vanStockReportPage15= vanStockReportPDF.startPage(vanStockReportPageInfo15);
        Canvas canvasStockM15 = vanStockReportPage15.getCanvas();

        int r = 20;

        for (VanStock vanStock : qtylist_3) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM15.drawText(vanStock.getDescription(), 20, r, vanStockReportPaint);
            canvasStockM15.drawText(vanStock.getTotQty(), 260, r, vanStockReportPaint);

            r = r +  20;
        }

        vanStockReportPDF.finishPage(vanStockReportPage15);

        //  page 16
        PdfDocument.PageInfo vanStockReportPageInfo16 = new PdfDocument.PageInfo.Builder(595, 842, 16).create();
        PdfDocument.Page vanStockReportPage16= vanStockReportPDF.startPage(vanStockReportPageInfo16);
        Canvas canvasStockM16 = vanStockReportPage16.getCanvas();

        int t = 20;

        for (VanStock vanStock : qtylist_4) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM16.drawText(vanStock.getDescription(), 20, t, vanStockReportPaint);
            canvasStockM16.drawText(vanStock.getTotQty(), 260, t, vanStockReportPaint);

           t = t +  20;
        }

        vanStockReportPDF.finishPage(vanStockReportPage16);

        // page 17
        PdfDocument.PageInfo vanStockReportPageInfo17 = new PdfDocument.PageInfo.Builder(595, 842, 17).create();
        PdfDocument.Page vanStockReportPage17= vanStockReportPDF.startPage(vanStockReportPageInfo17);
        Canvas canvasStockM17 = vanStockReportPage17.getCanvas();

        int p = 30;

        for (VanStock vanStock : qtylist_5) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
           canvasStockM17.drawText(vanStock.getDescription(), 20, p, vanStockReportPaint);
           canvasStockM17.drawText(vanStock.getTotQty(), 260, p, vanStockReportPaint);

            p = p +  20;
        }

        vanStockReportPDF.finishPage(vanStockReportPage17);

        // page 18
        PdfDocument.PageInfo vanStockReportPageInfo18 = new PdfDocument.PageInfo.Builder(595, 842, 18).create();
        PdfDocument.Page vanStockReportPage18= vanStockReportPDF.startPage(vanStockReportPageInfo18);
        Canvas canvasStockM18 = vanStockReportPage18.getCanvas();

        int h = 30;

        for (VanStock vanStock : qtylist_6) {

            vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
            vanStockReportPaint.setTextSize(8);
            vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvasStockM18.drawText(vanStock.getDescription(), 20, h, vanStockReportPaint);
            canvasStockM18.drawText(vanStock.getTotQty(), 260, h, vanStockReportPaint);

            h = h +  20;
        }

        // total
        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(8);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText(String.valueOf(totQty), 440, 300, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(8);
        canvasStockM18.drawText(String.valueOf(totAmt), 535, 300, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("------------------------", 40, 390, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("------------------------", 260, 390, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("------------------------", 470, 390, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("Prepared By", 50, 410, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("Checked By", 270, 410, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("Authorized By", 480, 410, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("------------------------", 470, 490, vanStockReportPaint);

        vanStockReportPaint.setTextAlign(Paint.Align.LEFT);
        vanStockReportPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        vanStockReportPaint.setTextSize(14);
        vanStockReportPaint.setColor(Color.BLACK);
        canvasStockM18.drawText("Received By", 485, 510, vanStockReportPaint);


        vanStockReportPDF.finishPage(vanStockReportPage18);
        
        File folder_1 = new File(Environment.getExternalStorageDirectory() + "/" + APP_NAME + "/" + DATE);
        File folder_2 = new File(Environment.getExternalStorageDirectory() + "/" + APP_NAME + "/" + DATE + "/"+ FOLDER_NAME);
        boolean success_1 = true;
        if (!folder_1.exists()) {
            success_1 = folder_1.mkdir();
        }

        boolean success_2 = true;
        if (!folder_2.exists()) {
            success_2 = folder_2.mkdir();
        }

        if (success_2) {
            File sd = Environment.getExternalStorageDirectory();
            String filePath = "//HameediaSFA//" + DATE + "//VAN STOCK REPORTS//"+ "Van Sales Out Report" + "-"+ currentTime + ".pdf"; // From SD directory.
            vanStockfile = new File(sd, filePath);

        } else {
            // Do something else on failure
            Toast.makeText(context, "PDF Creation Failed!", Toast.LENGTH_SHORT).show();
        }

        try {
            vanStockReportPDF.writeTo(new FileOutputStream(vanStockfile));
            Toast.makeText(context, "PDF Creation Success!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        vanStockReportPDF.close();


    }
}
