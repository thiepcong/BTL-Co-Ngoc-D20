import 'package:freezed_annotation/freezed_annotation.dart';

import '../../../core/models/price_list.dart';

part 'config_price_state.freezed.dart';

@freezed
class ConfigPriceState with _$ConfigPriceState {
  const ConfigPriceState._();

  const factory ConfigPriceState({
    @Default([]) List<PriceList> all,
    @Default([]) List<PriceList> current,
    String? message,
    Error? error,
  }) = _ConfigPriceState;
}
