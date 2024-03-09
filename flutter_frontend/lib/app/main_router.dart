import 'package:flutter/material.dart';
import 'package:auto_route/auto_route.dart';
import 'app_routes.dart';
import 'modules/config_price/view/config_price_view.dart';
import 'modules/login/view/login_view.dart';
import 'modules/login/view/manager_view.dart';
import 'modules/stat/view/stat_list.dart';
import 'modules/stat/view/stat_view.dart';
import 'modules/tranfer_mail/view/tranfer_mail_view.dart';
part 'main_router.gr.dart';

@MaterialAutoRouter(
  routes: <AutoRoute>[
    MaterialRoute(page: LoginView, path: Routes.login, initial: true),
    MaterialRoute(page: ManagerView, path: Routes.manager),
    MaterialRoute(page: StatView, path: Routes.stat),
    MaterialRoute(page: ConfigPriceView, path: Routes.configPrice),
    MaterialRoute(page: TranferMailView, path: Routes.tranferMail),
    MaterialRoute(page: StatListView, path: Routes.statList),
  ],
)
class MainRouter extends _$MainRouter {
  MainRouter({
    GlobalKey<NavigatorState>? navigatorKey,
  }) : super(navigatorKey);
}
