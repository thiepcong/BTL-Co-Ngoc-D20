import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:month_year_picker/month_year_picker.dart';
import 'core/values/app_theme.dart';
import 'main_router.dart';
import 'modules/config_price/api/config_price_api.dart';
import 'modules/config_price/repository/config_price_repository.dart';
import 'modules/list_client/api/list_client_api.dart';
import 'modules/list_client/repository/list_client_repository.dart';
import 'modules/login/api/login_api.dart';
import 'modules/login/repository/login_repository.dart';
import 'modules/stat/api/stat_api.dart';
import 'modules/stat/repository/stat_repository.dart';
import 'modules/tranfer_mail/api/tranfer_mail_api.dart';
import 'modules/tranfer_mail/repository/tranfer_mail_repository.dart';

final navigatorKey = GlobalKey<NavigatorState>();

class RouteApp extends StatefulWidget {
  const RouteApp({super.key});

  @override
  State<RouteApp> createState() => _RouteAppState();
}

class _RouteAppState extends State<RouteApp> {
  @override
  void initState() {
    super.initState();
    _appRouter = MainRouter(
      navigatorKey: navigatorKey,
    );
  }

  late MainRouter _appRouter;
  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider<LoginRepository>(
          create: (context) => LoginRepository(LoginApi()),
        ),
        RepositoryProvider<ConfigPriceRepository>(
          create: (context) => ConfigPriceRepository(ConfigPriceApi()),
        ),
        RepositoryProvider<ListClientRepository>(
          create: (context) => ListClientRepository(ListClientApi()),
        ),
        RepositoryProvider<StatRepository>(
          create: (context) => StatRepository(StatApi()),
        ),
        RepositoryProvider<TranferMailRepository>(
          create: (context) => TranferMailRepository(TranferMailApi()),
        ),
      ],
      child: MaterialApp.router(
        routeInformationParser: _appRouter.defaultRouteParser(),
        routerDelegate: _appRouter.delegate(
          navigatorObservers: () => [
            AutoRouteObserver(),
          ],
        ),
        theme: appTheme,
        localizationsDelegates: const [
          GlobalMaterialLocalizations.delegate,
          MonthYearPickerLocalizations.delegate,
        ],
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}
