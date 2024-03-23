import 'package:flutter/material.dart';
import '../../../core/values/app_colors.dart';
import '../../../core/values/text_styles.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';

class TranferMailView extends StatefulWidget {
  const TranferMailView({super.key});

  @override
  State<TranferMailView> createState() => _TranferMailViewState();
}

class _TranferMailViewState extends State<TranferMailView> {
  @override
  Widget build(BuildContext context) {
    return Title(
        color: AppColors.colorFFFFFFFF,
        title: 'Gửi Email',
        child: _buildPage(context));
  }

  Widget _buildPage(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(
        label: 'Gửi Mail Tới Khách hàng',
        bgColor: AppColors.colorFF940000,
        style: TextStyles.boldWhiteS20,
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
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
                5: FlexColumnWidth(2),
                6: FlexColumnWidth(1),
                7: FlexColumnWidth(1),
              },
              children: [
                const TableRow(
                  children: [
                    TableCell(child: Center(child: Text('STT'))),
                    TableCell(child: Center(child: Text('Tên mẫu mail'))),
                    TableCell(child: Center(child: Text('Chủ đề'))),
                    TableCell(child: Center(child: Text('Nội dung'))),
                    TableCell(child: Center(child: Text('Thời gian tạo'))),
                    TableCell(child: Center(child: Text('Chỉnh sửa lần cuối'))),
                    TableCell(child: Center(child: Text('Xem chi tiết'))),
                    TableCell(child: Center(child: Text(''))),
                  ],
                ),
                TableRowEmailItem(),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class TableRowEmailItem extends TableRow {
  @override
  List<Widget> get children => [
        const TableCell(child: Center(child: Text('1'))),
        const TableCell(child: Center(child: Text('Thông báo tiền điện'))),
        const TableCell(
            child: Center(child: Text('...,.,.,.,.,.,.,.,.,.,.,A'))),
        const TableCell(
            child: Center(child: Text('dsadsdsadsadsdsadsasdasdsads'))),
        const TableCell(child: Center(child: Text('22-3-2022'))),
        const TableCell(child: Center(child: Text('22-3-2022'))),
        const TableCell(
            child: Center(
                child: Text(
          'Xem chi tiết',
          style: TextStyle(decoration: TextDecoration.underline),
        ))),
        TableCell(
          child: Center(
            child: RadioListTile(
              value: 1,
              groupValue: 1,
              onChanged: (e) {},
              selected: true,
            ),
          ),
        ),
      ];
}
