import 'package:freezed_annotation/freezed_annotation.dart';

import '../../../core/models/template_model.dart';

part 'tranfer_mail_state.freezed.dart';

@freezed
class TranferMailState with _$TranferMailState {
  const TranferMailState._();

  const factory TranferMailState({
    @Default([]) List<TemplateModel> templates,
    @Default(0) int selectedIndex,
    @Default(false) bool isLoading,
    @Default(false) bool sendEmailDone,
    String? message,
    Error? error,
  }) = _TranferMailState;
}
