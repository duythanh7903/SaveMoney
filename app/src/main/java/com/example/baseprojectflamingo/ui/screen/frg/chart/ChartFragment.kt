package com.example.baseprojectflamingo.ui.screen.frg.chart

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.charts.Cartesian
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.formatNumberWithDots
import com.example.baseprojectflamingo.commons.AppConst.CHANEL_ID_NOTI
import com.example.baseprojectflamingo.commons.AppConst.NOTI_ID
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository
import com.example.baseprojectflamingo.databinding.FragmentChartBinding
import com.example.baseprojectflamingo.ui.screen.manage_cost.actual.ManageActualCostsActivity
import com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.ManageEstimateCostsActivity
import com.example.baseprojectflamingo.ui.screen.manage_income.expected.ManageExpectedIncomeActivity
import com.example.baseprojectflamingo.ui.screen.manage_income.real.ManageRealIncomeActivity
import java.util.Calendar


class ChartFragment : BaseFragment<FragmentChartBinding>(R.layout.fragment_chart) {

    private lateinit var viewModel: ChartViewModel

    override fun initData() = Unit

    override fun initView() {
        initViewModel()
        setAnimationText()
        observeData()
        clickViews()
        initChart()
    }

    private fun setAnimationText() = binding.apply {
        textDes1.isSelected = true
        textDes2.isSelected = true
        textDes3.isSelected = true
        textDes4.isSelected = true

        textTitle.isSelected = true
        textTitle2.isSelected = true
        textTitle3.isSelected = true
        textTitle4.isSelected = true
    }

    private fun clickViews() = binding.apply {
        buttonManageRealIncome.click { goToManageScreen(ManageRealIncomeActivity::class.java) }
        buttonManageActualCost.click { goToManageScreen(ManageActualCostsActivity::class.java) }
        buttonManageExpectedIncome.click { goToManageScreen(ManageExpectedIncomeActivity::class.java) }
        buttonManageEstimateCost.click { goToManageScreen(ManageEstimateCostsActivity::class.java) }
    }

    private fun <T> goToManageScreen(activity: Class<T>) =
        startActivity(Intent(requireActivity(), activity))

    private fun initViewModel() {
        val appDatabase = AppDatabase.getInstance(requireActivity())
        val realIncomeDao = appDatabase.recordRealIncomeDao()
        val actualCostDao = appDatabase.recordActualCostDao()
        val expectedIncomeDao = appDatabase.recordExpectedIncomeDao()
        val estCostDao = appDatabase.recordEstimateCostDao()
        val notiDao = appDatabase.notificationDao()
        val realIncomeRepo = RealIncomeRepository(realIncomeDao)
        val actualCostRepo = ActualCostRepository(actualCostDao)
        val expectedIncomeRepo = ExpectedIncomeRepository(expectedIncomeDao)
        val estCostRepo = EstCostRepository(estCostDao)
        val notiRepo = NotificationRepository(notiDao)
        val viewModelFactory = ChartViewModelFactory(
            realIncomeRepo,
            actualCostRepo,
            expectedIncomeRepo,
            estCostRepo,
            notiRepo
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[ChartViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() = viewModel.apply {
        listRecordRealIncomeToday.observe(viewLifecycleOwner) { listRecord ->
            val getStartDateAndEndDate = getStartDateAndEndDateOfToday()
            val startDate = getStartDateAndEndDate.first
            val endDate = getStartDateAndEndDate.second
            val totalIncomeToday =
                listRecord.filter { it.time in startDate until endDate }
                    .sumOf { it.revenue }
            binding.textMoney.text = "+ ${requireActivity().formatNumberWithDots(totalIncomeToday)}"
        }
    }

    private fun getStartDateAndEndDateOfToday(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()

        // Thiết lập thời gian cho ngày hôm nay
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.timeInMillis

        return Pair(startOfDay, endOfDay)
    }

    private fun initChart(
    ) = binding.chartRealIncomeAndActualCosts.apply {
        setProgressBar(binding.progressBar)
        val cartesian: Cartesian = AnyChart.line().apply {
            animation(true)
            padding(10.0, 20.0, 5.0, 20.0)
            crosshair().enabled(true)
            crosshair()
                .yLabel(false)
                .yStroke(null as Stroke?, null, null, null as String?, null as String?)
            tooltip().positionMode(TooltipPositionMode.POINT)
            title(
                activity?.getString(R.string.chart_information)
                    ?: "Comparison chart of income and expenditure (current and expected) for the month."
            )
            yAxis(0).title(activity?.getString(R.string.amount) ?: "Amount")
            xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
            background(false)
        }

        val seriesData: MutableList<DataEntry> = mutableListOf(
            CustomDataEntry("T01", 5834183, 2042356, 5974900, 1689846),
            CustomDataEntry("T02", 2316533, 6524392, 8741162, 9298536),
            CustomDataEntry("T03", 1547249, 9528775, 4610765, 3945742),
            CustomDataEntry("T04", 2234853, 3151634, 6358685, 4156818),
            CustomDataEntry("T05", 5684093, 8215862, 3131024, 6826648),
            CustomDataEntry("T06", 6826234, 2895315, 9592190, 4726723),
            CustomDataEntry("T07", 3181570, 8947544, 5613877, 1155973),
            CustomDataEntry("T08", 8483150, 8654217, 6476135, 4817568),
            CustomDataEntry("T09", 9210702, 2970937, 6542041, 7886643),
            CustomDataEntry("T10", 1846045, 1653134, 5158820, 7264718),
            CustomDataEntry("T11", 1825504, 5934705, 5036620, 7337799),
            CustomDataEntry("T12", 1365514, 8550565, 8702273, 2564786)
        )

        val set = Set.instantiate()
        set.data(seriesData)
        val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series2Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
        val series3Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value3' }")
        val series4Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value4' }")

        val series1 = cartesian.line(series1Mapping)
        series1.name(activity?.getString(R.string.real_income) ?: "Real Icome")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        val series2 = cartesian.line(series2Mapping)
        series2.name(activity?.getString(R.string.actual_costs) ?: "Actual Costs")
        series2.hovered().markers().enabled(true)
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        val series3 = cartesian.line(series3Mapping)
        series3.name(activity?.getString(R.string.expected_income) ?: "Expected Income")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        val series4 = cartesian.line(series4Mapping)
        series4.name(activity?.getString(R.string.estimated_cost) ?: "Estimated Cost")
        series4.hovered().markers().enabled(true)
        series4.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series4.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        setChart(cartesian)
        setZoomEnabled(true)
    }

    private fun sendNotification(context: Context) {
        val notificationId = NOTI_ID
        val channelId = CHANEL_ID_NOTI

        // Tạo thông báo
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_noti)
            .setContentTitle(context.getString(R.string.warning))
            .setContentText(context.getString(R.string.warning_des))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Gửi thông báo
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())

        // save notification to sql
        viewModel.saveNoti(
            NotificationEntity(
                title = context.getString(R.string.warning),
                content = context.getString(R.string.warning_des)
            )
        )
    }
}