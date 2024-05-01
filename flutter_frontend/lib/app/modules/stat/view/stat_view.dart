import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/core/values/show_message_internal.dart';

import '../../../core/values/app_colors.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../cubit/stat_cubit.dart';
import '../cubit/stat_state.dart';
import '../repository/stat_repository.dart';
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
        create: (_) => StatCubit(context.read<StatRepository>()),
        child: _buildPage(context),
      ),
    );
  }

  Widget _buildPage(BuildContext context) {
    return MultiBlocListener(
      listeners: [
        BlocListener<StatCubit, StatState>(
          listenWhen: (previous, current) =>
              previous.message != current.message,
          listener: (context, state) {
            if (state.message != null) {
              ShowMessageInternal.showOverlay(context, state.message!);
            }
          },
        ),
      ],
      child: Scaffold(
          appBar: const CustomAppBar(label: 'Xem báo cáo'),
          body: Stack(
            children: [
              DefaultTabController(
                length: 2,
                child: Column(
                  children: [
                    BlocBuilder<StatCubit, StatState>(
                      builder: (context, state) {
                        return TabBar(
                          onTap: (i) => context.read<StatCubit>().clean(),
                          tabs: const [
                            Tab(text: 'Doanh Thu'),
                            Tab(text: 'Số Lượng Mới Sử Dụng'),
                            // Tab(text: 'Nợ Tiền Dịch Vụ'),
                          ],
                        );
                      },
                    ),
                    const Expanded(
                        child: TabBarView(
                      children: [
                        StatRevenueView(),
                        StatNewView(),
                        // StatDebtView()
                      ],
                    ))
                  ],
                ),
              ),
              BlocSelector<StatCubit, StatState, bool>(
                selector: (state) => state.isLoading,
                builder: (context, isLoading) {
                  return isLoading
                      ? Container(
                          color: AppColors.colorFF000000.withOpacity(0.5),
                          alignment: Alignment.center,
                          child: const CircularProgressIndicator(),
                        )
                      : const SizedBox.shrink();
                },
              )
            ],
          )),
    );
  }
}
