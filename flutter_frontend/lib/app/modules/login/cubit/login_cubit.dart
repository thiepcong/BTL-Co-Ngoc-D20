import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/network/exceptions/bad_request_exception.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../../core/values/secure_key_constant.dart';
import '../repository/login_repository.dart';
import 'login_state.dart';

class LoginCubit extends Cubit<LoginState> {
  LoginCubit(this._loginRepository) : super(const LoginState());

  final LoginRepository _loginRepository;

  void login(String username, String password) async {
    try {
      final res =
          await _loginRepository.login(usename: username, password: password);
      final pre = await SharedPreferences.getInstance();
      pre.setString(SecureKeyConstants.accessToken, res);
    } catch (e) {
      if (e is BadRequestException) {
        return;
      }
      rethrow;
    }
  }
}
