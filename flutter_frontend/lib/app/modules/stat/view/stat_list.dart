import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/app/main_router.dart';

class StatListView extends StatelessWidget {
  const StatListView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: const Text('Xem báo cáo thống kê'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                context.pushRoute(const StatViewRoute());
              },
              child: const Text(
                  'Báo cáo thống kê số lượng nước sử dụng của từng hộ'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                context.pushRoute(const StatViewRoute());
              },
              child: const Text('Báo cáo thống kê doanh thu theo từng hộ'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                context.pushRoute(const StatViewRoute());
              },
              child: const Text('Báo cáo thống kê số lượng sử dụng dịch vụ'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                context.pushRoute(const StatViewRoute());
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
