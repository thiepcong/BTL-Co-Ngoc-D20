abstract class Routes {
  const Routes._();
  static const login = _Paths.login;

  static const manager = _Paths.manager;

  static const stat = _Paths.stat;

  static const configPrice = _Paths.configPrice;

  static const tranferMail = _Paths.tranferMail;

   static const statList = _Paths.statList;
}

abstract class _Paths {
  static const login = 'login';
  static const manager = 'manager';
  static const stat = 'stat';
  static const configPrice = 'config_price';
  static const tranferMail = 'tranfer_mail';
  static const statList = 'stat_list';
}
