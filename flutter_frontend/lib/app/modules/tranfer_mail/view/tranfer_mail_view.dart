import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../core/models/customer.dart';
import '../../../core/models/report_info_request.dart';
import '../../../core/values/app_colors.dart';
import '../../../core/values/text_styles.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../../../core/widgets/button/primary_button.dart';
import '../cubit/tranfer_mail_cubit.dart';
import '../cubit/tranfer_mail_state.dart';
import '../repository/tranfer_mail_repository.dart';

class TranferMailView extends StatefulWidget {
  const TranferMailView({
    super.key,
    this.customers,
    this.reportInforRequest,
  });

  final List<Customer>? customers;
  final ReportInforRequest? reportInforRequest;

  @override
  State<TranferMailView> createState() => _TranferMailViewState();
}

class _TranferMailViewState extends State<TranferMailView> {
  @override
  Widget build(BuildContext context) {
    return Title(
      color: AppColors.colorFFFFFFFF,
      title: 'Gửi Email',
      child: BlocProvider(
        create: (_) =>
            TranferMailCubit(context.read<TranferMailRepository>())..init(),
        child: _buildPage(context),
      ),
    );
  }

  Widget _buildPage(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(
        isLeading: true,
        label: 'Gửi Mail Tới Khách hàng',
        bgColor: AppColors.colorFF940000,
        style: TextStyles.boldWhiteS20,
      ),
      body: BlocBuilder<TranferMailCubit, TranferMailState>(
        builder: (context, state) {
          final cubit = context.read<TranferMailCubit>();
          return Stack(
            children: [
              SingleChildScrollView(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    if (widget.customers != null)
                      const Text(
                        "Danh sách khách hàng gửi mail",
                        style: TextStyles.boldBlackS20,
                      ),
                    if (widget.customers != null)
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Table(
                          border: TableBorder.all(),
                          columnWidths: const {
                            0: FlexColumnWidth(1),
                            1: FlexColumnWidth(1),
                            2: FlexColumnWidth(2),
                            3: FlexColumnWidth(2),
                            4: FlexColumnWidth(1),
                          },
                          children: [
                            const TableRow(
                              children: [
                                TableCell(child: Center(child: Text('STT'))),
                                TableCell(
                                    child:
                                        Center(child: Text('Mã khách hàng'))),
                                TableCell(
                                    child:
                                        Center(child: Text('Tên khách hàng'))),
                                TableCell(child: Center(child: Text('Email'))),
                                TableCell(
                                    child: Center(child: Text('Trạng thái'))),
                              ],
                            ),
                            ...widget.customers!
                                .asMap()
                                .entries
                                .map(
                                  (e) => TableRowItem(
                                    e.value,
                                    e.key + 1,
                                  ),
                                )
                                .toList(),
                          ],
                        ),
                      ),
                    const Text(
                      "Danh sách mẫu mail",
                      style: TextStyles.boldBlackS20,
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Table(
                        border: TableBorder.all(),
                        columnWidths: const {
                          0: FlexColumnWidth(1),
                          1: FlexColumnWidth(2),
                          2: FlexColumnWidth(2),
                          3: FlexColumnWidth(3),
                          4: FlexColumnWidth(2),
                          5: FlexColumnWidth(1),
                        },
                        children: [
                          const TableRow(
                            children: [
                              TableCell(child: Center(child: Text('STT'))),
                              TableCell(
                                  child: Center(child: Text('Tên mẫu mail'))),
                              TableCell(child: Center(child: Text('Chủ đề'))),
                              TableCell(child: Center(child: Text('Nội dung'))),
                              TableCell(
                                  child: Center(child: Text('Thời gian tạo'))),
                              TableCell(child: Center(child: Text(''))),
                            ],
                          ),
                          ...state.templates
                              .asMap()
                              .entries
                              .map((e) => TableRowEmailItem(
                                    index: e.key,
                                    name: e.value.templateName,
                                    content: e.value.templateContent,
                                    created: e.value.createdDate,
                                    onChanged: (e) =>
                                        cubit.setSelectedIndex(e ?? 0),
                                    selectedIndex: state.selectedIndex,
                                    subject: e.value.templateSubject,
                                  ))
                              .toList(),
                        ],
                      ),
                    ),
                    Row(
                      children: [
                        Expanded(
                          child: state.res != null
                              ? Row(
                                  children: [
                                    TextButton(
                                      onPressed: state.currentPage > 1
                                          ? () => cubit.setCurrentPage(
                                              state.currentPage - 1)
                                          : null,
                                      child: const Text('Trước'),
                                    ),
                                    Text(
                                        'Trang ${state.currentPage} : ${state.res?.totalPages ?? 1}'),
                                    TextButton(
                                      onPressed: state.currentPage <
                                              (state.res?.totalPages ?? 0)
                                          ? () => cubit.setCurrentPage(
                                              state.currentPage + 1)
                                          : null,
                                      child: const Text('Sau'),
                                    ),
                                  ],
                                )
                              : const SizedBox.shrink(),
                        ),
                        TextButton(
                            onPressed: () =>
                                _showDialogConfirm(() => cubit.sendEmail(
                                      widget.customers,
                                      widget.reportInforRequest,
                                    )),
                            child: const Text("Gửi Email")),
                      ],
                    ),
                  ],
                ),
              ),
              BlocSelector<TranferMailCubit, TranferMailState, bool>(
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
          );
        },
      ),
    );
  }

  void _showDialogConfirm(VoidCallback? onConfirm) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text("Bạn chắc chắn muốn gửi email?"),
          actions: [
            PrimaryButton(
              title: 'Quay lại',
              backgroundColor: AppColors.colorFFFFFFFF,
              textColor: AppColors.colorFF000000,
              textSize: 24,
              onTap: () => Navigator.pop(context),
            ),
            PrimaryButton(
              title: 'Xác nhận',
              backgroundColor: AppColors.colorFFFFFFFF,
              textColor: AppColors.colorFF000000,
              textSize: 24,
              onTap: () {
                Navigator.pop(context);
                onConfirm?.call();
              },
            ),
          ],
        );
      },
    );
  }
}

