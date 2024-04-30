import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:month_year_picker/month_year_picker.dart';

import '../../../core/models/customer.dart';
import '../../../core/values/app_colors.dart';
import '../../../core/values/data.dart';
import '../../../core/values/show_message_internal.dart';
import '../../../core/values/text_styles.dart';
import '../../../main_router.dart';
import '../cubit/stat_cubit.dart';
import '../cubit/stat_state.dart';

class StatDebtView extends StatefulWidget {
  const StatDebtView({super.key});

  @override
  State<StatDebtView> createState() => _StatDebtViewState();
}

class _StatDebtViewState extends State<StatDebtView> {
  Future<void> _selectDate(
      BuildContext context, Function(DateTime) onSelectDate) async {
    final DateTime? picked = await showMonthYearPicker(
        context: context,
        initialDate: DateTime.now(),
        firstDate: DateTime(2000),
        lastDate: DateTime.now(),
        locale: const Locale('vi'));
    if (picked != null) {
      onSelectDate.call(picked);
    }
  }

  List<String> districts = dataInfo.entries.map((e) => e.key).toList();
  List<String> wards = [];
  bool isWards = true;

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<StatCubit, StatState>(
      builder: (context, state) {
        final cubit = context.read<StatCubit>();
        return Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const SizedBox(
              width: double.infinity,
              child: Text(
                "Báo Cáo Thống Kê Số Lượng Hộ Còn Nợ Tiền Dịch Vụ",
                textAlign: TextAlign.center,
                style: TextStyles.boldBlackS16,
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text("Tỉnh: Hà Nội"),
                  const SizedBox(width: 24),
                  SizedBox(
                    width: 360,
                    child: Row(
                      children: [
                        Expanded(
                          child: DropdownButtonFormField<String>(
                            value: state.currentDistrict,
                            items: districts.map((String value) {
                              return DropdownMenuItem<String>(
                                value: value,
                                child: Text(value),
                              );
                            }).toList(),
                            onChanged: (value) {
                              if (value != null) {
                                cubit.setCurrentDistrict(value);
                              }
                              setState(() {
                                wards = dataInfo[value] ?? [];
                                if (!isWards) isWards = wards.isNotEmpty;
                              });
                            },
                            hint: const Text("Quận/Huyện"),
                          ),
                        ),
                        const SizedBox(width: 8),
                        Expanded(
                          child: wards.isEmpty
                              ? TextField(
                                  readOnly: true,
                                  onTap: () => setState(() => isWards = false),
                                  decoration: const InputDecoration(
                                    hintText: 'Phường/Xã',
                                    suffixIcon: Icon(Icons.arrow_drop_down),
                                  ),
                                )
                              : DropdownButtonFormField<String>(
                                  value: state.currentWard?.isNotEmpty ?? false
                                      ? state.currentWard
                                      : null,
                                  items: wards.map((String value) {
                                    return DropdownMenuItem<String>(
                                      value: value,
                                      child: Text(value),
                                    );
                                  }).toList(),
                                  onChanged: (value) {
                                    if (value != null) {
                                      cubit.setCurrentWard(value);
                                    }
                                  },
                                  hint: const Text("Phường/Xã"),
                                ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(width: 24),
                  ElevatedButton(
                    onPressed: () {
                      cubit.getDebtCustomer();
                    },
                    child: const Text('Thống kê'),
                  ),
                ],
              ),
            ),
            !isWards
                ? SizedBox(
                    width: double.infinity,
                    child: Text("Vui lòng chọn quận/huyện trước!",
                        textAlign: TextAlign.center,
                        style: TextStyles.regularBlackS14
                            .copyWith(color: AppColors.colorFFFF0000)),
                  )
                : const SizedBox.shrink(),
            Container(
              width: double.infinity,
              alignment: Alignment.center,
              padding: const EdgeInsets.all(8.0),
              child: SizedBox(
                width: 200,
                child: TextField(
                  decoration: InputDecoration(
                    labelText: 'Tháng',
                    suffixIcon: IconButton(
                      onPressed: () => _selectDate(
                        context,
                        (e) => cubit.setCurrentSelectDate(e),
                      ),
                      icon: const Icon(Icons.calendar_today),
                    ),
                  ),
                  controller: TextEditingController(
                      text: state.currentSelectDate != null
                          ? state.currentSelectDate.toString().substring(0, 7)
                          : ''),
                  readOnly: true,
                ),
              ),
            ),
            Expanded(
              child: state.currentDebt != null
                  ? Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Column(
                        children: [
                          Table(
                            border: TableBorder.all(),
                            columnWidths: const {
                              0: FlexColumnWidth(1),
                              1: FlexColumnWidth(1),
                              2: FlexColumnWidth(1),
                            },
                            children: [
                              const TableRow(
                                children: [
                                  TableCell(
                                      child: Center(child: Text('Số Lượng'))),
                                  TableCell(
                                      child: Center(child: Text('Tỉ lệ'))),
                                  TableCell(
                                      child: Center(child: Text('Danh sách'))),
                                ],
                              ),
                              TableRow(
                                children: [
                                  TableCell(
                                      child: Center(
                                          child: Text(state.currentDebt!.debtNum
                                              .toString()))),
                                  TableCell(
                                      child: Center(
                                          child: Text(state
                                                      .currentDebt!.percent ==
                                                  "NaN%"
                                              ? "0%"
                                              : state.currentDebt!.percent))),
                                  TableCell(
                                    child: Center(
                                        child: InkWell(
                                      onTap: () => cubit.getDebtCustomerList(),
                                      child: const Text(
                                        'Xem chi tiết',
                                        style: TextStyle(
                                            decoration:
                                                TextDecoration.underline),
                                      ),
                                    )),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ],
                      ),
                    )
                  : state.currentItem != null
                      ? Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            children: [
                              Table(
                                border: TableBorder.all(),
                                columnWidths: const {
                                  0: FlexColumnWidth(1),
                                  1: FlexColumnWidth(1),
                                  2: FlexColumnWidth(2),
                                  3: FlexColumnWidth(2),
                                  4: FlexColumnWidth(2),
                                  5: FlexColumnWidth(2),
                                  6: FlexColumnWidth(1),
                                  7: FlexColumnWidth(1),
                                  8: FlexColumnWidth(2)
                                },
                                children: [
                                  const TableRow(
                                    children: [
                                      TableCell(
                                          child: Center(child: Text('STT'))),
                                      TableCell(
                                          child: Center(
                                              child: Text('Mã khách hàng'))),
                                      TableCell(
                                          child: Center(
                                              child: Text('Tên khách hàng'))),
                                      TableCell(
                                          child:
                                              Center(child: Text('Địa chỉ'))),
                                      TableCell(
                                          child: Center(
                                              child: Text('Số điện thoại'))),
                                      TableCell(
                                          child: Center(child: Text('Email'))),
                                      TableCell(
                                          child: Center(
                                              child: Text('Số tiền nợ'))),
                                      TableCell(
                                          child: Center(
                                              child: Text('Trạng thái'))),
                                      TableCell(child: Center(child: Text(''))),
                                    ],
                                  ),
                                  ...state.currentItem!.reportDTOList
                                      .asMap()
                                      .entries
                                      .map((e) => TableRowItem(
                                            e.value,
                                            e.key,
                                            state.customerMails
                                                .contains(e.value),
                                            (ee) => cubit.setCurrentTrandMail(
                                                e.value, ee),
                                          ))
                                      .toList(),
                                ],
                              ),
                              Text(
                                  "Tổng tiền nợ: ${state.currentItem?.totalDebtNumber ?? 0} VNĐ")
                            ],
                          ),
                        )
                      : const SizedBox.shrink(),
            ),
            if (state.currentItem != null)
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Row(
                  children: [
                    Expanded(
                      child: Row(
                        children: [
                          TextButton(
                            onPressed: state.currentPage > 1
                                ? () => cubit.setCurrentPage(
                                    state.currentPage - 1, 3)
                                : null,
                            child: const Text('Trước'),
                          ),
                          Text(
                              'Trang ${state.currentPage} : ${state.currentItem?.pageDto.totalPages ?? 1}'),
                          TextButton(
                            onPressed: state.currentPage <
                                    (state.currentItem?.pageDto.totalPages ?? 0)
                                ? () => cubit.setCurrentPage(
                                    state.currentPage + 1, 3)
                                : null,
                            child: const Text('Sau'),
                          ),
                        ],
                      ),
                    ),
                    (state.currentItem?.reportDTOList ?? []).isNotEmpty
                        ? TextButton(
                            onPressed: () {},
                            child: const Text("Gửi mail cho tất cả khách hàng"),
                          )
                        : const SizedBox.shrink(),
                    TextButton(
                      onPressed: () {
                        if (state.customerMails.isEmpty) {
                          ShowMessageInternal.showOverlay(
                              context, 'Vui lòng chọn ít nhất một khách hàng');
                          return;
                        }
                        context.pushRoute(TranferMailViewRoute(
                            customers: state.customerMails));
                      },
                      child: const Text('Nhắc nhở'),
                    ),
                    TextButton(
                      onPressed: () {
                        // Xử lý khi nhấn nút Nhắc nhở
                      },
                      child: const Text('Xuất báo cáo'),
                    ),
                  ],
                ),
              ),
          ],
        );
      },
    );
  }
}

class TableRowItem extends TableRow {
  final Customer item;
  final int index;
  final bool isChoose;
  final void Function(bool?)? onChanged;

  const TableRowItem(this.item, this.index, this.isChoose, this.onChanged);
  @override
  List<Widget> get children => [
        TableCell(child: Center(child: Text(index.toString()))),
        TableCell(child: Center(child: Text(item.customerId.toString()))),
        TableCell(child: Center(child: Text(item.customerName.toString()))),
        TableCell(child: Center(child: Text('${item.district}-${item.ward}'))),
        TableCell(child: Center(child: Text(item.customerPhone.toString()))),
        TableCell(child: Center(child: Text(item.customerEmail.toString()))),
        TableCell(child: Center(child: Text(item.debtMoneyNumber.toString()))),
        TableCell(
            child: Center(child: Text(getByType(item.status.toString())))),
        TableCell(
            child:
                Center(child: Checkbox(value: isChoose, onChanged: onChanged))),
      ];

  String getByType(String type) {
    switch (type) {
      case 'paid':
        return "Đã đóng";
      case 'unpaid':
        return "Chưa đóng";
      default:
        return 'Còn Nợ';
    }
  }
}
