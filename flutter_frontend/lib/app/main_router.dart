import 'package:flutter/material.dart';
import 'package:auto_route/auto_route.dart';
import 'app_routes.dart';
import 'modules/login/view/login_view.dart';
import 'modules/login/view/manager_view.dart';
import 'modules/tranfer_mail/view/tranfer_mail_view.dart';
part 'main_router.gr.dart';

@MaterialAutoRouter(
  routes: <AutoRoute>[
    MaterialRoute(page: LoginView, path: Routes.login, initial: true),
    MaterialRoute(page: ManagerView, path: Routes.manager),
    MaterialRoute(page: TranferMailView, path: Routes.tranferMail),
  ],
)
class MainRouter extends _$MainRouter {
  MainRouter({
    GlobalKey<NavigatorState>? navigatorKey,
  }) : super(navigatorKey);
}
