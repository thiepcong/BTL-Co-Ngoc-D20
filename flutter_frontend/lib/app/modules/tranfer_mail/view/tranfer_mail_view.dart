import 'package:flutter/material.dart';

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
    return Scaffold(
      appBar: AppBar(
        title: const Text('Gửi Mail Tới Khách hàng'),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Padding(
            padding: EdgeInsets.all(8.0),
            child: Text(
              'Quản lý Khách hàng',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
          ),
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
              children: const [
                TableRow(
                  children: [
                    TableCell(child: Center(child: Text('STT'))),
                    TableCell(child: Center(child: Text('Mã khách hàng'))),
                    TableCell(child: Center(child: Text('Tên khách hàng'))),
                    TableCell(child: Center(child: Text('Địa chỉ'))),
                    TableCell(child: Center(child: Text('Số điện thoại'))),
                    TableCell(child: Center(child: Text('Email'))),
                    TableCell(child: Center(child: Text('Mã số thuế'))),
                    TableCell(child: Center(child: Text('Trạng thái'))),
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
                    TableCell(child: Center(child: Text('123456789'))),
                    TableCell(child: Center(child: Text('Active'))),
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
