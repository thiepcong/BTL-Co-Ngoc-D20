import 'package:flutter/material.dart';

class StatView extends StatefulWidget {
  const StatView({super.key});

  @override
  State<StatView> createState() => _StatViewState();
}

class _StatViewState extends State<StatView> {
  int _currentPage = 1;
  final int _totalPages = 100;

  DateTime _startDate = DateTime.now();
  DateTime _endDate = DateTime.now();

  Future<void> _selectDate(BuildContext context, bool isStartDate) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: isStartDate ? _startDate : _endDate,
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );
    if (picked != null) {
      setState(() {
        if (isStartDate) {
          _startDate = picked;
        } else {
          _endDate = picked;
        }
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
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
              children: <Widget>[
                Expanded(
                  child: TextField(
                    decoration: InputDecoration(
                      labelText: 'Ngày bắt đầu',
                      suffixIcon: IconButton(
                        onPressed: () => _selectDate(context, true),
                        icon: const Icon(Icons.calendar_today),
                      ),
                    ),
                    controller: TextEditingController(
                        text: _startDate.toString().substring(0, 10)),
                    readOnly: true,
                  ),
                ),
                const SizedBox(height: 20),
                Expanded(
                  child: TextField(
                    decoration: InputDecoration(
                      labelText: 'Ngày kết thúc',
                      suffixIcon: IconButton(
                        onPressed: () => _selectDate(context, false),
                        icon: const Icon(Icons.calendar_today),
                      ),
                    ),
                    controller: TextEditingController(
                        text: _endDate.toString().substring(0, 10)),
                    readOnly: true,
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
                columnWidths: const {
                  0: FlexColumnWidth(1),
                  1: FlexColumnWidth(2),
                  2: FlexColumnWidth(2),
                  3: FlexColumnWidth(3),
                  4: FlexColumnWidth(2),
                  5: FlexColumnWidth(2),
                  6: FlexColumnWidth(2),
                  7: FlexColumnWidth(1),
                  8: FlexColumnWidth(1)
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
                      TableCell(child: Center(child: Text('Số tiền(VNĐ)'))),
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
                      TableCell(child: Center(child: Text('1.200.000'))),
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
                    // Xử lý khi nhấn nút Nhắc nhở
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
      ),
    );
  }
}
