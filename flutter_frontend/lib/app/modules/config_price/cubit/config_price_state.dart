import 'package:freezed_annotation/freezed_annotation.dart';

part 'config_price_state.freezed.dart';

@freezed
class ConfigPriceState with _$ConfigPriceState {
  const ConfigPriceState._();

  const factory ConfigPriceState({
    String? message,
    Error? error,
  }) = _ConfigPriceState;
}
