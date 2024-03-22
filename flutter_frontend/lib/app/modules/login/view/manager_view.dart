import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/app/main_router.dart';
import 'package:side_navigation/side_navigation.dart';

import '../../../core/values/app_colors.dart';
import '../../../core/values/text_styles.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../../config_price/view/config_price_view.dart';
import '../../stat/view/stat_view.dart';
import '../../tranfer_mail/view/tranfer_mail_view.dart';

class ManagerView extends StatefulWidget {
  const ManagerView({super.key});

  @override
  State<ManagerView> createState() => _ManagerViewState();
}

class _ManagerViewState extends State<ManagerView> {
  List<Widget> views = const [
    TranferMailView(),
    StatView(),
    ConfigPriceView(),
  ];

  int selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        label: 'Quản lý',
        bgColor: AppColors.colorFF940000,
        style: TextStyles.boldWhiteS20,
      ),
      body: Row(
        children: [
          SideNavigationBar(
            selectedIndex: selectedIndex,
            items: const [
              SideNavigationBarItem(
                icon: Icons.mail,
                label: 'Gửi Mail tới khách hàng',
              ),
              SideNavigationBarItem(
                icon: Icons.bar_chart,
                label: 'Xem báo cáo',
              ),
              SideNavigationBarItem(
                icon: Icons.settings,
                label: 'Cấu hình',
              ),
              SideNavigationBarItem(
                icon: Icons.logout,
                label: 'Đăng xuất',
              ),
            ],
            onTap: (index) {
              if (index == 3) {
                context.router.pushAndPopUntil(
                  const LoginViewRoute(),
                  predicate: (_) => false,
                );
                return;
              }
              setState(() {
                selectedIndex = index;
              });
            },
          ),

          /// Make it take the rest of the available width
          Expanded(
            child: views.elementAt(selectedIndex),
          )
        ],
      ),
    );
  }
}
