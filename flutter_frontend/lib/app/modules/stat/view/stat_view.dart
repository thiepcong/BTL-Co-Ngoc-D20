import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../core/values/app_colors.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../cubit/stat_cubit.dart';
import 'stat_common_view.dart';
import 'stat_debt_view.dart';
import 'stat_new_view.dart';
import 'stat_revenue_view.dart';

class StatView extends StatefulWidget {
  const StatView({super.key});

  @override
  State<StatView> createState() => _StatViewState();
}

class _StatViewState extends State<StatView> {
  @override
  Widget build(BuildContext context) {
    return Title(
      color: AppColors.colorFFFFFFFF,
      title: 'Xem báo cáo',
      child: BlocProvider(
        create: (_) => StatCubit(),
        child: _buildPage(context),
      ),
    );
  }

  Widget _buildPage(BuildContext context) {
    return const Scaffold(
        appBar: CustomAppBar(label: 'Xem báo cáo'),
        body: DefaultTabController(
          length: 4,
          child: Column(
            children: [
              TabBar(
                tabs: [
                  Tab(text: 'Chung'),
                  Tab(text: 'Doanh Thu'),
                  Tab(text: 'Số Lượng Mới Sử Dụng'),
                  Tab(text: 'Nợ Tiền Dịch Vụ'),
                ],
              ),
              Expanded(
                  child: TabBarView(
                children: [
                  StatCommonView(),
                  StatRevenueView(),
                  StatNewView(),
                  StatDebtView()
                ],
              ))
            ],
          ),
        ));
  }
}
