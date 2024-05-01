import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:month_year_picker/month_year_picker.dart';

import '../../../core/models/customer.dart';
import '../../../core/values/app_colors.dart';
import '../../../core/values/data.dart';
import '../../../core/values/show_message_internal.dart';
import '../../../core/values/text_styles.dart';
import '../cubit/stat_cubit.dart';
import '../cubit/stat_state.dart';

class StatNewView extends StatefulWidget {
  const StatNewView({super.key});

  @override
  State<StatNewView> createState() => _StatNewViewState();
}

class _StatNewViewState extends State<StatNewView> {
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
                "Báo Cáo Số Lượng Mới Sử Dụng Dịch Vụ",
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
                      cubit.getNewCustomers();
                    },
                    child: const Text('Xem'),
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
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 180),
              child: Table(
                border: TableBorder.all(),
                columnWidths: const {
                  0: FlexColumnWidth(1),
                  1: FlexColumnWidth(1),
                },
                children: [
                  const TableRow(
                    children: [
                      TableCell(
                          child:
                              Center(child: Text('Số lượng hộ mới sử dụng'))),
                      TableCell(
                          child: Center(child: Text('Tỉ lệ hộ mới sử dụng'))),
                    ],
                  ),
                  TableRow(
                    children: [
                      TableCell(
                          child: Center(
                              child: Text(
                                  state.currentNew?.newCustomerNum.toString() ??
                                      ''))),
                      TableCell(
                          child: Center(
                              child: Text(state.currentNew?.percent ?? ''))),
                    ],
                  ),
                ],
              ),
            ),
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Table(
                  border: TableBorder.all(),
                  columnWidths: const {
                    0: FlexColumnWidth(1),
                    1: FlexColumnWidth(1),
                    2: FlexColumnWidth(2),
                    3: FlexColumnWidth(3),
                    4: FlexColumnWidth(2),
                    5: FlexColumnWidth(3.5),
                    7: FlexColumnWidth(1),
                  },
                  children: [
                    const TableRow(
                      children: [
                        TableCell(child: Center(child: Text('STT'))),
                        TableCell(child: Center(child: Text('Mã KH'))),
                        TableCell(child: Center(child: Text('Tên khách hàng'))),
                        TableCell(child: Center(child: Text('Địa chỉ'))),
                        TableCell(child: Center(child: Text('Số điện thoại'))),
                        TableCell(child: Center(child: Text('Email'))),
                        TableCell(child: Center(child: Text('Trạng thái'))),
                      ],
                    ),
                    ...(state.currentItem?.reportDTOList
                            .asMap()
                            .entries
                            .map((e) => TableRowItem(e.value, e.key))
                            .toList()) ??
                        [],
                  ],
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Row(
                children: [
                  Expanded(
                    child: Row(
                      children: [
                        TextButton(
                          onPressed: state.currentPage > 1
                              ? () =>
                                  cubit.setCurrentPage(state.currentPage - 1, 2)
                              : null,
                          child: const Text('Trước'),
                        ),
                        Text(
                            'Trang ${state.currentPage} : ${state.currentItem?.pageDto.totalPages ?? 1}'),
                        TextButton(
                          onPressed: state.currentPage <
                                  (state.currentItem?.pageDto.totalPages ?? 0)
                              ? () =>
                                  cubit.setCurrentPage(state.currentPage + 1, 2)
                              : null,
                          child: const Text('Sau'),
                        ),
                      ],
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      ShowMessageInternal.showOverlay(
                          context, "Xuất báo cáo thành công");
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

  const TableRowItem(this.item, this.index);
  @override
  List<Widget> get children => [
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Center(child: Text((index + 1).toString()))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Center(child: Text(item.customerId.toString()))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Padding(
                padding: const EdgeInsets.only(left: 8),
                child: Text(item.customerName.toString()))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Padding(
                padding: const EdgeInsets.only(left: 8),
                child: Text('${item.district}-${item.ward}'))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Center(child: Text(item.customerPhone.toString()))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Padding(
                padding: const EdgeInsets.only(left: 8),
                child: Text(item.customerEmail.toString()))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Center(child: Text(getByType(item.status.toString())))),
        // TableCell(
      //     child:
        //         Center(child: Checkbox(value: false, onChanged: (value) {}))),
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
