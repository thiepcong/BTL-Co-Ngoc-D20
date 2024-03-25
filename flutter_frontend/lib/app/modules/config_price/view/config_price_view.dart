import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/core/widgets/button/primary_button.dart';

import '../../../core/values/app_colors.dart';
import '../../../core/values/text_styles.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../cubit/config_price_cubit.dart';
import '../cubit/config_price_state.dart';
import '../repository/config_price_repository.dart';
import '../widgets/text_field_item.dart';

class ConfigPriceView extends StatefulWidget {
  const ConfigPriceView({super.key});

  @override
  State<ConfigPriceView> createState() => _ConfigPriceViewState();
}

class _ConfigPriceViewState extends State<ConfigPriceView> {
  @override
  Widget build(BuildContext context) {
    return Title(
      color: AppColors.colorFFFFFFFF,
      title: 'Cấu hình',
      child: BlocProvider(
        create: (_) =>
            ConfigPriceCubit(context.read<ConfigPriceRepository>())..init(),
        child: _buildPage(context),
      ),
    );
  }

  Widget _buildPage(BuildContext context) {
    return MultiBlocListener(
      listeners: [
        BlocListener<ConfigPriceCubit, ConfigPriceState>(
          listenWhen: (previous, current) =>
              previous.isValidate != current.isValidate,
          listener: (context, state) {
            if (state.isValidate == true) {
              showDialog(
                context: context,
                builder: (BuildContext context) {
                  return AlertDialog(
                    title: const Text('Thông báo'),
                    content: const Text('Thông tin đã được lưu!'),
                    actions: [
                      TextButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text('OK'),
                      ),
                    ],
                  );
                },
              );
            }
          },
        )
      ],
      child: BlocBuilder<ConfigPriceCubit, ConfigPriceState>(
        builder: (context, state) {
          final curentItem = state.curentItem;
          final lastPriceScale =
              (curentItem?.listPriceScales.isNotEmpty ?? false)
                  ? curentItem!.listPriceScales.last
                  : null;
          final cubit = context.read<ConfigPriceCubit>();
          return Scaffold(
            appBar: const CustomAppBar(label: 'Cấu hình giá nước'),
            body: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 24),
              child: DefaultTabController(
                length: 3,
                child: Column(
                  children: [
                    const TabBar(
                      tabs: [
                        Tab(text: 'Hộ nghèo'),
                        Tab(text: 'Hộ cận nghèo'),
                        Tab(text: 'Hộ cá nhân'),
                      ],
                    ),
                    const Text(
                      "*(Mỗi thang giá được tính từ lớn hơn hoặc bằng chỉ số bắt đầu và nhỏ hơn chỉ số kết thúc)",
                      style: TextStyles.regularBlackS14,
                    ),
                    Expanded(
                      child: TabBarView(
                        children: [
                          SingleChildScrollView(
                            child: Column(
                              children: [
                                const SizedBox(height: 10),
                                ElevatedButton(
                                  onPressed: () => cubit.addPriceScale(),
                                  child: const Text('Thêm bậc'),
                                ),
                                const SizedBox(height: 10),
                                if (state.isValidate == false)
                                  Text(
                                    'Vui lòng kiểm tra lại các trường!',
                                    style: TextStyles.regularBlackS14.copyWith(
                                        color: AppColors.colorFFFF0000),
                                  ),
                                const SizedBox(height: 10),
                                Table(
                                  border: TableBorder.all(),
                                  children: [
                                    const TableRow(children: [
                                      TableCell(child: Text('STT')),
                                      TableCell(child: Text('Chỉ số đầu (>=)')),
                                      TableCell(child: Text('Chỉ số cuối (<)')),
                                      TableCell(child: Text('Đơn giá')),
                                      TableCell(child: Text('')),
                                    ]),
                                    ...curentItem != null
                                        ? curentItem.listPriceScales
                                            .asMap()
                                            .entries
                                            .map((entry) {
                                            final index = entry.key;
                                            final item = entry.value;
                                            return TableRowItem(
                                              index,
                                              item,
                                              onChangeStartIndex: (e) =>
                                                  cubit.updatePriceListByItem(
                                                index,
                                                startIndex: e,
                                              ),
                                              onChangeEndIndex: (e) =>
                                                  cubit.updatePriceListByItem(
                                                index,
                                                endIndex: e,
                                              ),
                                              onChangePrice: (e) =>
                                                  cubit.updatePriceListByItem(
                                                index,
                                                price: e,
                                              ),
                                              onDelete: () => _showDialogDelete(
                                                  () => cubit
                                                      .deletePriceScaleByIndex(
                                                          index)),
                                            );
                                          }).toList()
                                        : [],
                                    TableRow(
                                      children: [
                                        TableCell(
                                          child: CustomTextField(
                                            text: curentItem != null &&
                                                    curentItem.listPriceScales
                                                        .isNotEmpty
                                                ? curentItem.listPriceScales
                                                        .length +
                                                    1
                                                : 1,
                                            enable: false,
                                          ),
                                        ),
                                        TableCell(
                                            child: CustomTextField(
                                          text: lastPriceScale?.endIndex ?? 0,
                                          enable: false,
                                        )),
                                        const TableCell(
                                            child: CustomTextField(
                                          isLast: true,
                                          enable: false,
                                        )),
                                        TableCell(
                                            child: CustomTextField(
                                          text: lastPriceScale?.price ?? 0,
                                        )),
                                        const TableCell(
                                            child: SizedBox.shrink()),
                                      ],
                                    )
                                  ],
                                ),
                                const SizedBox(height: 10),
                                ElevatedButton(
                                  onPressed: () {
                                    cubit.save();
                                  },
                                  child: const Text('Lưu'),
                                ),
                              ],
                            ),
                          ),
                          const Center(child: Text('Hộ cận nghèo')),
                          const Center(child: Text('Hộ cá nhân')),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }

  void _showDialogDelete(VoidCallback? onDelete) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text("Bạn chắc chắn muốn xoá?"),
          actions: [
            PrimaryButton(
              title: 'Quay lại',
              backgroundColor: AppColors.colorFFFFFFFF,
              textColor: AppColors.colorFF000000,
              textSize: 24,
              onTap: () => Navigator.pop(context),
            ),
            PrimaryButton(
              title: 'Xoá',
              backgroundColor: AppColors.colorFFFFFFFF,
              textColor: AppColors.colorFF000000,
              textSize: 24,
              onTap: () {
                Navigator.pop(context);
                onDelete?.call();
              },
            ),
          ],
        );
      },
    );
  }
}