class TableRowEmailItem extends TableRow {
  final int index;
  final String name;
  final String subject;
  final String content;
  final String created;
  final int selectedIndex;
  final void Function(int?)? onChanged;

  const TableRowEmailItem({
    super.key,
    super.decoration,
    super.children,
    required this.index,
    required this.name,
    required this.subject,
    required this.content,
    required this.created,
    required this.selectedIndex,
    required this.onChanged,
  });
  @override
  List<Widget> get children => [
        TableCell(child: Center(child: Text((index + 1).toString()))),
        TableCell(child: Center(child: Text(name))),
        TableCell(child: Center(child: Text(subject))),
        TableCell(child: Center(child: Text(content))),
        TableCell(child: Center(child: Text(created.substring(0, 10)))),
        // const TableCell(child: Center(child: Text('22-3-2022'))),
        // const TableCell(
        //     child: Center(
        //         child: Text(
        //   'Xem chi tiết',
        //   style: TextStyle(decoration: TextDecoration.underline),
        // ))),
        TableCell(
          child: Center(
            child: RadioListTile(
              value: index,
              groupValue: selectedIndex,
              onChanged: onChanged,
            ),
          ),
        ),
      ];
}

class TableRowItem extends TableRow {
  final Customer item;
  final int index;

  const TableRowItem(this.item, this.index);
  @override
  List<Widget> get children => [
        TableCell(child: Center(child: Text(index.toString()))),
        TableCell(child: Center(child: Text(item.customerId.toString()))),
        TableCell(child: Center(child: Text(item.customerName.toString()))),
        TableCell(child: Center(child: Text(item.customerEmail.toString()))),
        TableCell(
            child: Center(child: Text(getByType(item.status.toString())))),
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
