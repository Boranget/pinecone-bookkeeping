package com.blackorangejuice.songguojizhang.transaction.home.overview.in.classify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.List;

public class ClassifiedStatisticActivity extends BasicActivity {

    PieChart pieChartIncome;
    PieChart pieChartExpenditure;
    SongGuoDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;
    TextView backTextView;

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ClassifiedStatisticActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classified_statistic_page);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = SongGuoDatabaseHelper.getSongGuoDatabaseHelper(this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        initPieChart(GlobalConstant.INCOME);
        initPieChart(GlobalConstant.EXPENDITURE);

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_classified_statistic_page_back_textview);
        pieChartIncome = findViewById(R.id.activity_classified_statistic_page_pie_chart_income);
        pieChartExpenditure = findViewById(R.id.activity_classified_statistic_page_pie_chart_expenditure);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassifiedStatisticActivity.this.finish();
            }
        });
    }

    public void initPieChart(String incomeOrExpenditure){
        // 外观
        // 使用的颜色
        final int[] PIE_COLORS = {
                Color.rgb(181, 194, 202), Color.rgb(129, 216, 200), Color.rgb(241, 214, 145),
                Color.rgb(108, 176, 223), Color.rgb(195, 221, 155), Color.rgb(251, 215, 191),
                Color.rgb(237, 189, 189), Color.rgb(172, 217, 243)
        };
        PieChart pieChart = null;
        switch (incomeOrExpenditure){
            case GlobalConstant.INCOME:
                pieChart = pieChartIncome;
                pieChart.setCenterText("收入");
                break;
            case GlobalConstant.EXPENDITURE:
                pieChart = pieChartExpenditure;
                pieChart.setCenterText("支出");
                break;
        }
        pieChart.setUsePercentValues(true);//使用百分比
        pieChart.setExtraOffsets(25, 10, 25, 25); //设置边距
        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
        pieChart.setRotationEnabled(true);//是否可以旋转
        pieChart.setHighlightPerTapEnabled(true);//点击是否放大
        pieChart.setCenterTextSize(20f);//设置环中文字的大小
        pieChart.setDrawCenterText(true);//设置绘制环中文字
        pieChart.setRotationAngle(120f);//设置旋转角度
        pieChart.setTransparentCircleRadius(61f);//设置半透明圆环的半径,看着就有一种立体的感觉
        //这个方法为true就是环形图，为false就是饼图
        pieChart.setDrawHoleEnabled(true);
        //设置环形中间空白颜色是白色
        pieChart.setHoleColor(Color.WHITE);
        //设置半透明圆环的颜色
        pieChart.setTransparentCircleColor(Color.WHITE);
        //设置半透明圆环的透明度
        pieChart.setTransparentCircleAlpha(110);
        // 给饼图设置描述
        Description chartDescription = new Description();
        chartDescription.setText("");
        pieChart.setDescription(chartDescription);
        // 洞的半径
        pieChart.setHoleRadius(70f);
        // 透明圈半径
        pieChart.setTransparentCircleRadius(0f);
        // 是否显示标签
        pieChart.setDrawEntryLabels(false);
        // 设置圆环中心的文字



        // 数据
        // 饼图展示的数据集 使用数据库中查出的数据
        List<PieEntry> entries = accountItemMapper.selectClassifiedPie(incomeOrExpenditure);
        // 将数据集设置到饼图数据源
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(3f);//设置饼块之间的间隔
        pieDataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离
        pieDataSet.setColors(PIE_COLORS);//设置饼块的颜色
        //设置数据显示方式
        pieDataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        pieDataSet.setValueLinePart1Length(0.3f);
        pieDataSet.setValueLinePart2Length(0.4f);
        pieDataSet.setValueLineColor(Color.BLACK);//设置连接线的颜色
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.DKGRAY);
        pieChart.setData(pieData);

        pieChart.invalidate();
    }
}