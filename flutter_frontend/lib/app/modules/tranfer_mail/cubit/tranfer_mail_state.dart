import 'package:freezed_annotation/freezed_annotation.dart';

part 'tranfer_mail_state.freezed.dart';

@freezed
class TranferMailState with _$TranferMailState {
  const TranferMailState._();

  const factory TranferMailState({
    String? message,
    Error? error,
  }) = _TranferMailState;
}
