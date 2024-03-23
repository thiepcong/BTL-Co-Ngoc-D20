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
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const ManagerView(),
      );
    },
    TranferMailViewRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const TranferMailView(),
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
class ManagerViewRoute extends PageRouteInfo<void> {
  const ManagerViewRoute()
      : super(
          ManagerViewRoute.name,
          path: 'manager',
        );

  static const String name = 'ManagerViewRoute';
}

/// generated route for
/// [TranferMailView]
class TranferMailViewRoute extends PageRouteInfo<void> {
  const TranferMailViewRoute()
      : super(
          TranferMailViewRoute.name,
          path: 'tranfer_mail',
        );

  static const String name = 'TranferMailViewRoute';
}
