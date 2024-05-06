import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/app/main_router.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:side_navigation/side_navigation.dart';

import '../../../core/models/login_response.dart';
import '../../../core/values/app_colors.dart';
import '../../../core/values/text_styles.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../../config_price/view/config_price_view.dart';
import '../../list_client/view/list_client_view.dart';
import '../../stat/view/stat_view.dart';

class ManagerView extends StatefulWidget {
  const ManagerView({super.key, this.user});

  final User? user;

  @override
  State<ManagerView> createState() => _ManagerViewState();
}

class _ManagerViewState extends State<ManagerView> {
  List<Widget> views = const [
    ListClientView(),
    StatView(),
    ConfigPriceView(),
  ];

  int selectedIndex = 0;

  @override
  void initState() {
    init();
    super.initState();
  }

  String name = "";

  void init() async {
    final pre = await SharedPreferences.getInstance();
    setState(() {
      name = pre.getString("userName") ?? '';
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        label: 'Hệ thống tính tiền nước cho hộ cá nhân',
        bgColor: AppColors.colorFF940000,
        actions: [
          Text(
            'Quản lý: ${widget.user?.name ?? name}',
            style: TextStyles.regularWhiteS18,
          ),
          const SizedBox(width: 12),
        ],
        style: TextStyles.boldWhiteS20,
      ),
      body: Row(
        children: [
          SideNavigationBar(
            selectedIndex: selectedIndex,
            items: const [
              SideNavigationBarItem(
                icon: Icons.accessibility,
                label: 'Danh sách khách hàng',
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
