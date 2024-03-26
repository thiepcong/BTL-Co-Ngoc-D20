import 'package:freezed_annotation/freezed_annotation.dart';

import '../../../core/models/login_response.dart';

part 'login_state.freezed.dart';

@freezed
class LoginState with _$LoginState {
  const LoginState._();

  const factory LoginState({
    @Default(false) bool isLoading,
    @Default(false) bool authDone,
    User? user,
    String? message,
    Error? error,
  }) = _LoginState;
}
