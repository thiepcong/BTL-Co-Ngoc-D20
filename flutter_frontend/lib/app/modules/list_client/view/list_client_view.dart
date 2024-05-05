import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/core/values/show_message_internal.dart';
import 'package:flutter_frontend/app/main_router.dart';
import 'package:month_year_picker/month_year_picker.dart';

import '../../../core/models/customer.dart';
import '../../../core/models/report_info_request.dart';
import '../../../core/values/app_colors.dart';
import '../../../core/values/data.dart';
import '../../../core/values/text_styles.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../cubit/list_client_cubit.dart';
import '../cubit/list_client_state.dart';
import '../repository/list_client_repository.dart';

class ListClientView extends StatefulWidget {
  const ListClientView({super.key});

  @override
  State<ListClientView> createState() => _ListClientViewState();
}

class _ListClientViewState extends State<ListClientView> {
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
    return Title(
      color: AppColors.colorFFFFFFFF,
      title: 'Danh sách khách hàng',
      child: BlocProvider(
        create: (_) => ListClientCubit(context.read<ListClientRepository>()),
        child: _buildPage(context),
      ),
    );
  }

  Widget _buildPage(BuildContext context) {
    return MultiBlocListener(
      listeners: [
        BlocListener<ListClientCubit, ListClientState>(
          listenWhen: (previous, current) =>
              previous.message != current.message,
          listener: (context, state) {
            if (state.message != null) {
              ShowMessageInternal.showOverlay(context, state.message ?? '');
            }
          },
        ),
      ],
      child: BlocBuilder<ListClientCubit, ListClientState>(
        builder: (context, state) {
          final cubit = context.read<ListClientCubit>();
          bool checkFilter = (state.currentItem?.reportDTOList ?? [])
              .where((element) =>
                  element.moneyPrice != null && element.moneyPrice != 0)
              .toList()
              .isNotEmpty;
          return Scaffold(
            appBar: const CustomAppBar(label: 'Danh Sách Khách Hàng'),
            body: Stack(
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
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
                                        if (!isWards) {
                                          isWards = wards.isNotEmpty;
                                        }
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
                                          onTap: () =>
                                              setState(() => isWards = false),
                                          decoration: const InputDecoration(
                                            hintText: 'Phường/Xã',
                                            suffixIcon:
                                                Icon(Icons.arrow_drop_down),
                                          ),
                                        )
                                      : DropdownButtonFormField<String>(
                                          value:
                                              state.currentWard?.isNotEmpty ??
                                                      false
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
                              if (state.currentSelectDate == null) {
                                ShowMessageInternal.showOverlay(
                                    context, "Vui lòng chọn tháng");
                                return;
                              }
                              cubit.getListClient();
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
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          SizedBox(
                            width: 320,
                            child: TextField(
                              decoration: const InputDecoration(
                                hintText: 'Nhập từ khóa tìm kiếm...',
                                border: OutlineInputBorder(),
                              ),
                              onChanged: (e) => cubit.setKey(e),
                            ),
                          ),
                          const SizedBox(width: 8),
                          SizedBox(
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
                                      ? state.currentSelectDate
                                          .toString()
                                          .substring(0, 7)
                                      : ''),
                              readOnly: true,
                            ),
                          ),
                          const SizedBox(width: 8),
                          SizedBox(
                            width: 200,
                            child: DropdownButtonFormField<String>(
                              value: state.currentFilter,
                              items: ['Chưa đóng tiền', 'Đã đóng tiền']
                                  .map((String value) {
                                return DropdownMenuItem<String>(
                                  value: value,
                                  child: Text(value),
                                );
                              }).toList(),
                              onChanged: (value) =>
                                  cubit.setCurrentFilter(value),
                              hint: const Text('Lọc danh sách'),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Table(
                          border: TableBorder.all(),
                          columnWidths: {
                            0: const FlexColumnWidth(1),
                            1: const FlexColumnWidth(1),
                            2: const FlexColumnWidth(2),
                            3: const FlexColumnWidth(3),
                            4: const FlexColumnWidth(2),
                            5: const FlexColumnWidth(3.5),
                            6: const FlexColumnWidth(2),
                            if (checkFilter) 7: const FlexColumnWidth(1),
                          },
                          children: [
                            TableRow(
                              children: [
                                const TableCell(
                                    verticalAlignment:
                                        TableCellVerticalAlignment.middle,
                                    child: Center(child: Text('STT'))),
                                const TableCell(
                                    verticalAlignment:
                                        TableCellVerticalAlignment.middle,
                                    child: Center(child: Text('Mã KH'))),
                                const TableCell(
                                    verticalAlignment:
                                        TableCellVerticalAlignment.middle,
                                    child: Center(child: Text('Khách hàng'))),
                                const TableCell(
                                    verticalAlignment:
                                        TableCellVerticalAlignment.middle,
                                    child: Center(child: Text('Địa chỉ'))),
                                const TableCell(
                                    verticalAlignment:
                                        TableCellVerticalAlignment.middle,
                                    child:
                                        Center(child: Text('Số điện thoại'))),
                                const TableCell(
                                    verticalAlignment:
                                        TableCellVerticalAlignment.middle,
                                    child: Center(child: Text('Email'))),
                                const TableCell(
                                    verticalAlignment:
                                        TableCellVerticalAlignment.middle,
                                    child: Center(child: Text('Trạng thái'))),
                                if (checkFilter)
                                  const TableCell(
                                      verticalAlignment:
                                          TableCellVerticalAlignment.middle,
                                      child: Center(child: Text('Tổng tiền'))),
                                // const TableCell(
                                //     child: Center(child: Text('Chọn')))
                              ],
                            ),
                            ...state.currentItem?.reportDTOList
                                    .asMap()
                                    .entries
                                    .map(
                                      (e) => TableRowItem(
                                          e.value,
                                          e.key,
                                          state.customerMails.contains(e.value),
                                          (ee) => cubit.setCurrentTrandMail(
                                              e.value, ee),
                                          checkFilter),
                                    )
                                    .toList() ??
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
                                      ? () => cubit
                                          .setCurrentPage(state.currentPage - 1)
                                      : null,
                                  child: const Text('Trước'),
                                ),
                                Text(
                                    'Trang ${state.currentPage} : ${state.currentItem?.pageDto.totalPages ?? 1}'),
                                TextButton(
                                  onPressed: state.currentPage <
                                          (state.currentItem?.pageDto
                                                  .totalPages ??
                                              0)
                                      ? () => cubit
                                          .setCurrentPage(state.currentPage + 1)
                                      : null,
                                  child: const Text('Sau'),
                                ),
                              ],
                            ),
                          ),
                          (state.currentItem?.reportDTOList ?? []).isNotEmpty
                              ? TextButton(
                                  onPressed: () {
                                    context.pushRoute(TranferMailViewRoute(
                                        reportInforRequest: ReportInforRequest(
                                      district: state.currentDistrict,
                                      month: state.currentSelectDate,
                                      provine: "Hà Nội",
                                      ward: state.currentWard,
                                      search: "",
                                    )));
                                  },
                                  child: const Text(
                                      "Gửi mail cho tất cả khách hàng"),
                                )
                              : const SizedBox.shrink(),
                          // TextButton(
                          //   onPressed: () {
                          //     if (state.customerMails.isEmpty) {
                          //       ShowMessageInternal.showOverlay(context,
                          //           'Vui lòng chọn ít nhất một khách hàng');
                          //       return;
                          //     }
                          //     context.pushRoute(TranferMailViewRoute(
                          //         customers: state.customerMails));
                          //   },
                          //   child: const Text('Nhắc nhở'),
                          // ),
                          if ((state.currentItem?.reportDTOList ?? [])
                              .isNotEmpty)
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
                ),
                state.isLoading
                    ? Container(
                        color: AppColors.colorFF000000.withOpacity(0.5),
                        alignment: Alignment.center,
                        child: const CircularProgressIndicator(),
                      )
                    : const SizedBox.shrink()
              ],
            ),
          );
        },
      ),
    );
  }
}

