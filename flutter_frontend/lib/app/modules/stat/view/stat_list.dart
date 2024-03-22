import 'package:flutter/material.dart';


class StatListView extends StatelessWidget {
  const StatListView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Xem báo cáo thống kê'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
              },
              child: const Text(
                  'Báo cáo thống kê số lượng nước sử dụng của từng hộ'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
              },
              child: const Text('Báo cáo thống kê doanh thu theo từng hộ'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
              },
              child: const Text('Báo cáo thống kê số lượng sử dụng dịch vụ'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
              },
              child: const Text(
                  'Báo cáo thống kê số lượng hộ còn nợ tiền dịch vụ'),
            ),
          ],
        ),
      ),
    );
  }
}
