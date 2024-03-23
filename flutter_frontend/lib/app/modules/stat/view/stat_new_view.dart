import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:month_year_picker/month_year_picker.dart';

import '../../../core/values/app_colors.dart';
import '../../../core/values/data.dart';
import '../../../core/values/text_styles.dart';
import '../../../main_router.dart';
import '../cubit/stat_cubit.dart';
import '../cubit/stat_state.dart';

class StatNewView extends StatefulWidget {
  const StatNewView({super.key});

  @override
  State<StatNewView> createState() => _StatNewViewState();
}

class _StatNewViewState extends State<StatNewView> {
  int _currentPage = 1;
  final int _totalPages = 100;

  DateTime _month = DateTime.now();

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showMonthYearPicker(
        context: context,
        initialDate: DateTime.now(),
        firstDate: DateTime(2000),
        lastDate: DateTime.now(),
        locale: const Locale('vi'));
    if (picked != null) {
      setState(() {
        _month = picked;
      });
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
                                  value: state.currentWard,
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
                      // Xử lý khi nhấn nút Xem
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
                      onPressed: () => _selectDate(context),
                      icon: const Icon(Icons.calendar_today),
                    ),
                  ),
                  controller: TextEditingController(
                      text: _month.toString().substring(0, 7)),
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
                children: const [
                  TableRow(
                    children: [
                      TableCell(
                          child: Center(child: Text('Số lượng hộ sử dụng'))),
                      TableCell(child: Center(child: Text('Tỉ lệ hộ sử dụng'))),
                    ],
                  ),
                  TableRow(
                    children: [
                      TableCell(child: Center(child: Text('1000'))),
                      TableCell(child: Center(child: Text('3%'))),
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
                    1: FlexColumnWidth(2),
                    2: FlexColumnWidth(2),
                    3: FlexColumnWidth(3),
                    4: FlexColumnWidth(2),
                    5: FlexColumnWidth(2),
                    6: FlexColumnWidth(2),
                  },
                  children: const [
                    TableRow(
                      children: [
                        TableCell(child: Center(child: Text('STT'))),
                        TableCell(child: Center(child: Text('Mã khách hàng'))),
                        TableCell(child: Center(child: Text('Khách hàng'))),
                        TableCell(child: Center(child: Text('Địa chỉ'))),
                        TableCell(child: Center(child: Text('Số điện thoại'))),
                        TableCell(child: Center(child: Text('Email'))),
                        TableCell(child: Center(child: Text('Mã số thuế'))),
                      ],
                    ),
                    TableRow(
                      children: [
                        TableCell(child: Center(child: Text('1'))),
                        TableCell(child: Center(child: Text('KH001'))),
                        TableCell(child: Center(child: Text('Nguyễn Văn A'))),
                        TableCell(child: Center(child: Text('123 Đường ABC'))),
                        TableCell(child: Center(child: Text('0123456789'))),
                        TableCell(
                            child: Center(child: Text('example@example.com'))),
                        TableCell(child: Center(child: Text('123456'))),
                      ],
                    ),
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
                          onPressed: _currentPage > 1
                              ? () => setState(() => _currentPage--)
                              : null,
                          child: const Text('Previous'),
                        ),
                        Text('Page $_currentPage of $_totalPages'),
                        TextButton(
                          onPressed: _currentPage < _totalPages
                              ? () => setState(() => _currentPage++)
                              : null,
                          child: const Text('Next'),
                        ),
                      ],
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      context.pushRoute(const TranferMailViewRoute());
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
