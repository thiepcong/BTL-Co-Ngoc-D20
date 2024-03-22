import 'package:flutter/material.dart';
import '../../../core/values/app_colors.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';

class TranferMailView extends StatefulWidget {
  const TranferMailView({super.key});

  @override
  State<TranferMailView> createState() => _TranferMailViewState();
}

class _TranferMailViewState extends State<TranferMailView> {
  int _currentPage = 1;
  final int _totalPages = 100;

  @override
  Widget build(BuildContext context) {
    return Title(
        color: AppColors.colorFFFFFFFF,
        title: 'Gửi Email',
        child: _buildPage(context));
  }

  Widget _buildPage(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(label: 'Gửi Mail Tới Khách hàng'),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                Expanded(
                  child: DropdownButtonFormField<String>(
                    items: ['Tỉnh/Thành phố'].map((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    onChanged: (value) {},
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: DropdownButtonFormField<String>(
                    items: ['Quận/Huyện'].map((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    onChanged: (value) {},
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: DropdownButtonFormField<String>(
                    items: ['Phường/Xã'].map((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    onChanged: (value) {},
                  ),
                ),
                const SizedBox(width: 8),
                ElevatedButton(
                  onPressed: () {
                    // Xử lý khi nhấn nút Xem
                  },
                  child: const Text('Xem'),
                ),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                const Expanded(
                  child: TextField(
                    decoration: InputDecoration(
                      hintText: 'Nhập từ khóa tìm kiếm...',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: DropdownButtonFormField<String>(
                    items: ['Lọc danh sách'].map((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    onChanged: (value) {},
                  ),
                ),
              ],
            ),
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
                5: FlexColumnWidth(2),
                6: FlexColumnWidth(2),
                7: FlexColumnWidth(1),
              },
              children: [
                const TableRow(
                  children: [
                    TableCell(child: Center(child: Text('STT'))),
                    TableCell(child: Center(child: Text('Mã khách hàng'))),
                    TableCell(child: Center(child: Text('Tên khách hàng'))),
                    TableCell(child: Center(child: Text('Địa chỉ'))),
                    TableCell(child: Center(child: Text('Số điện thoại'))),
                    TableCell(child: Center(child: Text('Email'))),
                    TableCell(child: Center(child: Text('Mã số thuế'))),
                    TableCell(child: Center(child: Text('Trạng thái'))),
                    TableCell(child: Center(child: Text('Chọn')))
                  ],
                ),
                TableRow(
                  children: [
                    const TableCell(child: Center(child: Text('1'))),
                    const TableCell(child: Center(child: Text('KH001'))),
                    const TableCell(child: Center(child: Text('Nguyễn Văn A'))),
                    const TableCell(
                        child: Center(child: Text('123 Đường ABC'))),
                    const TableCell(child: Center(child: Text('0123456789'))),
                    const TableCell(
                        child: Center(child: Text('example@example.com'))),
                    const TableCell(child: Center(child: Text('123456789'))),
                    const TableCell(child: Center(child: Text('Active'))),
                    TableCell(
                        child: Center(
                            child:
                                Checkbox(value: false, onChanged: (value) {}))),
                  ],
                ),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Row(
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
                TextButton(
                  onPressed: () {
                    // Xử lý khi nhấn nút Nhắc nhở
                  },
                  child: const Text('Nhắc nhở'),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
