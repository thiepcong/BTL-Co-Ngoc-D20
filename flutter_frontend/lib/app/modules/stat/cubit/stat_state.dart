import 'package:freezed_annotation/freezed_annotation.dart';

part 'stat_state.freezed.dart';

@freezed
class StatState with _$StatState {
  const StatState._();

  const factory StatState({
    String? message,
    Error? error,
  }) = _StatState;
}