class TableRowItem extends TableRow {
  final Customer item;
  final int index;
  final bool isChoose;
  final bool isFilter;
  final void Function(bool?)? onChanged;

  const TableRowItem(
      this.item, this.index, this.isChoose, this.onChanged, this.isFilter);
  @override
  List<Widget> get children => [
        TableCell(
          verticalAlignment: TableCellVerticalAlignment.middle,
          child: Center(child: Text((index + 1).toString())),
        ),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Center(child: Text(item.customerId.toString()))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Padding(
              padding: const EdgeInsets.only(left: 8),
              child: Text(item.customerName.toString()),
            )),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Padding(
              padding: const EdgeInsets.only(left: 8),
              child: Text('${item.district}-${item.ward}'),
            )),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Center(child: Text(item.customerPhone.toString()))),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Padding(
              padding: const EdgeInsets.only(left: 8),
              child: Text(item.customerEmail.toString()),
            )),
        TableCell(
            verticalAlignment: TableCellVerticalAlignment.middle,
            child: Center(child: Text(getByType(item.status.toString())))),
        if (isFilter)
          TableCell(
              verticalAlignment: TableCellVerticalAlignment.middle,
              child: Center(child: Text(item.moneyPrice.toString()))),
        // TableCell(
        //     child:
        //         Center(child: Checkbox(value: isChoose, onChanged: onChanged))),
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
