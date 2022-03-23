package com.datamation.hmdsfa.view.dashboard;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.DashboardController;
import com.datamation.hmdsfa.controller.FItenrDetController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainDashboardFragment extends Fragment {

    private static final String LOG_TAG = MainDashboardFragment.class.getSimpleName();

    private HorizontalBarChart cumulativeLineChart;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.getDefault());

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

    private ArrayList<Double> targetValues;
    private List<Double> achievementValues;

    BarChart chart ;

//    private IOnDashboardFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainDashboardFragment.
     */
    public static MainDashboardFragment newInstance() {
//        MainDashboardFragment fragment = new MainDashboardFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return new MainDashboardFragment();
    }

    public MainDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_dashboard, container, false);

        cumulativeLineChart = (HorizontalBarChart) rootView.findViewById(R.id.dashboard_hBarChart);
        targetValues = new ArrayList<Double>();
        cumulativeLineChart.setDescription("");
        cumulativeLineChart.setDrawGridBackground(false);
        cumulativeLineChart.setPinchZoom(true);

        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setGroupingUsed(true);

        chart = rootView.findViewById(R.id.barChart);
        ArrayList monthTvA = new ArrayList();

        chart.setDescription("");
        double dailyAchieve = new DashboardController(getActivity()).getDailyAchievement();

        double monthlyAchieve = new DashboardController(getActivity()).getMonthAchievement();
        double monthlyTarget = new DashboardController(getActivity()).getRepTarget();
        double monthlyBalance = monthlyTarget - monthlyAchieve;

        if(monthlyBalance<0){
            monthlyBalance = 0;
        }

        double dailyTarget = new DashboardController(getActivity()).getRepTarget()/30;
        //chart.set
        monthTvA.add(new BarEntry((float)monthlyTarget, 0));
        monthTvA.add(new BarEntry((float)monthlyAchieve, 1));
        monthTvA.add(new BarEntry((float)monthlyBalance, 2));


        Log.d("******TRG", "onCreateView: "+ monthlyBalance +" = "+ monthlyTarget +" - "+ monthlyAchieve);


        ArrayList titl = new ArrayList();
        titl.add("Target");
        titl.add("Achievement");
        titl.add("Balance");

        BarDataSet bardataset = new BarDataSet(monthTvA, "values");
        //  bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        bardataset.setColors(new int[]{ContextCompat.getColor(getActivity(), R.color.red_error),
                ContextCompat.getColor(getActivity(), R.color.achievecolor),
                ContextCompat.getColor(getActivity(), R.color.theme_color_dark)});
        chart.animateY(2000);
        chart.setDrawGridBackground(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        // chart.xAxis.isEnabled = false;
        //chart.getXAxis().setEnabled(false);

        //chart.getAxisLeft().setDrawAxisLine(false);
        BarData data = new BarData(titl, bardataset);
        bardataset.setBarSpacePercent(35f);
        chart.setData(data);

        // horizontal barchart
        BarData data1 = new BarData(getXAxisValues(),getDataSet());
        cumulativeLineChart.setData(data1);
        cumulativeLineChart.animateXY(2000, 2000);
        cumulativeLineChart.setDrawGridBackground(false);
        cumulativeLineChart.setDrawBorders(false);
        cumulativeLineChart.setDrawValueAboveBar(false);
        cumulativeLineChart.getAxisLeft().setDrawGridLines(false);
        cumulativeLineChart.getAxisLeft().setEnabled(false);
        cumulativeLineChart.getAxisRight().setEnabled(false);
        cumulativeLineChart.invalidate();

        // pie chart
        PieChart pieChart = rootView.findViewById(R.id.piechart);
        ArrayList pieChartValues = new ArrayList();

        pieChart.setDescription("");

        pieChartValues.add(new Entry((float)dailyTarget, 0));
        pieChartValues.add(new Entry((float)dailyAchieve, 1));


        PieDataSet dataSet = new PieDataSet(pieChartValues, "(-values-)");

        ArrayList title = new ArrayList();

        title.add("Target");
        title.add("Achievement");


        PieData dataPie = new PieData(title, dataSet);
        pieChart.setData(dataPie);
        // dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setColors(new int[]{ContextCompat.getColor(getActivity(), R.color.day_achieve),
                ContextCompat.getColor(getActivity(),R.color.achievecolor )});
        //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(3000, 3000);
        return rootView;
    }

    private BarDataSet getDataSet() {
        int nonprd = new DashboardController(getActivity()).getNonPrdCount();
        int ordcount = new DashboardController(getActivity()).getProductiveCount();

        String route = "";
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        String curdate = curYear+"-"+ String.format("%02d", curMonth) + "-" + String.format("%02d", curDate);
        if(!new FItenrDetController(getActivity()).getRouteFromItenary(curdate).equals("")) {
            route = new DashboardController(getActivity()).getRoute();
        }else{
            route = new RouteDetController(getActivity()).getRouteCodeByDebCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());
        }

        int outlets = new DashboardController(getActivity()).getOutletCount(route);
        int notVisit = outlets - (ordcount+nonprd);
        if(notVisit > 0){
            notVisit = outlets - (ordcount+nonprd);
        }else{
            notVisit = 0;
        }

        ArrayList<BarEntry> entries = new ArrayList();
        entries.add(new BarEntry((float)ordcount, 0));
        entries.add(new BarEntry((float)notVisit, 1));
        entries.add(new BarEntry((float)nonprd, 2));


        BarDataSet dataset = new BarDataSet(entries,"count");
        dataset.setColors(new int[]{ContextCompat.getColor(getActivity(), R.color.main_green_stroke_color),
                ContextCompat.getColor(getActivity(), R.color.theme_color_dark),
                ContextCompat.getColor(getActivity(), R.color.visit_not_visited)});
        //  dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        return dataset;
    }

    private ArrayList<String> getXAxisValues() {
        int nonprd = new DashboardController(getActivity()).getNonPrdCount();
        int ordcount = new DashboardController(getActivity()).getProductiveCount();

        String route = "";
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        String curdate = curYear+"-"+ String.format("%02d", curMonth) + "-" + String.format("%02d", curDate);
        if(!new FItenrDetController(getActivity()).getRouteFromItenary(curdate).equals("")) {
            route = new DashboardController(getActivity()).getRoute();
        }else{
            route = new RouteDetController(getActivity()).getRouteCodeByDebCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());
        }

        int outlets = new DashboardController(getActivity()).getOutletCount(route);
        int notVisit = outlets - (ordcount+nonprd);
        if(notVisit > 0){
            notVisit = outlets - (ordcount+nonprd);
        }else{
            notVisit = 0;
        }
        ArrayList<String> labels = new ArrayList();
        labels.add("visit("+ordcount+")");
        labels.add("not visit("+notVisit+")");
        labels.add("nonproductive("+nonprd+")");
        return labels;
    }


}

