// **************************************************************************
// AutoRouteGenerator
// **************************************************************************

// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// AutoRouteGenerator
// **************************************************************************
//
// ignore_for_file: type=lint

part of 'main_router.dart';

class _$MainRouter extends RootStackRouter {
  _$MainRouter([GlobalKey<NavigatorState>? navigatorKey]) : super(navigatorKey);

  @override
  final Map<String, PageFactory> pagesMap = {
    LoginViewRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const LoginView(),
      );
    },
    ManagerViewRoute.name: (routeData) {
      final args = routeData.argsAs<ManagerViewRouteArgs>(
          orElse: () => const ManagerViewRouteArgs());
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: ManagerView(
          key: args.key,
          user: args.user,
        ),
      );
    },
    TranferMailViewRoute.name: (routeData) {
      final args = routeData.argsAs<TranferMailViewRouteArgs>(
          orElse: () => const TranferMailViewRouteArgs());
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: TranferMailView(
          key: args.key,
          customers: args.customers,
          reportInforRequest: args.reportInforRequest,
        ),
      );
    },
  };

  @override
  List<RouteConfig> get routes => [
        RouteConfig(
          '/#redirect',
          path: '/',
          redirectTo: 'login',
          fullMatch: true,
        ),
        RouteConfig(
          LoginViewRoute.name,
          path: 'login',
        ),
        RouteConfig(
          ManagerViewRoute.name,
          path: 'manager',
        ),
        RouteConfig(
          TranferMailViewRoute.name,
          path: 'tranfer_mail',
        ),
      ];
}

/// generated route for
/// [LoginView]
class LoginViewRoute extends PageRouteInfo<void> {
  const LoginViewRoute()
      : super(
          LoginViewRoute.name,
          path: 'login',
        );

  static const String name = 'LoginViewRoute';
}

/// generated route for
/// [ManagerView]
class ManagerViewRoute extends PageRouteInfo<ManagerViewRouteArgs> {
  ManagerViewRoute({
    Key? key,
    User? user,
  }) : super(
          ManagerViewRoute.name,
          path: 'manager',
          args: ManagerViewRouteArgs(
            key: key,
            user: user,
          ),
        );

  static const String name = 'ManagerViewRoute';
}

class ManagerViewRouteArgs {
  const ManagerViewRouteArgs({
    this.key,
    this.user,
  });

  final Key? key;

  final User? user;

  @override
  String toString() {
    return 'ManagerViewRouteArgs{key: $key, user: $user}';
  }
}

/// generated route for
/// [TranferMailView]
class TranferMailViewRoute extends PageRouteInfo<TranferMailViewRouteArgs> {
  TranferMailViewRoute({
    Key? key,
    List<Customer>? customers,
    ReportInforRequest? reportInforRequest,
  }) : super(
          TranferMailViewRoute.name,
          path: 'tranfer_mail',
          args: TranferMailViewRouteArgs(
            key: key,
            customers: customers,
            reportInforRequest: reportInforRequest,
          ),
        );

  static const String name = 'TranferMailViewRoute';
}

class TranferMailViewRouteArgs {
  const TranferMailViewRouteArgs({
    this.key,
    this.customers,
    this.reportInforRequest,
  });

  final Key? key;

  final List<Customer>? customers;

  final ReportInforRequest? reportInforRequest;

  @override
  String toString() {
    return 'TranferMailViewRouteArgs{key: $key, customers: $customers, reportInforRequest: $reportInforRequest}';
  }
}
