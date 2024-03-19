import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/app/main_router.dart';

class ManagerView extends StatelessWidget {
  const ManagerView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: const Text('Quản lý'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                context.pushRoute(const TranferMailViewRoute());
              },
              child: const Text('Gửi Mail tới khách hàng'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                context.pushRoute(const StatListViewRoute());
              },
              child: const Text('Xem báo cáo'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                context.pushRoute(const ConfigPriceViewRoute());
              },
              child: const Text('Cấu hình'),
            ),
          ],
        ),
      ),
    );
  }
}
