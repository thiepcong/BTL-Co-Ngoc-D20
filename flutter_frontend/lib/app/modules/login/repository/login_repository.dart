import '../api/login_api.dart';

class LoginRepository {
  final LoginApi _api;
  LoginRepository(this._api);

  Future<String> login({
    required String usename,
    required String password,
  }) {
    return _api.login(usename: usename, password: password);
  }
}
