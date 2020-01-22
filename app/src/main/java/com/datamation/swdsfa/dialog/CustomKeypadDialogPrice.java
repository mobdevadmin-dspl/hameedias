package com.datamation.swdsfa.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.datamation.swdsfa.R;

import java.util.Locale;

/**
 *Created by Rashmi on 7/8/2018.
 * The custom designed keypad dialog is defined here.
 */
public class CustomKeypadDialogPrice extends Dialog {


//    private Context context;

    private TextView header, value;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btnPointFive;
    private Button btn_point_two_five;
    private boolean showPointFive;
    private boolean pointerValueClicked = false;

    private IOnOkClickListener okClickListener;

//    public CustomKeypadDialog(Context context, IOnOkClickListener okClickListener) {
//        super(context, true, null);
//        this.context = context;
//        showPointFive = true;
//        this.okClickListener = okClickListener;
//    }

    public CustomKeypadDialogPrice(Context context, boolean showPointFive, IOnOkClickListener okClickListener) {
        super(context, true, null);
//        this.context = context;
        this.showPointFive = showPointFive;
        this.okClickListener = okClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.test_constraint);
       setContentView(R.layout.dialog_keypad_responsive_layout);

        header = (TextView) findViewById(R.id.dialog_keypad_header_tv);
        value = (TextView) findViewById(R.id.dialog_keypad_value);

        ImageView backspace = (ImageView) findViewById(R.id.dialog_keypad_img_back);
        ImageButton btnOk = (ImageButton) findViewById(R.id.btn_ok);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cncl);

        btn0 = (Button) findViewById(R.id.btn_0);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);

        btnPointFive = (Button) findViewById(R.id.btn_point_five);
        btn_point_two_five = (Button) findViewById(R.id.btn_point_two_five);

        if (showPointFive) {
            btnPointFive.setVisibility(View.VISIBLE);
            btnPointFive.setEnabled(true);
            btn_point_two_five.setVisibility(View.VISIBLE);
            btn_point_two_five.setEnabled(true);
        }

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (!val.equals("0")) {
                    val += "0";
                }
                value.setText(val);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "1";
                } else {
                    val += "1";
                }
                value.setText(val);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "2";
                } else {
                    val += "2";
                }
                value.setText(val);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "3";
                } else {
                    val += "3";
                }
                value.setText(val);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "4";
                } else {
                    val += "4";
                }
                value.setText(val);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "5";
                } else {
                    val += "5";
                }
                value.setText(val);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "6";
                } else {
                    val += "6";
                }
                value.setText(val);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "7";
                } else {
                    val += "7";
                }
                value.setText(val);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "8";
                } else {
                    val += "8";
                }
                value.setText(val);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if (val.equals("0")) {
                    val = "9";
                } else {
                    val += "9";
                }
                value.setText(val);
            }
        });

        btnPointFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pointerValueClicked) {
                    String val = value.getText().toString();
                    if (val.equals("0")) {
                        val = "0.5";
                    } else {
                        val += ".5";
                    }
                    value.setText(val);

                    pointerValueClicked = true;

                    btnPointFive.setVisibility(View.INVISIBLE);
                    btnPointFive.setEnabled(false);

                    btn_point_two_five.setVisibility(View.INVISIBLE);
                    btn_point_two_five.setEnabled(false);

                    btn0.setEnabled(false);
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    btn5.setEnabled(false);
                    btn6.setEnabled(false);
                    btn7.setEnabled(false);
                    btn8.setEnabled(false);
                    btn9.setEnabled(false);
                }
            }
        });

        btn_point_two_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pointerValueClicked) {
                    String val = value.getText().toString();
                    if (val.equals("0")) {
                        val = ".";
                    } else {
                        val += ".";
                    }
                    value.setText(val);

                    pointerValueClicked = true;

                    btnPointFive.setVisibility(View.INVISIBLE);
                    btnPointFive.setEnabled(false);

                    btn_point_two_five.setVisibility(View.INVISIBLE);
                    btn_point_two_five.setEnabled(false);

//                    btn0.setEnabled(false);
//                    btn1.setEnabled(false);
//                    btn2.setEnabled(false);
//                    btn3.setEnabled(false);
//                    btn4.setEnabled(false);
//                    btn5.setEnabled(false);
//                    btn6.setEnabled(false);
//                    btn7.setEnabled(false);
//                    btn8.setEnabled(false);
//                    btn9.setEnabled(false);
                }
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointerValueClicked) {
                    String val = value.getText().toString();
                    String[] array = val.split("\\.");
                    String newVal = array[0];
                    value.setText(newVal);
                    pointerValueClicked = false;

                    btnPointFive.setVisibility(View.VISIBLE);
                    btnPointFive.setEnabled(true);

                    btn_point_two_five.setVisibility(View.VISIBLE);
                    btn_point_two_five.setEnabled(true);

                    btn0.setEnabled(true);
                    btn1.setEnabled(true);
                    btn2.setEnabled(true);
                    btn3.setEnabled(true);
                    btn4.setEnabled(true);
                    btn5.setEnabled(true);
                    btn6.setEnabled(true);
                    btn7.setEnabled(true);
                    btn8.setEnabled(true);
                    btn9.setEnabled(true);

                } else {
                    String val = value.getText().toString();
                    if (!val.equals("0")) {
                        if (val.length() == 1) {
                            value.setText("0");
                        } else {
                            String newTxt = val.substring(0, val.length() - 1);
                            value.setText(newTxt);
                        }
                    }
                }
            }
        });

        backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                value.setText("0");
                return true;
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClickListener.okClicked(Double.parseDouble(value.getText().toString()));
                dismiss();
//                onOkClicked();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });

    }

    public void setHeader(String title) {
        header.setText(title.toUpperCase(Locale.getDefault()));
    }

//    public double onOkClicked() {
//        dismiss();
//        return 0;
//    }

    public void onCancelClicked() {
        dismiss();
    }

    public void loadValue(double newValue) {
        if (showPointFive) {
            if (newValue == 0) {
                value.setText("0");
            } else {
                String val = String.valueOf(newValue);
                String[] valArray = val.split("\\.");
                if (valArray[1].equalsIgnoreCase("0")) {
                    pointerValueClicked = false;
                    btnPointFive.setVisibility(View.VISIBLE);
                    btnPointFive.setEnabled(true);
                    btn_point_two_five.setVisibility(View.VISIBLE);
                    btn_point_two_five.setEnabled(true);
                    value.setText(valArray[0]);
                } else {
                    pointerValueClicked = true;
                    btnPointFive.setVisibility(View.INVISIBLE);
                    btnPointFive.setEnabled(false);
                    btn_point_two_five.setVisibility(View.INVISIBLE);
                    btn_point_two_five.setEnabled(false);
                    value.setText(val);
                }
            }
        } else {
            String val = String.valueOf((int) newValue);
            value.setText(val);
        }
    }

    public interface IOnOkClickListener {
        void okClicked(double value);
    }

}
